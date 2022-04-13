package com.salesforce.framework.Factory;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.salesforce.framework.Util.PropertiesOperations;
import com.salesforce.framework.Util.Util;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestBase extends Util{
	public static Properties prop;
	public static WebDriver driver;
	public  static EventFiringWebDriver e_driver;
	
	public TestBase() {
		super(driver);
	}
	
	public static void initialization() throws Exception {
		String browser = PropertiesOperations.getPropertyValueByKey("browser");
		String url = PropertiesOperations.getPropertyValueByKey("salesforce_URL");
		if(browser.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
//			driver = new ChromeDriver();
			
			//Incognito mode
			ChromeOptions options = new ChromeOptions();
//			options.addArguments("--incognito");
			options.addArguments("--disable-notifications");
			driver = new ChromeDriver(options);	
		}
		else if(browser.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			FirefoxOptions fOptions = new FirefoxOptions();
			fOptions.addArguments("-private");
			driver = new FirefoxDriver(fOptions);
		}
		else if(browser.equalsIgnoreCase("edge")) {
			WebDriverManager.edgedriver().setup();
			InternetExplorerOptions iOptions = new InternetExplorerOptions();
			iOptions.addCommandSwitches("-private");
			driver = new EdgeDriver();
		}
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(Util.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(Util.IMPLICIT_WAIT, TimeUnit.SECONDS);
		long start = System.currentTimeMillis();
		driver.get(url);
		long finish = System.currentTimeMillis();
		long totalTime = finish - start; 
		System.out.println("Total Time for page load - "+totalTime / 1000.0 + "seconds"); 	
	}
	
	@BeforeMethod
	public void setUp() throws Exception {
		initialization();
	}
	
	@AfterMethod
	public void closeBrowser() {
//		driver.close();
	}
}