package com.cydeo.tests.day06_xmlpath_deserialization;
import static io.restassured.RestAssured.*;

import com.cydeo.pojo.Spartan;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import com.cydeo.utilities.SpartanTestBase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SpartanToPojoTest extends SpartanTestBase{

/** Given accept type is application/json
 And Path param id = 10
 When i send GET request to /api/spartans
 Then status code is 200
 And content type is json
 And spartan data matching:
 id > 10
 name>Lorenza
 gender >Female
 phone >3312820936
 */

@DisplayName("GET /spartan/{id} -> pojo object")
@Test
public void spartanToPojoTest() {

    Response response = given().accept(ContentType.JSON)
            .and().pathParam("id", 10)
            .when().get("/spartans/{id}");
    response.prettyPrint();

    //DE_SERIALIZATION Json > Pojo object. Jackson is doing all the work in the background
    Spartan spartan = response.as(Spartan.class);
    System.out.println(spartan);

    // now we can use getters to read values
    System.out.println("id = " + spartan.getId());
    System.out.println("name = " + spartan.getName());
    System.out.println("gender = " + spartan.getGender());
    System.out.println("phone = " + spartan.getPhone());





}

}
