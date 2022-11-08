package gefAco.aulas;

import io.restassured.RestAssured;
import io.restassured.filter.Filter;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.*;

public class AulasFilterTest {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://demointranet.preving.com/gef-backend/";
        RestAssured.filters(new RequestLoggingFilter(), new Filter[]{new ResponseLoggingFilter()});
    }

    @Test
    public void getAulas() {

        String response = RestAssured.given().contentType(ContentType.JSON)
                .when().get("classrooms")
                .then().statusCode(200)
                .extract()
                .asString();
    }

    @Test
    public void getHomologaciones() {

        String response = RestAssured.given().contentType(ContentType.JSON)
                .when().get("homologations")
                .then().statusCode(200)
                .extract()
                .asString();
    }

    @Test
    public void getRegiones() {

        String response = RestAssured.given().contentType(ContentType.JSON)
                .when().get("regions")
                .then().statusCode(400)
                .extract()
                .asString();
    }

    @Test
    public void getFiltersAulas() {

        //Response response = RestAssured.given().get("classrooms/filter?name=PS01");
        //String json = response.getBody().asString();
        //System.out.println(json);

        //JsonPath jBody = response.jsonPath();

        //Get value of Location Key
        //String nameAula = jBody.get("name");
        //System.out.println(nameAula);

        //Assert.assertTrue(nameAula.equalsIgnoreCase("'PS01"));


        Response response = RestAssured.
                given()
                //.queryParam("format", "json")
                .contentType(ContentType.JSON).
                //.headers("Authorization", "Bearer " + bearer, new Object[0]).
                when()
                .get("classrooms/filter?name=PS01");
                //then()
                //.statusCode(200)
                //.body("email", Matchers.equalTo("pab159@mailinator.com"), new Object[0])
                //.assertThat().statusCode(is(equalTo(200)));


        MatcherAssert.assertThat(response.getStatusCode(), Matchers.equalTo(200));
        MatcherAssert.assertThat(response.getBody().asString().contains("name"), Matchers.notNullValue());

        String name = (String)JsonPath.from(response.body().asString()).get("name");
        System.out.println("***** Name: " + name);


    }

    @Test
    public void editAula() {

        // {,"region":{"id":8,"createdDate":"2022-07-01T00:00:00","createdBy":0,"code":"MG","description":"Madrid y Guadalajara","provinces":[{"code":"19","name":"GUADALAJARA"},{"code":"28","name":"MADRID"}]},"office":{"id":200438,"name":"CUALTIS-GUADALAJARA","phone":null,"fax":null,"email":null,"address":"Avda. Castilla, 8","postalCode":"19002","location":{"id":1850,"name":"GUADALAJARA","order":null,"province":{"code":"19","name":"GUADALAJARA"}}},"courseTypeIds":[],"homologationIds":[2],"own":false,"active":false,"maxCapacity":6,"realCapacity":5,"area":10,"pictures":[],"location":{"id":null,"address":" ","maps":" ","location":{"id":3310,"name":"CONIL DE LA FRONTERA","order":null,"province":{"code":"11","name":"CADIZ"}}},"type":{"id":1,"createdDate":"2022-07-01T00:00:00","createdBy":0,"code":"FIXED","description":"Fixed, not movable classroom "},"resourceIds":[6,8,9,10],"contact":{"id":383,"user":null,"cellphone":"600000660","email":"ps04@test.es","name":" "},"provider":{"id":383,"pricePerHour":10,"provider":{"id":156,"createdDate":"2022-07-01T00:00:00","createdBy":0,"businessName":"ANGELINA CASTILLO BOLADO","cif":"30560969H","description":null},"agreement":null},"deactivation":{"id":383,"createdDate":null,"createdBy":null,"observations":"Pendiente de pintar"},"observations":"Pendiente de pintar"}

        Map<String, Object> data = new HashMap();
        data.put("id", "383");
        data.put("name", "PS04");
        JSONObject json = new JSONObject(data);

        RestAssured
                .given().contentType(ContentType.JSON).body(json.toString())
                .when().put("classrooms/383")
                .then().statusCode(200);


    }
}
