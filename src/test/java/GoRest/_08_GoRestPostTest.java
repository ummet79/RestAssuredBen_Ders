package GoRest;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class _08_GoRestPostTest {


    Faker randomUreteci = new Faker();
    RequestSpecification reqSpec;
    int PostId;

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
    public void CreatePost()
    {

        String rndTitle=randomUreteci.lorem().sentence();
        String rndBody=randomUreteci.lorem().paragraph();

        Map<String,String> newPost=new HashMap<>();// postman deki body kısmı map olarak hazırlandı
        newPost.put("user_id","7530336");
        newPost.put("title",rndTitle);
        newPost.put("body",rndBody);

        PostId=

                given()
                        .spec(reqSpec)
                        .body(newPost)
                        .when()
                        .post("posts")    //http ile başlamıyorsa baseURI başına geliyor

                        .then()
                        .log().body()
                        .statusCode(201)
                        .extract().path("id")
        ;
        System.out.println("PostId = " + PostId);
    }

    @Test(dependsOnMethods = "CreatePost")
    public void GetPostById()
    {

        given()
                .spec(reqSpec)
                .when()
                .get("posts/"+PostId)

                .then()
                .log().body()
                .statusCode(200)
                .body("id",equalTo(PostId))
        ;
    }
    @Test(dependsOnMethods = "GetPostById")
    public void UpdatePost()
    {
        String updTitle="Ümmet Özsarı post Title";
        String updBody="güzel bir paragraf cümlesi yazlaım";

        Map<String,String> updPost=new HashMap<>();// postman deki body kısmı map olarak hazırlandı
        updPost.put("title",updTitle);
        updPost.put("body",updBody);


        given()
                .spec(reqSpec)
                .body(updPost)

                .when()
                .put("posts/"+PostId)

                .then()
                .log().body()
                .statusCode(200)
                .body("id",equalTo(PostId))
                .body("title",equalTo(updTitle))
                .body("body",equalTo(updBody))
        ;

    }
    @Test(dependsOnMethods = "UpdatePost")
    public void DeletePost()
    {

        given()
                .spec(reqSpec)

                .when()
                .delete("posts/"+PostId)

                .then()
                .log().body()
                .statusCode(204)
        ;
    }
    @Test(dependsOnMethods = "DeletePost")
    public void deletePostNegative()
    {
        given()
                .spec(reqSpec)

                .when()
                .delete("posts/"+PostId)

                .then()
                .statusCode(404)
        ;
    }



}
