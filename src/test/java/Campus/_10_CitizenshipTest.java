package Campus;

import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsStringIgnoringCase;
import static org.hamcrest.Matchers.equalTo;

public class _10_CitizenshipTest extends CampusParent

{
    String CitizenName;
    String CitizenShortName="";
    String CitizenShipId="";


    @Test
    public void CreateCitizenship()
    {
        CitizenName=randomUreteci.nation().nationality()+ randomUreteci.number().digits(5);
        CitizenShortName=randomUreteci.address().countryCode()+ randomUreteci.number().digits(5);

        Map<String,String> newCitizenship=new HashMap<>();
        newCitizenship.put("name",CitizenName);
        newCitizenship.put("shortName",CitizenShortName);

        CitizenShipId=
                given()
                        .spec(reqSpec)
                        .body(newCitizenship)

                        .when()
                        .post("/school-service/api/citizenships")

                        .then()
                        .log().body()
                        .statusCode(201)
                        .extract().path("id")
                ;
        System.out.println("CitizenShipId = " + CitizenShipId);
    }

    @Test (dependsOnMethods = "CreateCitizenship")
    public void CreatecitizenshipNegative()
    {

        Map<String,String> newCitizenship=new HashMap<>();
        newCitizenship.put("name",CitizenName);
        newCitizenship.put("shortName",CitizenShortName);

        given()
                .spec(reqSpec)
                .body(newCitizenship)

                .when()
                .post("/school-service/api/citizenships")    //http ile başlamıyorsa baseURI başına geliyor

                .then()
                .log().body()
                .statusCode(400)
                .body("message",containsStringIgnoringCase("already"))
        ;
    }

    @Test (dependsOnMethods = "CreatecitizenshipNegative")
    public void UpdateCitizenShip()
    {
        CitizenName="UmmetUlkesi"+randomUreteci.number().digits(5);

        Map<String,String> updateCitizenship=new HashMap<>();
        updateCitizenship.put("id",CitizenShipId);
        updateCitizenship.put("name",CitizenName);
        updateCitizenship.put("shortName",CitizenShortName);

        given()
                .spec(reqSpec)
                .body(updateCitizenship)

                .when()
                .put("/school-service/api/citizenships")    //http ile başlamıyorsa baseURI başına geliyor

                .then()
                .log().body()
                .statusCode(200)
                .body("name",equalTo(CitizenName))
        ;
    }
    @Test (dependsOnMethods = "UpdateCitizenShip")
    public void deleteCitizenShip()    {

        given()
                .spec(reqSpec)

                .when()
                .delete("/school-service/api/citizenships/"+CitizenShipId)    //http ile başlamıyorsa baseURI başına geliyor

                .then()
                .log().body()
                .statusCode(200)
        ;
    }

    @Test (dependsOnMethods = "deleteCitizenShip")
    public void deleteCitizenShipNegative()
    {

        given()
                .spec(reqSpec)

                .when()
                .delete("/school-service/api/citizenships/"+CitizenShipId)    //http ile başlamıyorsa baseURI başına geliyor

                .then()
                .log().body()
                .statusCode(400)

        ;
    }





}
