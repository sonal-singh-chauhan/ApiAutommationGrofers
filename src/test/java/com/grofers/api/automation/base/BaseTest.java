package com.grofers.api.automation.base;

import com.jayway.restassured.RestAssured;

public class BaseTest {

    public void InItBaseTest(){
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com/";
    }
    public void flushTest(){
        RestAssured.reset();
    }
}
