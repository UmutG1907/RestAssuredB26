package com.cydeo.tests.day05_path_jsonpath;
import static io.restassured.RestAssured.*;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import com.cydeo.utilities.SpartanTestBase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.cydeo.utilities.HRApiTestBase;

import java.util.List;

public class HrEmployeesJsonPathTest extends HRApiTestBase {

    /**
     * Given accept type is Json
     * And query param limit is 200
     * When I send GET request to /employees
     * Then I can use jsonPath to query and read values from json body
     */


    @DisplayName("GET /employees?limit=200 => jsonPath filters")
    @Test
    public void jsonPathEmployeesTest(){
        Response response = given().accept(ContentType.JSON)
                .and().queryParam("limit",200)
                .when().get("/employees");

        assertEquals(HttpStatus.SC_OK, response.getStatusCode());

        //convert/parse json response body to jsonPath object
        JsonPath jsonPath = response.jsonPath();

        System.out.println("first employee firstname = " +jsonPath.getString("items[0].first_name"));
        System.out.println("first employee job id = " +jsonPath.getString("items[0].job_id"));

        // working with JsonPath lists
        // get all the emails into a list and print out

        List<String> emails = jsonPath.getList("items.email");
        System.out.println(emails);

        //assert TGATES is among emails
        assertTrue(emails.contains("TGATES"));

        //get all the employees names who work for department_id 90
       List<String> namesAtDept90 = jsonPath.getList("items.findAll{it.department_id == 90}.first_name");
        System.out.println("namesAtDept90 = " + namesAtDept90);
        System.out.println("namesAtDept90.size() = " + namesAtDept90.size());

        //get all employees first names who work as IT_PROG
        List<String> ITEmployees = jsonPath.getList("items.findAll{it.job_id=='IT_PROG'}.first_name");
        System.out.println(ITEmployees);

        //get all employee first names whose salary is more than or equal 5000
        List<String> highSalaryEmp = jsonPath.getList("items.findAll{it.salary >= 5000}.first_name");
        System.out.println("highSalaryEmp = " + highSalaryEmp);

        //get employee first name who has max salary
        String maxSalaryEmp = jsonPath.getString("items.max{it.salary}.first_name");
        System.out.println("Max Salary = " + maxSalaryEmp);

        //get employee first name who has min salary
        String minSalaryEmp = jsonPath.getString("items.min{it.salary}.first_name");
        System.out.println("Minimum Salary = " + minSalaryEmp);
    }


}
