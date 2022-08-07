package com.cydeo.tests.day04_path_jsonpath;
import com.cydeo.utilities.ConfigurationReader;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import io.restassured.http.ContentType;
import com.cydeo.utilities.SpartanTestBase;

import java.util.List;

public class SpartanPathMethodTest extends SpartanTestBase {

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

    @DisplayName("GET /spartan/{id} and path()")
    @Test
    public void readJsonUsingPathTest(){


        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id",13)
                .when().get("spartans/{id}");

        /**
         * {
         * "id": "13",
         * "name": "Jaimie",
         * "gender": "Female",
         * "phone": 7842554879
         * }
         */

        System.out.println("id = " + response.path("id"));
        System.out.println("name = " + response.path("name"));
        System.out.println("gender = " + response.path("gender"));
        System.out.println("phone = " + response.path("phone"));

        assertEquals(HttpStatus.SC_OK, response.getStatusCode());
        assertEquals("application/json", response.getContentType());

        int spartanId = response.path("id");
        assertEquals(13,spartanId);
        assertEquals("Jaimie", response.path("name"));
        assertEquals("Female", response.path("gender"));
        assertEquals(7842554879L, (long)response.path("phone"));

    }

    /**
     * Given accept is json
     * When I send get request to /api/spartans
     * Then status code is 200
     * And content type is json
     * And I can navigate json array object
     */

    @DisplayName("GET /spartans and path()")
    @Test
    public void readSpartanJsonArrayUsingPathTest(){

        Response response = given().accept(ContentType.JSON)
                .when().get("/spartans");

        assertEquals(HttpStatus.SC_OK, response.getStatusCode());
        assertEquals("application/json", response.getContentType());

        //Print first spartan id and name
        System.out.println("first spartan id = " + response.path("id[0]"));
        System.out.println("first spartan name = " + response.path("name[0]"));
        System.out.println("first spartan name = " + response.path("[0].name"));


        //Print last spartan id and name
        System.out.println("last spartan id = " + response.path("id[-1]"));
        System.out.println("last spartan name = " + response.path("name[-1]"));

        //get all ids into a List
        List<Integer> allIds = response.path("id");
        System.out.println("allIds size = " + allIds.size());
        System.out.println("allIds = " + allIds);
        assertTrue(allIds.contains(22) && allIds.size() == 97);

        //get all names and say hi
        List<String> allNames = response.path("name");
        System.out.println("All names size = " + allNames.size());
        for (String each : allNames) {
            System.out.println("Hi " + each);
        }

        //Add Bye now
        allNames.forEach(name -> System.out.println("Bye " + name) );

    }

}
