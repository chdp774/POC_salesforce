package com.salesforce.framework.Util;

import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.util.Assert;

public class Util {
	public static long PAGE_LOAD_TIMEOUT = 90;
	public static long IMPLICIT_WAIT = 10;
	public static WebDriver driver;
	
	public Util(WebDriver driver) {
		Util.driver = driver;
		PageFactory.initElements(driver, this);
	}
	/* Method to wait for specified amount of time */
	public static void waitFor(int millisec) {
		try {
			Thread.sleep(millisec * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void waitForPageLoad() {
		By loadElement = By.xpath("//div[@class= 'ngx-overlay']");
		WebDriverWait wait = new WebDriverWait(driver, 90);
		wait.until(ExpectedConditions.presenceOfElementLocated(loadElement));
	}

	/* Method to keyboard action - ESC button */

	public static void keyboardEsc() {
		Actions action = new Actions(driver);
		action.sendKeys(Keys.ESCAPE).build().perform();
	}

	/*
	 * Method to wait for an element until it is visible for the specified amount of time
	 */
	
	public static void waitForElement(WebElement element, int timeunitForsec) {
		WebDriverWait wait = new WebDriverWait(driver, timeunitForsec);
		wait.until(ExpectedConditions.visibilityOf(element));
	}
	
	public static void waitForElement(String xpath, int timeunitForsec) {
		WebDriverWait wait = new WebDriverWait(driver, timeunitForsec);
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(xpath))));
	}
	
	public static void waitForElementClickable(WebElement element, int timeunitForsec) {
		WebDriverWait wait = new WebDriverWait(driver, timeunitForsec);
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}

	public static void waitForElementClickable(String xpath, int timeunitForsec) {
		WebDriverWait wait = new WebDriverWait(driver, timeunitForsec);
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath(xpath))));
	}

	public static void waitForVisibility(WebElement element, int timeunitForsec) {
		WebDriverWait wait = new WebDriverWait(driver, timeunitForsec);
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	public static void waitForVisibility(String xpath, int timeunitForsec) {
		WebDriverWait wait = new WebDriverWait(driver, timeunitForsec);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
	}

	public static void clickUsingJavascript(WebElement ele) {
		try {
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].click()", ele);
		} catch (Exception E) {
			waitFor(5);
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].click()", ele);
		}
	}

	/* Method to select value from drop down using visible text */
	public static void selectFromDropdownUsingVisibleText(WebElement ele, String valuetoselect) {
		Select s = new Select(ele);
		s.selectByVisibleText(valuetoselect);
	}

	// select value from drop down using index
	public static void selectFromDropdownUsingIndex(WebElement ele, int indexvalue) {
		Select s = new Select(ele);
		s.selectByIndex(indexvalue);
	}

	// select value from drop down using value of the value
	public static void selectFromDropdownUsingValue(WebElement ele, String value) {
		Select s = new Select(ele);
		s.selectByValue(value);
	}
	
	/*
	 * Select value from drop down using for loop
	 */
	public void selectFromDropdown(By elements, String value) {
		List<WebElement> list = driver.findElements(elements);
		for(int i=0; i<=list.size(); i++) {
			if(list.get(i).getText().equalsIgnoreCase(value)) {
				list.get(i).click();
				break;
			}
		}
	}
	
	public void scrollToDown() {
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("window.scrollBy(0,1500)");
	}
	
	/*
	 * Generating Random values
	 */
	public static String randomAlphNumeric(int count) {
		String val = RandomStringUtils.randomAlphanumeric(count);
		return val;
	}
	
	public static String randomAlph(int count) {
		String val = RandomStringUtils.randomAlphabetic(count);
		return val;
	}
	
	public static String randomNum(int count) {
		String val = RandomStringUtils.randomNumeric(count);
		return val;
	}
	
	// Random number other then Zero
	public static String number(int num) {
		Random rand = new Random();
		int ran = rand.nextInt(num - 1)+1;
		String value = String.valueOf(ran);
		return value;
	}
}