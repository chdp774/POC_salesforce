package com.salesforce.framework.Util;

import java.io.File;
import java.util.HashMap;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelOperations {
	String filePath = "./src/main/resources/testdata/Imagility_testdata.xlsx";
	Sheet sh;

	public ExcelOperations(String sheetName) {
		// Open file -- Workbook
		File testDataFile = new File(filePath);
		Workbook wb = null;
		try {
			wb = WorkbookFactory.create(testDataFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		sh = wb.getSheet(sheetName);
	}

	// Get testdata from sheet in hashmap based on rownum
	public HashMap<String, String> getTestDataInMap(int rowNum) throws Exception {
		// Read data row by row and put in map
		HashMap<String, String> hm = new HashMap<String, String>();

		for (int i = 0; i < sh.getRow(0).getLastCellNum(); i++) {
			sh.getRow(rowNum).getCell(i).setCellType(CellType.STRING);
			hm.put(sh.getRow(0).getCell(i).toString(), sh.getRow(rowNum).getCell(i).toString());
		}

		return hm;
	}

	// Get row count
	public int getRowCount() {
		return sh.getLastRowNum();
	}

	// Get Col count
	public int getColCount() {
		return sh.getRow(0).getLastCellNum();
	}

}
