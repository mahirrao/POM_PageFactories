package prac.mda.rough;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.*;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;

import prac.mda.Base.Page;
import prac.mda.pages.actions.HomePage;

public class RoughWork
{

	public static void main(String[] args)
	{
//		Page.initConfiguration();
//		HomePage home = new HomePage();
//		Page.topNavigation.goToLoginMenu().loginToSite("mayuresh.ahirrao@gmail.com", "Mayur31885");
//		Page.quitBrowser();
		
		RestAssured.baseURI = "https://api.openweathermap.org/data/2.5";
		
		Map<String, String> weatherQueries = new HashMap<String, String>();
		weatherQueries.put("q", "pune");
		weatherQueries.put("appid", "7fe67bf08c80ded756e598d6f8fedaea");
		weatherQueries.put("units", "metric");
		
		
		
		String response = given()
		.queryParams(weatherQueries)
		.when().get("/weather")
		.then().extract().asString();
		
		System.out.println(response);
		
		JsonPath js = new JsonPath(response);
		
		String currentTemp = js.getString("main.temp");

		System.out.println(currentTemp);
				
//		RestAssured.baseURI ="https://samples.openweathermap.org/data/2.5/";
//		RequestSpecification request = RestAssured.given();
//		
//		Response response = request.log().all().queryParam("q","New York,US")
//                .queryParam("appid", "7fe67bf08c80ded756e598d6f8fedaea")
//	   .get("/weather");
//		
//		String jsonString = response.asString();
//		System.out.println(response.getStatusCode()); 
//		System.out.println(jsonString); 
//		Assert.assertEquals(jsonString.contains("Pune"), true);
	}

}
