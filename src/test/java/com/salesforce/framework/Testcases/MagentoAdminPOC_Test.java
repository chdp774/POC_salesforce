package com.salesforce.framework.Testcases;

import java.sql.SQLException;
import org.testng.annotations.Test;

import com.salesforce.framework.Factory.TestBase_M2;
import com.salesforce.framework.PageObject.MagentoAdmin_POC_page;
import com.salesforce.framework.Util.ExcelOperations;

public class MagentoAdminPOC_Test extends TestBase_M2{
	MagentoAdmin_POC_page m2;
	ExcelOperations excel = new ExcelOperations("TableData");

	@Test()
	public void BeneficiaryRegistrationMap() throws InterruptedException, SQLException {
		m2 = new MagentoAdmin_POC_page(driver);
		m2.M2Login();
		m2.ordersNavigation();
		m2.getExcelData();
		m2.searchWithAtriumID();
		m2.update_db_table_with_MagentoDetails();
//		m2.getCreatedDate();
//		m2.getValuesNull();
	}
}
