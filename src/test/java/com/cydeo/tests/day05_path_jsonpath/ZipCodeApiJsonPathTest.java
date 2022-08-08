package com.cydeo.tests.day05_path_jsonpath;

import com.cydeo.utilities.ConfigurationReader;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class ZipCodeApiJsonPathTest {

    @BeforeAll
    public static void setUp(){
        System.out.println("Setting up base URL ...");
        RestAssured.baseURI = ConfigurationReader.getProperty("zipcode.api.url");
    }
    /**
     * Given accept type is json
     * and country path param value is "us"
     * and postal code path param value is 22102
     * When I send get request to http://api.zippopotam.us/{country}/{postal-code}
     * Then status code is 200
     * Then "post code" is "22102"
     * And  "country" is "United States"
     * And "place name" is "Mc Lean"
     * And  "state" is "Virginia"
     */

    @DisplayName("GET us/zipCode - jsonPath")
    @Test
    public void zipCodeJsonPathTest(){
        Response response = given().accept(ContentType.JSON)
                .and().pathParam("country","us")
                .and().pathParam("postal-code", "22102")
                .when().get("/{country}/{postal-code}");

        response.prettyPrint();
        Assertions.assertEquals(HttpStatus.SC_OK, response.getStatusCode());

        //assign response json payload./body to JsonPath
        JsonPath jsonPath = response.jsonPath();

        //Navigate the json and print country value
        String countryVal = jsonPath.getString("country");
        System.out.println("Country name = " + countryVal);
        Assertions.assertEquals("United States", jsonPath.getString("country"));

        //Navigate the json and print/assert post code value
        System.out.println("post code = " + jsonPath.getString("'post code'"));
        String zipCode = jsonPath.getString("'post code'");

        Assertions.assertEquals("22102", jsonPath.getString("'post code'"));

        System.out.println("place name = " + jsonPath.getString("places[0].'place name'"));
        String placeName = jsonPath.getString("places[0].'place name'");
        Assertions.assertEquals("Mc Lean", placeName );

        //verify state is Virginia
        String state = jsonPath.getString("places[0].state");
        System.out.println("state name = " + state);
        Assertions.assertEquals("Virginia", state);
    }


}
