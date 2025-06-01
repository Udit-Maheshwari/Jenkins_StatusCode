package Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import utils.GenericMethods;

//import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {

	public static WebDriver driver;
	protected static ExtentHtmlReporter reporters;
	protected static ExtentReports extent;
	protected static ExtentTest logger;
	protected static Actions act;
	protected static WebDriverWait wait;
	protected static Properties prop;
	private static String propFilePath = System.getProperty("user.dir") + "//src//test//resources//config//application.Properties";
	protected static String folderName;
	protected static String path;
	protected static String url;
	static String browser;
	static String testEnv;
    public static ChromeOptions chOptions = new ChromeOptions();



    @BeforeSuite(alwaysRun = true)
    public void beforeSuiteConfig() {
        BaseTest.initializeReportAndReadProperty();
    }

    @BeforeClass(alwaysRun = true)
    public void launchbrowser() {
        testEnv = GenericMethods.fetchProperty("testEnv");
        browser = GenericMethods.fetchProperty("browser");
        driver = BaseTest.launchBrowser(browser, testEnv);
        act = new Actions(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    /*@BeforeMethod
    public void hitHomePageUrl()
    {
        String url = CommonMethods.fetchProperty("url");
        CommonMethods.openUrl(url);
    }*/
    @AfterMethod(alwaysRun = true)
    public void getResult(ITestResult result) throws Exception {
        if (result.getStatus() == ITestResult.FAILURE) {
            try {
                String ss = GenericMethods.CaptureScreenshot(result.getName());
                logger.log(Status.FAIL, (String) result.getThrowable().getMessage(), MediaEntityBuilder.createScreenCaptureFromPath(path + "//screenshot//" + ss + ".png").build());
            } catch (IOException e) {

                e.printStackTrace();
            }
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            logger.pass(result.getName() + " test case is verified");
            logger.log(Status.PASS, MarkupHelper.
                    createLabel(" Verified", ExtentColor.GREEN));
        } else if (result.getStatus() == ITestResult.SKIP) {
            logger.skip(result.getName() + "this test case is skipped");

        }
        extent.flush();
    }

    @AfterClass(alwaysRun = true)
    public void closebrowser() {
        //this method will close the browser
        GenericMethods.browserClose();
    }

    @AfterSuite(alwaysRun = true)
    public void aftersuitConfig() throws Exception {
        //this method will save the report
        extent.flush();
    }

    /////***************Base class methods****************////
    //this method will launch browser
    public static WebDriver launchBrowser(String browser, String testEnv) {
    	browser = "chrome";
        if (browser.equalsIgnoreCase("chrome")) {
            //WebDriverManager.chromedriver().setup();
            System.setProperty("webdriver.http.factory", "jdk-http-client");
            //ChromeOptions chOptions = new ChromeOptions();
            try {
                chOptions.addArguments("--start-maximized");
                chOptions.addArguments("--disable-notifications");
                chOptions.addArguments("--disable-extensions");
                //if(testEnv.equalsIgnoreCase("prod") || testEnv.equalsIgnoreCase("stage")) {
                //chOptions.setExperimentalOption("debuggerAddress", "localhost:2125");
                //}
            } catch (Exception ex) {
            }
            driver = new ChromeDriver(chOptions);
        } else if (browser.equalsIgnoreCase("Edge")) {

            //	WebDriverManager.edgedriver().setup();

            //WebDriverManager.edgedriver().setup();
            EdgeOptions edOptions = new EdgeOptions();
            edOptions.addArguments("--start-maximized");
            edOptions.addArguments("--disable-notifications");
            edOptions.addArguments("--disable-extensions");
            if (testEnv.equalsIgnoreCase("prod") || testEnv.equalsIgnoreCase("stage")) {
                edOptions.setExperimentalOption("debuggerAddress", "localhost:2125");
            }
        } else if (browser.equalsIgnoreCase("Firefox")) {
            //WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        } else if (browser.equalsIgnoreCase("IE")) {

            //WebDriverManager.iedriver().setup();
            //WebDriverManager.iedriver().setup();

        }
        driver.manage().deleteAllCookies();
        //driver.manage().window().maximize();
        return driver;
    }

    //this method will read data from properties file
    public static void readProperty() {
        try {
            prop = new Properties();
            FileInputStream reader = new FileInputStream(propFilePath);
            prop.load(reader);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    //this method will initialize the extent report and read property file
    public static void initializeReportAndReadProperty() {
        BaseTest.readProperty();
        path = createFolderForReportAndMovePreviousResultToArchiveFolder();
        reporters = new ExtentHtmlReporter(path + "/index.html");
        reporters.config().setTheme(Theme.DARK);
        reporters.config().setDocumentTitle("adobe test extent report");
        reporters.config().setReportName("adobe test report");
        extent = new ExtentReports();
        extent.attachReporter(reporters);

    }

    //this method will check the presence of Archived test results folder if not present this will create it
    private static void createFolderIfNotPresent(List<String> dirName) {
        for (String file : dirName) {
            File currentDir = new File(System.getProperty("user.dir") + "/" + file);
            if (!currentDir.exists())
                currentDir.mkdir();
        }
    }

    //this method will create the folder for report and move previous result to archive folder
    private static String createFolderForReportAndMovePreviousResultToArchiveFolder() {
        List<String> list = new ArrayList<String>();
        list.add(GenericMethods.fetchProperty("archiveResultFolder"));
        list.add(GenericMethods.fetchProperty("currentResultFolder"));
        createFolderIfNotPresent(list);
        File currentDir = new File(System.getProperty("user.dir") + "//" + GenericMethods.fetchProperty("currentResultFolder")); // current directory
        File destination = new File(System.getProperty("user.dir") + "//" + GenericMethods.fetchProperty("archiveResultFolder"));
        moveDirectoryContents(currentDir, "Test Report", destination);
        folderName = "Test Report" + GenericMethods.getDateInGivenFormat("_dd_MM_yyyy_HH_mm");
        String path = System.getProperty("user.dir") + "//" + GenericMethods.fetchProperty("currentResultFolder") + "//" + folderName;
        return path;
    }

    //this method will move the current result directory to archive directory
    private static void moveDirectoryContents(File mainsourcedir, String directoryNameContains, File destination) {
        File[] files = mainsourcedir.listFiles();
        for (File file : files) {
            if (file.isDirectory() && file.getName().contains(directoryNameContains)) {
                file.mkdir();
                //Files.move(file.toPath(), (new File(destination+"//"+file.getName())).toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
        }
    }

    //this method will compare the string and add the log in report
    public static void stringCompare(String actual, String expected, String stringName) throws Exception {
        if (actual.equals(expected)) {
            logger.log(Status.PASS, stringName + " verified");
        } else {
            String ss = GenericMethods.CaptureScreenshot(stringName);
            logger.fail(stringName + " is not same", MediaEntityBuilder.createScreenCaptureFromPath(path + "//screenshot//" + ss + ".png").build());
        }
    }

    //this method will log the info in report
    public static void logInfoInReport(String message) {
        logger.log(Status.INFO, message);
    }
}
