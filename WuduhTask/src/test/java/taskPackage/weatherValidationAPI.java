package taskPackage;

import org.testng.annotations.Test;
import java.util.HashMap;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

public class weatherValidationAPI {

    private static String baseURL = "https://api.openweathermap.org";
    @Test
    public void validateStatusCode(){
        given().baseUri(baseURL)
        .queryParam("q", "London")
        .queryParam("appid", "b87eae039d0f11a63a0766484e15cbaa")
        .when().get("/data/2.5/weather")
        .then().statusCode(200);
    }

    @Test
    public void validateResponseTime(){
        given().baseUri(baseURL)
        .queryParam("q", "London")
        .queryParam("appid", "b87eae039d0f11a63a0766484e15cbaa")
        .when().get("/data/2.5/weather")
        .then().time(lessThan(2000L));
    }

    @Test
    public void schemaValidation(){
        given().baseUri(baseURL)
        .queryParam("q", "London")
        .queryParam("appid", "b87eae039d0f11a63a0766484e15cbaa")
        .when().get("/data/2.5/weather")
        .then().assertThat().body(matchesJsonSchemaInClasspath("schema.json"));
    }

    @Test
    public void dataVerification(){
        given().baseUri(baseURL)
        .queryParam("q", "London")
        .queryParam("appid", "b87eae039d0f11a63a0766484e15cbaa")
        .when().get("/data/2.5/weather")
        .then().assertThat()
                .body("name", equalTo("London"))
                .body("cod", equalTo(200))
                .body("coord.lon", equalTo(-0.1257F))
                .body("coord.lat", equalTo(51.5085F))
                .body("sys.country", equalTo("GB"));
    }
}
