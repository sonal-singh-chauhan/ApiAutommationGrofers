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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/***
 * @author Sonal Singh Chauhan
 */
public class UsersITTest extends BaseTest {
    private static Logger logger = LoggerFactory.getLogger(UsersITTest.class);
    @BeforeTest
    public void BeforeTest(){
        InItBaseTest();
    }
    @Test
    public void UserTaskCompletionTest(){
        logger.info("Starting test for all users of grofers city should have more thann 50% of their assigned task completed");
        logger.info("Getting all users list");
        Response userResponse = Users.GetAllUsers();
        Assert.assertEquals(userResponse.getStatusCode(),200,"Other than 200 status code found while calling users api");
        JsonElement userRootNode = new JsonParser().parse(userResponse.body().asString());
        JsonArray users = userRootNode.getAsJsonArray();
        for(int i=0; i<users.size(); i++){
            logger.info("Checking weather user {} with Id={} is resident of grofers city or not",users.get(i).getAsJsonObject().get("name")
                ,users.get(i).getAsJsonObject().get("id"));
            if(CityGeoLocationConstants.GROFERS_CITY_LOCATION.IsBelongsToThisCity(
                    users.get(i).getAsJsonObject().get("address").getAsJsonObject().get("geo").getAsJsonObject().get("lat").getAsDouble()
                    ,users.get(i).getAsJsonObject().get("address").getAsJsonObject().get("geo").getAsJsonObject().get("lng").getAsDouble())
            ){
                logger.info("user {} with Id={} is a resident of grofers city. Getting it's todo tasks",users.get(i).getAsJsonObject().get("name")
                        ,users.get(i).getAsJsonObject().get("id"));
                Response todoResponse = ToDo.GetToDoOfUser(users.get(i).getAsJsonObject().get("id").getAsLong());
                Assert.assertEquals(todoResponse.getStatusCode(),200,"Other than 200 status code found while calling todo api");
                JsonElement todoRootNode = new JsonParser().parse(todoResponse.body().asString());
                if(todoRootNode.getAsJsonArray().size()>0)
                    Assert.assertTrue(ToDo.TodoCompletionRate(todoRootNode.getAsJsonArray()) > 50f);
            }
        }
        logger.info("End test for all users of grofers city should have more thann 50% of their assigned task completed");
    }
    @AfterTest
    public void AfterTest(){ flushTest();}
}
