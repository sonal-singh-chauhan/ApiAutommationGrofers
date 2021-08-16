package com.grofers.api.automation.tests;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.grofers.api.automation.base.BaseTest;
import com.grofers.api.automation.constant.CityGeoLocationConstants;
import com.grofers.api.automation.service.client.ToDo;
import com.grofers.api.automation.service.client.Users;
import com.jayway.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

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
        Response userResponse = Users.GetAllUsers();
        JsonElement userRootNode = new JsonParser().parse(userResponse.body().asString());
        JsonArray users = userRootNode.getAsJsonArray();
        for(int i=0; i<users.size(); i++){
            if(CityGeoLocationConstants.GROFERS_CITY_LOCATION.IsBelongsToThisCity(
                    users.get(i).getAsJsonObject().get("address").getAsJsonObject().get("geo").getAsJsonObject().get("lat").getAsDouble()
                    ,users.get(i).getAsJsonObject().get("address").getAsJsonObject().get("geo").getAsJsonObject().get("lng").getAsDouble())
            ){
                Response todoResponse = ToDo.GetToDoOfUser(users.get(i).getAsJsonObject().get("id").getAsLong());
                JsonElement todoRootNode = new JsonParser().parse(todoResponse.body().asString());
                if(todoRootNode.getAsJsonArray().size()>0)
                    Assert.assertTrue(ToDo.TodoCompletionRate(todoRootNode.getAsJsonArray()) > 50f);
            }
        }
    }
    @AfterTest
    public void AfterTest(){}
}
