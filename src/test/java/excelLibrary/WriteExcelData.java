package excelLibrary;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

public class WriteExcelData {
    static XSSFWorkbook workbook = new XSSFWorkbook();
    static XSSFSheet spreadsheet = workbook.createSheet("Results");
    static XSSFRow row;
    static XSSFFont font = workbook.createFont();
    static XSSFCellStyle style = workbook.createCellStyle();

    @SuppressWarnings("unused")
    public static void writeDataintoExcel(Map<String, Object[]> outputData, String fileName) throws IOException {
        Set<String> keyid = outputData.keySet();
        int rowid = 0;
        // For bold style font
        font.setBold(true);
        style.setFont(font);

        // writing the data into the sheets...
        for (String key : keyid) {
            row = spreadsheet.createRow(rowid++);
            Object[] objectArr = outputData.get(key);
            int cellid = 0;
            for (Object obj : objectArr) {
                Cell cell = row.createCell(cellid++);
                if (key.equals("1"))
                    cell.setCellStyle(style);
                cell.setCellValue((String) obj);
            }
        }
        FileOutputStream out = new FileOutputStream(new File("./test-output/ExcelReports/" + fileName));
        workbook.write(out);
        out.close();
    }
    
    public static void writeDataInExcelSheetInAscOrder(Map<Integer, Object[]> outputData, String fileName, String sheetName) throws IOException {
        try {
            FileInputStream fis = new FileInputStream(new File("./test-output/ExcelReports/" + fileName));
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            XSSFSheet spreadsheet = workbook.getSheetAt(0);
            XSSFRow row;
            XSSFFont font = workbook.createFont();
            XSSFCellStyle style = workbook.createCellStyle();

            if (sheetName.length() > 31) {
                sheetName = sheetName.substring(0, 31);
            }
            int count = 0;
            int totalSheets = workbook.getNumberOfSheets();
            System.out.println("Sheet name " + sheetName);
            for (int i = 0; i < totalSheets; i++) {
                spreadsheet = workbook.getSheetAt(i);
                if (spreadsheet.getSheetName().equals(sheetName)) {
                    spreadsheet = workbook.getSheet(sheetName);
                    break;
                } else {
                    count++;
                }
            }

            if (count == totalSheets) {
                spreadsheet = workbook.createSheet(sheetName);
            }

            Set<Integer> keyid = new TreeSet<>(outputData.keySet());

            int rowid = 0;
            // For bold style font
            font.setBold(true);
            style.setFont(font);

            CellStyle greenStyle = workbook.createCellStyle();
            greenStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
            greenStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            CellStyle redStyle = workbook.createCellStyle();
            redStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
            redStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            Font whiteFont = workbook.createFont();
            whiteFont.setColor(IndexedColors.WHITE.getIndex());
            redStyle.setFont(whiteFont);
            greenStyle.setFont(whiteFont);

            // writing the data into the sheets...
            for (Integer key : keyid) {
                try {
                    row = spreadsheet.createRow(rowid++);
                    Object[] objectArr = outputData.get(key);
                    int cellid = 0;
                    for (Object obj : objectArr) {
                        try {
                            Cell cell = row.createCell(cellid++);
                            if (key == 1)
                                cell.setCellStyle(style);
                            if (obj.toString().equals("Pass")) {
                                cell.setCellStyle(greenStyle);
                            } else if (obj.toString().equals("Fail")) {
                                cell.setCellStyle(redStyle);
                            }
                            cell.setCellValue((String) obj);

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                } catch (Exception en) {
                    en.printStackTrace();
                }
            }
            FileOutputStream out = new FileOutputStream(new File("./test-output/ExcelReports/" + fileName));
            workbook.write(out);
            out.close();
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    //------Method to delete all existing data in the workbook -----//
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
