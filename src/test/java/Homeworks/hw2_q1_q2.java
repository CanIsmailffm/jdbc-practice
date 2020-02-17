package Homeworks;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

public class hw2_q1_q2 {

    @BeforeClass
    public void setUpClass(){
        baseURI = ConfigurationReader.get("spartanapi.uri");
    }

    /*
    Q1:
Given accept type is json
And path param id is 20
When user sends a get request to "/spartans/{id}"
Then status code is 200
And content-type is "application/json;charset=UTF-8"
And response header contains Date
And Transfer-Encoding is chunked
And response payload values match the following:
id is 20,
name is "Lothario",
gender is "Male",
phone is 7551551687
     */
    @Test
    public void q1(){


        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id", 20).when()
                .get("/spartans/{id}");

        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json;charset=UTF-8");
        assertTrue(response.headers().hasHeaderWithName("Date"));
        assertEquals(response.getHeader("Transfer-Encoding"),"chunked");

        JsonPath json =  response.jsonPath();

        int id = json.getInt("id");
        String name = json.getString("name");
        String gender = json.getString("gender");
        long phone = json.getLong("phone");

        System.out.println("phone = "+phone);

        assertEquals(id,20);
        assertEquals(name,"Lothario");
        assertEquals(gender,"Male");
        assertEquals(phone,"7551551687"); // expected [7551551687] but found [7551551687]

    }

    /*
    Q2:
Given accept type is json
And query param gender = Female
And query param nameContains = r
When user sends a get request to "/spartans/search"
Then status code is 200
And content-type is "application/json;char"
And all genders are Female
And all names contains r
And size is 20
And totalPages is 1
And sorted is false
     */
    @Test
    public void q2(){
        Response response = given().accept(ContentType.JSON)
                .and().queryParams("gender", "Female","nameContains", "r")
                .when().get("/spartans/search");

        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json;charset=UTF-8");
        assertTrue(response.body().asString().contains("Female"));
        assertTrue(response.body().asString().contains("r"));

        int size = response.path("size");
        assertEquals(size,20);

        int totalPages = response.path("totalPages");
        assertEquals(totalPages,1);

        Boolean sorted = response.path("pageable.sort.sorted");
        System.out.println("sorted:  "+ sorted);
        assertTrue(sorted.equals(false));







    }
}
