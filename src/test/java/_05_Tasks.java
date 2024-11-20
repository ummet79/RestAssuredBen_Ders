import Model.ToDo;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

/**
 * Task 1
 * create a request to https://jsonplaceholder.typicode.com/todos/2
 * expect status 200
 * expect content type JSON
 * expect title in response body to be "quis ut nam facilis et officia qui"
 */

public class _05_Tasks {

    @Test
    public void Task1() {

        given()
                .when()
                .get("https://jsonplaceholder.typicode.com/todos/2")

                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("title", equalTo("quis ut nam facilis et officia qui"))
        ;
    }

    @Test
    public void Task2() {
        /**
         * Task 2
         * create a request to https://jsonplaceholder.typicode.com/todos/2
         * expect status 200
         * expect content type JSON
         *a) expect response completed status to be false(hamcrest)
         *b) extract completed field and testNG assertion(testNG)
         */

        // a)
        given()
                .when()
                .get("https://jsonplaceholder.typicode.com/todos/2")

                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("completed", equalTo(false))//hemcrest ile assertion
        ;

        // b)
        boolean completedData =

                given()
                        .when()
                        .get("https://jsonplaceholder.typicode.com/todos/2")

                        .then()
                        .statusCode(200)
                        .contentType(ContentType.JSON)
                        .extract().path("completed");
        //Assert.assertTrue(!completedData);
        Assert.assertFalse(completedData);
    }

    @Test
    public void Task3() {
        /** Task 3
         * create a request to https://jsonplaceholder.typicode.com/todos/2
         * Converting Into POJO body data and write
         */
        ToDo todoNesnesi=
        given()
                .when()
                .get("https://jsonplaceholder.typicode.com/todos/2")

                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .extract().body().as(ToDo.class)
        ;
        System.out.println("todoNesnesi.getTitle() = " + todoNesnesi.getTitle());
        System.out.println("todoNesnesi.isCompleted() = " + todoNesnesi.isCompleted());
        System.out.println("todoNesnesi = " + todoNesnesi);

        Assert.assertTrue(todoNesnesi.getUserId()==1);
        Assert.assertEquals(todoNesnesi.getTitle(),"quis ut nam facilis et officia qui");






    }
}
