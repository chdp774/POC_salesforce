package com.salesforce.framework.Util;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentSetup {
	static ExtentReports extent;
	
	public static ExtentReports setupExtentReport() {
		
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");
		Date date = new Date();
		String actualDate = format.format(date);
		
		String reportPath = System.getProperty("user.dir")+"/Reports/ExecutionReport_"+actualDate+".html";
		
		ExtentSparkReporter sparkReport = new ExtentSparkReporter(reportPath);
		extent = new ExtentReports();
		extent.attachReporter(sparkReport); 
		sparkReport.config().setDocumentTitle("Imagility report");
		sparkReport.config().setTheme(Theme.DARK);
		sparkReport.config().setReportName("prasad");
		
//		extent.setSystemInfo("Executed on Environment: ", PropertiesOperations.getPropertyValueByKey("imagilityURL"));
//		extent.setSystemInfo("Executed on Browser: ", PropertiesOperations.getPropertyValueByKey("browser"));
		extent.setSystemInfo("Executed on Environment: ", System.getProperty("os.name"));
		extent.setSystemInfo("Executed on Environment: ", System.getProperty("user.name"));
		
		return extent;
	}
}