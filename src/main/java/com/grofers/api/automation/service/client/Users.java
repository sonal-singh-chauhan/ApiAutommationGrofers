package com.grofers.api.automation.service.client;

import com.jayway.restassured.response.Response;

import static com.jayway.restassured.RestAssured.given;

/***
 * @author Sonal Singh Chauhan
 */
public class Users {
    public static Response GetAllUsers(){
        return given().header("Content-Type","application/json").when().log().all().get("/users")
                .then().extract().response();
    }
}
