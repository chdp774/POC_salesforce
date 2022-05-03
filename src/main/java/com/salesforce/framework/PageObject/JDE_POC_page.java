package com.salesforce.framework.PageObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.ibm.as400.access.AS400JDBCDriver;
import com.salesforce.framework.Util.PropertiesOperations;
import com.salesforce.framework.Util.Xls_Reader;

public class JDE_POC_page {
//	private WebDriver driver;
//	
//	public JDE_POC_page(WebDriver driver) {
//		PageFactory.initElements(driver, this);
//		this.driver = driver;
//	}
	List<String> dateList;
	Map<String, String> map_date = new HashMap<String, String>();
	Map<String, String> map_time = new HashMap<String, String>();
	Map<String, String> map_name = new HashMap<String, String>();
	public void DBconectionChecking(String atriumID) {
		try {
			System.out.println("Trying to connect........");
			String driver = "com.ibm.as400.access.AS400JDBCDriver";
			String url = "jdbc:as400://10.20.20.63";
			
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url, "ITREAD", "tech3qa1");
			
			String query = "select ABAN8, ABUPMJ, ABUPMT, ABDC from QADTA.F0101 Where ABAN8='" + atriumID +"'";

			Statement stmt = conn.createStatement();
			ResultSet result = stmt.executeQuery(query);
			while(result.next()) {
				String atriumid = result.getString("ABAN8");
				String lastUpdateDate = result.getString("ABUPMJ");
				String lastUpdateTime = result.getString("ABUPMT");
				String acctName = result.getString("ABDC");
				
				System.out.println(atriumid + " --- " + lastUpdateDate + " --- " + lastUpdateTime + " --- " + acctName );
				String date = dateFormat(lastUpdateDate);
				System.out.println("Formated date: "+date);
				String time = timeFormat(lastUpdateTime);
				
				String dateAndTime = date + " " +time;
//				dateList.add(date);
				map_date.put(atriumid, dateAndTime);
				map_name.put(atriumid, acctName);
				System.out.println("-----" + dateList.size());
			}
			System.out.println("-----DONE------");
			conn.close();
		}catch(Exception e) {
//			System.out.println("DB connection failed");
		}
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
	
	public void getIDs() throws SQLException {
		for(int i=0; i<str.size(); i++) {
			String atriumid = str.get(i);
			System.out.println("Atrium ID : " +atriumid);
			DBconectionChecking(atriumid);
			
			Connection connection = DriverManager.getConnection("jdbc:postgresql://ec2-52-36-238-103.us-west-2.compute.amazonaws.com:5432/perf", "admin","perfadmin");
			System.out.println("Connection to postgreSQL server successfully");
			String searchType = "J";
			String searchID = PropertiesOperations.getPropertyValueByKey("accountSearchkey");
			String sql = "INSERT INTO perf (create_date, atrium_id, account_name, search_type, search_id) VALUES (?,?,?,?,?)";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, map_date.get(atriumid));
			statement.setString(2, atriumid);
			statement.setString(3, map_name.get(atriumid));
			statement.setString(4, searchType);
			statement.setString(5, searchID);
			
			statement.executeUpdate();
//			System.out.println(createdDate + " -- " + acctName + " -- " + atriumID + " -- " + searchType);
		}
	}
	
	public static String dateFormat(String str) {
//		String str = "122102";
		char[] ch = str.toCharArray();
		String year = str.substring(0, 3);
		String daysCount = str.substring(3, 6);
		int daysCount_int = Integer.parseInt(daysCount);
//		System.out.println("days Count value is: "+daysCount);
//		System.out.println("year value is: "+ year);
		String Uyear = year.replaceAll(year, "2022");
//		System.out.println(Uyear + ":" + daysCount);
		
		LocalDate date = LocalDate.of(2022, Month.JANUARY, 1);
		LocalDate value = date.plusDays(daysCount_int - 1);
		String val = value.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
//		System.out.println("Formated date : " +val);
		return val;
	}
	
	public static String timeFormat(String str) {
		
		char[] ch = str.toCharArray();
			if(str.length() == 6) {
				System.out.println("Formated Time: " +ch[0] +""+ ch[1] + ":" + ch[2] + ch[3] + ":" + ch[4] + ch[5]);
				String str1 = ch[0] +""+ ch[1] + ":" + ch[2] + ch[3] + ":" + ch[4] + ch[5];
				System.out.println(str1);
				return str1;
			}else {
				System.out.println("Formated Time: " + "0" +ch[0]  + ":" + ch[1] + ch[2] + ":" + ch[3]+ ch[4]);
				String str2 =  "0" +ch[0]  + ":" + ch[1] + ch[2] + ":" + ch[3]+ ch[4];
				System.out.println(str2);
				return str2;
			}
	}
}
