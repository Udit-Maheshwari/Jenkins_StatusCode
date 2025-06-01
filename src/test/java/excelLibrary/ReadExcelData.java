package excelLibrary;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcelData {
	
	 static ExcelUtil reader;
//	    private static String testInputPath = System.getProperty("user.dir") + "//src//test//resources//test-input//";
//
//	    public static String getValue(String fileName, String sheet, String columnName, int rowNumber) {
//
//	        String filePath = testInputPath + fileName;
//	        String value = null;
//	        String result = null;
//	        try {
//	            DataFormatter formatter = new DataFormatter();
//	            Workbook wb = WorkbookFactory.create(new FileInputStream((new File(filePath))));
//
//	            int columnMax = wb.getSheet(sheet).getRow(0).getLastCellNum();
//	            // int rowMax=wb.getSheet(sheet).getLastRowNum();
//	            // logger.debug("Row count is " + rowNumber);
//
//	            for (int i = 0; i < columnMax; i++) {
//	                value = formatter.formatCellValue(wb.getSheet(sheet).getRow(0).getCell(i));
//	                if (columnName.equals(value)) {
//	                    // logger.debug(
//	                    // "Found entry corresponding to the column number : " + i + " and row number :
//	                    // " + rowNumber);
//	                    result = formatter.formatCellValue(wb.getSheet(sheet).getRow(rowNumber).getCell(i));
//	                    break;
//	                }
//	            }
//
//	        } catch (Exception e) {
//	            String message = "";
//
//	            for (StackTraceElement stackTraceElement : Thread.currentThread().getStackTrace()) {
//	                message = message + System.lineSeparator() + stackTraceElement.toString();
//	            }
//
//	            System.out.println(e);
//	            System.out.println(message);
//	        }
//
//	        if (result == "") {
//	            return null;
//	        } else
//	            return result;
//	    }
	    
	    private static String testInputPath = System.getProperty("user.dir") + "//src//test//resources//test-input//";

	    public static String getValue(String fileName, String sheetName, String columnName, int rowNumber) {

	        Workbook wb = null;
	        InputStream inputStream = null;
	        String result = null;

	        try {
	            // Try to load from classpath first (for Jenkins or Maven test runs)
	            inputStream = ExcelUtil.class.getClassLoader().getResourceAsStream("test-input/" + fileName);

	            // If not found in classpath, try absolute path (for local runs)
	            if (inputStream == null) {
	                File file = new File(testInputPath + fileName);
	                if (!file.exists()) {
	                    throw new FileNotFoundException("Excel file not found at: " + file.getAbsolutePath());
	                }
	                inputStream = new FileInputStream(file);
	            }

	            wb = WorkbookFactory.create(inputStream);

	            Sheet sheet = wb.getSheet(sheetName);
	            if (sheet == null) {
	                throw new IllegalArgumentException("Sheet '" + sheetName + "' not found in workbook.");
	            }

	            Row headerRow = sheet.getRow(0);
	            if (headerRow == null) {
	                throw new IllegalArgumentException("Header row is missing in sheet: " + sheetName);
	            }

	            DataFormatter formatter = new DataFormatter();
	            int columnMax = headerRow.getLastCellNum();

	            for (int i = 0; i < columnMax; i++) {
	                String header = formatter.formatCellValue(headerRow.getCell(i));
	                if (columnName.equalsIgnoreCase(header.trim())) {
	                    Row dataRow = sheet.getRow(rowNumber);
	                    if (dataRow != null) {
	                        result = formatter.formatCellValue(dataRow.getCell(i));
	                    }
	                    break;
	                }
	            }

	            wb.close();
	            inputStream.close();

	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        return (result == null || result.trim().isEmpty()) ? null : result.trim();
	    }
	    
	    public static void deleteAllSheetsInExcel(String fileName) throws IOException {
	        FileInputStream file = new FileInputStream(new File("./test-output/ExcelReports/" + fileName));
	        XSSFWorkbook workbook = new XSSFWorkbook(file);
	        XSSFSheet spreadsheet = workbook.getSheetAt(0);
	        try {
	            int totalRows = spreadsheet.getLastRowNum();
	            for (int j = 0; j <= totalRows; j++) {
	                XSSFRow row1 = spreadsheet.getRow(j);
	                spreadsheet.removeRow(row1);
	            }

	            int totalSheets = workbook.getNumberOfSheets();
	            for (int i = 1; i < totalSheets; i++) {
	                workbook.removeSheetAt(1);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            try {
	                int totalSheets = workbook.getNumberOfSheets();
	                for (int i = 1; i < totalSheets; i++) {
	                    workbook.removeSheetAt(1);
	                }
	            } catch (Exception ex) {
	            }

	        } finally {
	            FileOutputStream output = new FileOutputStream(new File("./test-output/ExcelReports/" + fileName));
	            workbook.write(output);
	            output.close();
	            workbook.close();
	        }
	    }
	    
}
