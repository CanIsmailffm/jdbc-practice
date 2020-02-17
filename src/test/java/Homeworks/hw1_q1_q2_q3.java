package Homeworks;


import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;


public class hw1_q1_q2_q3 {


    @BeforeClass
    public void setUpClass(){
        // static import edince restAssured u baseURI nin basina yazmaya gerek yok
        baseURI = ConfigurationReader.get("hrapi.uri");
    }

    /*Q1:
- Given accept type is Json
- Path param value- US
- When users sends request to /countries

- Then status code is 200
- And Content - Type is Json
- And country_id is US
- And Country_name is United States of America
- And Region_id is 2
*/
    @Test
    public void q1(){
        Response response = given().accept(ContentType.JSON)
                .and().queryParam("q", "{\"country_id\":\"US\"}")
                .when().get("/countries");


        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json");

        String CountryID = response.path("items.country_id[0]");
        String CountryName = response.path("items.country_name[0]");
        assertEquals(CountryID, "US");
        assertEquals(CountryName, "United States of America");

    }

    /*
    Q2:
- Given accept type is Json
- Query param value - q={"department_id":80}
- When users sends request to /employees
- Then status code is 200
- And Content - Type is Json
- And all job_ids start with 'SA'
- And all department_ids are 80
- Count is 25
     */
    @Test
    public void q2(){
       Response response= given().accept(ContentType.JSON)
                .queryParam("q","{\"department_id\":80}")
                .when().get("/employees");

       assertEquals(response.statusCode(),200);
       assertEquals(response.contentType(), "application/json");

       List<String> JobIds = response.path("items.job_id");
        for (String jobid : JobIds) {
            String FullJobID = jobid;
            String ActualSA = FullJobID.substring(0,2);
                assertEquals(ActualSA, "SA");
        }
        List<String > departmentIds = response.path("items.department_id");
        for (Object departmentId : departmentIds){
            assertEquals(departmentId,80);
        }

        Object count = response.path("count");
        assertEquals(count, 25);
    }


    /*Q3:
- Given accept type is Json
-Query param value q= region_id 3
- When users sends request to /countries
- Then status code is 200
- And all regions_id is 3
- And count is 6
- And hasMore is false
- And Country_name are;
Australia,China,India,Japan,Malaysia,Singapore
     */

    @Test
    public void q3(){

        Response response = given().accept(ContentType.JSON)
                .queryParam("q", "{\"region_id\":3}")
                .get("/countries");

        assertEquals(response.statusCode(),200);

        List<String > allRegionIds = response.path("items.region_id");
        for (Object Actualids : allRegionIds){
            assertEquals(Actualids,3);
        }

        int count = response.path("count");
        assertEquals(count, 6);

        JsonPath json = response.jsonPath();
        Boolean ActualHasMore = json.getBoolean("hasMore");
        assertTrue(ActualHasMore.equals(false));



        //???
        List<String> AllActualCountryName = response.path("items.country_name");


        System.out.println(AllActualCountryName);
        assertEquals(AllActualCountryName,"[Australia,China,India,Japan,Malaysia,Singapore]");





    }

}