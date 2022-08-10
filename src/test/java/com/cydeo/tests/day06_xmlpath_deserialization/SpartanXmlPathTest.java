package com.cydeo.tests.day06_xmlpath_deserialization;

import com.cydeo.utilities.SpartanTestBase;
import com.cydeo.utilities.ConfigurationReader;
import static io.restassured.RestAssured.*;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import com.cydeo.utilities.SpartanTestBase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SpartanXmlPathTest extends SpartanTestBase {

    /**
     Given accept type is application/xml
     When i send GET request to /api/spartans
     Then status code is 200
     And content type is XML
     And spartan at index 0 is matching:
     id > 107
     name>Ezio Auditore
     gender >Male
     phone >7224120202

     */

    @DisplayName("GET /spartans -> xml path")
    @Test
    public void spartanXMLPathTest() {

        Response response = given().accept(ContentType.XML)
                .when().get("/spartans");
        //response.prettyPrint();

        assertEquals(HttpStatus.SC_OK, response.getStatusCode());
        assertEquals("application/xml", response.contentType());

        //convert XML response body to XMLPATH object
        XmlPath xmlPath = response.xmlPath();

        System.out.println("id = " + xmlPath.getInt("List.item[0].id"));
        System.out.println("name = " + xmlPath.getString("List.item[0].name"));
        System.out.println("gender = " + xmlPath.getString("List.item[0].gender"));
        System.out.println("phone = " + xmlPath.getLong("List.item[0].phone"));


        List<String> allNames = xmlPath.getList("List.item.name");
        System.out.println("Employees count = " + allNames.size());
        System.out.println(allNames);



    }





}
