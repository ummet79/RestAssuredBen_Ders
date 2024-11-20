import Model.Location;
import Model.Place;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;



public class _06_PathAndJsonPath {

    @Test
    public void extractingPath()
    {
        // gelen body de bilgiyi dışarı almanın 2 yöntemini gördük
        // .extract.path("")     ,   as(ToDo.Class)

        String postCode=
                given()
                        .when()
                        .get("http://api.zippopotam.us/us/90210")

                        .then()
                        .log().body()
                        .extract().path("'post code'")
                ;

        System.out.println("postCode = " + postCode);
        int postCodeInt=Integer.parseInt(postCode);
        System.out.println("postCodeInt = " + postCodeInt);
    }

    @Test
    public void extractingJsonPath()
    {
        // gelen body de bilgiyi dışarı almanın 2 yöntemini gördük
        // .extract.path("")     ,   as(ToDo.Class)

        int postCode=
                given()
                        .when()
                        .get("http://api.zippopotam.us/us/90210")

                        .then()
                        //.log().body()
                        .extract().jsonPath().getInt("'post code'")
                ;   //1. avantaj : tip dönüşümü otomatik, uygum tip verilmeli

        System.out.println("postCode = " + postCode);
    }

    @Test
    public void extractingJsonPathIcNesne()
    {
        Response donendata=
                given()
                        .when()
                        .get("http://api.zippopotam.us/us/90210")

                        .then()
                        //.log().body()  tekrar yazdırmama gerek yok
                        .extract().response()
                ;
        Location locationAsPathNesnesi=donendata.as(Location.class);
        // System.out.println("locationAsPathNesnesi = " + locationAsPathNesnesi);
        // bana sadece place dizisi lazım olsa bile, diğer bütün classları yazmak zorundayım

        //eğer içerdeki bir nesne tipli parçayı (burada Places)  almak istedseydim
        List<Place>places= donendata.jsonPath().getList("places", Place.class);
        System.out.println("places = " + places);

        //sadece places dizisi lazım ise diğerlerini yazmak zorunda değilsin

        //daha önceki örneklerde (as) Class dönüşümleri için bütün yapıya karşılık gelen
        //gereken tüm classları yazarak dönüştürüp istedğimiz elemanlara ulaşıyorduk

        //burada ise (jsonPath) aradaki bir veriyi classa dönüştürerek bir list olarak almamıza
        //imkan veren JSONPATH i kullandık. böylece tek class ile veri alınmış oldu
        // diğer classlara gerek kalmadan

        //path: class veya tip dönüşümüne imkan vermeyen direk veriyi verir. List<String> gibi
        //jsonPath: class dönüşümüne ve tip dönüşümüne izin vererek, veriyi istediğimiz formatta verir






    }


}
