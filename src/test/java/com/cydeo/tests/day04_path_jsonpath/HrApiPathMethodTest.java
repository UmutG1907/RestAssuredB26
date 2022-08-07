package com.cydeo.tests.day04_path_jsonpath;
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import io.restassured.http.ContentType;
import com.cydeo.utilities.SpartanTestBase;
import com.cydeo.utilities.HRApiTestBase;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;

import java.util.List;

public class HrApiPathMethodTest extends HRApiTestBase {

    @Test
    public void readCountriesUsingPathTest(){

        Response response = given().accept(ContentType.JSON)
                .when().get("/countries");
        assertEquals(HttpStatus.SC_OK, response.statusCode());

        System.out.println("first country id = " + response.path("items[0].country_id"));
        System.out.println("first country name = " + response.path("items[0].country_name"));

        assertEquals("AR", response.path("items[0].country_id"));
        assertEquals("Argentina", response.path("items[0].country_name"));

        //store all country names into List
        List<String> allCountries = response.path("items.country_name");
        System.out.println(allCountries);
    }

}
