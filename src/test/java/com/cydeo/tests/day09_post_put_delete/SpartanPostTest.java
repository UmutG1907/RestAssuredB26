package com.cydeo.tests.day09_post_put_delete;
import com.cydeo.utilities.SpartanRestUtils;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.apache.http.protocol.HTTP;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import com.cydeo.utilities.SpartanTestBase;

import java.util.HashMap;
import java.util.Map;

public class SpartanPostTest extends SpartanTestBase {

    /**
     * Given accept is json and content type is json
     * and body is :
      {
        "gender": "Female",
        "name": "testPost",
        "phone": 1231231233
      }
     * When I send POST request to /spartans
     * Then status code is 201
     * And Content type is json
     * And "success" is  "A Spartan is Born!"
     * Data name, gender, phone matches by my request details
     */


    @DisplayName("POST new spartan using string json")
    @Test
    public void addNewSpartanAsJsonTest(){

        String jsonBody = "{\n" +
                "        \"gender\": \"Female\",\n" +
                "        \"name\": \"testPost\",\n" +
                "        \"phone\": 1231231233\n" +
                "      }";

        Response response = given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().body(jsonBody)
                .when().post("/spartans");

        response.prettyPrint();

        System.out.println("status code is = " + response.statusCode());
        assertThat(response.statusCode(), is(response.getStatusCode()));

        //verify header
        assertThat(response.contentType(), is(response.getContentType()));

        //assign response to jsonPath
        JsonPath jsonPath = response.jsonPath();
        assertThat(jsonPath.getString("success"), equalTo("A Spartan is Born!"));
        assertThat(jsonPath.getString("data.name"), equalTo("testPost"));
        assertThat(jsonPath.getString("data.gender"), equalTo("Female"));
        assertThat(jsonPath.getLong("data.phone"), equalTo(1231231233L));

        //extract the id of newly added spartan
        int spartanId = jsonPath.getInt("data.id");
        System.out.println("Spartan id --> " + spartanId);

        //delete the spartan after post
        SpartanRestUtils.deleteSpartanById(spartanId);
    }

    @DisplayName("POST /spartans using map - SERIALIZATION")
    @Test
    public void addNewSpartanAsMapTest(){
        Map<String, Object> spartanPostMap = new HashMap<>();
        spartanPostMap.put("gender", "Female");
        spartanPostMap.put("name", "testPost");
        spartanPostMap.put("phone", 1231231233L);

        Response response = given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().body(spartanPostMap)     //-->Map to Json - serialization
                .when().post("/spartans");

        response.prettyPrint();

        System.out.println("status code is = " + response.statusCode());
        assertThat(response.statusCode(), is(response.getStatusCode()));

        //verify header
        assertThat(response.contentType(), is(response.getContentType()));


        //assign response to jsonPath
        JsonPath jsonPath = response.jsonPath();
        assertThat(jsonPath.getString("success"), equalTo("A Spartan is Born!"));
        assertThat(jsonPath.getString("data.name"), equalTo(spartanPostMap.get("name")));
        assertThat(jsonPath.getString("data.gender"), equalTo(spartanPostMap.get("gender")));
        assertThat(jsonPath.getLong("data.phone"), equalTo(spartanPostMap.get("phone")));

        //extract the id of newly added spartan
        int spartanId = jsonPath.getInt("data.id");
        System.out.println("Spartan id --> " + spartanId);

        //delete the spartan after post
        SpartanRestUtils.deleteSpartanById(spartanId);
    }

}






