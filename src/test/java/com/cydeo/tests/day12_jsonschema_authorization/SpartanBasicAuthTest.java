package com.cydeo.tests.day12_jsonschema_authorization;

import com.cydeo.utilities.SpartanSecureTestBase;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;

import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import io.restassured.http.ContentType;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
public class SpartanBasicAuthTest extends SpartanSecureTestBase {

    /**
     given accept type is JSON
     and basic auth credentials are admin, admin
     when user sends GET request to /spartans
     Then status code is 200
     And content type is JSON
     */

    @DisplayName("GET /spartans with basic auth")
    @Test
    public void getSpartanWithAuthTest(){
        given().accept(ContentType.JSON)
                .and().auth().basic("admin", "admin")
                .when().get("/spartans")
                .then().assertThat().statusCode(200)
                .and().contentType(ContentType.JSON)
                .log().all();
    }

    /**
     given accept type is JSON
     when user sends GET request to /spartans
     Then status code is 401
     And content type is JSON
     And verify message is unauthorized
     And log response
     */

    @DisplayName("GET /spartans with basic auth")
    @Test
    public void getAllSpartanUnAuthorizedTest(){
        given().accept(ContentType.JSON)
                .when().get("/spartans")
                .then().statusCode(HttpStatus.SC_UNAUTHORIZED)  //401
                .and().contentType(ContentType.JSON)
                .and().body("message", equalTo("Unauthorized"))
                .log().all();
    }

}
