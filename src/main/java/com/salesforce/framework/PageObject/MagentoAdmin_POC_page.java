package com.salesforce.framework.PageObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.salesforce.framework.Factory.TestBase;
import com.salesforce.framework.Factory.TestBase_M2;
import com.salesforce.framework.Util.PropertiesOperations;
import com.salesforce.framework.Util.Xls_Reader;

import io.github.bonigarcia.wdm.WebDriverManager;

public class MagentoAdmin_POC_page extends TestBase_M2{
	private WebDriver driver;
	
	public MagentoAdmin_POC_page(WebDriver driver) {
		PageFactory.initElements(driver, this);
		this.driver = driver;
	}
	
	public void M2Login() {
		userName_txt.sendKeys("CSSPECIAL");
		password_text.sendKeys("Overlord1");
		login_btn.click();
	}
	
	public void ordersNavigation() throws InterruptedException {
		waitFor(10);
		
		sales_menubar.click();
		waitFor(3);
		orders_menuItem.click();
		waitFor(10);
	}
	
	List<String> str;
	public void getExcelData() {
		Xls_Reader reader = new Xls_Reader("./src/main/resources/testdata/atriumID_datasheet.xlsx");
		str = new ArrayList<String>();
		int colCount = reader.getRowCount("TableData");
		System.out.println("Total Columns count" + colCount);
		for(int i=2; i<=colCount; i++) {
			String value = reader.getCellData("TableData", "AtriumID", i);
			System.out.println("Atrium ID from from excel " + value);
			str.add(value);
		}
		System.out.println("list values "+ str);
	}
	
	public void searchWithAtriumID() {
		waitFor(10);
		searchBox.clear();
		System.out.println("search field cleared");
		String values = Arrays.toString(str.toArray());
		searchBox.sendKeys(values + Keys.ENTER);
		waitFor(10);
	}
	Map<String, String> mapValues = new HashMap<String, String>();
	public void getCreatedDate() {
		List<WebElement> tableRows = driver.findElements(By.xpath("//tr[@class='data-row' or @class='data-row _odd-row']"));
		for(int i=2; i<tableRows.size()+2; i++) {			
			String createdDate = driver.findElement(By.xpath("//tbody/tr[" + i + "]/td[10]/div")).getText();
			String atriumID = driver.findElement(By.xpath("//tbody/tr[" + i + "]/td[17]/div")).getText();
			String name = driver.findElement(By.xpath("//tbody/tr[" + i +"]/td[3]/div")).getText();
			mapValues.put(atriumID, createdDate);
			System.out.println(atriumID + "----" + createdDate + "----" + name);
		}
//		System.out.println("Map value is: " + mapValues.get("85035473"));
	}
	
	public void update_db_table_with_MagentoDetails() throws SQLException {
		Connection connection = DriverManager.getConnection("jdbc:postgresql://ec2-52-36-238-103.us-west-2.compute.amazonaws.com:5432/perf", "admin","perfadmin");
		System.out.println("Connection to postgreSQL server successfully");
//		String sql = "INSERT INTO sfaccounts2 VALUES('4/10/2022 12:13 PM','prasadch', 1234, 'abc')";
		
		List<WebElement> rowCount = driver.findElements(By.xpath("//tr[@class='data-row' or @class='data-row _odd-row']"));
		for(int i=2; i<rowCount.size()+2; i++) {
			
			String createdDate = driver.findElement(By.xpath("//tbody/tr[" + i + "]/td[10]/div")).getText();
			String atriumID = driver.findElement(By.xpath("//tbody/tr[" + i + "]/td[17]/div")).getText();
			String acctName = driver.findElement(By.xpath("//tbody/tr[" + i +"]/td[3]/div")).getText();
			String searchType = "M";
			String searchID = PropertiesOperations.getPropertyValueByKey("accountSearchkey");
			
			String sql = "INSERT INTO perf (create_date, atrium_id, account_name, search_type, search_id) VALUES (?,?,?,?,?)";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, createdDate);
			statement.setString(2, atriumID);
			statement.setString(3, acctName);
			statement.setString(4, searchType);
			statement.setString(5, searchID);
			
			statement.executeUpdate();
			System.out.println(createdDate + " -- " + acctName + " -- " + atriumID + " -- " + searchType);
		}
		
