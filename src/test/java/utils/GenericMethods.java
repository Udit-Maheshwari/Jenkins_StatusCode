package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.response.Response;
import pageObject.GenericPage;

import org.apache.commons.io.FileUtils;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import Test.BaseTest;

import org.openqa.selenium.support.ui.WebDriverWait;

public class GenericMethods extends BaseTest {
    /*public GenericMethods(WebDriver driver) {
        PageFactory.initElements(driver,this);
    }*/
    public static List<String> unProcessedList = Collections.synchronizedList(new ArrayList<String>());
    public static int i = 0;
    public static int j = 0;

    public static List<List<String>> outputData = Collections.synchronizedList(new ArrayList<List<String>>());
    public static Set<String> ignoredStatusCodeHrefs = Collections.synchronizedSet(new HashSet<String>());
    public static Set<String> ignoredStatusCodeSrcs = Collections.synchronizedSet(new HashSet<String>());
    public static List<String> unProcessedList1 = Collections.synchronizedList(new ArrayList<String>());


    //this method will scroll to particular element
    /**
     * This method will get country and locale from pageURL
     *
     * @param testEnv
     * @param url
     * @return
     */
    public static Map<String, String> getCountryNameFromURL(String testEnv, String url) {
        Map<String, String> map = new HashMap<>();
        String country = null, locale = null;
        try {
            String[] arr = url.split("/");
            switch (testEnv) {
                case "live":
                    if (arr[3].contains("_")) {
                        country = "/" + arr[3].split("_")[0];
                        locale = arr[3].split("_")[1] + "/";
                    } else {
                        country = "/" + arr[3];
                        locale = driver.findElement(By.xpath("//html[@lang]")).getAttribute("lang").split("-")[0];
                    }
                    System.out.println("Country---> " + country + ": Locale---> " + locale);
                    break;
                case "stage":
                    if (arr[3].contains("_")) {
                        country = "/" + arr[3].split("_")[0];
                        locale = arr[3].split("_")[1] + "/";
                    } else {
                        country = "/" + arr[3];
                        locale = driver.findElement(By.xpath("//html[@lang]")).getAttribute("lang").split("-")[0];
                        ;
                    }
                    System.out.println("Country---> " + country + ": Locale---> " + locale);
                    break;
                case "prod":
                    country = "/" + arr[5];
                    locale = arr[6] + "/";
                    System.out.println("Country---> " + arr[5] + ": Locale---> " + arr[6]);
                    break;
                case "prod1":
                    country = "/" + arr[6];
                    locale = arr[7] + "/";
                    System.out.println("Country---> " + arr[5] + ": Locale---> " + arr[6]);
                    break;
                case "us":
                    country = "/us";
                    locale = "en/";
                    System.out.println("Country---> " + country + ": Locale---> " + locale);
                    break;
                default:
                    country = "";
                    locale = "";
                    System.out.println("Country and locale not found in url");
                    break;
            }
            map.put("country", country);
            map.put("locale", locale);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
        }
        return map;
    }

    public static Map<String, String> getCountryNameFromURL1(String testEnv, String url) {
        Map<String, String> map = new HashMap<>();
        String country = null, locale = null;
        try {
            String[] arr = url.split("/");
            switch (testEnv) {
                case "live":
                    if (arr[3].contains("_")) {
                        country = "/" + arr[3].split("_")[0];
                        locale = arr[3].split("_")[1] + "/";
                    } else {
                        country = "/" + arr[3];
                        locale = "";
                    }
                    System.out.println("Country---> " + country + ": Locale---> " + locale);
                    break;
                case "stage":
                    if (arr[3].contains("_")) {
                        country = "/" + arr[3].split("_")[0];
                        locale = arr[3].split("_")[1] + "/";
                    } else {
                        country = "/" + arr[3];
                        locale = "";
                    }
                    System.out.println("Country---> " + country + ": Locale---> " + locale);
                    break;
                case "prod":
                    country = "/" + arr[5];
                    locale = arr[6] + "/";
                    System.out.println("Country---> " + arr[5] + ": Locale---> " + arr[6]);
                    break;
                case "us":
                    country = "/us";
                    locale = "en/";
                    System.out.println("Country---> " + country + ": Locale---> " + locale);
                    break;
                default:
                    country = "";
                    locale = "";
                    System.out.println("Country and locale not found in url");
                    break;
            }
            map.put("country", country);
            map.put("locale", locale);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
        }
        return map;
    }


