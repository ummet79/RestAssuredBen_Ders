import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class _02_ApiTestSpec {

    RequestSpecification requestSpec;
    ResponseSpecification responseSpec;

    @BeforeClass
    public void Setup(){
        baseURI ="https://gorest.co.in/public/v1";

        requestSpec =new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .log(LogDetail.URI)
                .build();

        responseSpec =new ResponseSpecBuilder()
                .expectStatusCode(200)
                .log(LogDetail.BODY)
                .build();
    }

    @Test
    public void Test1() {

        given()
                .spec(requestSpec)

                .when()
                .get("/users")

                .then()
                .spec(responseSpec)
        ;
    }
    @Test
    public void Test2(){

        given()
                .spec(requestSpec)

                .when()
                .get("/users")  // base URI ekleniyor

                .then()
                .spec(responseSpec)
        ;
    }





}
