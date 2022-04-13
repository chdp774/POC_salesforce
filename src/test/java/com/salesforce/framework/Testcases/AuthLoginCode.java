package com.salesforce.framework.Testcases;

import org.apache.commons.lang3.StringUtils;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import static io.restassured.RestAssured.given;

public class AuthLoginCode {
	static String accessToken;
	@Test(priority = 1)
	public static String accessToken() {
		
//			RestAssured.baseURI = "https://login.microsoftonline.com/8e101901-86db-4f5a-9c76-c74445d2d95e/oauth2/token";
			
			String response = given().log().all().contentType("application/x-www-form-urlencoded;charset=utf-8")
			.formParam("grant_type", "client_credentials")
			.formParam("client_id", "99b2ba1d-c831-4791-a58b-13435f6f3845")
			.formParam("client_secret", "ufj7Q~-N3v1YL~.GKPwn1ucHfEJot1_EXf_Ye")
			.formParam("resource", "https://graph.microsoft.com/").when().accept("application/json")
			.post("https://login.microsoftonline.com/8e101901-86db-4f5a-9c76-c74445d2d95e/oauth2/token")
			.then().log().all().extract().response().asString();
			
		JsonPath js = new JsonPath(response);
		accessToken = js.getString("access_token");
		System.out.println("Generated AccessToken is : "+accessToken);	
		System.out.println("********************");
		return accessToken;
//		RestAssured.baseURI = "https://graph.microsoft.com/v1.0/";
//		given().log().all().header("Content-Type", "application/json").header("Authorization","Bearer "+accessToken)
//		.formParam("grant_type", "client_credentials")
//		.formParam("client_id", "99b2ba1d-c831-4791-a58b-13435f6f3845")
//		.formParam("client_secret", "ufj7Q~-N3v1YL~.GKPwn1ucHfEJot1_EXf_Ye")
//		.formParam("resource", "https://graph.microsoft.com/").when().accept("application/json")
//		.when().get("users/ab00debc-a887-4276-9065-f4bf9569ee08/mailFolders/AAMkADJhNGUwM2U1LTEwNTMtNGJhMy05MDgzLWYzMmMwZDU4NDdhMQAuAAAAAABSo6rkdkIZTq-X0wz478s6AQDSDTjdJPoaTogDug0lcAz9AAAAAQExAAA=/messages?$orderby=receivedDateTime%20desc&Top=1&select=body")
//		.then().log().all().extract().response().asString();
			
	}
	
//	@Test(priority = 2)
//	public static void login2() {
//		RestAssured.baseURI = "https://graph.microsoft.com/";
//		given().header("Authorization","Bearer "+accessToken())
//		.get("v1.0/users/ab00debc-a887-4276-9065-f4bf9569ee08/mailFolders/AAMkADJhNGUwM2U1LTEwNTMtNGJhMy05MDgzLWYzMmMwZDU4NDdhMQAuAAAAAABSo6rkdkIZTq-X0wz478s6AQDSDTjdJPoaTogDug0lcAz9AAAAAQExAAA=/messages?$orderby=receivedDateTime%20desc&Top=1&select=body")
//		.then().assertThat().statusCode(200).extract().response().asString();
////		String response = given().log().all().contentType("application/x-www-form-urlencoded;charset=utf-8").header("Authorization","Bearer "+accessToken())
////				.formParam("grant_type", "client_credentials")
////				.formParam("client_id", "99b2ba1d-c831-4791-a58b-13435f6f3845")
////				.formParam("client_secret", "ufj7Q~-N3v1YL~.GKPwn1ucHfEJot1_EXf_Ye")
////				.formParam("resource", "https://graph.microsoft.com/").when().accept("application/json")
////				.get("https://graph.microsoft.com/v1.0/users/ab00debc-a887-4276-9065-f4bf9569ee08/mailFolders/AAMkADJhNGUwM2U1LTEwNTMtNGJhMy05MDgzLWYzMmMwZDU4NDdhMQAuAAAAAABSo6rkdkIZTq-X0wz478s6AQDSDTjdJPoaTogDug0lcAz9AAAAAQExAAA=/messages?$orderby=receivedDateTime%20desc&Top=1&select=body")
////				.then().log().all().extract().response().asString();
//		System.out.println("**********completed");
//	}
	
	@Test(priority = 2)
	public static String login2() {
		RestAssured.baseURI = "https://graph.microsoft.com/v1.0/users/ab00debc-a887-4276-9065-f4bf9569ee08/mailFolders/AAMkADJhNGUwM2U1LTEwNTMtNGJhMy05MDgzLWYzMmMwZDU4NDdhMQAuAAAAAABSo6rkdkIZTq-X0wz478s6AQDSDTjdJPoaTogDug0lcAz9AAAAAQExAAA=/messages";
		String response = given().header("Authorization","Bearer "+accessToken())
		.when().get("")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath js = new JsonPath(response);
		String overallText = js.getString("value.body.content[0]");
		
		String otpCode = StringUtils.substringBetween(overallText, "Code: ", "If");
		System.out.println("Generated code is : "+otpCode);	
		return otpCode;
	}
	
}
