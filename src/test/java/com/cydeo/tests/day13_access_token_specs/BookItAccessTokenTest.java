package com.cydeo.tests.day13_access_token_specs;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import org.junit.jupiter.api.DisplayName;
import io.restassured.response.Response.*;


public class BookItAccessTokenTest {
    /**
     Given accept type is Json
     And Query params: email = blyst6@si.edu & password = barbabaslyst
     When I send GET request to
     Url: https://cybertek-reservation-api-qa3.herokuapp.com/sign
     Then status code is 200
     And accessCode should be present and not empty
     */
    @DisplayName("GET /sign - request authorization api/server/service")
    @Test
    public void bookItLoginTest() {
        Response response = given().accept(ContentType.JSON)
                .and().queryParam("email", "blyst6@si.edu")
                .and().queryParam("password", "barbabaslyst")
                .when().get("https://cybertek-reservation-api-qa3.herokuapp.com/sign");

        response.prettyPrint();
        System.out.println("Status code = " + response.statusCode());
        Assertions.assertEquals(200, response.statusCode());

        String accessToken = response.path("accessToken");
        System.out.println(accessToken);
        //assert accessToken is not empty
        Assertions.assertTrue(!accessToken.isEmpty() && accessToken != null);
    }
}

//urls and credentials go to configuration.properties
//Test base for BookItAPITestBase
// -> assign baseUrl
// -> generateToken method
// -> call that method