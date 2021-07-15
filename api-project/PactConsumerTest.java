package liveproject;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.extension.ExtendWith;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
//import au.com.dius.pact.consumer.dsl.DslPart;
//import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
//import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;


@SuppressWarnings("unused")
@ExtendWith(PactConsumerTestExt.class)
public class PactConsumerTest {
  //Create Map for the Headers
  Map<String, String> headers = new Map<String, String>();
  
  //Set Resource URI
  String createUser = "/api/users";
  
  //Create Pact Contract
  @Pact(provider="UserProvider", consumer = "UserConsumer")
  public RequestResponsePact createPact(PactDslWithProvider builder) throws ParseException {
	  //Add Headers
	  headers.put("Content-Type", "application/json");
	  
	  //Create Request JSon
	  DslPart bodySentCreateUser = new PactDslJsonBody()
			  .numberType("id", 1)
			  .stringType("firstName","String")
			  .stringType("lastName","String")
			  .stringType("email","String");
	  //Create Response Json
	  DslPart bodyReceivedCreateUser = new PactDslJsonBody()
			  .numberType("id", 1)
			  .stringType("firstName","String")
			  .stringType("lastName","String")
			  .stringType("email","String");
	  //Create Rules for request and response
	  return builder.given("A request to create a User")
			  .uponReceiving("A request to create a User")
			  .path(createUser)
			  .method("POST")
			  .headers(headers)
			  .body(bodySentCreateUser)
			  .willRespondWith()
			  .status(201)
			  .body(bodyReceivedCreateUser)
			  .toPact();
			  
  }
  
  @Test
	@PactTestFor(providerName = "UserProvider", port = "8989")
	public void runTest() {
		RestAssured.baseURI = "http://localhost:8 989";
		RequestSpecification rq = RestAssured.given().headers(headers).when();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", 1);
		map.put("firstName", "Adhiyan");
		map.put("lastName", "Anbarasan");
		map.put("email", "adhiyananbarasan@mail.com");

		Response response = rq.body(map).post(createUser);
		assert (response.getStatusCode() == 201);
	}
 
}
