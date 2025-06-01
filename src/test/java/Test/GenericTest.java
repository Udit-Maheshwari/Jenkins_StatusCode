package Test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import excelLibrary.ReadExcelData;
import excelLibrary.WriteExcelData;
import pageObject.GenericPage;
import utils.GenericMethods;
import utils.WaitLibrary;

public class GenericTest extends GenericPage {
	
	@BeforeMethod
    public void initializePage() {
        PageFactory.initElements(driver, this);
    }

    SoftAssert sAssert = new SoftAssert();
    String inputFileName = "GenericTestDataInput.xlsx";
    // String inputSheetName = "FooterBID";
    String inputSheetName = "AEM_page_component";
    String inputSheetName1 = "Milo_page_component";
    GenericPage genPage = new GenericPage();
    long startTime;

    @BeforeClass()
    public void deleteExistingDataInReport() throws IOException {
        startTime = System.currentTimeMillis();
        String outputFilename = GenericMethods.getValue("application.properties", "Generic_file");
        WriteExcelData.deleteAllSheetsInExcel(outputFilename);
        GenericMethods.deleteAllFilesInDirectory(
                new File(System.getProperty("user.dir") + "//test-output//WordReports//FailedScreenshots//"));
        GenericMethods
                .deleteAllFilesInDirectory(new File(System.getProperty("user.dir") + "//test-output//WordReports//"));
    }
    
    @Test(groups = {"page_component"})
    public void testPageComponentsMilo() throws Exception {

        logger = extent.createTest("TC-01_Validate Components present on page")
                .assignAuthor("Rajesh").assignCategory("Smoke").assignDevice("WebBrowser");
        logger.pass("testPageComponents started");
        Map<Integer, Object[]> outputData = new LinkedHashMap<Integer, Object[]>();
        Map<String, String> pageCompMap = new HashMap<>();
        int rowNum = 1, count = 2;
        String inputSheetName = "Milo_page_component";
        String url = ReadExcelData.getValue(inputFileName, inputSheetName, "URLs", rowNum);
        try {
            outputData.put(1, new Object[]{"URL", "Geo Routing Modal", "Geo Routing Modal Status",
                    "Nav", "Nav Type", "Primary Nav Options", "Nav Status", "Breadcrumb", "Breadcrumb Text", "BreadCrumb Status", "Footer", "Footer Type", "Footer Status", "Region Selector", "Region Selector Working", "Region Selector Close Working", "Region Selector Status"});
            url = ReadExcelData.getValue(inputFileName, inputSheetName, "URLs", rowNum);
            while (!url.equals("")) {
                logger.debug("Webpage URL--> " + url);
                driver.get(url);
                System.out.println(url);
                WaitLibrary.iSleep(2);
                GenericMethods.scrollToPageEndInSteps();
                GenericPage.scrollDownTillFooterIsDisplayed();
                try {
                    PageFactory.initElements(driver, this);
                    pageCompMap = checkMiloPageComponents();
                } finally {
                    outputData.put(count, new Object[]{url,
                            pageCompMap.get("grm"),
                            pageCompMap.get("grmStatus"),
                            pageCompMap.get("nav"),
                            pageCompMap.get("navType"),
                            pageCompMap.get("navOptions"),
                            pageCompMap.get("navStatus"),
                            pageCompMap.get("brcmbs"),
                            pageCompMap.get("brcmbsTxt"),
                            pageCompMap.get("brcmbsStatus"),
                            pageCompMap.get("footer"),
                            pageCompMap.get("footerType"),
                            pageCompMap.get("footerStatus"),
                            pageCompMap.get("rgnslctr"),
                            pageCompMap.get("rgnslctrOpen"),
                            pageCompMap.get("rgnslctrClose"),
                            pageCompMap.get("rgnslctrStatus")
                    });
                    count++;
                    rowNum++;
                    url = ReadExcelData.getValue(inputFileName, inputSheetName, "URLs", rowNum);
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            String outputFilename = GenericMethods.getValue("application.properties", "Generic_file");
            WriteExcelData.writeDataInExcelSheetInAscOrder(outputData, outputFilename, "testPageComponents");
        }
    }
    
    
}
