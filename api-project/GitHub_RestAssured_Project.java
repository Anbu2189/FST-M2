package projects;

import static io.restassured.RestAssured.given;
//import static org.hamcrest.CoreMatchers.equalTo;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class GitHub_RestAssured_Project {
	
	//Declare Request Specification
	RequestSpecification requestSpec; 
    
    //Global Properties
    String sshKey;
    int sshKeyId;
    
    @BeforeClass
    public void setup() {
    	//Create Request Specification
    	requestSpec = new RequestSpecBuilder()
    			//Set Content type
    			.setContentType(ContentType.JSON)
    			.addHeader("Authorization", "token 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" )
    			//Set Base URL
    			.setBaseUri("https://api.github.com")
    			//Build Request Specification
    			.build();
    	
    	sshKey = "ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQCWg5sTohiWY9R2b1gSYDZSMV2rjitgoL03a5NF7zwAsqm2EIocGTVKMZh5sjsZrNIsj3Mc+fhLTiTWvGUbHHmjqzKTGrNGjehjT1SLjPIWYOZJkvEqo/CKHWCwZyhJzZY+YSX9trGsTt0DtQuaagGjZVYFnkSt8aGYF4MT/Roly1Yed/wGGgZNXlufMtiaLs+f63pKc/XIrqSEOMPRPEKC+zqmq0/p+CJldVYN2tHLCh1NjNaULt9OHawsLERbrrFgFZavrlVgnOY3fs2+dVOEI2U6oOvdEzSv1/tsTrt3BfultsD3dCfPfnqY7C3ulJ1KRYifhhaW9eh2fkQF3WYT";
    }
    
    @Test(priority =1)
    public void addKeys() {
    	String reqBody = "{\"title\": \"TestKey\", \"Key\": \"" + sshKey + "\" }";
    	Response response = given().spec(requestSpec)//Use Request Spec
    			.body(reqBody) //Send Request body
    			.when().post("/user/keys"); //Send POST Request
    	String resBody = response.getBody().asPrettyString();
    	System.out.println(resBody);
    	sshKeyId = response.then().extract().path("id");
    	
    	//Assertions
    	response.then().statusCode(201);
    }
    
    @Test(priority =2)
    public void getKeys() {
    	
    	Response response = given().spec(requestSpec) //Use Request Spec
    			.when().get("/user/keys"); //Send Get Request
    	
    	//Print response
    	String resBody = response.getBody().asPrettyString();
    	System.out.println(resBody);
    	
    	//Assertions
    }
    
    @Test(priority =3)
    public void deleteKeys() {
    	Response response = given().spec(requestSpec) //Use Request Spec
    			.pathParam("keyId", 54447148).when().delete("/user/keys/{keyId}");
    	
    	String resBody = response.getBody().asPrettyString();
    	System.out.println(resBody);
    	
    	//Assertions
    	response.then().statusCode(204);
    	
    }
    
}
