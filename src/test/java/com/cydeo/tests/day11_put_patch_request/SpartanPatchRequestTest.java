package com.cydeo.tests.day11_put_patch_request;
import com.cydeo.utilities.SpartanRestUtils;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.apache.http.protocol.HTTP;
import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import com.cydeo.utilities.SpartanTestBase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;


public class SpartanPatchRequestTest extends SpartanTestBase {

    /**
     PATCH /api/spartans/{id}
     Partially Update A Spartan

     Given accept type is json
     And content type is json
     And path param id is 15
     And json body is
     {
     "phone": 1234567425
     }
     When i send PATCH request to /spartans/{id}
     Then status code 204
     */


    @DisplayName("PATCH /spartans/{id} --> Partial Change")
    @Test
    public void spartanPatchTest(){
        Map<String, Long> requestMap = new HashMap<>();
        requestMap.put("phone", 1234333325L);

        int spartanId = 15;

        given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().pathParam("id", spartanId)
                .and().body(requestMap)
                .when().patch("/spartans/{id}")
                .then().statusCode(204)
                .and().body(emptyOrNullString());

        Map<String, Object> spartanMap = SpartanRestUtils.getSpartan(15);
        System.out.println("spartanMap = " + spartanMap);
        //compare spartanMap from GET matches phone number in PATCH request.
        assertThat(spartanMap.get("phone"), equalTo(requestMap.get("phone")));


    }
}
