package activities;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;

public class Activity1 {
	    // Set base URL
	    final static String ROOT_URI = "https://petstore.swagger.io/v2/pet";

	  @Test(priority=1)
	    public void addNewPet() {
	        // Create JSON request
	        String reqBody = "{"
	            + "\"id\": 77232,"
	            + "\"name\": \"Riley\","
	            + " \"status\": \"alive\""
	        + "}";

	        Response response = 
	            given().contentType(ContentType.JSON) // Set headers
	            .body(reqBody) // Add request body
	            .when().post(ROOT_URI); // Send POST request

	        // Assertion
	        response.then().body("id", equalTo(77232));
	        response.then().body("name", equalTo("Riley"));
	        response.then().body("status", equalTo("alive"));
	    }

	    @Test(priority=2)
	    public void getPetInfo() {
	        Response response = 
	            given().contentType(ContentType.JSON) // Set headers
	            .when().pathParam("petId", "77232") // Set path parameter
	            .get(ROOT_URI + "/{petId}"); // Send GET request

	        // Assertion
	        response.then().body("id", equalTo(77232));
	        response.then().body("name", equalTo("Riley"));
	        response.then().body("status", equalTo("alive"));
	    } 
	    
	    @Test(priority=3)
	    public void deletePet() {
	        Response response3 =	
	            given().contentType(ContentType.JSON) // Set headers
	            .when().pathParam("petId", "77232") // Set path parameter
	            .delete(ROOT_URI + "/{petId}"); // Send DELETE request
	        RestAssured.defaultParser = Parser.JSON;    
	        // Now let us print the body of the message to see what response
	        // we have received from the server
	        String responseBody = response3.getBody().asString();
	        //String responseBody = response.getBody().prettyPrint();
	        System.out.println("Response Body is =>  " + responseBody);


	        // Assertion
	        response3.then().body("code", equalTo(200));
	        response3.then().body("message", equalTo("77232"));
	    }
	}
