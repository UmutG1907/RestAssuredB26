package com.cydeo.utilities;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.*;

public class SpartanRestUtils {

    private static String baseUrl = ConfigurationReader.getProperty("spartan.api.url");

    public static void deleteSpartanById(int spartanID){
        System.out.println("DELETE spartan with id {" +spartanID + "}");
        //send DELETE request {{baseUrl}}/api/spartans/{id}
        given().pathParam("id", spartanID)
                .when().delete(baseUrl + "/spartans/{id}")
                .then().log().all();
    }

    public static Map<String, Object> getSpartan(int spartanId) {
        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id", spartanId)
                .when().get(baseUrl + "/spartans/{id}");

        //return response.as(Map.class);
        Map<String, Object> spartanMap = response.as(Map.class);
        return spartanMap;
    }
}
