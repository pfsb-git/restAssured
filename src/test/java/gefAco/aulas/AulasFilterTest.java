package gefAco.aulas;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.Filter;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

import io.restassured.specification.RequestSpecification;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testng.Assert;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.requestSpecification;
import static org.hamcrest.Matchers.*;

public class AulasFilterTest {
    static String bearerToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJleGV2aV9nZXN0b3IiLCJ1X2lkIjoxMzkwMTYsInVfZW1haWwiOiJkYW5pZWwucm9zYWRvQHNuZ3VsYXIuY29tIiwiY3JlYXRlZCI6MTY2ODAxMzI0OTAyNCwidV9hcGVsbGlkb3MiOiJHZXN0b3IgIiwicm9sZXMiOlsiZ2MtNjMiLCIyOS0yOTAxMDAiLCIxNy00MDYwMCIsIjE3LTQwNDAwIiwiMTctNDAyMDAiLCIzNS0zNTAxMDAiLCJnZWYtNTYwMDEiXSwiZXhwIjoxNjY4MDMxMjQ5LCJ1X25vbWJyZSI6IkV4ZXZpIn0.UnUy5k52Ph7lC551hi2f8LZfwBuGmcxNgLrCmBRc2-D6w6XkA-BlLcN54TnjnjKZDbKp4lh2RzVLok5hHUW3jQ";
    public static void login(){
        String response = given().contentType(ContentType.JSON)
                .when().get("classrooms")
                .then().statusCode(200)
                .extract()
                .asString();

    }

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://demointranet.preving.com/gef-backend/";

        RequestSpecification rsp = new RequestSpecBuilder().
                                        addHeader("Authorization", "Bearer " + bearerToken).
                                        addHeader("Content-Type", ContentType.JSON.toString()).
                                        addHeader("Accept", ContentType.JSON.toString())
                                        .build();
        RestAssured.requestSpecification = rsp;

        //RestAssured.filters(new RequestLoggingFilter(), new Filter[]{new ResponseLoggingFilter()});
    }

    @Test
    public void getAulasWithoutToken() {

        String response = given()
                .headers("Authorization", "Bearer")
                .get("classrooms")
                .then().statusCode(401)
                .extract()
                .asString();
    }

    @Test
    public void getAulas() {

        Response response =
                    given().when()
                        .get("classrooms")
                    .then()
                        .contentType(ContentType.JSON)
                        .extract()
                        .response();

        Assert.assertEquals(response.getStatusCode(), 200, "BUG : Status code is coming as different");
    }

    @Test
    public void getHomologaciones() {

        Response response =
                given().when()
                        .get("homologations")
                    .then()
                        .contentType(ContentType.JSON)
                        .extract()
                        .response();

        Assert.assertEquals(response.getStatusCode(), 200, "BUG : Status code is coming as different");
    }

    @Test
    public void getRegiones() {

        Response response =
                given().when()
                        .get("regions")
                    .then()
                        .contentType(ContentType.JSON)
                        .extract()
                        .response();

        Assert.assertEquals(response.getStatusCode(), 200, "BUG : Status code is coming as different");

    }

    @Test
    public void getFiltersAulas() {

        Response response =
                given().when()
                        .get("classrooms/filter?name=TestLeon")
                    .then()
                        .contentType(ContentType.JSON)
                        .assertThat()
                        .extract()
                        .response();

        Assert.assertEquals(response.getStatusCode(), 200, "BUG : Status code is coming as different");
        //Assert.assertEquals(response.getBody().asString().toLowerCase().contains("TestLeon"), "Bug: No coincide");
        Assert.assertEquals(response.jsonPath().getList("name").get(0).toString(), "TestLeon", "Bug: No coincide");

        //String json = response.getBody().asString();
        //System.out.println(json);

    }

    @Test
    public void editAula() {

        // {,"region":{"id":8,"createdDate":"2022-07-01T00:00:00","createdBy":0,"code":"MG","description":"Madrid y Guadalajara","provinces":[{"code":"19","name":"GUADALAJARA"},{"code":"28","name":"MADRID"}]},"office":{"id":200438,"name":"CUALTIS-GUADALAJARA","phone":null,"fax":null,"email":null,"address":"Avda. Castilla, 8","postalCode":"19002","location":{"id":1850,"name":"GUADALAJARA","order":null,"province":{"code":"19","name":"GUADALAJARA"}}},"courseTypeIds":[],"homologationIds":[2],"own":false,"active":false,"maxCapacity":6,"realCapacity":5,"area":10,"pictures":[],"location":{"id":null,"address":" ","maps":" ","location":{"id":3310,"name":"CONIL DE LA FRONTERA","order":null,"province":{"code":"11","name":"CADIZ"}}},"type":{"id":1,"createdDate":"2022-07-01T00:00:00","createdBy":0,"code":"FIXED","description":"Fixed, not movable classroom "},"resourceIds":[6,8,9,10],"contact":{"id":383,"user":null,"cellphone":"600000660","email":"ps04@test.es","name":" "},"provider":{"id":383,"pricePerHour":10,"provider":{"id":156,"createdDate":"2022-07-01T00:00:00","createdBy":0,"businessName":"ANGELINA CASTILLO BOLADO","cif":"30560969H","description":null},"agreement":null},"deactivation":{"id":383,"createdDate":null,"createdBy":null,"observations":"Pendiente de pintar"},"observations":"Pendiente de pintar"}

        //Map<String, Object> data = new HashMap();
        //data.put("id", "383");
        //data.put("name", "PS04");
        //JSONObject json = new JSONObject(data);

        //given().contentType(ContentType.JSON).body(json.toString())
        //        .when().put("classrooms/383")
        //        .then().statusCode(401);


    }
}
