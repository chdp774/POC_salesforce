package com.salesforce.framework.PageObject;

import static io.restassured.RestAssured.given;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.salesforce.framework.Factory.TestBase;
import com.salesforce.framework.Util.PropertiesOperations;

import groovyjarjarantlr4.v4.parse.ANTLRParser.element_return;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;


public class SalesforcePOC extends TestBase{
	private WebDriver driver;
	static String accessToken;
	public SalesforcePOC(WebDriver driver) {
		
		PageFactory.initElements(driver, this);
		this.driver = driver;
	}
	
	public void doLogin() {
		waitFor(1);
		userName.sendKeys(PropertiesOperations.getPropertyValueByKey("userName"));
		password.sendKeys(PropertiesOperations.getPropertyValueByKey("password"));
		waitFor(1);
		login_Btn.click();
		waitFor(10);
		verificationCode.sendKeys(login2());
	}
	
	public void simpleImportBtn() {
//		waitFor(15);
		By loadElement = By.xpath("//a[@title = 'SimpleImport Free']/span");
		WebDriverWait wait = new WebDriverWait(driver, 120);
		wait.until(ExpectedConditions.presenceOfElementLocated(loadElement));
		waitFor(3);
		clickUsingJavascript(simpleImport_Btn);
	}
	
	public void fileUpload() {
		System.out.println("On fileupload page");
		WebElement first = driver.findElement(By.xpath("//iframe[@title='accessibility title']"));
		waitFor(5);
		//Frame1
		driver.switchTo().frame(first);
		//Child Frame2
		driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@id='theIframe']")));
		
		By loadElement = By.xpath("//div[@id='uploadFileButton']/parent::div/parent::div");
		WebDriverWait wait = new WebDriverWait(driver, 120);
		wait.until(ExpectedConditions.presenceOfElementLocated(loadElement));
		
