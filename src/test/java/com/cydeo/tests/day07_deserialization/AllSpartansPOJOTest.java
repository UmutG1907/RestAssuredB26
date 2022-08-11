package com.cydeo.tests.day07_deserialization;
import static io.restassured.RestAssured.*;
import com.cydeo.pojo.Spartan;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import com.cydeo.utilities.SpartanTestBase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.stream.Collectors;

public class AllSpartansPOJOTest extends SpartanTestBase {

    /**
     * Given accept type is Json
     * When I send GET request to /spartans
     * Then status code is 200
     * And content type is Json
     * And I can convert json to list of spartan pojo's
     */

    @DisplayName("GET /spartans")
    @Test
    public void allSpartansPOJOTest(){
        Response response = given().accept(ContentType.JSON)
                .when().get("/spartans");
        assertEquals(HttpStatus.SC_OK, response.getStatusCode());
        assertEquals("application/json", response.getContentType());
        System.out.println(response.getContentType());
        System.out.println(response.getStatusCode());

        //Convert response to jsonPath
        JsonPath jsonPath = response.jsonPath();

        //convert json to pojo --> DE-SERIALIZATION, happens in Jackson dependency
        //Using jsonPath extract List of spartans/ do de-serialization
        List<Spartan> allSpartans = jsonPath.getList("", Spartan.class);

        System.out.println("count = " + allSpartans.size());

        //first spartan
        System.out.println("first spartan = " + allSpartans.get(0));

        //using streams: filter and store female spartans into a different List
        List<Spartan> femaleSpartans = allSpartans.stream()
                .filter(spartan -> spartan.getGender().equals("Female")).collect(Collectors.toList());

        System.out.println(femaleSpartans);
        System.out.println(femaleSpartans.size());

        //using streams: filter and store male spartans into a different List
        //source.stream.filter(condition in lambda).collect(type)

        long count = allSpartans.stream().filter(spartan -> spartan.getGender().equals("Male")).count();
        System.out.println("male spartan count = " + count);
        List<Spartan> maleSpartans = allSpartans.stream()
                .filter(sp -> sp.getGender().equals("Male")).collect(Collectors.toList());

        System.out.println("male spartans = " + maleSpartans);

    }

}
