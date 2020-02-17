package Homeworks;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.*;

public class hw3_UINamesApiTesting {


    //@BeforeClass
    //public void setUpClass(){
    //  baseURI = ConfigurationReader.get("press_spacebar"); }



    /*
    No params test
1. Send a get request without providing any parameters
2. Verify status code 200, content type application/json;charset=utf-8
3. Verify that name, surname, gender, region fields have value
     */
    @Test
    public void noParamsTest(){
        given().accept(ContentType.JSON).and()
                .when().get("https://uinames.com/api?region=germany")
                .then().assertThat().statusCode(200)
                .and().contentType("application/json;charset=utf-8")
                .and().body("name",is(notNullValue()),"surname",is(notNullValue()),
                                 "gender",is(notNullValue()), "region",is(notNullValue()));
    }


    /*
    Gender test
1. Create a request by providing query parameter: gender, male or female
2. Verify status code 200, content type application/json; charset=utf-8
3. Verify that value of gender field is same from step 1
     */
    @Test
    public void genderTes(){

         given().accept(ContentType.JSON)
                 .queryParam("gender","male")
                .when().get("https://uinames.com/api?gender=male")
                .then().assertThat().statusCode(200)
                .contentType("application/json; charset=utf-8")
                .body("gender", equalTo("male"));

        // 3. Verify that value of gender field is same from step 1 ????

    }

    /*
    2 params test
1. Create a request by providing query parameters: a valid region and gender
NOTE: Available region values are given in the documentation
2. Verify status code 200, content type application/json; charset=utf-8
3. Verify that value of gender field is same from step 1
     */

    @Test
    public void paramsTest(){


        //creating map and adding query parameters
        Map<String,Object> paramsMap = new HashMap<>();
        paramsMap.put("region","Mexico");
        paramsMap.put("gender","male");

        Response response = given().accept(ContentType.JSON)
                .and().queryParams(paramsMap)
                .when().get("https://uinames.com/api/?region=Mexico&gender=male");

        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json; charset=utf-8");
        assertTrue(response.body().asString().contains("Mexico"));
        assertTrue(response.body().asString().contains("male"));


    }
}