		connection.close();
		System.out.println("Query completed...........");
	}
	
	public void getValuesNull() throws SQLException {
		Connection connection = DriverManager.getConnection("jdbc:postgresql://ec2-52-36-238-103.us-west-2.compute.amazonaws.com:5432/perf", "admin","perfadmin");
		String query ="select * from perf where m2_createdate IS NULL";

		Statement statement = connection.createStatement();
		ResultSet result = statement.executeQuery(query);
		int count=1;
		while(result.next()) {
			String id = result.getString("atrium_id");
//			System.out.println("id --- " +id);
			String m2_date = mapValues.get(id);
			updateRecords(id, m2_date);
			System.out.print(count +". " + id + " " );
			count++;
		}
		connection.close();
		System.out.println("done");
	}
	
	public void updateRecords(String id, String date) throws SQLException {
		Connection connection = DriverManager.getConnection("jdbc:postgresql://ec2-52-36-238-103.us-west-2.compute.amazonaws.com:5432/perf", "admin","perfadmin");
		
		String query = "Update perf SET m2_createdate = ? WHERE atrium_id =?";
		
		PreparedStatement stmt = connection.prepareStatement(query);
//		String str = "12345";
//		String str1 = "dp123";
		stmt.setString(1, date);
		stmt.setString(2, id);
		int i = stmt.executeUpdate();
		 if(i>0){
	          System.out.println("Record updated sucessfully");
	     }else{
	          System.out.println("No Such record in the Database");
	     }
		 
		connection.close();
	}

	
	public void update_db_table() throws SQLException {
		Connection connection = DriverManager.getConnection("jdbc:postgresql://ec2-52-36-238-103.us-west-2.compute.amazonaws.com:5432/perf", "admin","perfadmin");
		System.out.println("Connection to postgreSQL server successfully");
//		String sql = "INSERT INTO sfaccounts2 VALUES('4/10/2022 12:13 PM','prasadch', 1234, 'abc')";
		
		List<WebElement> rowCount = driver.findElements(By.xpath("//tr[@class='data-row' or @class='data-row _odd-row']"));
		for(int i=2; i<rowCount.size()+1; i++) {
			
			String M2createDate = driver.findElement(By.xpath("//tbody/tr[" + i + "]/td[10]/div")).getText();
			String atriumID = driver.findElement(By.xpath("//tbody/tr[" + i + "]/td[17]/div")).getText();
			
			String sql = "INSERT INTO perf (create_date, atrium_id, account_name, search_type) VALUES (?,?,?,?)";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, M2createDate);
			
			statement.executeUpdate();
			System.out.println(M2createDate + " -- ");
		}
		
		connection.close();
		System.out.println("Query completed...........");
	}
	
	@FindBy(css = "#username")
	private WebElement userName_txt;
	
	@FindBy(css = "#login")
	private WebElement password_text;
	
	@FindBy(xpath = "//span[contains(text(), 'Sign in')]")
	private WebElement login_btn;
	
	@FindBy(xpath = "//span[contains(text(), 'Customers')]/parent::a/parent::li[@id='menu-magento-customer-customer']")
	private WebElement sales_menubar;
	
	@FindBy(xpath = "//span[contains(text(), 'All Customers')]/parent::a/parent::li")
	private WebElement orders_menuItem;
	
	@FindBy(xpath = "//input[@type='text' and @id='fulltext']")
	private WebElement searchBox;
	
	@FindBy(xpath = "")
	private WebElement ff;
	
	@FindBy(xpath = "")
	private WebElement bb;
	
	@FindBy(xpath = "")
	private WebElement sd;
}
