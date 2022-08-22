package com.cydeo.tests.day12_jsonschema_authorization;
import com.cydeo.utilities.SpartanRestUtils;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import io.restassured.http.ContentType;


import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import com.cydeo.utilities.SpartanTestBase;

public class SpartanPOSTJsonSchemaValidationTest extends SpartanTestBase {
    /**
     given accept is json and content type is json
     and body is:
     {
     "gender": "Male",
     "name": "TestPost1"
     "phone": 1234567425
     }
     When I send POST request to /spartans
     Then status code is 201
     And body should match SpartanPostSchema.json schema
     */

    @DisplayName("POST /spartan -> Json Schema Validation")
    @Test
    public void postSpartanSchemaTest(){
        Map<String, Object> newSpartan = new HashMap<>();       //Serialize to JSON
        newSpartan.put("gender", "Male");
        newSpartan.put("name", "TestPost1");
        newSpartan.put("phone", 1234567425L);

        int newSpartanId = given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().body(newSpartan)
                .when().post("/spartans")
                .then().statusCode(HttpStatus.SC_CREATED)
                .and().body(JsonSchemaValidator.matchesJsonSchema(new File("src/test/resources/jsonschemas/SpartanPOSTSchema.json")))
                .log().all()
                .and().extract().jsonPath().getInt("data.id");  // will return id as integer value
                //.and().extract().response(); // will return Response object
                //.and().extract().jsonPath(); // will return JsonPath object

        System.out.println("Id number -> " + newSpartanId);
        SpartanRestUtils.deleteSpartanById(newSpartanId);
    }
}
