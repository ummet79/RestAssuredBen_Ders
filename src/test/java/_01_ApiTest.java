import io.restassured.http.ContentType;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.*;


public class _01_ApiTest {
    @Test
    public void Test1()
    {
        // 1- Endpoint i çağırmadna önce hazırlıkların yapıldığı bölüm : Request, gidecek body
        //                                                           token
        // 2- Endpoint in çağrıldığı bölüm  : Endpoint in çağrılması(METOD: GET,POST ..)
        // 3- Endpoint çağrıldıktan sonraki bölüm : Response, Test(Assert), data

        given().
                //1.blümle ilgili işler : giden body,token
                        when().
                //2.blümle ilgili işler : metod , endpoint
                        then()
        //3.bölümle ilgili işler: gelen data, assert,test
        ;
    }

    @Test
    public void Test2() {
        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                //.log().body()
                .log().all()  // dönen bütün bigiler
                .statusCode(200)  //dönen değer 200 e eşit mi, assert
        ;
    }

    @Test
    public void contentTypeTest() {
        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body()  // dönen data kısmı
                .statusCode(200)  //dönen değer 200 e eşit mi, assert
                .contentType(ContentType.JSON) //dönen datanın cinsi JSON mı
        ;
    }


    @Test
    public void checkCountryInResponceBody() {
        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body()  // dönen data kısmı
                .statusCode(200)  //dönen değer 200 e eşit mi, assert
                .contentType(ContentType.JSON) //dönen datanın cinsi JSON mı
                .body("country", equalTo("United States"))// country yi dışarı almadan
        // bulundu yeri (path i) vererek içerde assertion yapıyorum.Bunu hamcrest kütüphanesi yapıyor

        // pm.test("Ulke Bulunamadı", function () {
        // pm.expect(pm.response.json().message).to.eql("Country not found");
        ;
    }

    @Test
    public void checkCountryInResponceBody2() {
        // Soru : "http://api.zippopotam.us/us/90210"  endpoint indne dönen
        // place dizisinin ilk elemanının state değerinin  "California"
        // olduğunu doğrulayınız


        given()
                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body()
                .statusCode(200)
                .body("places[0].state",equalTo("California"))
        ;

    }

    @Test
    public void checkHasItem() {
            // Soru : "http://api.zippopotam.us/tr/01000"  endpoint in dönen
            // place dizisinin herhangi bir elemanında  "Dörtağaç Köyü" değerinin
            // olduğunu doğrulayınız


        given()
                .when()
                .get("http://api.zippopotam.us/tr/01000")

                .then()
                .statusCode(200)
                .body("places.'place name'",hasItem("Dörtağaç Köyü"))//places
        ;
    }
    @Test
    public void checkHasItemTekrar() {
        // Soru : "http://api.zippopotam.us/tr/01000"  endpoint in dönen
        // place dizisinin herhangi bir elemanında  "Dörtağaç Köyü" değerinin
        // olduğunu doğrulayınız


        given()
                .when()
                .get("http://api.zippopotam.us/tr/01000")

                .then()
                .log().body()
                .body("places.'place name'",hasItem("Dörtağaç Köyü"))
        ;
    }

    @Test
    public void bodyArrayHasSizeTest() {
        // Soru : "http://api.zippopotam.us/us/90210"  endpoint in dönen
        // place dizisinin dizi uzunluğunun 1 olduğunu doğrulayınız.

        given()
                .when()
                .get("http://api.zippopotam.us/us/90210")


                .then()
                .statusCode(200)
                .body("places",hasSize(1)) //places in eleman uzunluğu 1 mi?
        ;
    }

    @Test
    public void combiningTest() {
        // Soru : "http://api.zippopotam.us/us/90210"  endpoint in dönen
        // place dizisinin dizi uzunluğunun 1 olduğunu doğrulayınız.

        given()
                .when()
                .get("http://api.zippopotam.us/us/90210")
                .then()
                .statusCode(200)
                .body("places",hasSize(1)) //places in eleman uzunluğu 1 mi?
                .body("places[0].state",equalTo("California"))
                .body("places.'place name'",hasItem("Beverly Hills"))
        //yukarıda olduğu gibi istenilen kadar test eklenebilir
        ;
    }

    @Test
    public void pathParamTest() {

        given()
                .pathParam("ulke","us")
                .pathParam("postakodu","90210")
                .log().uri() //oluşacak endpoint i yazdıralım

                .when()
                .get("http://api.zippopotam.us/{ulke}/{postakodu}")
                .then()
                .log().body()
        ;
    }

    @Test
    public void queryParamTest() {
        //https://gorest.co.in/public/v1/users?page=3

        given()
                .param("page",3)
                .log().uri()

                .when()
                .get("https://gorest.co.in/public/v1/users")  // URL kısmını buraya yazıldı

                .then()
                .log().body()
        ;
    }
    @Test
    public void soru() {
        // https://gorest.co.in/public/v1/users?page=3
        // bu linkteki 1 den 10 kadar sayfaları çağırdığınızda response daki donen page degerlerinin
        // çağrılan page nosu ile aynı olup olmadığını kontrol ediniz.


        for (int i = 1; i <=10 ; i++) {

            given()
                    .param("page", i)
                    .log().uri()

                    .when()
                    .get("https://gorest.co.in/public/v1/users")  // URL kısmını buraya yazıldı

                    .then()
                    .log().body()
                    .body("meta.pagination.page", equalTo(i))
            ;
        }
    }


    @Test
    public void queryParamTest_Soru() {

            // https://gorest.co.in/public/v1/users?page=3
            // bu linkteki 1 den 10 kadar sayfaları çağırdığınızda response daki donen page degerlerinin
            // çağrılan page nosu ile aynı olup olmadığını kontrol ediniz.

        for (int i = 1; i <=10 ; i++) {
        given()

                .param("page", i)
                    .log().uri()

                    .when()
                    .get("https://gorest.co.in/public/v1/users")

                    .then()
                    .log().body()
                    .body("meta.pagination.page", equalTo(i))
            ;
        }
    }







}





