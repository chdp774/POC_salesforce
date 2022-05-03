package com.salesforce.framework.PageObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.salesforce.framework.Factory.TestBase;
import com.salesforce.framework.Util.PropertiesOperations;
import com.salesforce.framework.Util.Xls_Reader;

public class AccountInformationPage extends TestBase{
	
	private WebDriver driver;
	
	public AccountInformationPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
		this.driver = driver;
	}
	
	public void clickOnAccountTab() {
		waitFor(15);
//		accounts_btn.click();
		driver.switchTo().defaultContent();
		By loadElement = By.xpath("//a[@title = 'Accounts']/span");
		WebDriverWait wait = new WebDriverWait(driver, 120);
		wait.until(ExpectedConditions.presenceOfElementLocated(loadElement));
		
		waitFor(2);
		clickUsingJavascript(accounts_btn);
	}
	
	public void setAllAccountsOption() {
		waitFor(5);
		recentAccounts.click();
		recentSearchBox.sendKeys("All accounts");
		allAccountsOption.click();
	}
	
	public void accountSearch() {
		waitFor(2);
		searchField.sendKeys(PropertiesOperations.getPropertyValueByKey("accountSearchkey") +Keys.ENTER);
	}
	
	public void allAccountsCheckbox() {
		waitFor(3);
		accountsCheckbox.click();
		waitFor(5);
	}

	public void tabledata() {
		waitFor(2);
		Xls_Reader reader = new Xls_Reader("./src/main/resources/testdata/atriumID_datasheet.xlsx");
		reader.addSheet("TableData");
		reader.addColumn("TableData", "CreatedDate");
		reader.addColumn("TableData", "AccountName");
		reader.addColumn("TableData", "AtriumID");
		reader.addColumn("TableData", "SeachType");
		List<WebElement> rowCount = driver.findElements(By.xpath("//th[@scope='row']/span/span"));
		for(int i=1; i<rowCount.size()+1; i++) {
			String createDate = driver.findElement(By.xpath("//tbody/tr[" + i + "]/th/span/span")).getText();
			String acctName = driver.findElement(By.xpath("//tbody/tr[" + i + "]/td[3]/span/a")).getText();
			String attriumID = driver.findElement(By.xpath("//tbody/tr[" + i + "]/td[5]/span/span")).getText();
			String searchType = driver.findElement(By.xpath("//tbody/tr[" + i + "]/td[8]/span/span")).getText();
			
			reader.setCellData("TableData", "CreatedDate", i+1, createDate);
			reader.setCellData("TableData", "AccountName", i+1, acctName);
			reader.setCellData("TableData", "AtriumID", i+1, attriumID);
			reader.setCellData("TableData", "SeachType", i+1, searchType);
			
			System.out.println(createDate + " -- " + acctName + " -- " + attriumID + " -- " + searchType);
		}
	}
	
	public void update_db_table() throws SQLException {
		Connection connection = DriverManager.getConnection("jdbc:postgresql://ec2-52-36-238-103.us-west-2.compute.amazonaws.com:5432/perf", "admin","perfadmin");
		System.out.println("Connection to postgreSQL server successfully");
//		String sql = "INSERT INTO sfaccounts2 VALUES('4/10/2022 12:13 PM','prasadch', 1234, 'abc')";
		
		List<WebElement> rowCount = driver.findElements(By.xpath("//th[@scope='row']/span/span"));
		for(int i=1; i<rowCount.size()+1; i++) {
			
			String createDate = driver.findElement(By.xpath("//tbody/tr[" + i + "]/th/span/span")).getText();
			String attriumID = driver.findElement(By.xpath("//tbody/tr[" + i + "]/td[5]/span/span")).getText();
			String acctName = driver.findElement(By.xpath("//tbody/tr[" + i + "]/td[3]/span/a")).getText();
			String searchType = driver.findElement(By.xpath("//tbody/tr[" + i + "]/td[8]/span/span")).getText();
			String searchID = PropertiesOperations.getPropertyValueByKey("accountSearchkey");
			
			String sql = "INSERT INTO perf (create_date, atrium_id, account_name, search_type, search_id ) VALUES (?,?,?,?,?)";
			
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, createDate);
			statement.setString(2, attriumID);
			statement.setString(3, acctName);
			statement.setString(4, searchType);
			statement.setString(5, searchID);
			
			statement.executeUpdate();
			System.out.println(createDate + " -- " + acctName + " -- " + attriumID + " -- " + searchType);
		}
		
		connection.close();
		System.out.println("Query completed...........");
	}
	
	@FindBy(xpath = "//a[@title = 'Accounts']/span")
	private WebElement accounts_btn;
	
	@FindBy(xpath = "//input[@type='search' and @placeholder='Search this list...']")
	private WebElement searchField;
	
	@FindBy(xpath = "//th[@scope='col']//label[@class='slds-checkbox']/parent::span/parent::div")
	private WebElement accountsCheckbox;
	
	@FindBy(xpath = "//th[@scope='row']/span/a")
	private WebElement table_name;
	
	@FindBy(xpath = "//div[@data-aura-class='forceListViewPicker']")
	private WebElement recentAccounts;
	
	@FindBy(xpath = "//input[@role='combobox']")
	private WebElement recentSearchBox;
	
	@FindBy(xpath = "//mark[contains(text(), 'All Accounts')]")
	private WebElement allAccountsOption;
	
	@FindBy(xpath = "")
	private WebElement we;
}
