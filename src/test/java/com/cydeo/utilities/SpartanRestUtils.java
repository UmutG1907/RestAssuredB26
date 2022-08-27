package com.cydeo.utilities;
import com.cydeo.pojo.Spartan;
import com.github.javafaker.Faker;
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

    /**
     This method creates object of Spartan pojo class
     and assigns random data using Faker class
      @return
     */
    public static Spartan getNewSpartan(){
        //create Faker class object to help us generate random values
        Faker random = new Faker();
        Spartan spartan = new Spartan();
        spartan.setName(random.name().firstName()); //set random firstname
        //set random gender. 1 - Female otherwise Male
        int num = random.number().numberBetween(1,3);
        if(num == 1){
            spartan.setGender("Female");
        }else{
            spartan.setGender("Male");
        }
        //generate random phone number
        spartan.setPhone(random.number().numberBetween(1000000000L, 9999999999L));

        return spartan;
    }
}
