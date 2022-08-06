package com.cydeo.tests.day04_path_jsonpath;
import com.cydeo.utilities.ConfigurationReader;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import io.restassured.http.ContentType;

public class HRApiGetTest {

    @BeforeEach //Before in Junit 4
    public void setUp(){                 //http://3.93.242.50:1000/ords/hr
        //RestAssured.baseURI = ConfigurationReader.getProperty("hr.api.url");
        baseURI = ConfigurationReader.getProperty("hr.api.url");
    }

    /**
     * Given accept type is json
     * When user send get request to /ords/hr/regions
     * Status code should be 200
     * Content type should be "application/json"
     * And body should contain "Europe"
     */

    @DisplayName("GET /regions")
    @Test
    public void getRegionsTest(){

        Response response = given().accept(ContentType.JSON)
                .when().get("/regions");
        response.prettyPrint();
        assertEquals(200, response.getStatusCode());
        assertEquals("application/json",response.getContentType());

        assertTrue(response.body().asString().contains("Europe"),"Europe is not in json body");

    }


    /**
     * Given accept type is json
     And Path param "region_id" value is 1
     When user send get request to /ords/hr/regions/{region_id}
     Status code should be 200
     Content type should be "application/json"
     And body should contain "Europe"
     */

    @DisplayName("GET regions/{region_id} path param")
    @Test
    public void getSingleRegionPathParamTest(){
        Response response = given().log().all().accept(ContentType.JSON)
                .and().pathParam("region_id", 1)
                .when().get("/regions/{region_id}"); //baseurl/regions/1

        response.prettyPrint();

        assertEquals(HttpStatus.SC_OK, response.getStatusCode());
        assertEquals(ContentType.JSON.toString(), response.getContentType());
    }

    /**
     * Given accept type is json
     * And query param q={"region_name": "Americas"}
     * When user send get request to /ords/hr/regions
     * Status code should be 200
     * Content type should be "application/json"
     * And region name should be "Americas"
     * And region id should be "2"
     */

    @DisplayName("GET /regions?q={\"region_name\":\"Americas\"}")
    @Test
    public void getRegionWithQueryParamTest(){
        Response response = given().log().all()
                .and().queryParam("q","{\"region_name\": \"Americas\"}")
                .and().accept(ContentType.JSON)
                .when().get("/regions");
        response.prettyPrint();

        assertEquals(HttpStatus.SC_OK, response.getStatusCode());
        assertEquals("application/json",response.getContentType());
        assertTrue(response.body().asString().contains("Americas"),"Americas is not in json body");
        assertTrue(response.body().asString().contains("2"),"region_id is incorrect");

    }






}
