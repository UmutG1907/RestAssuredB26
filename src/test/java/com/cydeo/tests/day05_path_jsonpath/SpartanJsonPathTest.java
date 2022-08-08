package com.cydeo.tests.day05_path_jsonpath;
import com.cydeo.utilities.ConfigurationReader;
import static io.restassured.RestAssured.*;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import com.cydeo.utilities.SpartanTestBase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

;

public class SpartanJsonPathTest extends SpartanTestBase {

    /**
     * Given accept is json
     * And path param id is 13
     * When I send get request to /api/spartans/{id}
     * ----------
     * Then status code is 200
     * And content type is json
     * And id value is 13
     * And name is "Jaimie"
     * And gender is "Female"
     * And phone is "7842554879"
     */

    @DisplayName("GET /api/spartans/{id} and JsonPath Test")
    @Test
    public void getSpartansJsonPathTest(){

        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id", 13)
                .when().get("/spartans/{id}");

        assertEquals(HttpStatus.SC_OK, response.getStatusCode());
        assertEquals("application/json", response.getContentType());
        assertEquals("application/json", response.getHeader("content-type"));

        //parse/assign response body to jsonPath
        JsonPath jsonPath = response.jsonPath();

        int idVal = jsonPath.getInt("id");
        assertEquals(13,idVal);
        String name = jsonPath.getString("name");
        assertEquals("Jaimie", name);
        String gender = jsonPath.getString("gender");
        assertEquals("Female", gender);
        long phoneNumber = jsonPath.getLong("phone");
        assertEquals(7842554879L, phoneNumber);

        System.out.println("Status code = " + response.getStatusCode());
        System.out.println("Content type = " + response.getContentType());
        System.out.println("idVal = " + idVal);
        System.out.println("name = " + name);
        System.out.println("gender = " + gender);
        System.out.println("phoneNumber = " + phoneNumber);
    }


}
