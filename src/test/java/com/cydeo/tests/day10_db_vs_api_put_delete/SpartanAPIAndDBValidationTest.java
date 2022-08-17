package com.cydeo.tests.day10_db_vs_api_put_delete;
import com.cydeo.utilities.ConfigurationReader;
import com.cydeo.utilities.DBUtils;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import com.cydeo.utilities.SpartanTestBase;

import java.util.HashMap;
import java.util.Map;

public class SpartanAPIAndDBValidationTest extends SpartanTestBase {

    /**
     given accept is json and content type is json
     and body is:
     {
     "gender": "Male",
     "name": "PostVSDatabase"
     "phone": 1234567425
     }
     When I send POST request to /spartans
     Then status code is 201
     And content type is json
     And "success" is "A Spartan is Born!"
     When I send database query
     SELECT name, gender, phone
     FROM spartans
     WHERE spartan_id = newIdFrom Post request;
     Then name, gender , phone values must match with POST request details
     */

    @DisplayName("POST /api/spartan -> then validate in DB")
    @Test
    public void postNewSpartanThenValidateInDBTest(){
        Map<String, Object> postRequestMap = new HashMap<>();
        postRequestMap.put("gender", "Male");
        postRequestMap.put("name", "PostVSDatabase");
        postRequestMap.put("phone", 1234567425L);

        Response response = given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().body(postRequestMap)
                .when().post("/spartans");       // SERIALIZATION
        response.prettyPrint();

        assertThat(response.statusCode(), is(201));
        assertThat(response.contentType(), is("application/json"));

        JsonPath jsonPath = response.jsonPath();

//        assertThat(response.path("success"), equalTo("A Spartan is Born!"));
//        assertThat(response.jsonPath().getString("success"), equalTo("A Spartan is Born!"));

        assertThat(jsonPath.getString("success"), equalTo("A Spartan is Born!"));

        int newSpartanID = jsonPath.getInt("data.id");
        System.out.println("New spartan ID -> " +newSpartanID);

        //database steps
        String query = "Select name, gender, phone FROM spartans WHERE spartan_id = " + newSpartanID;



        String dbUrl = ConfigurationReader.getProperty("spartan.db.url");
        String dbUsername = ConfigurationReader.getProperty("spartan.db.userName");
        String dbPassword = ConfigurationReader.getProperty("spartan.db.password");

        //connect to db
        DBUtils.createConnection(dbUrl, dbUsername, dbPassword);
        //run the query and get result as Map obj
        Map<String, Object> dbMap = DBUtils.getRowMap(query);
        System.out.println("dbMap = " + dbMap);
        //assert/validate data from database Matches data from post request

        assertThat(dbMap.get("NAME"), equalTo(postRequestMap.get("name")));
        assertThat(dbMap.get("GENDER"), equalTo(postRequestMap.get("gender")));
        assertThat(dbMap.get("PHONE"), equalTo(postRequestMap.get("phone") + ""));
                    //actual                  //expected

        //disconnect from database
        DBUtils.destroy();


    }


}
