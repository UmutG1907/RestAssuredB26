package com.cydeo.utilities;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public abstract class SpartanSecureTestBase {

    @BeforeAll  //@BeforeClass in Junit4
    public static void setUp() {
        RestAssured.baseURI = ConfigurationReader.getProperty("spartan.secure.api.url");
    }


}
