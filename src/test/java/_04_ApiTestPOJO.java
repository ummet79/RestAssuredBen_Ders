import Model.Location;
import org.testng.annotations.Test;

import java.util.Locale;

import static io.restassured.RestAssured.*;


public class _04_ApiTestPOJO {

    @Test
    public void exractJsonAll_POJO(){
        Location locationNesnesi=

        given()

                .when()
                .get("https://api.zippopotam.us/us/90210")

                .then()
                .extract().body().as(Location.class) //tüm body i al, Location.class (kalıba göre) çevir
                ;
        System.out.println("locationNesnesi.getCountry() = " + locationNesnesi.getCountry());
        System.out.println("locationNesnesi = " + locationNesnesi);





    }



}
