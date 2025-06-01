package excelLibrary;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFCreationHelper;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ExcelUtil {
    public String path;
    public static FileInputStream fis = null;
    public FileOutputStream fileOut = null;
    private XSSFWorkbook workbook = null;
    private XSSFSheet sheet = null;
    private XSSFRow row = null;
    private XSSFCell cell = null;
    private static String testInputPath = System.getProperty("user.dir") + "//src//test//resources//test-input//";

    public ExcelUtil(String path) {
        this.path = path;
        try {
            fis = new FileInputStream(path);
            workbook = new XSSFWorkbook(fis);
            sheet = workbook.getSheetAt(0);
            fis.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    // returns the row count in a sheet
    public int getRowCount(String sheetName) {
        int index = workbook.getSheetIndex(sheetName);
        if (index == -1)
            return 0;
        else {
            sheet = workbook.getSheetAt(index);
            int number = sheet.getLastRowNum() + 1;
            return number;
        }
    }

    // returns the data from a cell
    @SuppressWarnings({"deprecation"})
    public String getCellData(String sheetName, String colName, int rowNum) {
        try {
            if (rowNum <= 0)
                return "";

            int index = workbook.getSheetIndex(sheetName);
            int col_Num = -1;
            if (index == -1)
                return "";

            sheet = workbook.getSheetAt(index);
            row = sheet.getRow(0);
            for (int i = 0; i < row.getLastCellNum(); i++) {
                //System.out.println(row.getCell(i).getStringCellValue().trim());
                if (row.getCell(i).getStringCellValue().trim().equals(colName.trim()))
                    col_Num = i;
            }
            if (col_Num == -1)
                return "";

            sheet = workbook.getSheetAt(index);
            row = sheet.getRow(rowNum - 1);
            if (row == null)
                return "";
            cell = row.getCell(col_Num);
            switch (cell.getCellType()) {
                case STRING:
                    return cell.getStringCellValue();
                case NUMERIC:
                    return String.valueOf(cell.getNumericCellValue());
                case BOOLEAN:
                    return String.valueOf(cell.getBooleanCellValue());
                case FORMULA:
                    return cell.getCellFormula();
                case BLANK:
                    return "";
                default:
                    return cell.getStringCellValue();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "row " + rowNum + " or column " + colName + " does not exist in xls";
        }
    }


    // returns the data from a cell
    public String getCellData(String sheetName, int colNum, int rowNum) {
        try {
            if (rowNum <= 0)
                return "";

            int index = workbook.getSheetIndex(sheetName);

            if (index == -1)
                return "";


            sheet = workbook.getSheetAt(index);
            row = sheet.getRow(rowNum - 1);
            if (row == null)
                return "";
            cell = row.getCell(colNum);
            switch (cell.getCellType()) {
                case STRING:
                    return cell.getStringCellValue();
                case NUMERIC:
                    return String.valueOf(cell.getNumericCellValue());
                case BOOLEAN:
                    return String.valueOf(cell.getBooleanCellValue());
                case FORMULA:
                    return cell.getCellFormula();
                case BLANK:
                    return "";
                default:
                    return cell.getStringCellValue();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "row " + rowNum + " or column " + colNum + " does not exist  in xls";
        }
    }


    // returns true if data is set successfully else false
    public boolean setCellData(String sheetName, String colName, int rowNum, String data) {
        try {
            fis = new FileInputStream(path);
            workbook = new XSSFWorkbook(fis);

            if (rowNum <= 0)
                return false;

            int index = workbook.getSheetIndex(sheetName);
            int colNum = -1;
            if (index == -1)
                return false;


            sheet = workbook.getSheetAt(index);


            row = sheet.getRow(0);
            for (int i = 0; i < row.getLastCellNum(); i++) {
                //System.out.println(row.getCell(i).getStringCellValue().trim());
                if (row.getCell(i).getStringCellValue().trim().equals(colName))
                    colNum = i;
            }
            if (colNum == -1)
                return false;

            sheet.autoSizeColumn(colNum);
            row = sheet.getRow(rowNum - 1);
            if (row == null)
                row = sheet.createRow(rowNum - 1);

            cell = row.getCell(colNum);
            if (cell == null)
                cell = row.createCell(colNum);

            // cell style
            //CellStyle cs = workbook.createCellStyle();
            //cs.setWrapText(true);
            //cell.setCellStyle(cs);
            cell.setCellValue(data);
            fileOut = new FileOutputStream(path);
            workbook.write(fileOut);
            fileOut.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    // returns true if data is set successfully else false
    public boolean setCellData(String sheetName, String colName, int rowNum, String data, String url) {
        //System.out.println("setCellData setCellData******************");
        try {
            fis = new FileInputStream(path);
            workbook = new XSSFWorkbook(fis);

            if (rowNum <= 0)
                return false;

            int index = workbook.getSheetIndex(sheetName);
            int colNum = -1;
            if (index == -1)
                return false;


            sheet = workbook.getSheetAt(index);
            //System.out.println("A");
            row = sheet.getRow(0);
            for (int i = 0; i < row.getLastCellNum(); i++) {
                //System.out.println(row.getCell(i).getStringCellValue().trim());
                if (row.getCell(i).getStringCellValue().trim().equalsIgnoreCase(colName))
                    colNum = i;
            }

            if (colNum == -1)
                return false;
            sheet.autoSizeColumn(colNum); //ashish
            row = sheet.getRow(rowNum - 1);
            if (row == null)
                row = sheet.createRow(rowNum - 1);

            cell = row.getCell(colNum);
            if (cell == null)
                cell = row.createCell(colNum);

            cell.setCellValue(data);
            //XSSFCreationHelper createHelper = workbook.getCreationHelper();

            //cell style for hyperlinks
            //by default hypelrinks are blue and underlined
            CellStyle hlink_style = workbook.createCellStyle();
            XSSFFont hlink_font = workbook.createFont();
            hlink_font.setUnderline(XSSFFont.U_SINGLE);
            hlink_font.setColor(IndexedColors.BLUE.getIndex());
            hlink_style.setFont(hlink_font);
            //hlink_style.setWrapText(true);

			/*XSSFHyperlink link = createHelper.createHyperlink(XSSFHyperlink.LINK_FILE);
			link.setAddress(url);
			cell.setHyperlink(link);
			cell.setCellStyle(hlink_style);*/

            fileOut = new FileOutputStream(path);
            workbook.write(fileOut);
            fileOut.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    // returns true if sheet is created successfully else false
    public boolean addSheet(String sheetname) {

        FileOutputStream fileOut;
        try {
            workbook.createSheet(sheetname);
            fileOut = new FileOutputStream(path);
            workbook.write(fileOut);
            fileOut.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    // returns true if sheet is removed successfully else false if sheet does not exist
    public boolean removeSheet(String sheetName) {
        int index = workbook.getSheetIndex(sheetName);
        if (index == -1)
            return false;

        FileOutputStream fileOut;
        try {
            workbook.removeSheetAt(index);
            fileOut = new FileOutputStream(path);
            workbook.write(fileOut);
            fileOut.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    // returns true if column is created successfully
    public boolean addColumn(String sheetName, String colName) {
        //System.out.println("**************addColumn*********************");

        try {
            fis = new FileInputStream(path);
            workbook = new XSSFWorkbook(fis);
            int index = workbook.getSheetIndex(sheetName);
            if (index == -1)
                return false;
            //XSSFCellStyle style = workbook.createCellStyle();
            //style.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
            //style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            sheet = workbook.getSheetAt(index);
            row = sheet.getRow(0);
            if (row == null)
                row = sheet.createRow(0);
            //cell = row.getCell();
            //if (cell == null)
            //System.out.println(row.getLastCellNum());
            if (row.getLastCellNum() == -1)
                cell = row.createCell(0);
            else
                cell = row.createCell(row.getLastCellNum());
            cell.setCellValue(colName);
            //cell.setCellStyle(style);
            fileOut = new FileOutputStream(path);
            workbook.write(fileOut);
            fileOut.close();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;


    }

    // removes a column and all the contents
    public boolean removeColumn(String sheetName, int colNum) {
        try {
            if (!isSheetExist(sheetName))
                return false;
            fis = new FileInputStream(path);
            workbook = new XSSFWorkbook(fis);
            sheet = workbook.getSheet(sheetName);
            workbook.getCreationHelper();

            for (int i = 0; i < getRowCount(sheetName); i++) {
                row = sheet.getRow(i);
                if (row != null) {
                    cell = row.getCell(colNum);
                    if (cell != null) {
                        //cell.setCellStyle(style);
                        row.removeCell(cell);
                    }
                }
            }
            fileOut = new FileOutputStream(path);
            workbook.write(fileOut);
            fileOut.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }

    // find whether sheets exists
    public boolean isSheetExist(String sheetName) {
        int index = workbook.getSheetIndex(sheetName);
        if (index == -1) {
            index = workbook.getSheetIndex(sheetName.toUpperCase());
            if (index == -1)
                return false;
            else
                return true;
        } else
            return true;
    }

    // returns number of columns in a sheet
    public int getColumnCount(String sheetName) {
        // check if sheet exists
        if (!isSheetExist(sheetName))
            return -1;
        sheet = workbook.getSheet(sheetName);
        row = sheet.getRow(0);
        if (row == null)
            return -1;
        return row.getLastCellNum();
    }

    //String sheetName, String testCaseName,String keyword ,String URL,String message
    public boolean addHyperLink(String sheetName, String screenShotColName, String testCaseName, int index, String url, String message) {
        //System.out.println("ADDING addHyperLink******************");
        url = url.replace('\\', '/');
        if (!isSheetExist(sheetName))
            return false;
        sheet = workbook.getSheet(sheetName);
        for (int i = 2; i <= getRowCount(sheetName); i++) {
            if (getCellData(sheetName, 0, i).equalsIgnoreCase(testCaseName)) {
                //System.out.println("**caught "+(i+index));
                setCellData(sheetName, screenShotColName, i + index, message, url);
                break;
            }
        }
        return true;
    }

    public int getCellRowNum(String sheetName, String colName, String cellValue) {

        for (int i = 2; i <= getRowCount(sheetName); i++) {
            if (getCellData(sheetName, colName, i).equalsIgnoreCase(cellValue)) {
                return i;
            }
        }
        return -1;

    }

    public static List<String> getPrice(String fileName, String sheet, String columnName, int rowNumber) {
        String filePath = testInputPath + fileName;
        String value = null;
        ArrayList<String> testData = new ArrayList<String>();
        try {
            DataFormatter formatter = new DataFormatter();
            Workbook wb = WorkbookFactory.create(new FileInputStream((new File(filePath))));

            int columnMax = wb.getSheet(sheet).getRow(0).getLastCellNum();
            // int rowMax=wb.getSheet(sheet).getLastRowNum();
            // logger.debug("Row count is " + rowNumber);

            for (int i = 0; i < columnMax; i++) {
                value = formatter.formatCellValue(wb.getSheet(sheet).getRow(0).getCell(i));
                if (columnName.equals(value)) {
                    // logger.debug(
                    // "Found entry corresponding to the column number : " + i + " and row number :
                    // " + rowNumber);
                    testData.add(formatter.formatCellValue(wb.getSheet(sheet).getRow(rowNumber).getCell(i)));
                    testData.add(formatter.formatCellValue(wb.getSheet(sheet).getRow(rowNumber).getCell(i + 1)));
                    break;
                }
            }

        } catch (Exception e) {
            String message = "";

            for (StackTraceElement stackTraceElement : Thread.currentThread().getStackTrace()) {
                message = message + System.lineSeparator() + stackTraceElement.toString();
            }

            System.out.println(e);
            System.out.println(message);
        }


        return testData;


    }

    //Fetching multiple values from the expected test data
    public static Map<String, String> getValues(String fileName, String sheet, String columnName, String actual_value) {

        String filePath = testInputPath + fileName;
        String value = null;
        int rowNumber = 1;
        Map<String, String> pricingDetails = new HashedMap<>();
        try {
            DataFormatter formatter = new DataFormatter();
            Workbook wb = WorkbookFactory.create(new FileInputStream((new File(filePath))));
            int columnMax = wb.getSheet(sheet).getRow(0).getLastCellNum();
            for (int i = 0; i < columnMax; i++) {
                value = formatter.formatCellValue(wb.getSheet(sheet).getRow(0).getCell(i));
                if (columnName.equals(value)) {
                    String expextedTestData = formatter.formatCellValue(wb.getSheet(sheet).getRow(rowNumber).getCell(i));
                    while (!expextedTestData.equals("")) {
                        expextedTestData = formatter.formatCellValue(wb.getSheet(sheet).getRow(rowNumber).getCell(i));
                        if (actual_value.equalsIgnoreCase(expextedTestData)) {
                            pricingDetails.put("expected_data", formatter.formatCellValue(wb.getSheet(sheet).getRow(rowNumber).getCell(i + 5)));
                            break;
                        } else {
                            rowNumber++;
                        }
                    }
                }
            }
        } catch (Exception e) {
            String message = "";
            for (StackTraceElement stackTraceElement : Thread.currentThread().getStackTrace()) {
                message = message + System.lineSeparator() + stackTraceElement.toString();
            }
            System.out.println(e);
            System.out.println(message);
        }
        return pricingDetails;
    }

    public static int getNumberOfRows(String fileName, String sheet) throws Exception, FileNotFoundException, IOException {
        String filePath = testInputPath + fileName;
        Workbook wb = WorkbookFactory.create(new FileInputStream((new File(filePath))));
        int physicalRows = wb.getSheet(sheet).getPhysicalNumberOfRows();
        return physicalRows;
    }


    public static Map<String, String> getValueFromRow(String fileName, String sheet, String columnName, int rowNumber) {
        String filePath = testInputPath + fileName;
        String value = null;
        //int rowNumber = 1;
        Map<String, String> result = new HashedMap<>();

        try {
            DataFormatter formatter = new DataFormatter();
            Workbook wb = WorkbookFactory.create(new FileInputStream((new File(filePath))));
            int columnMax = wb.getSheet(sheet).getRow(0).getLastCellNum();
            for (int i = 0; i < columnMax; i++) {
                value = formatter.formatCellValue(wb.getSheet(sheet).getRow(0).getCell(i));
                if (columnName.equals(value)) {
                    String expextedTestData = formatter.formatCellValue(wb.getSheet(sheet).getRow(rowNumber).getCell(i));
                    while (!expextedTestData.equals("")) {
                        expextedTestData = formatter.formatCellValue(wb.getSheet(sheet).getRow(rowNumber).getCell(i));
                        result.put("exp_value", expextedTestData);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            String message = "";
            for (StackTraceElement stackTraceElement : Thread.currentThread().getStackTrace()) {
                message = message + System.lineSeparator() + stackTraceElement.toString();
            }
            System.out.println(e);
            System.out.println(message);
        }
        return result;
    }


    // to run this on stand alone
    public static void main(String arg[]) throws IOException {


		/* datatable = new XLSReader(System.getProperty("user.dir")+"\\src\\com\\qtpselenium\\xls\\Controller.xlsx");
						for(int col=0 ;col< datatable.getColumnCount("TC5"); col++){
							System.out.println(datatable.getCellData("TC5", col, 1)); 
						} */
    }

    public static Map<String, String> getValuesWithColumnNames(String fileName, String sheet, String columnName, String actual_value) {

        String filePath = testInputPath + fileName;
        String value = null;
        int rowNumber = 1;
        Map<String, String> pricingDetails = new HashedMap<>();
        try {
            DataFormatter formatter = new DataFormatter();
            Workbook wb = WorkbookFactory.create(new FileInputStream((new File(filePath))));
            int columnMax = wb.getSheet(sheet).getRow(0).getLastCellNum();
            for (int i = 0; i < columnMax; i++) {
                value = formatter.formatCellValue(wb.getSheet(sheet).getRow(0).getCell(i));
                if (columnName.equals(value)) {
                    String expectedTestData = formatter.formatCellValue(wb.getSheet(sheet).getRow(rowNumber).getCell(i));
                    while (!expectedTestData.equals("")) {
                        expectedTestData = formatter.formatCellValue(wb.getSheet(sheet).getRow(rowNumber).getCell(i));
                        if (actual_value.equalsIgnoreCase(expectedTestData)) {
                            for (int j = 0; j < columnMax; j++) {
                                pricingDetails.put(formatter.formatCellValue(wb.getSheet(sheet).getRow(0).getCell(j)), formatter.formatCellValue(wb.getSheet(sheet).getRow(rowNumber).getCell(j)));
                            }
                            break;
                        } else {
                            rowNumber++;
                        }
                    }
                }
            }
        } catch (Exception e) {
            String message = "";
            for (StackTraceElement stackTraceElement : Thread.currentThread().getStackTrace()) {
                message = message + System.lineSeparator() + stackTraceElement.toString();
            }
            System.out.println(e);
            System.out.println(message);
        }
        //System.out.println("Pricing Details "+pricingDetails);
        return pricingDetails;
    }


    public static void sFrogColumnAppend(String fileName, String sheet, int rowNumber) {

        String filePath = testInputPath + fileName;

        String result = null;
        try {
            DataFormatter formatter = new DataFormatter();
            File excel = new File(filePath);
            fis = new FileInputStream(excel);
            XSSFWorkbook wb = new XSSFWorkbook(fis);
            XSSFSheet sh = wb.getSheet(sheet);
            int rowTotal = sh.getLastRowNum();
            int colTotal = sh.getRow(0).getLastCellNum();
            wb.getSheet(sheet).shiftColumns(2, colTotal, 2);
            FileOutputStream out = new FileOutputStream(new File(filePath));
            wb.write(out);
            out.close();
            while (rowNumber <= rowTotal) {
                //    result = formatter.formatCellValue(wb.getSheet(sheet).getRow(rowNumber).getCell(1));
                String country = utils.GenericMethods.getCountryNameFromURL("prod", result).get("country");
                String lang = utils.GenericMethods.getCountryNameFromURL("prod", result).get("locale");
                if (rowNumber == 1) {
                    wb.getSheet(sheet).getRow(0).createCell(2).setCellValue("Country");
                    wb.getSheet(sheet).getRow(0).createCell(3).setCellValue("Locale");
                }
                // Writing data in the appended column
                wb.getSheet(sheet).getRow(rowNumber).createCell(2).setCellValue(country);
                wb.getSheet(sheet).getRow(rowNumber).createCell(3).setCellValue(lang);
                FileOutputStream out1 = new FileOutputStream(new File(filePath));
                wb.write(out1);
                out1.close();
                rowNumber++;
            }
            wb.close();
            fis.close();

        } catch (Exception e) {
            String message = "";
            e.printStackTrace();
            for (StackTraceElement stackTraceElement : Thread.currentThread().getStackTrace()) {
                message = message + System.lineSeparator() + stackTraceElement.toString();
            }

            System.out.println(e);
            System.out.println(message);
        }
    }


    public static Map<String, String> getExpValuesWithColumnNames(String fileName, String sheet, String columnName, String actual_value) {

        String filePath = testInputPath + fileName;
        String value = null;
        int rowNumber = 1;
        Map<String, String> pricingDetails = new HashedMap<>();
        try {
            DataFormatter formatter = new DataFormatter();
            Workbook wb = WorkbookFactory.create(new FileInputStream((new File(filePath))));
            int columnMax = wb.getSheet(sheet).getRow(0).getLastCellNum();
            for (int i = 0; i < columnMax; i++) {
                value = formatter.formatCellValue(wb.getSheet(sheet).getRow(0).getCell(i));
                if (columnName.equals(value)) {
                    String expectedTestData = formatter.formatCellValue(wb.getSheet(sheet).getRow(rowNumber).getCell(i));
                    while (!expectedTestData.equals("")) {
                        expectedTestData = formatter.formatCellValue(wb.getSheet(sheet).getRow(rowNumber).getCell(i));
                        expectedTestData = expectedTestData.toLowerCase();
                        actual_value = actual_value.toLowerCase();
                        if (expectedTestData.contains(actual_value)) {
                            for (int j = 0; j < columnMax; j++) {
                                pricingDetails.put(formatter.formatCellValue(wb.getSheet(sheet).getRow(0).getCell(j)), formatter.formatCellValue(wb.getSheet(sheet).getRow(rowNumber).getCell(j)));
                            }
                            break;
                        } else {
                            rowNumber++;
                        }
                    }
                }
            }
        } catch (Exception e) {
            String message = "";
            for (StackTraceElement stackTraceElement : Thread.currentThread().getStackTrace()) {
                message = message + System.lineSeparator() + stackTraceElement.toString();
            }
            System.out.println(e);
            System.out.println(message);
        }
        //System.out.println("Pricing Details "+pricingDetails);
        return pricingDetails;
    }

    public static Map<String, String> getValuesWithColumnNames(String fileName, String sheet, String columnName1, String columnValue1, String columnName2, String columnValue2) {

        String filePath = testInputPath + fileName;
        String value = null;
        String value2 = "";
        int rowNumber1 = 1, rowNumber2 = 1;
        Map<String, String> pricingDetails = new HashedMap<>();
        try {
            DataFormatter formatter = new DataFormatter();
            Workbook wb = WorkbookFactory.create(new FileInputStream((new File(filePath))));
            int columnMax = wb.getSheet(sheet).getRow(0).getLastCellNum();
            int columnValue1RowNumber;
            outerLoop:
            for (int i = 0; i < columnMax; i++) {
                value = formatter.formatCellValue(wb.getSheet(sheet).getRow(0).getCell(i));
                if (columnName1.equals(value)) {
                    String expectedTestData = formatter.formatCellValue(wb.getSheet(sheet).getRow(rowNumber1).getCell(i));
                    while (!expectedTestData.equals("")) {
                        expectedTestData = formatter.formatCellValue(wb.getSheet(sheet).getRow(rowNumber1).getCell(i));
                        if (columnValue1.equalsIgnoreCase(expectedTestData)) {
                            columnValue1RowNumber = rowNumber1;
                            for (int k = 0; k < columnMax; k++) {
                                value2 = formatter.formatCellValue(wb.getSheet(sheet).getRow(0).getCell(k));
                                if (columnName2.equals(value2)) {
                                    rowNumber2 = 1;
                                    try {
                                        String expectedTestData2 = formatter.formatCellValue(wb.getSheet(sheet).getRow(rowNumber2).getCell(k));
                                        while (!expectedTestData2.equals("")) {
                                            expectedTestData2 = formatter.formatCellValue(wb.getSheet(sheet).getRow(rowNumber2).getCell(k));
                                            if (columnValue2.equalsIgnoreCase(expectedTestData2) && columnValue1RowNumber == rowNumber2) {
                                                for (int j = 0; j < columnMax; j++) {
                                                    pricingDetails.put(formatter.formatCellValue(wb.getSheet(sheet).getRow(0).getCell(j)), formatter.formatCellValue(wb.getSheet(sheet).getRow(rowNumber2).getCell(j)));
                                                }
                                                break outerLoop;
                                            } else {
                                                rowNumber2++;
                                            }
                                        }
                                    } catch (Exception e) {
                                    }
                                }
                            }
                        }
                        rowNumber1++;
                    }
                }
            }
        } catch (Exception e) {
            String message = "";
            for (StackTraceElement stackTraceElement : Thread.currentThread().getStackTrace()) {
                message = message + System.lineSeparator() + stackTraceElement.toString();
            }
            System.out.println(e);
            System.out.println(message);
        }
        //System.out.println("Pricing Details "+pricingDetails);
        return pricingDetails;
    }

    public static Map<String, String> getExpValuesWithColumnNames2(String fileName, String sheet, String columnName, String actual_value) {

        String filePath = testInputPath + fileName;
        String value = null;
        int rowNumber = 1;
        Map<String, String> pricingDetails = new HashedMap<>();
        try {
            DataFormatter formatter = new DataFormatter();
            Workbook wb = WorkbookFactory.create(new FileInputStream((new File(filePath))));
            int columnMax = wb.getSheet(sheet).getRow(0).getLastCellNum();
            for (int i = 0; i < columnMax; i++) {
                value = formatter.formatCellValue(wb.getSheet(sheet).getRow(0).getCell(i));
                if (columnName.equals(value)) {
                    String expectedTestData = formatter.formatCellValue(wb.getSheet(sheet).getRow(rowNumber).getCell(i));
                    while (!expectedTestData.equals("")) {
                        expectedTestData = formatter.formatCellValue(wb.getSheet(sheet).getRow(rowNumber).getCell(i));
                        expectedTestData = expectedTestData.toLowerCase();
                        actual_value = actual_value.toLowerCase();
                        if (actual_value.contains(expectedTestData)) {
                            for (int j = 0; j < columnMax; j++) {
                                pricingDetails.put(formatter.formatCellValue(wb.getSheet(sheet).getRow(0).getCell(j)), formatter.formatCellValue(wb.getSheet(sheet).getRow(rowNumber).getCell(j)));
                            }
                            break;
                        } else {
                            rowNumber++;
                        }
                    }
                }
            }
        } catch (Exception e) {
            String message = "";
            for (StackTraceElement stackTraceElement : Thread.currentThread().getStackTrace()) {
                message = message + System.lineSeparator() + stackTraceElement.toString();
            }
            System.out.println(e);
            System.out.println(message);
        }
        //System.out.println("Pricing Details "+pricingDetails);
        return pricingDetails;
    }
    
    public static Map<String, String> getValuesWithColumnNames_ContainsColumnValue1(String fileName, String sheet, String columnName1, String columnValue1, String columnName2, String columnValue2) {

        String filePath = testInputPath + fileName;
        String value = null;
        String value2 = "";
        int rowNumber1 = 1, rowNumber2 = 1;
        Map<String, String> pricingDetails = new HashedMap<>();
        try {
            DataFormatter formatter = new DataFormatter();
            Workbook wb = WorkbookFactory.create(new FileInputStream((new File(filePath))));
            int columnMax = wb.getSheet(sheet).getRow(0).getLastCellNum();
            int columnValue1RowNumber;
            outerLoop:
            for (int i = 0; i < columnMax; i++) {
                value = formatter.formatCellValue(wb.getSheet(sheet).getRow(0).getCell(i));
                if (columnName1.equals(value)) {
                    String expectedTestData = formatter.formatCellValue(wb.getSheet(sheet).getRow(rowNumber1).getCell(i));
                    while (!expectedTestData.equals("")) {
                        expectedTestData = formatter.formatCellValue(wb.getSheet(sheet).getRow(rowNumber1).getCell(i));
                        if (columnValue1.contains(expectedTestData)) {
                            columnValue1RowNumber = rowNumber1;
                            for (int k = 0; k < columnMax; k++) {
                                value2 = formatter.formatCellValue(wb.getSheet(sheet).getRow(0).getCell(k));
                                if (columnName2.equals(value2)) {
                                    rowNumber2 = 1;
                                    try {
                                        String expectedTestData2 = formatter.formatCellValue(wb.getSheet(sheet).getRow(rowNumber2).getCell(k));
                                        while (!expectedTestData2.equals("")) {
                                            expectedTestData2 = formatter.formatCellValue(wb.getSheet(sheet).getRow(rowNumber2).getCell(k));
                                            if (columnValue2.equalsIgnoreCase(expectedTestData2) && columnValue1RowNumber == rowNumber2) {
                                                for (int j = 0; j < columnMax; j++) {
                                                    pricingDetails.put(formatter.formatCellValue(wb.getSheet(sheet).getRow(0).getCell(j)), formatter.formatCellValue(wb.getSheet(sheet).getRow(rowNumber2).getCell(j)));
                                                }
                                                break outerLoop;
                                            } else {
                                                rowNumber2++;
                                            }
                                        }
                                    } catch (Exception e) {
                                    }
                                }
                            }
                        }
                        rowNumber1++;
                    }
                }
            }
        } catch (Exception e) {
            String message = "";
            for (StackTraceElement stackTraceElement : Thread.currentThread().getStackTrace()) {
                message = message + System.lineSeparator() + stackTraceElement.toString();
            }
            System.out.println(e);
            System.out.println(message);
        }
        //System.out.println("Pricing Details "+pricingDetails);
        return pricingDetails;
    }

    public static Map<String, String> getValuesWithColumnNamesUsingKeyValuesInSheet(String fileName, String sheet, String columnName, String actual_value) {

        String filePath = testInputPath + fileName;
        String value = null;
        int rowNumber = 1;
        Map<String, String> pricingDetails = new HashedMap<>();
        try {
            DataFormatter formatter = new DataFormatter();
            Workbook wb = WorkbookFactory.create(new FileInputStream((new File(filePath))));
            int columnMax = wb.getSheet(sheet).getRow(0).getLastCellNum();
            for (int i = 0; i < columnMax; i++) {
                value = formatter.formatCellValue(wb.getSheet(sheet).getRow(0).getCell(i));
                if (columnName.equals(value)) {
                    String expectedTestData = formatter.formatCellValue(wb.getSheet(sheet).getRow(rowNumber).getCell(i));
                    whileLoop :
                    while (!expectedTestData.equals("")) {
                        expectedTestData = formatter.formatCellValue(wb.getSheet(sheet).getRow(rowNumber).getCell(i));
                        if(expectedTestData.equals("NA")){
                            rowNumber++;
                            continue whileLoop;
                        }
                        List<List<String>> allKeyValues = new ArrayList<List<String>>();
                        String[] mainValues = expectedTestData.split(";");
                        for (String mainValue : mainValues) {
                            List<String> keyValues = new ArrayList<String>();
                            if(!mainValue.trim().isBlank()){
                                String[] subValues = mainValue.split("/");
                                for (String subValue : subValues) {
                                    keyValues.add(subValue.trim());
                                }
                                keyValues = keyValues.stream().filter(val -> !val.isBlank()).collect(Collectors.toList());
                                allKeyValues.add(keyValues);
                            }
                        }
                        System.out.println("All Key Values : "+ allKeyValues);
                        int cnt = 0;
                        for (int l = 0; l < allKeyValues.size(); l++) {
                            if (allKeyValues.get(l).stream().anyMatch(key -> actual_value.contains(key))) {
                                cnt++;
                            }
                        }
                        if (cnt == allKeyValues.size()) {
                            for (int j = 0; j < columnMax; j++) {
                                pricingDetails.put(formatter.formatCellValue(wb.getSheet(sheet).getRow(0).getCell(j)), formatter.formatCellValue(wb.getSheet(sheet).getRow(rowNumber).getCell(j)));
                            }
                            break;
                        } else {
                            rowNumber++;
                        }
                        /*if (actual_value.equalsIgnoreCase(expectedTestData)) {
                            for (int j = 0; j < columnMax; j++) {
                                pricingDetails.put(formatter.formatCellValue(wb.getSheet(sheet).getRow(0).getCell(j)), formatter.formatCellValue(wb.getSheet(sheet).getRow(rowNumber).getCell(j)));
                            }
                            break;
                        } else {
                            rowNumber++;
                        }*/
                    }
                }
            }
        } catch (Exception e) {
            String message = "";
            for (StackTraceElement stackTraceElement : Thread.currentThread().getStackTrace()) {
                message = message + System.lineSeparator() + stackTraceElement.toString();
            }
            System.out.println(e);
            System.out.println(message);
        }
        //System.out.println("Pricing Details "+pricingDetails);
        return pricingDetails;
    }


}



