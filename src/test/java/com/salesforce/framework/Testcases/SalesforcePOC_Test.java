package com.salesforce.framework.Testcases;

import java.awt.AWTException;
import java.io.IOException;
import java.sql.SQLException;

import org.testng.annotations.Test;
import com.salesforce.framework.Factory.TestBase;
import com.salesforce.framework.PageObject.AccountInformationPage;
import com.salesforce.framework.PageObject.SalesforcePOC;

public class SalesforcePOC_Test extends TestBase{
	SalesforcePOC sf;
	AccountInformationPage accounts;
	
	@Test
	public void launchSite() throws IOException, SQLException, AWTException {
		System.out.println("testing");
		sf = new SalesforcePOC(driver);
		accounts = new AccountInformationPage(driver);
		sf.doLogin();
		sf.simpleImportBtn();
		sf.fileUpload();
		sf.excelUpload();
		sf.masterDD();
		sf.loadMappingDD();
		sf.nextBtn();
		sf.submitBtn();
		accounts.clickOnAccountTab();
		accounts.setAllAccountsOption();
		accounts.accountSearch();
		accounts.allAccountsCheckbox();
		accounts.update_db_table();
	}
}
