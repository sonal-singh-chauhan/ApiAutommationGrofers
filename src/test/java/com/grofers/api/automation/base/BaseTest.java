package com.grofers.api.automation.base;

import com.jayway.restassured.RestAssured;

public class BaseTest {

    public void InItBaseTest(){
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com/";
    }
    public void InItBaseTest(String baseURI){
        RestAssured.baseURI = baseURI;
    }
    public void flushTest(){
        RestAssured.reset();
    }
}
