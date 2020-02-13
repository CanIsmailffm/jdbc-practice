package apitests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

public class SpartanTest {

    String SpartanAllUrl = "http://54.161.128.36:8000/api/spartans";

    @Test
    public void viewAllSpartans(){
        Response response = RestAssured.get(SpartanAllUrl);

        // print te status code
        System.out.println(response.statusCode());

        // print response body
        System.out.println(response.body().asString());

        //pretty print , like as it si in postman
        System.out.println(response.body().prettyPrint());

    }

    //Test Case
    // when user send get request to /api/spartans end point
    // Then status code must be 200
    // And body should contains Orion

    @Test
    public void viewSpartanTest1(){

        Response response = RestAssured.get(SpartanAllUrl);

        //verify status code is 200
        Assert.assertEquals(response.statusCode(),200);
        //verify body includes Orion
        Assert.assertTrue(response.body().asString().contains("Orion"));

    }


    //Test Case
    // Given accept type application/json
    // when user send get request to /api/spartans end point
    // Then status code must be 200
    // And Response content type must be json
    // And body should contains Orion

    @Test
    public void viewSpartanTest2(){
        Response response = RestAssured.given().accept(ContentType.JSON)
                .when().get(SpartanAllUrl);
        //verify status code
        Assert.assertEquals(response.statusCode(),200);
        //verify response content type is json
        Assert.assertEquals(response.contentType(),"application/json;charset=UTF-8");
    }



    //Test Case
    // Given accept type application/json
    // when user send get request to /api/spartans end point
    // Then status code must be 200
    // And Response content type must be xml
    // And body should contains Orion

    @Test
    public void viewSpartanTest3(){
        Response response = given().accept(ContentType.XML)
                .when().get(SpartanAllUrl);
        //verify status code
        assertEquals(response.statusCode(),200);
        //verify content type
        assertEquals(response.contentType(),"application/xml;charset=UTF-8");
        //body contains Orion
        assertTrue(response.body().asString().contains("Orion"));
    }

    //Test Case (üstekki TC in aynisi 2.Way)
    // Given accept type application/json
    // when user send get request to /api/spartans end point
    // Then status code must be 200
    // And Response content type must be xml

    @Test
    public void viewSpartanTest4(){
                // request starts here
         given().accept(ContentType.XML)
                .when().get(SpartanAllUrl)
                 //response starts here
                 .then().statusCode(200)
                 .and().contentType("application/xml;charset=UTF-8");

    }


    /*
       Given the accept type Json
       When I send get request to /api/spartans/3
       Then status code must be 200
       And Content type should be json
       and body should contains Fidole
    */
    @Test
    public void viewSpartanTest5(){

        Response response = given().accept(ContentType.JSON).when().get(SpartanAllUrl + "/3");

        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json;charset=UTF-8");
        assertTrue(response.body().asString().contains("Fidole"));

    }

    // TASK-2
    /*
       Given the accept type xml
       When I send get request to /api/spartans/3
       Then status code must be 406
    */
    @Test
    public void negativeContentType(){

        Response response = given().accept(ContentType.XML)
                .when().get(SpartanAllUrl + "/3");

        assertEquals(response.getStatusCode(),406);
    }


}
