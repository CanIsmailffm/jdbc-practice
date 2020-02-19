package Homeworks;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    public void genderTest(){

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

    /*
    Invalid gender test
1. Create a request by providing query parameter: invalid gender
2. Verify status code 400 and status line contains Bad Request
3. Verify that value of error field is Invalid gender
     */
    @Test
    public void invalidGenderTest(){
        given().accept(ContentType.JSON)
                .when().get("https://uinames.com/api/?gender=invalidgender")
                .then().assertThat().statusLine("HTTP/1.1 400 Bad Request")
                .body("error",equalTo("Invalid gender"));

    }

    /*
    Invalid region test
1. Create a request by providing query parameter: invalid region
2. Verify status code 400 and status line contains Bad Request
3. Verify that value of error field is Region or language not found
     */
    @Test
    public void invalidRegionTest(){
        given().accept(ContentType.JSON)
                .when().get("https://uinames.com/api/?region=invalidgender")
                .then().assertThat().statusLine("HTTP/1.1 400 Bad Request")
                .body("error",equalTo("Region or language not found"));

    }

    /*
    Amount and regions test
1. Create request by providing query parameters: a valid region and amount (must be bigger than 1)
2. Verify status code 200, content type application/json; charset=utf-8
3. Verify that all objects have different name+surname combination
     */
    @Test
    public void amountAndRegionsTest(){
        given().accept(ContentType.JSON)
                .and().queryParams("region", "Romania",
                        "amount", "4")
                .get("https://uinames.com/api?region=Romania&amount=4")
                .then().assertThat().statusCode(200)
                .contentType("application/json; charset=utf-8");

                // more than one map you need list
            //3. Verify that all objects have different name+surname combination ???

    }
    @Test
    public void test6() {
        int parameterValue=4;
        Response response = given().accept(ContentType.JSON)
                .and().queryParam("region", "Romania")
                .and().queryParam("amount", parameterValue)
                .when().get("https://uinames.com/api?region=Romania&amount=4");
        assertEquals(response.statusCode(), 200);
        assertEquals(response.contentType(), "application/json; charset=utf-8");

        // gökhan abiden
        List<String> list1 =new ArrayList<>();
        for(int i=0;i<parameterValue;i++){
            list1.add(response.path("name["+i+"]")+" "+response.path("surname["+i+"]"));
            assertFalse(list1.get(i).equals(list1.equals(i+1)));
        }
        System.out.println("list1 = " + list1);
    }
    /*
    3 params test
1. Create a request by providing query parameters: a valid region, gender and amount (must be bigger than 1)
2. Verify status code 200, content type application/json; charset=utf-8
3. Verify that all objects the response have the same region and gender passed in step 1
     */

    @Test
    public void threeParamsTest(){
        int parameterValue=4;
        Response response = given().accept(ContentType.JSON)
                .and().queryParams("gender", "male","region",    "Romania")
                .and().queryParam("amount", parameterValue)
                .when().get("https://uinames.com/api?region=Romania&gender=male&amount=4");
        assertEquals(response.statusCode(), 200);
        assertEquals(response.contentType(), "application/json; charset=utf-8");

        List<String> list2 = new ArrayList<>();
                for (int i=0; i<parameterValue; i++){

                    list2.add(response.path("gender[" + i +"]")+" " +response.path("region[" + i+"]"));
                    System.out.println("list2 = " +  list2);

                    assertFalse(list2.get(i).equals(list2.equals(i+1)));
                }
        System.out.println("list2 = " + list2);
    }

    @Test
    public void threeParamsTestR(){  // ramazan abi
        ValidatableResponse body = given().accept(ContentType.JSON).and()
                .queryParam("gender", "male")
                .and().queryParam("region", "Germany")
                .and().queryParam("amount", 20)
                .when().get("https://uinames.com/api?region=Germany&gender=male&amount=20")
                .then().assertThat().statusCode(200)
                .and().contentType("application/json; charset=utf-8")
                .and().body("gender", hasItem("male"),
                        "region", hasItem("Germany"));

    }

    /*
    Amount count test
1. Create a request by providing query parameter: amount (must be bigger than 1)
2. Verify status code 200, content type application/json; charset=utf-8
3. Verify that number of objects returned in the response is same as the amount passed in step 1
     */
    @Test
    public void amountCountTest(){
        given().accept(ContentType.JSON)
                .queryParam("amount",20)
                .when().get("https://uinames.com/api?amount=20")
                .then().assertThat().statusCode(200)
                .contentType("application/json; charset=utf-8")
                .body("amount",hasItem(20)); // number of objects returned in response ???



    }

}
