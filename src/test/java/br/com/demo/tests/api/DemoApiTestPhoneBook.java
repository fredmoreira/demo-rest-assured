package br.com.demo.tests.api;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import org.junit.BeforeClass;
import org.junit.Test;

import com.jayway.restassured.RestAssured;



public class DemoApiTestPhoneBook {

	@BeforeClass
	public static void setUp()
	{
		RestAssured.baseURI = "http://localhost:5000";
	}

	@Test
	public void shouldReturnApiUp()
	{
		given().
		contentType("application/json").
		when().
		get("/").
		then().
		statusCode(200).
		body("title", hasItem("! :) :) API PHONE-BOOK UP :) :) !"));
	}

	@Test
	public void endpointNotFound()
	{
		given().
		contentType("application/json").
		when().
		get("/tester").
		then().
		statusCode(404);
	}

	@Test
	public void getRequestTester()
	{
		given().
		when().
			get("contacts?name=Tester").
		then().
			statusCode(200).
			body("[0]._id", is("56d5efa8c82593800291c02b")).
			body("[0].name", is("Tester")).
			body("[0].mobilephone", is("0552188889999")).
			body("[0].homephone", is("0552133332222"));
	}

	@Test
	public void postRequestContactFull() {
		given().
			contentType("application/json").
			body("{\"name\":\"testerTDC\",\"mobilephone\":\"0313198888777\",\"homephone\":\"0313133334444\"}").
		when().
			post("/contacts/").
		then().
			statusCode(201).
			body("name", is("testerTDC")).
			body("mobilephone", is("0313198888777")).
			body("homephone", is("0313133334444"));
	}
	
	@Test
	public void postRequestFail() {
		given().
			contentType("application/json").
			body("{\"name\":\"testerTDC\"}").
		when().
			post("/contacts/").
		then().
			statusCode(400).
			body("", hasItem("Missing required property: mobilephone"));
	}
}