    public static void scrollToElement(WebElement locator) {
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true)", locator);
        } catch (Exception e) {
        }
    }

    public static void scrollToPageEnd() {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }

    public static void scrollToPageTop() {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, -document.body.scrollHeight)");
    }


    //this method will read the key and return the value for the same
    public static String fetchProperty(String propertyName) {
        return prop.getProperty(propertyName);
    }

    //this method will print the string
    public static String fetchCurrentURL() {
        return driver.getCurrentUrl();
    }

    //this method will open the url
    public static void openUrl(String url) {
        driver.get(url);
    }

    //this method will close the browser window
    public static void browserClose() {
        driver.quit();
    }

    //this method will get the current url
    public static void printString(String message) {
        System.out.println(message);
    }

    //this method will enter data in the textfield
    public static void enterData(WebElement locator, String message) {
        locator.clear();
        locator.sendKeys(message);
    }

    public static void enterDataAndClickTab(WebElement locator, String message) {
        try {
            wait.until(ExpectedConditions.visibilityOf(locator));
            locator.clear();
            locator.sendKeys(message);

            Actions action = new Actions(driver);
            action.sendKeys(Keys.TAB).build().perform();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //this method will enter data in the textfield and log the same in report
    public static void enterData(WebElement locator, String testData, String messageLog) {
        locator.sendKeys(testData);
        logger.info(messageLog + " " + testData);
    }

    //this method will press enter in the textfield
    public static void pressEnter(WebElement locator) {
        locator.sendKeys(Keys.ENTER);
    }

    //this method will click the webelement
    public static void click(WebElement locator) {
        locator.click();
    }

    /**
     * Webelement click methods
     *
     * @param locator
     * @param message
     */
    public static void click(WebElement locator, String message) {
        locator.click();
        logger.info(message);
    }

    public static void clickUsingActions(WebDriver driver, WebElement element, String elemName) {
        Actions actions = new Actions(driver);
        actions.moveToElement(element).click().build().perform();
    }

    public static void clickUsingJS(WebElement element, String elemName) {
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        } catch (Exception e) {
            logger.error(elemName + " NOT clickable on the page !!! \n" + e);
        }
    }

    //this method will getText of webelement
    public static String getText(WebElement locator) {
        return locator.getText();
    }

    /**
     * This method will get webelement attribute value/property
     *
     * @param element
     * @param attributeName
     * @return
     */
    public static String getElemAttributeVal(WebElement element, String attributeName) {
        String attributeValue = null;
        try {
            attributeName = element.getAttribute(attributeName);
        } catch (Exception e) {
            logger.error("Error fetching " + attributeName + " from element " + element);
        }
        return attributeValue;
    }

    //this method will get innerhtml text of webelement
    public static String getElementTxt(WebElement element, String elemName) {
        String txt = "";
        try {
            txt = element.getAttribute("innerHTML").toString().replaceAll("\\<.*?>", "").replaceAll("\\s+", " ").replaceAll("&nbsp;", "");
        } catch (Exception e) {
            logger.error(element + " NOT present on the page !!!");
        }
        return txt;
    }

    public static String getElementTxtWithoutReplacingNbsp(WebElement element, String elemName) {
        String txt = "";
        try {
            txt = element.getAttribute("innerHTML").toString().replaceAll("\\<.*?>", "").replaceAll("\\s+", " ");
        } catch (Exception e) {
            logger.error(element + " NOT present on the page !!!");
        }
        return txt;
    }

    public static String getElementTxtWithoutStyleData(WebElement element, String elemName) {
        String txt = "";
        try {
            txt = element.getAttribute("innerHTML").toString().replaceAll("\\<style\\>.*?\\<\\/style\\>", "").replaceAll("\\<.*?>", "").replaceAll("\\s+", " ").replaceAll("&nbsp;", "");
        } catch (Exception e) {
            logger.error(element + " NOT present on the page !!!");
        }
        return txt;
    }

    //this method will move to particular webelement
    public static void moveToElement(WebElement locator) {
        act.moveToElement(locator).build().perform();
    }

    //this method will return the date string in given format
    public static String getDateInGivenFormat(String format) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
        LocalDateTime localDate = LocalDateTime.now();
        return dtf.format(localDate);
    }

    //this method will capture screenshot
    public static String CaptureScreenshot(String testName) throws Exception {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File src = ts.getScreenshotAs(OutputType.FILE);
        long picname = System.currentTimeMillis();
        FileUtils.copyFile(src, new File(path + "//screenshot//" + testName + picname + ".png"));
        return (testName + picname);
    }

    //this method will return the title of browser window
    public static String fetchTitle() {
        return driver.getTitle();
    }

    /**
     * Dropdown Operations
     *
     * @param e
     * @param text
     */

    public static void selectByTextFromDropdown(WebElement e, String text) {
        try {
            wait.until(ExpectedConditions.visibilityOf(e));
            Select dropdown = new Select(e);
            dropdown.selectByVisibleText(text);
        } catch (Exception ex) {
        }

    }

    public static void selectByValueFromDropdown(WebElement e, String value) {
        Select dropdown = new Select(e);
        dropdown.selectByValue(value);
    }

    public static void selectByIndexFromDropdown(WebElement e, int index) {
        try {
            wait.until(ExpectedConditions.visibilityOf(e));
            Select dropdown = new Select(e);
            dropdown.selectByIndex(index);
        } catch (Exception ex) {
        }

    }

    public static String getSelectedOptionInDropdown(WebElement dropdown) {
        String selectedOption = "";
        try {
            Select sc = new Select(dropdown);
            selectedOption = sc.getFirstSelectedOption().getText().trim();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return selectedOption;
    }


    /**
     * This method will get country and locale from Milo pageURL
     *
     * @param testEnv
     * @param url
     * @return
     */
    public static Map<String, String> getCountryNameFromURL_Milo(String testEnv, String url) {
        Map<String, String> map = new HashMap<>();
        String country = null, locale = null;
        try {
            String[] arr = url.split("/");
            switch (testEnv) {
                case "live":
                    country = arr[3];
                    //locale = "";
                    locale = driver.findElement(By.xpath("//html[@lang]")).getAttribute("lang").split("-")[0];
                    System.out.println("Country---> " + country + ": Locale---> " + locale);
                    break;
                case "stage":
                    if (arr[3].contains("_")) {
                        country = "/" + arr[3].split("_")[0];
                        locale = arr[3].split("_")[1] + "/";
                    } else {
                        country = "/" + arr[3];
                        locale = "en/";
                    }
                    System.out.println("Country---> " + country + ": Locale---> " + locale);
                    break;
                case "prod":
                    if (arr[3].contains("_")) {
                        country = arr[3].split("_")[0];
                        locale = arr[3].split("_")[1];
                    } else {
                        country = arr[3];
                        locale = "";
                    }

                    System.out.println("Country---> " + country + ": Locale---> " + locale);
                    break;
                case "us":
                    country = "/us";
                    locale = "en/";
                    System.out.println("Country---> " + country + ": Locale---> " + locale);
                    break;
                default:
                    country = "";
                    locale = "";
                    System.out.println("Country and locale not found in url");
                    break;
            }
            map.put("country", country);
            map.put("locale", locale);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
        }
        return map;
    }

    /**
     * This method will check height of webelemnt on page should be > 0
     *
     * @param e
     * @param eName
     * @return
     */
    public static boolean checkVisibilityOfElement(WebElement e, String eName) {
        boolean isEleDisplayed = false;
        int height = 0;
        WaitLibrary.iWaitMs(200);
        try {
            wait.until(ExpectedConditions.visibilityOf(e));
            height = e.getSize().getHeight();
            //if((e.isEnabled()==true && e.isDisplayed()==true) || height>0) {
            if (e.isDisplayed() == true || height > 0) {
                isEleDisplayed = true;
            } else {
                isEleDisplayed = false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            //throw new TestException(String.format("The following element is not visible: [%s]", eName));
        }
        return isEleDisplayed;
    }

    /**
     * This method will return the visible element among all elements in a list
     *
     * @param el
     * @param elemName
     * @return
     * @implNote Assumption: only 1 element of list is visible on page
     */
    public static WebElement getVisibleElementInList(List<WebElement> el, String elemName) {
        WebElement e = null;
        try {
            for (WebElement ele : el) {
                if (GenericMethods.checkVisibilityOfElement(ele, elemName)) {
                    e = ele;
                    break;
                }
            }
        } catch (NoSuchElementException ex) {
            logger.error("ERROR: " + elemName + " not visible on the page !!!");
        }
        return e;
    }

    /**
     * This method will return anchor tag properties
     */
    public static Map<String, String> getAnchorLinkData(WebElement e) {
        Map<String, String> linkMap = new HashMap<>();
        try {
            linkMap.put("href", e.getAttribute("href"));
            linkMap.put("daall", e.getAttribute("daa-ll"));
            linkMap.put("text", getElementTxt(e, "Links"));
            linkMap.put("daalh", e.getAttribute("daa-lh"));
            linkMap.put("arialabel", e.getAttribute("aria-label"));
            linkMap.put("visibility", String.valueOf(checkVisibilityOfElement(e, "link")));
			/*int response_code = getResponseStatusCode(e.getAttribute("href"));
		String responseStatus = GenericMethods.getResponseStatus(response_code);
		linkMap.put("responsecode", String.valueOf(response_code));
		linkMap.put("responsestatus", String.valueOf(responseStatus));*/

        } catch (Exception ex) {
            logger.error("Exception: Adding link data in map");
        }
        return linkMap;
    }

    /**
     * This method will return img links data
     *
     * @param WebElement e
     * @return
     */
    public Map<String, String> getImgLinkData(WebElement e) {
        Map<String, String> linkMap = new HashMap<>();
        linkMap.put("href", e.getAttribute("href"));
        linkMap.put("daall", e.getAttribute("daa-ll"));
        linkMap.put("text", getElementTxt(e, "Links"));
        linkMap.put("daalh", e.getAttribute("daa-lh"));
        int response_code = getResponseStatusCode(e.getAttribute("href"));
        linkMap.put("responsecode", String.valueOf(response_code));
        String responseStatus = GenericMethods.getResponseStatus(response_code);
        linkMap.put("responsestatus", String.valueOf(responseStatus));
        linkMap.put("visibility", String.valueOf(checkVisibilityOfElement(e, "link")));
        return linkMap;
    }

    /**
     * This method will return the data of a CTA
     *
     * @return list of CTA details
     */
    public static Map<String, String> getCTAData(WebElement e) {
        Map<String, String> ctaData = new HashMap<>();
        if (checkVisibilityOfElement(e, "CTA")) {
            ctaData.put("text", getElementTxt(e, "CTA Text"));
            ctaData.put("title", e.getAttribute("title"));
            ctaData.put("class", e.getAttribute("class"));
            ctaData.put("href", e.getAttribute("href"));
            ctaData.put("offerId", e.getAttribute("data-offer-id"));
            ctaData.put("checkoutType", e.getAttribute("data-checkout-type"));
            ctaData.put("daa-ll", e.getAttribute("daa-ll"));
            if (e.getSize().getHeight() > 0) {
                ctaData.put("visibility", "Visible");
            } else {
                ctaData.put("visibility", "Invisible");
            }
        }
        return ctaData;
    }

    /**
     * This method will return the data of a CTA
     *
     * @return list of CTA details
     */
    public static Map<String, String> getCTAData(WebElement e, String cta) {
        Map<String, String> ctaData = new HashMap<>();
        if (checkVisibilityOfElement(e, "CTA")) {
            try {
                ctaData.put(cta + "_text", GenericMethods.getElementTxt(e, cta));
                ctaData.put(cta + "_title", e.getAttribute("title"));
                ctaData.put(cta + "_class", e.getAttribute("class"));
                ctaData.put(cta + "_href", e.getAttribute("href"));
                ctaData.put(cta + "_offerId", e.getAttribute("data-offer-id"));
                ctaData.put(cta + "_checkoutType", e.getAttribute("data-checkout-type"));
                ctaData.put(cta + "_location", String.valueOf(e.getLocation().getX() + "-" + e.getLocation().getX()));
                ctaData.put(cta + "_daall", e.getAttribute("daa-ll"));
                ctaData.put(cta + "_height", String.valueOf(e.getSize().getHeight()));
                ctaData.put(cta + "_visibility", String.valueOf(e.isDisplayed()));
            } catch (Exception ex) {
                logger.error("Error while fetching CTA properties");
            }
        }
        return ctaData;
    }

    /**
     * This method wil check if text present in page source
     *
     * @param text
     * @return
     */
    public static boolean isTextPresent(String text) {
        try {
            boolean b = driver.getPageSource().contains(text);
            return b;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * This method will search for the text on page
     *
     * @param searchStr
     * @return
     */
    public static List<Map<String, String>> searchTextOnPage(String searchStr) {
        List<Map<String, String>> searchOccurences = new ArrayList<>();
        List<WebElement> eList = new ArrayList<>();
        Integer idx;
        if (isTextPresent(searchStr)) {
            eList = driver.findElements(By.xpath("//*[contains(text(),'" + searchStr + "')]"));
            for (WebElement e : eList) {
                if (checkVisibilityOfElement(e, "Text")) {
                    Map<String, String> searchTextDetails = new HashMap<>();
                    idx = eList.indexOf(e);
                    JavascriptExecutor executor = (JavascriptExecutor) driver;
                    Object elementAttributes = executor.executeScript("var items = {}; for (index = 0; index < arguments[0].attributes.length; ++index) { items[arguments[0].attributes[index].name] = arguments[0].attributes[index].value }; return items;", e);
                    System.out.println("All Attributes: " + elementAttributes.toString());
                    searchTextDetails.put("index", idx.toString());
                    searchTextDetails.put("tag", e.getTagName());
                    if (e.getTagName().equals("span")) {
                        searchTextDetails.put("dataAnalytics", e.getAttribute("data-analytics-productinfo"));
                    } else {
                        searchTextDetails.put("dataAnalytics", getElementTxt(e, "Text Ele"));
                    }
                    searchTextDetails.put("class", e.getAttribute("class"));
                    searchTextDetails.put("location", e.getLocation().toString());
                    searchOccurences.add(searchTextDetails);
                    System.out.println(searchOccurences.toString());
                }
            }
        } else {
            System.out.println(searchStr + " not present on page");
        }
        return searchOccurences;
    }

    public static List<String> getTextOnPage(String searchStr) {
        Object elementAttributes = null;
        List<WebElement> eList = new ArrayList<>();
        Map<Integer, String> priceMap = new HashMap<>();
        List<String> priceli = new ArrayList<>();
        Integer idx = 0;
        if (isTextPresent(searchStr)) {
            eList = driver.findElements(By.xpath("//*[contains(text(),'" + searchStr + "')]"));
            for (WebElement e : eList) {
                if (checkVisibilityOfElement(e, "Text")) {
                    idx = eList.indexOf(e);
                    JavascriptExecutor executor = (JavascriptExecutor) driver;
                    elementAttributes = executor.executeScript("var items = {}; for (index = 0; index < arguments[0].attributes.length; ++index) { items[arguments[0].attributes[index].name] = arguments[0].attributes[index].value }; return items;", e);
                    priceli.add(idx + "===" + elementAttributes.toString());
                    System.out.println("All Attributes: " + elementAttributes.toString());
                }
            }
        } else {
            System.out.println(searchStr + " not present on page");
        }
        return priceli;
    }

    /**
     * This method will remove duplicates from a string
     *
     * @param str
     * @return
     */
    public static String removeDuplicates(String str) {
        String s = "";
        LinkedHashSet<Character> set = new LinkedHashSet<>();
        for (int i = 0; i < str.length(); i++)
            set.add(str.charAt(i));
        for (Character ch : set) {
            s += ch;
        }
        return s;
    }

    /**
     * This method will combine the strings
     *
     * @param str1
     * @param str2
     * @return
     */
    public String combineStrings(String str1, String str2) {
        return str1.concat(str2);
    }


    /**
     * This method will fetch the digits/numbers from string
     *
     * @param str
     * @return
     * @throws ParseException
     */
    public String getNumber(String str) throws ParseException {
        NumberFormat format = NumberFormat.getCurrencyInstance();
        Number number = format.parse(str);
        System.out.println(number.toString());
        return number.toString();
    }

    /**
     * This method will get country and language from url string
     *
     * @param url
     * @return
     */
    public static String getCoLangFromCartLink(String url) {
        String s, co = "", lang = "", st1, st2;
        st1 = url.split("&")[1];
        st2 = url.split("&")[4];
        if (st1.contains("co=")) {
            co = st1.substring(st1.indexOf("=") + 1);
        }
        if (st2.contains("lang=")) {
            lang = st2.substring(st2.indexOf("=") + 1);
        }
        s = co + ":" + lang;
        return s;
    }


    /**
     * This method will getXpath of webelement from page
     *
     * @param e
     * @return
     */
    public static String getXpathOfElement(WebElement e) {
        String elementInfo = e.toString();
        elementInfo = elementInfo.substring(elementInfo.indexOf("->"));
        String elementLocator = elementInfo.substring(elementInfo.indexOf(": "));
        elementLocator = elementLocator.substring(2, elementLocator.length() - 1);
        System.out.println(elementInfo);
        return elementInfo;
    }

    /**
     * This Method will get child node of parent webelement node
     *
     * @param driver
     * @param e
     * @param s
     * @return
     */
    public static WebElement getChildNodeFromParentNode(WebDriver driver, WebElement e, String s) {
        return driver.findElement(By.xpath(getXpathOfElement(e) + s));
    }

    /**
     * Return response code of the given link
     *
     * @param link
     * @return
     */
    public static int getResponseStatusCode(String link) {
        int respCode = 0;
        HttpURLConnection conn = null;
        try {
            URL url = new URL(link);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("HEAD");
            conn.setConnectTimeout(3000);
            conn.connect();
            respCode = conn.getResponseCode();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != conn)
                conn.disconnect();
        }
        return respCode;
    }

    /**
     * This method will fetch the response code status
     *
     * @param responseCode
     * @return
     */
    public static String getResponseStatus(int responseCode) {
        String status = "";
        switch (responseCode) {
            case HttpURLConnection.HTTP_OK:
                status = "OK";
                logger.info(" **200: OK**");
                break;
            case HttpURLConnection.HTTP_MOVED_PERM:
                status = "Moved Permanently";
                logger.warning("**301: Moved Permanently**");
                break;
            case HttpURLConnection.HTTP_MOVED_TEMP:
                status = "Temporary Redirect";
                logger.warning("**302: Temporary Redirect**");
                break;
            case HttpURLConnection.HTTP_BAD_REQUEST:
                status = "Bad Request";
                logger.fatal("**400: Bad Request**");
                break;
            case HttpURLConnection.HTTP_NOT_FOUND:
                status = "Not Found";
                logger.fatal("**404: Not Found**");
                break;
            case HttpURLConnection.HTTP_INTERNAL_ERROR:
                status = "Internal Server Error";
                logger.error("**500: Internal Server Error**");
                break;
            case HttpURLConnection.HTTP_BAD_GATEWAY:
                status = "Bad Gateway";
                logger.error("**502: Bad Gateway**");
                break;
            case HttpURLConnection.HTTP_UNAVAILABLE:
                status = "Service Unavailable";
                logger.error("**503: Service Unavailable**");
                break;
            default:
                status = String.valueOf(responseCode);
                logger.fatal("**Response code: " + status + " **");
                break;
        }
        return status;
    }

    //---------------------------------------------- JSON Operations -------------------------------//


    public static String getValue(String filename, String key) {
        String value = null;
        try {
            Properties prop = new Properties();
            prop.load(new FileInputStream(new File(System.getProperty("user.dir") + "//resource//config//" + filename)));
            value = prop.getProperty(key);
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFoundException" + " " + value);
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("IOException" + " " + value);
            e.printStackTrace();
        }
        return value;
    }

    public static void setValue(String filename, String key, String newValue) throws IOException {
        FileInputStream in = new FileInputStream(new File("./src/test/resources/" + filename));
        Properties props = new Properties();
        props.load(in);
        in.close();

        System.out.println("replacing value for :  " + key);

        FileOutputStream out;
        try {
            out = new FileOutputStream(new File("./src/test/resources/" + filename));
            props.setProperty(key, newValue);
            props.store(out, null);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static String addIndexToEndOfXpath(String xpath, int i) {
        return "(" + xpath + ")[" + i + "]";
    }

    public static boolean checkVisibilityOfEle(WebElement e, String eName) {
        boolean isEleDisplayed = false;
        WaitLibrary.iWaitMs(200);
        try {
            e.getTagName();
            WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofMillis(1500));
            wait1.until(ExpectedConditions.visibilityOf(e));
            if (e.isDisplayed() == true) {
                isEleDisplayed = true;
            } else {
                isEleDisplayed = false;
            }
        } catch (Exception ex) {
            //throw new TestException(String.format("The following element is not visible: [%s]", eName));
        }
        return isEleDisplayed;
    }

    public static boolean checkVisibilityOfEleWithLessWaitTime(WebElement e, String eName) {
        boolean isEleDisplayed = false;
        WaitLibrary.iWaitMs(200);
        try {
            e.getTagName();
            WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofMillis(300));
            wait1.until(ExpectedConditions.visibilityOf(e));
            if (e.isDisplayed() == true) {
                isEleDisplayed = true;
            } else {
                isEleDisplayed = false;
            }
        } catch (Exception ex) {
            //throw new TestException(String.format("The following element is not visible: [%s]", eName));
        }
        return isEleDisplayed;
    }

    public static boolean checkVisibilityOfEleWithLessWaitTime1(WebElement e, String eName) {
        boolean isEleDisplayed = false;
        //WaitLibrary.iWaitMs(200);
        try {
            e.getTagName();
            WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofMillis(100));
            wait1.until(ExpectedConditions.visibilityOf(e));
            if (e.isDisplayed() == true) {
                isEleDisplayed = true;
            } else {
                isEleDisplayed = false;
            }
        } catch (Exception ex) {
            //throw new TestException(String.format("The following element is not visible: [%s]", eName));
        }
        return isEleDisplayed;
    }

    public static boolean checkVisibilityOfEleWithHeight(WebElement e, String eName) {
        boolean isEleDisplayed = false;
        WaitLibrary.iWaitMs(200);
        try {
            e.getTagName();
            WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofMillis(2500));
            wait1.until(ExpectedConditions.visibilityOf(e));
            if (e.isDisplayed() == true && e.getSize().getHeight() > 0) {
                isEleDisplayed = true;
            } else {
                isEleDisplayed = false;
            }
        } catch (Exception ex) {
            //throw new TestException(String.format("The following element is not visible: [%s]", eName));
        }
        return isEleDisplayed;
    }


    public static String getResponseCode(String url) {
        try {
            RestAssured.config = RestAssuredConfig.config()
                    .httpClient(HttpClientConfig.httpClientConfig()
                            .setParam("http.socket.timeout", 5000)
                            .setParam("http.connection.timeout", 5000));

            Response response = RestAssured.given().relaxedHTTPSValidation().when().redirects().follow(false).get(url);
            return "" + response.getStatusCode();
        } catch (Exception ex) {
            //ex.printStackTrace();
            return "";
        }
    }

    public static String getResponseCodeWithMoreWaitTime(String url) {
        try {
            RestAssured.config = RestAssuredConfig.config()
                    .httpClient(HttpClientConfig.httpClientConfig()
                            .setParam("http.socket.timeout", 10000)
                            .setParam("http.connection.timeout", 10000));

            Response response = RestAssured.given().relaxedHTTPSValidation().when().redirects().follow(false).get(url);
            return "" + response.getStatusCode();
        } catch (Exception ex) {
            //ex.printStackTrace();
            return "";
        }
    }

    public static String getResponseCodeWithWaitTimeReduced(String url) {
        try {
            RestAssured.config = RestAssuredConfig.config()
                    .httpClient(HttpClientConfig.httpClientConfig()
                            .setParam("http.socket.timeout", 1000)
                            .setParam("http.connection.timeout", 1000));

            Response response = RestAssured.given().relaxedHTTPSValidation().when().redirects().follow(false).get(url);
            return "" + response.getStatusCode();
        } catch (Exception ex) {
            //ex.printStackTrace();
            return "";
        }
    }

    public static String getResponseCodeFollowRedirect(String url) {
        try {
            RestAssured.config = RestAssuredConfig.config()
                    .httpClient(HttpClientConfig.httpClientConfig()
                            .setParam("http.socket.timeout", 5000)
                            .setParam("http.connection.timeout", 5000));

            Response response = RestAssured.given().relaxedHTTPSValidation().when().redirects().follow(true).redirects().max(10).get(url);
            return "" + response.getStatusCode();
        } catch (Exception ex) {
            //ex.printStackTrace();
            return "";
        }
    }
    //	public static int getResponseCode(String url) {
    //		Response response = RestAssured.given().when().get(url);
    //		return response.getStatusCode();
    //	}


    public static void waitTillPageLoads() {
        JavascriptExecutor j = (JavascriptExecutor) driver;
        for (int i = 0; i < 50; i++) {
            if (j.executeScript("return document.readyState").toString().equals("complete")) {
                break;
            }
            try {
                Thread.sleep(1000);
                System.out.println("Waited for page load");
            } catch (InterruptedException ex) {
                System.out.println("Page has not loaded yet ");
            }
        }
    }


    public static void scrollToPageEndInSteps() {
        try {
            long totalHeight = (long) ((JavascriptExecutor) driver).executeScript("return document.body.scrollHeight");
            long scrollHeight = totalHeight / 10;
            for (int i = 1; i <= 10; i++) {
                JavascriptExecutor j = (JavascriptExecutor) driver;
                j.executeScript("window.scrollBy(0," + scrollHeight + ")");
                WaitLibrary.iWaitMs(200);
            }
            scrollToPageEnd();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void scrollToMidPage() {
        try {
            scrollToPageTop();
            long totalHeight = (long) ((JavascriptExecutor) driver).executeScript("return document.body.scrollHeight");
            long scrollHeight = totalHeight / 2;
            JavascriptExecutor j = (JavascriptExecutor) driver;
            j.executeScript("window.scrollBy(0," + scrollHeight + ")");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //---------------------------------------------- JSON Operations -------------------------------//

    public static Object parameters(Object obj) {
        Map<String, Object> getKeyVal = new HashMap<>();
        if (!(obj instanceof String) || obj == null) {
            for (Field field : obj.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                try {
                    getKeyVal.put(field.getName(), field.get(obj));
                } catch (Exception e) {
                }
            }
        }
        return getKeyVal.get("map");
    }

    /**
     * This method will get key value from JSON
     *
     * @param json
     * @param key
     * @return
     */
    public static Object getKey(JSONObject json, String key) {
        boolean exists = json.has(key);
        Iterator<?> keys;
        //List<String> jList = new ArrayList<String>();
        String nextKeys;
        if (!exists) {
            keys = json.keys();
            while (keys.hasNext()) {
                nextKeys = (String) keys.next();
                try {
                    if (json.get(nextKeys) instanceof JSONObject) {
                        if (exists == false) {
                            getKey(json.getJSONObject(nextKeys), key);
                        }
                    } else if (json.get(nextKeys) instanceof JSONArray) {
                        JSONArray jsonarr = json.getJSONArray(nextKeys);
                        for (int i = 0; i < jsonarr.length(); i++) {
                            String jsonarrStr = jsonarr.get(i).toString();
                            JSONObject innerJson = new JSONObject(jsonarrStr);
                            if (exists == false) {
                                getKey(innerJson, key);
                            }
                        }
                    }
                } catch (Exception e) {
                }
            }
        } else {
            //System.out.println(json.get(key));
        }
        return json.get(key);
    }


    public static String setBrokenHrefLinkDataInOutputData(String url, WebElement e, String index) {
        try {
            //if (!e.getTagName().equalsIgnoreCase("script") && !e.getTagName().equalsIgnoreCase("link") && !e.getTagName().equalsIgnoreCase("iframe")) {
            //if (!e.getTagName().equalsIgnoreCase("script") && !e.getTagName().equalsIgnoreCase("iframe") && !e.getTagName().equalsIgnoreCase("use")) {
            if (!e.getTagName().equalsIgnoreCase("use")) {
                String visibility = String.valueOf(checkVisibilityOfEleWithLessWaitTime1(e, "link"));
                String hrefLinkValue = e.getAttribute("href");
                if (!ignoredStatusCodeHrefs.contains(hrefLinkValue)) {
                    String responseCode = "";
                    if (hrefLinkValue.contains("twitter.com/Adobe") || hrefLinkValue.contains("tel:1-8") || hrefLinkValue.contains("instagram") || hrefLinkValue.contains("facebook") || hrefLinkValue.contains("tel:+") || hrefLinkValue.trim().startsWith("#footer-icon-")) {
                        responseCode = getResponseCodeWithWaitTimeReduced(hrefLinkValue);
                    } else {
                        responseCode = getResponseCode(hrefLinkValue);
                    }
                    if (!responseCode.equals("200") && !responseCode.equals("999")) {
                        //if (responseCode.startsWith("4") || responseCode.isBlank()) {
                        List<String> hrefDataList = Collections.synchronizedList(new ArrayList<String>());
                        hrefDataList.add(url);
                        String href = "";
                        String tagType = "";
                        String text = "";
                        String position = "";
                        String redirectURLStatus = "";
                        try {
                            href = hrefLinkValue;
                            text = getElementTxt(e, "Links");
                            if (e.getTagName().equals("a")) {
                                tagType = "Anchor";
                            } else {
                                tagType = e.getTagName();
                            }
                            position = getPositionOfElement(e);
                            if(responseCode.startsWith("3")){
                                redirectURLStatus = GenericMethods.getResponseCodeFollowRedirect(hrefLinkValue);
                            }else{
                                redirectURLStatus = "NA";
                            }
                        } catch (Exception ab) {
                            ab.printStackTrace();
                        } finally {
                            hrefDataList.add(href);
                            hrefDataList.add(tagType);
                            hrefDataList.add(index);
                            hrefDataList.add(position);
                            hrefDataList.add(text);
                            hrefDataList.add(visibility);
                            hrefDataList.add(responseCode);
                            hrefDataList.add(redirectURLStatus);
                            outputData.add(hrefDataList);
                            j++;
                        }
                    } else {
                        ignoredStatusCodeHrefs.add(hrefLinkValue);
                        //unProcessedList.add(responseCode);
                    }
                }
            }
        } catch (Exception en) {
            en.printStackTrace();
        }
        return "";
    }

    public static String setBrokenHrefLinkDataForExpress_TemplatePageInOutputData(String url, WebElement e, String index) {
        try {
            String visibility = String.valueOf(checkVisibilityOfEle(e, "link"));
            String parentWindow = driver.getWindowHandle();
            String alt = "";
            String url1 = e.getAttribute("href");

            try {
                alt = e.findElement(By.xpath(".//img")).getAttribute("alt");
            } catch (Exception e2) {
            }
            driver.switchTo().newWindow(WindowType.TAB);
            Set<String> windows = driver.getWindowHandles();
            if (windows.size() > 1) {
                for (String str : windows) {
                    if (!str.equals(parentWindow)) {
                        driver.switchTo().window(str);
                    }
                }
            }
            driver.get(url1);
            if (fetchCurrentURL().isBlank()) {
                driver.get(url1);
            }
            WaitLibrary.iWaitMs(500);
            PageFactory.initElements(driver, GenericMethods.class);
            String currentLink = driver.getCurrentUrl();
            String responseCode = getResponseCode(currentLink);
            driver.close();
            driver.switchTo().window(parentWindow);
            PageFactory.initElements(driver, GenericMethods.class);

            if (responseCode.startsWith("4") || responseCode.isBlank()) {
                List<String> hrefDataList = Collections.synchronizedList(new ArrayList<String>());
                hrefDataList.add(url);
                String href = "";
                String tagType = "";
                String text = "";
                String position = "";
                try {
                    href = e.getAttribute("href");
                    text = getElementTxt(e, "Links");
                    if (e.getTagName().equals("a")) {
                        tagType = "Anchor";
                    } else {
                        tagType = e.getTagName();
                    }
                    position = getPositionOfElement(e);
                } catch (Exception ab) {
                    ab.printStackTrace();
                } finally {
                    hrefDataList.add(href);
                    hrefDataList.add(tagType);
                    hrefDataList.add(index);
                    hrefDataList.add(position);
                    if (!alt.isBlank()) {
                        hrefDataList.add(alt);
                    } else {
                        hrefDataList.add(text);
                    }
                    hrefDataList.add(visibility);
                    hrefDataList.add(responseCode);
                    outputData.add(hrefDataList);
                    j++;
                }
            } else {
                unProcessedList.add(responseCode);
            }
            /*} else {
                unProcessedList.add(visibility);
            }*/
        } catch (Exception en) {
            en.printStackTrace();
        }
        return "";
    }


    public static String setBrokenSrcLinkDataInOutputData(String url, WebElement e, String index) {
        try {
            //if (!e.getTagName().equalsIgnoreCase("script") && !e.getTagName().equalsIgnoreCase("link") && !e.getTagName().equalsIgnoreCase("iframe")) {
            String visibility = String.valueOf(checkVisibilityOfEleWithLessWaitTime1(e, "link"));
            String srcValue = e.getAttribute("src");
            if (!ignoredStatusCodeSrcs.contains(srcValue)) {
                String responseCode = getResponseCode(srcValue);
                if (!responseCode.equals("200") && !responseCode.equals("999")) {
                    //if (responseCode.startsWith("4") || responseCode.isBlank()) {
                    List<String> srcDataList = Collections.synchronizedList(new ArrayList<String>());
                    srcDataList.add(url);
                    String src = "";
                    String tagType = "";
                    String alt = "";
                    String position = "";
                    String redirectURLStatus = "";
                    try {
                        src = srcValue;
                        alt = e.getAttribute("alt");
                        if (e.getTagName().equals("img")) {
                            tagType = "Image";
                        } else {
                            tagType = e.getTagName();
                        }
                        position = getPositionOfElement(e);
                        if (responseCode.startsWith("3")) {
                            redirectURLStatus = GenericMethods.getResponseCodeFollowRedirect(srcValue);
                        } else {
                            redirectURLStatus = "NA";
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    } finally {
                        srcDataList.add(src);
                        srcDataList.add(tagType);
                        srcDataList.add(index);
                        srcDataList.add(position);
                        srcDataList.add(alt);
                        srcDataList.add(visibility);
                        srcDataList.add(responseCode);
                        srcDataList.add(redirectURLStatus);
                        outputData.add(srcDataList);
                        j++;
                    }
                } else {
                    ignoredStatusCodeSrcs.add(srcValue);
                    //unProcessedList.add(responseCode);
                }
            }
            //}
        } catch (Exception exe) {
            exe.printStackTrace();
        }
        return "";
    }

    public static String CC_Milo_setBackgroundBrokenSrcLinkDataInOutputData(String url, WebElement e, String index) {
        try {
            if (!e.getTagName().equalsIgnoreCase("script") && !e.getTagName().equalsIgnoreCase("link") && !e.getTagName().equalsIgnoreCase("iframe")) {
                String visibility = String.valueOf(checkVisibilityOfEleWithLessWaitTime1(e, "link"));
                String responseCode = getResponseCode(e.getAttribute("style").split("url\\(")[1].replaceAll("\"", "").replaceAll(";", "").replaceAll("\\)", ""));
                if (responseCode.startsWith("4") || responseCode.isBlank()) {
                    List<String> srcDataList = Collections.synchronizedList(new ArrayList<String>());
                    srcDataList.add(url);
                    String src = "";
                    String tagType = "";
                    String imageTitle = "";
                    String position = "";
                    try {
                        src = e.getAttribute("style").split("url\\(")[1].replaceAll("\"", "").replaceAll(";", "").replaceAll("\\)", "");
                        try {
                            imageTitle = getElementTxt(e.findElement(By.xpath(".//following::*[contains(@class,'Card-title')]")), "Image Title");
                            if (imageTitle.isBlank()) {
                                imageTitle = getElementTxt(e.findElement(By.xpath(".//parent::a")), "Image Title");
                            }
                        } catch (Exception e2) {
                        }

                        if (e.getTagName().equals("div") && e.getAttribute("style").contains("background-image")) {
                            tagType = "Card Background Image";
                        } else {
                            tagType = e.getTagName();
                        }
                        position = getPositionOfElement(e);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    } finally {
                        srcDataList.add(src);
                        srcDataList.add(tagType);
                        srcDataList.add(index);
                        srcDataList.add(position);
                        srcDataList.add(imageTitle);
                        srcDataList.add(visibility);
                        srcDataList.add(responseCode);
                        outputData.add(srcDataList);
                        j++;
                    }
                } else {
                    unProcessedList.add(responseCode);
                }
            /*} else {
                unProcessedList.add(visibility);
            }*/
            } else {
                unProcessedList.add(e.getTagName());
            }
        } catch (Exception exe) {
            exe.printStackTrace();
        }
        return "";
    }


    public static void navigateToPreviousPage() {
        driver.navigate().back();
        waitTillPageLoads();
    }

    public static List<Map<String, String>> getCommerceLinksAndVerifyCartURL() {
        Map<String, String> ctaMap = null;
        List<Map<String, String>> cTAList = new ArrayList<>();
        List<WebElement> commLinks = driver.findElements(By.xpath("//a[contains(@href,'/commerce.adobe.com')]"));
        for (WebElement ele : commLinks) {
            ctaMap = new HashMap<>();
            try {
                //String href= ele.getAttribute("href");
                //if(href.contains("/commerce.adobe.com/")) {
                ctaMap = getCTAData(ele);
                //try {
                if (!ctaMap.get("text").equals(null)) {
                  //  String cartURL = GenericPage.clickLinkAndOpenPage(ele);
                   // ctaMap.put("Cart URL", cartURL);
                }
                //}
                //catch(Exception ex) {}
                cTAList.add(ctaMap);
            } catch (Exception ex) {
            }
        }
        return cTAList;
    }

    //--------Methods to find javascript load issue   -----  //

    public static Map<String, String> getResponseCode1(String url) {
        Map<String, String> responseInfo = new HashMap<String, String>();
        String responseBody = "";
        String responseCode = "";
        try {
            RestAssured.config = RestAssuredConfig.config()
                    .httpClient(HttpClientConfig.httpClientConfig()
                            .setParam("http.socket.timeout", 7000)
                            .setParam("http.connection.timeout", 7000));

            Response response = RestAssured.given().relaxedHTTPSValidation().when().get(url);
            responseBody = response.body().asString();
            responseCode = "" + response.getStatusCode();

			/*System.out.println("Response body is : "+responseBody);
			if(!responseBody.contains("<a href")){
				System.out.println("Failed to load .....");
			}
			else{
				System.out.println("Its a pass .....");
			}*/

        } catch (Exception ex) {
            ex.printStackTrace();

        } finally {
            responseInfo.put("responseCode", responseCode);
            responseInfo.put("responseBody", responseBody);
        }
        return responseInfo;
    }

    public static String setBrokenHrefLinkDataInOutputData1(String url, WebElement e, String index) {
        try {
            String visibility = String.valueOf(checkVisibilityOfEle(e, "link"));
            if (visibility.equalsIgnoreCase("true")) {
                Map<String, String> responseInfo = getResponseCode1(e.getAttribute("href"));
                if (!responseInfo.get("responseCode").equals("200") && !responseInfo.get("responseCode").equals("999")) {
                    List<String> hrefDataList = Collections.synchronizedList(new ArrayList<String>());
                    hrefDataList.add(url);
                    String href = "";
                    String tagType = "";
                    String text = "";
                    try {
                        href = e.getAttribute("href");
                        text = getElementTxt(e, "Links");
                        if (e.getTagName().equals("a")) {
                            tagType = "Anchor";
                        } else {
                            tagType = e.getTagName();
                        }
                    } catch (Exception ab) {
                        ab.printStackTrace();
                    } finally {
                        hrefDataList.add(href);
                        hrefDataList.add(tagType);
                        hrefDataList.add(index);
                        hrefDataList.add(text);
                        hrefDataList.add(visibility);
                        hrefDataList.add(responseInfo.get("responseCode"));
                        outputData.add(hrefDataList);
                        j++;
                    }
                } else if (responseInfo.get("responseCode").equals("200")) {
                    if (!responseInfo.get("responseBody").contains("<h1")) {
                        System.out.println("Failed to load .....");
                        List<String> hrefDataList1 = Collections.synchronizedList(new ArrayList<String>());
                        hrefDataList1.add(url);
                        hrefDataList1.add(e.getAttribute("href"));
                        hrefDataList1.add("Broken link - Page didn't load");
                        outputData.add(hrefDataList1);
                    } else {
                        unProcessedList.add(responseInfo.get("responseCode"));
                    }
                } else {
                    unProcessedList.add(responseInfo.get("responseCode"));
                }
            } else {
                unProcessedList.add(visibility);
            }
        } catch (Exception en) {
            en.printStackTrace();
        }
        return "";
    }

    public static String setBrokenSrcLinkDataInOutputData1(String url, WebElement e, String index) {
        try {
            String visibility = String.valueOf(checkVisibilityOfEle(e, "link"));
            if (visibility.equalsIgnoreCase("true")) {
                Map<String, String> responseInfo = getResponseCode1(e.getAttribute("src"));
                if (!responseInfo.get("responseCode").equals("200") && !responseInfo.get("responseCode").equals("999")) {
                    List<String> srcDataList = Collections.synchronizedList(new ArrayList<String>());
                    srcDataList.add(url);
                    String src = "";
                    String tagType = "";
                    String alt = "";
                    try {
                        src = e.getAttribute("src");
                        alt = e.getAttribute("alt");
                        if (e.getTagName().equals("img")) {
                            tagType = "Image";
                        } else {
                            tagType = e.getTagName();
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    } finally {
                        srcDataList.add(src);
                        srcDataList.add(tagType);
                        srcDataList.add(index);
                        srcDataList.add(alt);
                        srcDataList.add(visibility);
                        srcDataList.add(responseInfo.get("responseCode"));
                        outputData.add(srcDataList);
                        j++;
                    }
                }
                /*else if (responseInfo.get("responseCode").equals("200")) {
                    if (!responseInfo.get("responseBody").contains("<a href")) {
                        System.out.println("Failed to load .....");
                        List<String> hrefDataList1 = Collections.synchronizedList(new ArrayList<String>());
                        hrefDataList1.add(url);
                        hrefDataList1.add(e.getAttribute("src"));
                        hrefDataList1.add("Broken link - Page didn't load");
                        outputData.add(hrefDataList1);
                    } else {
                        unProcessedList.add(responseInfo.get("responseCode"));
                    }
                }*/
                else {
                    unProcessedList.add(responseInfo.get("responseCode"));
                }
            } else {
                unProcessedList.add(visibility);
            }
        } catch (Exception exe) {
            exe.printStackTrace();
        }
        return "";
    }

    public static String clickAndOpenLinkInNewTab(WebElement ele) {
        String parentWindow = driver.getWindowHandle();
        String clicklnk = Keys.chord(Keys.CONTROL, Keys.ENTER);
        try {
            Set<String> oldWindows = driver.getWindowHandles();
            wait.until(ExpectedConditions.elementToBeClickable(ele));
            GenericMethods.scrollToElement(ele);
            ele.sendKeys(clicklnk);
            WaitLibrary.iWaitMs(1000);
            Set<String> newWindows = driver.getWindowHandles();
            for (String win : oldWindows) {
                newWindows.remove(win);
            }
            System.out.println("New WINDOW " + newWindows.toArray(new String[newWindows.size()])[0]);
            driver.switchTo().window(newWindows.toArray(new String[newWindows.size()])[0]);
            WaitLibrary.iWaitMs(1000);
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("EXCEPTION: Link Click");
        }
        return parentWindow;
    }

    public static String getPositionOfElement(WebElement ele) {
        String pos = "";
        try {
            List<WebElement> gnavList1 = ele.findElements(By.xpath(".//ancestor::*[contains(@class,'gnavTopnav')]"));
            List<WebElement> gnavList2 = ele.findElements(By.xpath(".//ancestor::*[self::*[contains(@class,'gnav-wrapper')] or self::nav[contains(@class,'feds-topnav')]]"));

            List<WebElement> contentList1 = ele.findElements(By.xpath(".//ancestor::div[contains(@class,'container  ')]"));
            List<WebElement> contentList2 = ele.findElements(By.xpath(".//ancestor::div[contains(@class,'section')]//ancestor::main"));
            List<WebElement> contentList3 = ele.findElements(By.xpath(".//ancestor::main"));

            List<WebElement> footerList1 = ele.findElements(By.xpath(".//ancestor::footer[@class='footer']"));
            List<WebElement> footerList2 = ele.findElements(By.xpath(".//ancestor::*[self::*[@class='gnavFooter'] or self::footer]"));

            List<WebElement> geoPopupList1 = ele.findElements(By.xpath(".//ancestor::div[@id='localeModal' or @id='locale-modal']"));

            List<WebElement> breadCrumbList1 = ele.findElements(By.xpath(".//ancestor::div[contains(@data-feds-element,'breadcrumbs')]"));
            List<WebElement> breadCrumbList2 = ele.findElements(By.xpath(".//ancestor::*[self::nav[@class='breadcrumbs'] or self::nav[contains(@class,'feds-breadcrumbs')]]"));

            List<WebElement> regionSelectorList1 = ele.findElements(By.xpath(".//ancestor::nav[@class='language-Navigation']"));
            List<WebElement> regionSelectorList2 = ele.findElements(By.xpath(".//ancestor::div[contains(@id,'langnav')]"));


            if (((gnavList1.size() + gnavList2.size()) == 1) && ((breadCrumbList1.size() + breadCrumbList2.size()) == 0)) {
                pos = "NAV HEADER";
            } else if ((contentList1.size() + contentList2.size()) == 1) {
                pos = "CONTENT";
            } else if ((footerList1.size() + footerList2.size()) == 1) {
                pos = "NAV FOOTER";
                //} else if (((gnavList1.size() + gnavList2.size()) == 1) && ((breadCrumbList1.size() + breadCrumbList2.size()) == 1)) {
            } else if ((breadCrumbList1.size() + breadCrumbList2.size()) == 1) {
                pos = "NAV BREADCRUMBS";
            } else if (geoPopupList1.size() == 1) {
                pos = "GEO POPUP";
            } else if ((regionSelectorList1.size() + regionSelectorList2.size()) == 1) {
                pos = "REGION SELECTOR";
            } else if (contentList3.size() == 1) {
                pos = "CONTENT";
            } else {
                pos = "Not found";
            }
        } catch (Exception e) {
        }
        return pos;
    }

    //--------Methods to clear Browser Cookies--------//
    public static void clearBrowserCookies() {
        driver.manage().deleteAllCookies();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("localStorage.clear();");
        js.executeScript("sessionStorage.clear();");
        js.executeScript("location.reload();");
    }

    //--------Methods to Handle Alert Popup--------//
    public static void handlingAlertPopUp() {
        try {
            driver.switchTo().alert().accept();
        } catch (Exception e) {
        }
    }

    public static List<WebElement> getVisibleElementsInList(List<WebElement> elements) {
        List<WebElement> visibleElements = new ArrayList<WebElement>();
        try {
            for (WebElement e : elements) {
                if (checkVisibilityOfEle(e, "WebElement")) {
                    visibleElements.add(e);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return visibleElements;
    }

    public static List<WebElement> getVisibleElementsInListByUsingParallelStream(List<WebElement> elements) {
        List<WebElement> visibleElements = new ArrayList<WebElement>();
        WaitLibrary.iWaitMs(200);
        try {
            visibleElements = elements.parallelStream().filter(ele -> GenericMethods.checkVisibilityOfEleWithLessWaitTime(ele, "")).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return visibleElements;
    }

    public static void waitUntilElementDisappears(WebElement element) {
        try {
            WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(20));
            wait1.until(ExpectedConditions.invisibilityOf(element));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void waitUntilElementDisappears(WebDriver driverIns, WebElement element) {
        try {
            WebDriverWait wait1 = new WebDriverWait(driverIns, Duration.ofSeconds(20));
            wait1.until(ExpectedConditions.invisibilityOf(element));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void clearBrowserCache() {
        try {
            driver.manage().deleteAllCookies();
            driver.get("chrome://settings/clearBrowserData");
            WaitLibrary.iWaitMs(2500);
            new WebDriverWait(driver, Duration.ofSeconds(20)).until(ExpectedConditions.elementToBeClickable((WebElement) ((JavascriptExecutor) driver).executeScript("return document.querySelector('settings-ui').shadowRoot.querySelector('settings-main').shadowRoot.querySelector('settings-basic-page').shadowRoot.querySelector('settings-section > settings-privacy-page').shadowRoot.querySelector('settings-clear-browsing-data-dialog').shadowRoot.querySelector('#clearBrowsingDataDialog').querySelector('#clearBrowsingDataConfirm')"))).click();
            System.out.println("Clear data Button Clicked");
        } catch (Exception e) {
            e.printStackTrace();
            try {
                new WebDriverWait(driver, Duration.ofSeconds(20)).until(ExpectedConditions.elementToBeClickable((WebElement) ((JavascriptExecutor) driver).executeScript("return document.querySelector('settings-ui').shadowRoot.querySelector('settings-main').shadowRoot.querySelector('settings-basic-page').shadowRoot.querySelector('settings-section > settings-privacy-page').shadowRoot.querySelector('settings-clear-browsing-data-dialog').shadowRoot.querySelector('#clearBrowsingDataDialog').querySelector('#clearBrowsingDataConfirm')"))).click();
                System.out.println("Clear data Button Clicked inside Catch block");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    //-------------------------scroll to top by specific pixel --------------//

    public static void scrollToPageTopByPixel(String pixel) {
        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, -" + pixel + ")");
    }


    //-----------------------Take Screenshot ---------------------------------------//

    public static void takeScreensot(String fileWithPath) throws Exception {
        try {
            TakesScreenshot scrShot = ((TakesScreenshot) driver);
            File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
            File DestFile = new File(fileWithPath);
            Path path = Path.of(String.valueOf(DestFile));
            FileUtils.copyFile(SrcFile, DestFile);
        } catch (Exception e) {
        }
    }

    //---------------------- delete all files in a directory -------------------------//

    public static void deleteAllFilesInDirectory(File dir) {
        try {
            for (File file : dir.listFiles())
                if (!file.isDirectory())
                    file.delete();
        } catch (Exception e) {
        }
    }

    public static void enterTab() {
        Actions action = new Actions(driver);
        action.sendKeys(Keys.TAB).build().perform();
        WaitLibrary.iWaitMs(2000);
    }

    //-------------------------------Wait Until WebElement is loaded in DOM --------------------------//

    public static void waitUntilLocatorLoadsInDom(By locator) {
        try {
            List<WebElement> elements = driver.findElements(locator);
            for (int i = 0; i < 28; i++) {
                if (elements.size() == 0) {
                    WaitLibrary.iWaitMs(750);
                    System.out.println("Waiting for locator to load in dom");
                } else if (elements.size() > 0) {
                    break;
                }
                elements = driver.findElements(locator);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void waitUntilLocatorDisappearsInDom(By locator) {
        try {
            List<WebElement> elements = driver.findElements(locator);
            for (int i = 0; i < 28; i++) {
                if (elements.size() == 0) {
                    break;
                } else if (elements.size() > 0) {
                    WaitLibrary.iWaitMs(500);
                }
                elements = driver.findElements(locator);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String clickAndOpenLinkInNewTabByUsingJS(WebElement ele) {
        String parentWindow = driver.getWindowHandle();
        try {
            Set<String> oldWindows = driver.getWindowHandles();
            wait.until(ExpectedConditions.elementToBeClickable(ele));
            GenericMethods.scrollToElement(ele);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].setAttribute('target', '_blank')", ele);
            PageFactory.initElements(driver, GenericMethods.class);
            GenericMethods.clickUsingJS(ele, "");
            Set<String> newWindows = driver.getWindowHandles();
            for (String win : oldWindows) {
                newWindows.remove(win);
            }
            System.out.println("New WINDOW " + newWindows.toArray(new String[newWindows.size()])[0]);
            driver.switchTo().window(newWindows.toArray(new String[newWindows.size()])[0]);
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("EXCEPTION: Link Click");
        }
        return parentWindow;
    }

    public static void scrollToPageMidInSteps() {
        try {
            scrollToPageEnd();
            long totalHeight = (long) ((JavascriptExecutor) driver).executeScript("return document.body.scrollHeight");
            long scrollHeight = totalHeight / 10;
            for (int i = 1; i <= 5; i++) {
                JavascriptExecutor j = (JavascriptExecutor) driver;
                j.executeScript("window.scrollBy(0, -" + scrollHeight + ")");

                WaitLibrary.iWaitMs(300);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getTextFromListWebElements(List<WebElement> elements) {
        String text = "";
        try {
            for (WebElement ele : elements) {
                text = text + GenericMethods.getElementTxt(ele,"") + " ";
            }
        } catch (Exception e) {
        }
        return text;
    }

}