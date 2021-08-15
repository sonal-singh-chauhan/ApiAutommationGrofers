package com.grofers.api.automation.tests;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.grofers.api.automation.base.BaseTest;
import com.jayway.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import static com.jayway.restassured.RestAssured.given;

/***
 * @author Sonal Singh Chauhan
 */
public class UsersITTest extends BaseTest {

    @BeforeTest
    public void BeforeTest(){
        InItBaseTest();
    }
    @Test
    public void UserTaskCompletionTest(){
        Response userResponse = given().header("Content-Type","application/json").when().log().all().get("/users")
                            .then().extract().response();
        JsonElement userRootNode = new JsonParser().parse(userResponse.body().asString());
        JsonArray users = userRootNode.getAsJsonArray();
        for(int i=0; i<users.size(); i++){
            if(IsBelongToGrofersCity(users.get(i).getAsJsonObject().get("address"))) {
                Response todoResponse = given().queryParam("userId", users.get(i).getAsJsonObject().get("id"))
                        .when().log().all().get("/todos")
                        .then().extract().response();
                JsonElement todoRootNode = new JsonParser().parse(todoResponse.body().asString());
                if(todoRootNode.getAsJsonArray().size()>0)
                    Assert.assertTrue(TodoCompletionRate(todoRootNode.getAsJsonArray()) > 50f);
            }
        }
    }
    public float TodoCompletionRate(JsonArray todo){
        if(todo.size()==0)
            return 100f;
        int todoCompleted = 0;
        for(int j=0; j<todo.size(); j++){
            if(todo.get(j).getAsJsonObject().get("completed").getAsBoolean())
                todoCompleted++;
        }
        return (todoCompleted*100)/todo.size();
    }
    public boolean IsBelongToGrofersCity(JsonElement geoLocation){
        double lat = geoLocation.getAsJsonObject().get("geo").getAsJsonObject().get("lat").getAsDouble();
        double lng = geoLocation.getAsJsonObject().get("geo").getAsJsonObject().get("lng").getAsDouble();
        boolean isBelongToGrofersCity = false;
        if(lat > -40f && lat < 5f)
            if(lng > 5f && lng < 100f)
                isBelongToGrofersCity = true;
        return isBelongToGrofersCity;
    }
    @AfterTest
    public void AfterTest(){}

}
