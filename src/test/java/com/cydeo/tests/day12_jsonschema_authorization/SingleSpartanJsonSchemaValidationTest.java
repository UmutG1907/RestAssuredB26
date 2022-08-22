package com.cydeo.tests.day12_jsonschema_authorization;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import io.restassured.http.ContentType;


import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import com.cydeo.utilities.SpartanTestBase;

public class SingleSpartanJsonSchemaValidationTest extends SpartanTestBase {
/**
 * given accept type is json
 * and path param id is 15
 * when I send GEt request to /spartans/{id}
 * then status code is 200
 * And json payload/body matches SingleSpartanSchema.json
 */
    @DisplayName("GET /spartan/{id} --> JSON Schema Validation")
    @Test
    public void singleSpartanSchemaValidationTest(){
        given().accept(ContentType.JSON)
                .and().pathParam("id", 15)
                .when().get("/spartans/{id}")
                .then().statusCode(HttpStatus.SC_OK)
                .and().body(JsonSchemaValidator.matchesJsonSchema(new File("src/test/resources/jsonschemas/SingleSpartanSchema.json")))
                .and().log().all();
    }

    /**
     * given accept type is json
     * when I send GET request to /spartans
     * then status code is 200
     * And json payload/body matches AllSpartanSchema.json
     */

    @DisplayName("GET /spartan  -->JSON Schema Validation")
    @Test
    public void allSpartansJsonSchemaValidationTest(){
        given().accept(ContentType.JSON)
                .when().get("/spartans")
                .then().statusCode(HttpStatus.SC_OK)
                .and().body(JsonSchemaValidator.matchesJsonSchema(new File("src/test/resources/jsonschemas/AllSpartansSchema.json")))
                .log().all();
    }

    /**
     given accept type is json
     And query params: nameContains "e" and gender "Female"
     when I send GET request to /spartans/search
     Then status code is 200
     And json payload/body matches SearchSpartansSchema.json
     */

    @DisplayName("GET /spartans --> Search Spartan Json Schema Validation")
    @Test
    public void searchSpartansJsonSchemaValidation(){
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("nameContains", "e");
        queryParams.put("gender", "Female");

        given().accept(ContentType.JSON)
                .and().queryParams(queryParams)
                .when().get("/spartans/search")
                .then().statusCode(HttpStatus.SC_OK)
                .and().body(JsonSchemaValidator.matchesJsonSchema(new File("src/test/resources/jsonschemas/SearchSpartanSchema.json")))
                .log().all();
    }
}
