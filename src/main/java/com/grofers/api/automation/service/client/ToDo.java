package com.grofers.api.automation.service.client;

import com.google.gson.JsonArray;
import com.jayway.restassured.response.Response;
import static com.jayway.restassured.RestAssured.given;

/***
 * @author Sonal Singh Chauhan
 */
public class ToDo {
    public static Response GetToDoOfUser(long userId){
        return given().queryParam("userId", userId)
                .when().log().all().get("/todos")
                .then().extract().response();
    }
    public static float TodoCompletionRate(JsonArray todo){
        if(todo.size()==0)
            return 100f;
        int todoCompleted = 0;
        for(int j=0; j<todo.size(); j++){
            if(todo.get(j).getAsJsonObject().get("completed").getAsBoolean())
                todoCompleted++;
        }
        return (todoCompleted*100)/todo.size();
    }
}
