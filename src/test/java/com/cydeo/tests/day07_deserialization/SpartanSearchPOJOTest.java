package com.cydeo.tests.day07_deserialization;
import static io.restassured.RestAssured.*;
import com.cydeo.pojo.Spartan;
import com.cydeo.pojo.SpartanSearch;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import com.cydeo.utilities.SpartanTestBase;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SpartanSearchPOJOTest extends SpartanTestBase {

    /**
     * Given accept Json
     * And query param nameContains value is "e"
     * And query param gender value is "Female"
     * When I send get request to /spartans/search
     * Tehn status code is 200
     * And content type is Json
     * And json response data is as expected
     */
    @Test
    public void spartanSearchToPOJOTest(){
        Map<String, String> queryMap = new HashMap<>();
        queryMap.put("nameContains", "e");
        queryMap.put("gender", "Female");

        Response response = given().accept(ContentType.JSON)
                .and().queryParams(queryMap)
                .when().get("/spartans/search");
        response.prettyPrint();

        assertEquals(HttpStatus.SC_OK, response.getStatusCode());
        assertEquals(ContentType.JSON.toString(), response.getContentType());

        SpartanSearch spartanSearch = response.body().as(SpartanSearch.class);

        //totalElement
        System.out.println("Total element = " + spartanSearch.getTotalElement());
        System.out.println("All spartans = " + spartanSearch.getContent());
        System.out.println("First spartan info = " +spartanSearch.getContent().get(0));


        Spartan secondSpartan = spartanSearch.getContent().get(1);
        System.out.println("Second spartan = " + secondSpartan);

        System.out.println("Second spartan id = " + secondSpartan.getId());

        List<Spartan> spartanData = spartanSearch.getContent();
        System.out.println("Second spartan = " + spartanData.get(1));

        // read all names into list
        List<String> names = new ArrayList<>();
        for (Spartan sp : spartanData){
            names.add(sp.getName());
        }

        System.out.println("names = " + names);
        //using functional programming. stream
        List<String> allNames = spartanData.stream().map(sp -> sp.getName()).collect(Collectors.toList());

        System.out.println("allNames = " + allNames);



    }


}