		waitFor(5);
		upload_Btn.click();
		System.out.println("File uploading Completed");
	}
	
	public void excelUpload() throws IOException, AWTException {
		waitFor(2);
//		Runtime.getRuntime().exec(PropertiesOperations.getPropertyValueByKey("filepath"));
		Robot rb = new Robot();
		rb.delay(3000);
		
		StringSelection ss = new StringSelection(System.getProperty("user.dir") + ".\\src\\main\\resources\\testdata\\SF_Accountupdate_POC.csv");
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
		
		rb.keyPress(KeyEvent.VK_CONTROL);
		rb.keyPress(KeyEvent.VK_V);
		rb.keyRelease(KeyEvent.VK_CONTROL);
		
		rb.keyPress(KeyEvent.VK_ENTER);
		rb.keyRelease(KeyEvent.VK_ENTER);
		
		By loadElement = By.xpath("//div[@class='panel-btn']/parent::div");
		WebDriverWait wait = new WebDriverWait(driver, 120);
		wait.until(ExpectedConditions.presenceOfElementLocated(loadElement));
		waitForElementClickable(importToSalesforce, 50);
		waitFor(4);
		importToSalesforce.click();
	}
	
	public void masterDD() {
		
		
		By loadElement = By.xpath("//div[contains(@class, 'slds-react-select__control')]");
		WebDriverWait wait = new WebDriverWait(driver, 120);
		wait.until(ExpectedConditions.presenceOfElementLocated(loadElement));
		
		waitFor(3);
		WebElement master = driver.findElement(By.xpath("//div[contains(@class, 'slds-react-select__control')]"));
		master.click();
		waitFor(3);
		
		driver.findElement(By.xpath("//div[contains(text(),'Lead (Lead)')]")).click();
	}
	
	public void loadMappingDD() {
		waitFor(12);
		
		By loadElement = By.xpath("//div[contains(@class, 'slds-react-select__value-container css')]");
		WebDriverWait wait = new WebDriverWait(driver, 120);
		wait.until(ExpectedConditions.presenceOfElementLocated(loadElement));
		
		WebElement masterDD = driver.findElement(By.xpath("//div[contains(@class, 'slds-react-select__value-container css')]"));
		
		masterDD.click();
		waitFor(2);
		driver.findElement(By.xpath("//div[contains(text(),'JdV Lead Upload Mapping')]")).click();
	}
	
	public void nextBtn() {
		waitFor(7);
		waitForElementClickable(nextBtn, 50);
		clickUsingJavascript(nextBtn);
		waitFor(5);
		waitForElementClickable(nextBtn, 50);
		clickUsingJavascript(nextBtn);
		waitFor(5);
		waitForElementClickable(nextBtn, 50);
		clickUsingJavascript(nextBtn);
	}
	
	public void submitBtn() {
		waitFor(5);
		waitForElementClickable(submitBtn, 50);
		clickUsingJavascript(submitBtn);
		
		By loadElement = By.xpath("//div[contains(text(), 'Inserts')]");
		WebDriverWait wait = new WebDriverWait(driver, 120);
		wait.until(ExpectedConditions.presenceOfElementLocated(loadElement));
		
		Boolean item = driver.findElement(By.xpath("//div[contains(text(), 'Inserts')]")).isEnabled();
		System.out.println("value "+ item);
		System.out.println("Account import completed");
		waitFor(60);
	}
	
	public void moveToElement() {
		Actions action = new Actions(driver);
		action.moveByOffset(320, 420).click().build().perform();
	}
	
	public static String accessToken() {
		String response = given().contentType("application/x-www-form-urlencoded;charset=utf-8")
		.formParam("grant_type", "client_credentials")
		.formParam("client_id", "99b2ba1d-c831-4791-a58b-13435f6f3845")
		.formParam("client_secret", "ufj7Q~-N3v1YL~.GKPwn1ucHfEJot1_EXf_Ye")
		.formParam("resource", "https://graph.microsoft.com/").when().accept("application/json")
		.post("https://login.microsoftonline.com/8e101901-86db-4f5a-9c76-c74445d2d95e/oauth2/token")
		.then().extract().response().asString();
		
		JsonPath js = new JsonPath(response);
		accessToken = js.getString("access_token");
		System.out.println("Generated AccessToken is : "+accessToken);	
		System.out.println("********************");
		return accessToken;
	}
	
	public static String login2() {
		RestAssured.baseURI = "https://graph.microsoft.com/v1.0/users/ab00debc-a887-4276-9065-f4bf9569ee08/mailFolders/AAMkADJhNGUwM2U1LTEwNTMtNGJhMy05MDgzLWYzMmMwZDU4NDdhMQAuAAAAAABSo6rkdkIZTq-X0wz478s6AQDSDTjdJPoaTogDug0lcAz9AAAAAQExAAA=/messages";
		String response = given().header("Authorization","Bearer "+accessToken())
		.when().get("")
		.then().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath js = new JsonPath(response);
		String overallText = js.getString("value.body.content[0]");
		
		String otpCode = StringUtils.substringBetween(overallText, "Code: ", "If");
		System.out.println("Generated code is : "+otpCode);	
		return otpCode;
	}
	
	
	//Locators
	@FindBy(css = "#username")
	private WebElement userName;
		
	@FindBy(css = "#password")
	private WebElement password;
		
	@FindBy(css = "#Login")
	private WebElement login_Btn;
		
	@FindBy(xpath = "//a[@title = 'SimpleImport Free']/span")
	private WebElement simpleImport_Btn;
	
	@FindBy(xpath = "//a[@title = 'Accounts']/span")
	private WebElement accounts_btn;
	
	@FindBy(css = ".navbar-brand.redirect-link img")
	private WebElement simpleImportLogo;
	
	@FindBy(xpath = "//div[@id='uploadFileButton']/parent::div/parent::div")
	private WebElement upload_Btn;
	
	@FindBy(css = "#emc")
	private WebElement verificationCode;
	
	@FindBy(css = "#save")
	private WebElement saveBtn;
	
	@FindBy(xpath = "//button[@id='uploadFileButton']")
	private WebElement test3;
	
	@FindBy(xpath = "//div[@class='panel-btn']/parent::div")
	private WebElement importToSalesforce;
	
	@FindBy(xpath = "//button[contains(text(), 'Next')]")
	private WebElement nextBtn;
	
	@FindBy(xpath = "//button[contains(text(), 'Submit')]")
	private WebElement submitBtn;
	
	@FindBy(xpath = "")
	private WebElement fds;
		
}
