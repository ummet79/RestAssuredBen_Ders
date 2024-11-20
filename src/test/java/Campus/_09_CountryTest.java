package Campus;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


public class _09_CountryTest {
    Faker randomUreteci = new Faker();
    RequestSpecification reqSpec;
    String CountryId;
    String CountryName="";
    String CountryCode="";


    @BeforeClass
    public void Setup()
    {
        baseURI="https://test.mersys.io/";

        Map<String,String> userCredential=new HashMap<>();
        userCredential.put("username","turkeyts");
        userCredential.put("password","TechnoStudy123");
        userCredential.put("rememberMe","true");

        Cookies cookies=
        given()
                .contentType(ContentType.JSON)
                .body(userCredential)

                .when()
                .post("/auth/login")

                .then()
                .log().all()
                .statusCode(200)
                .extract().response().detailedCookies()

                ;
        System.out.println("cookies = " + cookies);
        reqSpec=new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .addCookies(cookies)
                .build();
    }
    @Test
    public void CreateCountry()
    {
        CountryName=randomUreteci.address().country()+ randomUreteci.number().digits(5);
        CountryCode=randomUreteci.address().countryCode()+ randomUreteci.number().digits(5);

        Map<String,String> newCountry=new HashMap<>();

        newCountry.put("name",CountryName);
        newCountry.put("code",CountryCode);

        CountryId=
                given()
                        .spec(reqSpec)
                        .body(newCountry)
                        .when()
                        .post("/school-service/api/countries")    //http ile başlamıyorsa baseURI başına geliyor

                        .then()
                        .log().body()
                        .statusCode(201)
                        .extract().path("id")

        ;
    }
    @Test (dependsOnMethods = "CreateCountry")
    public void CreateCountryNegative()
    {
        Map<String,String> newCountry=new HashMap<>();

        newCountry.put("name",CountryName);
        newCountry.put("code",CountryCode);

        given()
                .spec(reqSpec)
                .body(newCountry)

                .when()
                .post("/school-service/api/countries")

                .then()
                .log().body()
                .statusCode(400)
                .body("message",containsStringIgnoringCase("already"))
        ;
    }
    @Test (dependsOnMethods = "CreateCountryNegative")
    public void UpdateCountry()
    {
        CountryName="UmmetUlkesi"+randomUreteci.number().digits(5);
        CountryCode="491700"+randomUreteci.number().digits(5);

        Map<String,String> UpdateCountry=new HashMap<>();
        UpdateCountry.put("id",CountryId);
        UpdateCountry.put("name",CountryName);
        UpdateCountry.put("code",CountryCode);


        given()
                .spec(reqSpec)
                .body(UpdateCountry)

                .when()
                .put("/school-service/api/countries")

                .then()
                .log().body()
                .statusCode(200)
                .body("name",equalTo(CountryName))
                .body("code",equalTo(CountryCode))
        ;
    }
    @Test (dependsOnMethods = "UpdateCountry")
    public void deleteCountry()
    {
        Map<String,String> DeleteCountry=new HashMap<>();
        DeleteCountry.put("id",CountryId);


        given()
                .spec(reqSpec)
                .body(DeleteCountry)
                .pathParam("CountryId",CountryId)

                .when()
                .delete("/school-service/api/countries/{CountryId}")

                .then()
                .log().body()
                .statusCode(200)
        ;
    }

}
