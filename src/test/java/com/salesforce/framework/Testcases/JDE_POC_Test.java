package com.salesforce.framework.Testcases;

import java.sql.SQLException;

import org.testng.annotations.Test;

import com.salesforce.framework.PageObject.JDE_POC_page;
import com.salesforce.framework.PageObject.MagentoAdmin_POC_page;
import com.salesforce.framework.Util.ExcelOperations;

public class JDE_POC_Test {
	JDE_POC_page jde;
	ExcelOperations excel = new ExcelOperations("TableData");

	@Test()
	public void BeneficiaryRegistrationMap() throws InterruptedException, SQLException {
		jde = new JDE_POC_page();
		jde.getExcelData();
		jde.getIDs();
	}
}
