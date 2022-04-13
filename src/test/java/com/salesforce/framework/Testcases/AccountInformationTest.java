package com.salesforce.framework.Testcases;

import java.io.IOException;
import java.sql.SQLException;

import org.testng.annotations.Test;

import com.salesforce.framework.Factory.TestBase;
import com.salesforce.framework.PageObject.AccountInformationPage;
import com.salesforce.framework.PageObject.SalesforcePOC;

public class AccountInformationTest extends TestBase{
SalesforcePOC sf;
AccountInformationPage accounts;
	@Test
	public void launchSite() throws IOException, SQLException {
		System.out.println("testing");
		sf = new SalesforcePOC(driver);
		accounts = new AccountInformationPage(driver);
		sf.doLogin();
		accounts.clickOnAccountTab();
		accounts.setAllAccountsOption();
		accounts.accountSearch();
		accounts.allAccountsCheckbox();
//		accounts.tabledata();
//		accounts.update_db_table();
	}
}
