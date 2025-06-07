package Test;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import org.junit.BeforeClass;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import excelLibrary.ReadExcelData;
import excelLibrary.WriteExcelData;
import pageObject.GenericPage;
import pageObject.PageType;
import utils.GenericMethods;
import utils.WaitLibrary;

public class PageTypeTest extends PageType{
	
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
    
    @Test
    public void verifyPageTypeIsMilo_Dexter() throws IOException {
        logger = extent.createTest("Verify Product Page Is Milo Or Dexter")
                .assignAuthor("Udit").assignCategory("ALL").assignDevice("WebBrowser");
        logger.pass("verifyPageIsMilo_Dexter started");
        Map<Integer, Object[]> outputData = new TreeMap<Integer, Object[]>();
        inputSheetName = "Milo_page_component";
        sAssert = new SoftAssert();
        int rowNum = 1, count = 2;
        String url;
        try {
            outputData.put(1, new Object[]{"Source URL", "Country", "Locale", "Current Page Type Is Milo/Dexter"});
            url = ReadExcelData.getValueUsingJenkinsExcel(inputSheetName, "URLs", rowNum);
            while (!url.equals("")) {
                logger.debug("Webpage URL--> " + url);
                driver.get(url);
                System.out.println("Page open is -->" + url);
                GenericMethods.waitTillPageLoads();
                GenericMethods.scrollToPageEndInSteps();
                GenericPage.scrollDownTillFooterIsDisplayed();
                genPage.closeGeoPopUpModal();
                WaitLibrary.iWaitMs(2000);

                String country = "", locale = "";
                try {
                    if (url.contains("author.prod") || url.contains("author.corp")) {
                        country = GenericMethods.getCountryNameFromURL("prod", url).get("country").replaceAll("/", "");
                        locale = GenericMethods.getCountryNameFromURL("prod", url).get("locale").replaceAll("/", "");
                    } else {
                        country = GenericMethods.getCountryNameFromURL("live", url).get("country").replaceAll("/", "");
                        locale = GenericMethods.getCountryNameFromURL("live", url).get("locale").replaceAll("/", "");
                    }
                } catch (Exception e) {
                }

                String pageType = "";
                try {
                    pageType = verifyCurrentPageTypeIsMilo_Dexter();
                    sAssert.assertFalse(pageType.isBlank(), url + " Current page Type is not fetching");
                    if (pageType == null || pageType.isBlank()) {
                        pageType = "NA";
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    outputData.put(count, new Object[]{url, country, locale, pageType});
                    count++;
                    rowNum++;
                    url = ReadExcelData.getValueUsingJenkinsExcel(inputSheetName, "URLs", rowNum);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            String outputFilename = GenericMethods.getValue("application.properties", "Generic_file");
            WriteExcelData.writeDataInExcelSheetInAscOrder(outputData, outputFilename, "verifyPageIsMilo_Dexter");
            sAssert.assertAll();
        }
    }

}
