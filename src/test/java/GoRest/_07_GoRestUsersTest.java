package GoRest;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class _07_GoRestUsersTest {

    Faker randomUreteci = new Faker();
    RequestSpecification reqSpec;
    int userID=0;

    @BeforeClass
    public void SetUp()
    {
        baseURI = "https://gorest.co.in/public/v2/";

        reqSpec = new RequestSpecBuilder()
                .addHeader("Authorization", "Bearer 824f90db7422a20febe08bf9f0bdd76261a3db141dd8c335341973edcb9e1e24")
                .setContentType(ContentType.JSON)
                .build();
    }
    @Test
    public void CreateUser()
    {

        String rndFullName=randomUreteci.name().fullName();
        String rndEMail=randomUreteci.internet().emailAddress();

        Map<String,String> newUser=new HashMap<>();// postman deki body kısmı map olarak hazırlandı
        newUser.put("name",rndFullName);
        newUser.put("gender","male");
        newUser.put("email",rndEMail);
        newUser.put("status","active");

        userID=

        given()
                .spec(reqSpec)
                .body(newUser)
                .when()
                .post("users")    //http ile başlamıyorsa baseURI başına geliyor

                .then()
                .log().body()
                .statusCode(201)
                .extract().path("id")
        ;
        System.out.println("userID = " + userID);
    }

    @Test(dependsOnMethods = "CreateUser")
    public void GetUserById()
    {

                given()
                        .spec(reqSpec)
                        .log().uri()

                        .when()
                        .get("users/"+userID)

                        .then()
                        .log().body()
                        .statusCode(200)
                        .body("id",equalTo(userID))
        ;
    }

    @Test(dependsOnMethods = "GetUserById")
    public void UpdateUser()
    {
        String updName="Ümmet Özsarı";
        //String updEmail="temmu7979@gmail.com";

        Map<String,String> updUser=new HashMap<>();// postman deki body kısmı map olarak hazırlandı
        updUser.put("name",updName);
        //updUser.put("email",updEmail);


        given()
                .spec(reqSpec)
                .body(updUser)

                .when()
                .put("users/"+userID)

                .then()
                .log().body()
                .statusCode(200)
                .body("id",equalTo(userID))
                .body("name",equalTo(updName))
                //.body("email",equalTo(updEmail))
        ;

    }
    @Test(dependsOnMethods = "UpdateUser")
    public void DeleteUser()
    {

        given()
                .spec(reqSpec)
                .when()
                .delete("users/"+userID)

                .then()
                .log().body()
                .statusCode(204)
        ;
    }
    @Test(dependsOnMethods = "DeleteUser")
    public void DeleteUserNegative()
    {
        given()
                .spec(reqSpec)

                .when()
                .delete("users/"+userID)

                .then()
                .statusCode(404)
        ;
    }




}
