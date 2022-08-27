package com.cydeo.tests.day09_post_put_delete;
import com.cydeo.pojo.Spartan;
import com.cydeo.utilities.SpartanRestUtils;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.Map;
import com.cydeo.utilities.SpartanTestBase;

public class SpartanPostThenGET extends SpartanTestBase {

    //new Spartan object with random values
    Spartan newSpartan = SpartanRestUtils.getNewSpartan();

    @DisplayName("POST /spartan -> GET with id and compare")
    @Test
    public void postSpartanThenGETTest(){
        System.out.println("NewSpartan -> " + newSpartan);

        Response response = given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().body(newSpartan)
                .when().post("/spartans");

        response.prettyPrint();
        assertThat(response.statusCode(), is(201));

        //get id value using jsonpath(extract)
        int newSpartanID = response.jsonPath().getInt("data.id");

        //send GET request with newSpartanID and compare
        Response response1 = given().accept(ContentType.JSON)
                .and().pathParam("id", newSpartanID)
                .when().get("/spartans/{id}");

        System.out.println("GET request body :");
        response1.prettyPrint();
        //convert GET request json body to another Spartan object. Deserialization
        Spartan spartanFromGet = response1.as(Spartan.class);

        //compare Posted Spartan with GET request Spartan
        /**
         spartanFromGet.getName() matches newSpartan.getName()
         spartanFromGet.getName() matches newSpartan.getGender()
         spartanFromGet.getName() matches newSpartan.getPhone()
         */
        assertThat(spartanFromGet.getName(), equalTo(newSpartan.getName()));
        assertThat(spartanFromGet.getGender(), equalTo(newSpartan.getGender()));
        assertThat(spartanFromGet.getPhone(), equalTo(newSpartan.getPhone()));

        //delete Spartan
        SpartanRestUtils.deleteSpartanById(newSpartanID);
    }
}
