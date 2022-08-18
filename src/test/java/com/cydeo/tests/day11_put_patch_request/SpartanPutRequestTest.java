package com.cydeo.tests.day11_put_patch_request;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import com.cydeo.utilities.SpartanTestBase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;


public class SpartanPutRequestTest extends SpartanTestBase {

/**
 Given accept type is json
 And content type is json
 And path param id is 15
 And json body is
 {
 "gender": "Male",
 "name": "PutRequest"
 "phone": 1234567425
 }
 When i send PUT request to /spartans/{id}
 Then status code 204
 */


    @DisplayName("PUT /spartans/{id} --> Put Request by ID")
    @Test
    public void updateSpartanTest(){

        Map<String, Object> updateMap = new HashMap<>();
        updateMap.put("gender", "Male");
        updateMap.put("name", "PutRequest");
        updateMap.put("phone", 1234567425L);


        Response response = given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .pathParam("id", 82)
                .and().body(updateMap)              //--> Serialization
                .when().put("/spartans/{id}");

        response.prettyPrint();
        System.out.println("Status code -> " + response.statusCode());
        assertThat(response.statusCode(), is(204));
                    //actual                //expected










    }



}
