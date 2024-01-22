package phase3_project.multiple_environmants;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import org.testng.annotations.Test;



import io.restassured.RestAssured;
import org.apache.log4j.Logger;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class reqres {

    private static final String BASE_URL = "https://reqres.in/api";
    private static final Logger logger = Logger.getLogger(reqres.class);
    
    @Test(description = "Test for executing POST request using rest assured")
	public void postRequestTest() {
		logger.info("Start :: POST request test using rest assured.");

		String response = null;
		try {
			// create user post data
			PostData postData = new PostData("Mike SMith", "Dermotologist");
			logger.info("Request Object:: "+postData);
			
			RestAssured.given().baseUri(BASE_URL).when().contentType(ContentType.JSON).body(postData).post("/users")
					.then().assertThat().statusCode(201).and().assertThat().body("name", equalTo(postData.name)).and()
					.assertThat().body("job", equalTo(postData.job));

			response = RestAssured.given().baseUri(BASE_URL).when().contentType(ContentType.JSON).body(postData)
					.post("/users").getBody().asString();
		} catch (Exception e) {
			logger.error("Exception Object :: " + e.toString());
			logger.error("End Exception :: " + e.getLocalizedMessage());
		}
		
		logger.info("Response Object:: "+response);
		logger.info("End :: POST request test using rest assured.");
	}
    @Test(description = "Test for executing GET request using rest assured")
	public void getRequestTest() {
		logger.info("Start :: GET All users test.");
		// paginated response params
		int page = 2 , total_pages =2, per_page=6,  total=12;
		
		logger.info("GET : URL "+BASE_URL + "/users?page="+page);
		
		RestAssured.given().baseUri(BASE_URL)
		.when().get("/users?page="+page).then().statusCode(200)
		.and()
		.assertThat()
		.body("page", equalTo(page)).and()
		.body("total", equalTo(total)).and()
		.body("per_page", equalTo(per_page)).and()
		.body("total_pages", equalTo(total_pages)).and()
		.body("data[0].first_name", equalTo("Michael"));
		
		String response = RestAssured.given().baseUri(BASE_URL).queryParam ("page",page)
		.when().get("/users?page="+page).getBody().asString();
		
		logger.info("Reponse :: "+response);
		logger.info("End :: GET All users test.");
	}
    @Test(description = "Test for logging in")
	public void logging() {
		logger.info("Start :: GET All users test.");
		// paginated response params
		int page = 1 , total_pages =2, per_page=6,  total=12;
		
		logger.info("GET : URL "+BASE_URL + "/unknown");
		
		RestAssured.given().baseUri(BASE_URL)
		.when().get("/unknown").then().statusCode(200)
		.and()
		.assertThat()
		.body("page", equalTo(page)).and()
		.body("total", equalTo(total)).and()
		.body("per_page", equalTo(per_page)).and()
		.body("total_pages", equalTo(total_pages)).and()
		.body("data[0].name", equalTo("cerulean"));
		
		String response = RestAssured.given().baseUri(BASE_URL).queryParam ("page",page)
		.when().get("/unknown").getBody().asString();
		
		logger.info("Reponse :: "+response);
		logger.info("End :: GET All users test.");
	}
}

class PostData {
	
	public final String name;
	public final String job;

	public PostData(String name, String job) {
		super();
		this.name = name;
		this.job = job;
	}

	@Override
	public String toString() {
		return "PostData [name=" + name + ", job=" + job + "]";
	}
}
