package pageObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.Set;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;


import Test.BaseTest;
import utils.GenericMethods;
import utils.WaitLibrary;

public class GenericPage extends BaseTest {
	
	public GenericPage() {
        PageFactory.initElements(driver, this);
    }

    public static SoftAssert sAssert;
    public static WebDriver driver1;


	@FindBy(css = "#locale-modal-v2 > button")
	private static WebElement geoPopupMilo;
	
	 //------------------------MILO Page Components ---------------------------------------------//
    @FindBy(xpath = "//div[contains(@id,'locale-modal')]")
    private static WebElement miloGeoRoutingModalLoc;

    @FindBy(xpath = "//div[contains(@id,'locale-modal')]//button[contains(@class,'close')]")
    private static WebElement miloGeoRoutingModalCloseBtn;

    @FindBy(xpath = "//div[@class='gnav-wrapper' or contains(@class,'topnav-wrapper')]")
    private static WebElement miloNav;

    @FindBy(xpath = "//div[@class='gnav-wrapper' or contains(@class,'topnav-wrapper')]//ancestor::header")
    private static WebElement miloNavHeader;

    //@FindBy(xpath = "//div[@class='gnav-wrapper']//div[contains(@class,'mainnav')]//*[self::div[contains(@class,'navitem')] or self::*[contains(@class,'cta')]]/a")
    //@FindBy(xpath = "//div[@class='gnav-wrapper' or contains(@class,'topnav-wrapper')]//div[contains(@class,'navItem--megaMenu')]//button[contains(@daa-lh,'header')]")
    @FindBy(xpath = "//div[@class='gnav-wrapper' or contains(@class,'topnav-wrapper')]//*[contains(@class,'navItem--megaMenu') or contains(@class,'feds-navItem')]//button[contains(@daa-lh,'header')]")
    private static List<WebElement> miloNavMenuOptions;

    //@FindBy(xpath = "//div[@class='gnav-wrapper']//nav[@class='gnav']//a[not(.//ancestor::div[contains(@class,'mainnav') or contains(@id,'profile')])][not(@class='gnav-logo')]")
    @FindBy(xpath = "//div[@class='gnav-wrapper' or contains(@class,'topnav-wrapper')]//nav[@class='gnav' or contains(@class,'topnav')]//a[not(.//ancestor::div[contains(@class,'mainnav') or contains(@id,'profile') or contains(@class,'menu-items')])][not(@class='gnav-logo')][not(./ancestor::p)]")
    private static List<WebElement> miloNavNonMenuOptions;

    //@FindBy(xpath = "//div[@class='gnav-wrapper' or contains(@class,'topnav-wrapper')]//nav[@class='gnav' or contains(@class,'topnav')]//*[self::a or self::button][not(.//ancestor::div[contains(@class,'mainnav') or contains(@id,'profile') or contains(@class,'menu-items') or @class='feds-signIn-dropdown' or @class='feds-menu-content' or @class='feds-search'])][not(@class='gnav-logo' or @class='feds-logo')][not(./ancestor::p)]")
    @FindBy(xpath = "//div[@class='gnav-wrapper' or contains(@class,'topnav-wrapper')]//nav[@class='gnav' or contains(@class,'topnav')]//*[self::a or self::button][not(.//ancestor::div[contains(@id,'profile') or contains(@class,'menu-items') or @class='feds-signIn-dropdown' or @class='feds-menu-content' or @class='feds-search'])][not(@class='gnav-logo' or @class='feds-logo')][not(./ancestor::p)]")
    private static List<WebElement> miloNavOptions;

    @FindBy(xpath = "//div[@class='gnav-wrapper' or contains(@class,'topnav-wrapper')]//nav[contains(@class,'breadcrumbs')]")
    private static WebElement miloBreadCrumbs;

    @FindBy(xpath = "//div[@class='gnav-wrapper' or contains(@class,'topnav-wrapper')]//nav[contains(@class,'breadcrumbs')]//li")
    private static List<WebElement> miloBreadCrumbsElements;

    @FindBy(xpath = "//footer[contains(@class,'footer')][.//div[contains(@class,'menu-content') or contains(@class,'feds-footer-wrapper')]]")
    private static WebElement miloFooter1;

    @FindBy(xpath = "//footer[contains(@class,'footer')]/descendant::div[@class='footer-region' or contains(@class,'feds-regionPicker')]")
    private static WebElement miloFooterRegionSel;

    @FindBy(xpath = "//footer[contains(@class,'footer')]/descendant::div[@class='footer-region' or contains(@class,'feds-regionPicker')]//*[self::a or self::button]")
    private static WebElement miloFooterRegionSelLink;

    @FindBy(xpath = "//div[contains(@id,'langnav')]")
    public static WebElement miloFooterRegionSelPopup;

    @FindBy(xpath = "//div[@class='language-menu']")
    private static WebElement miloFooterRegionSelPopupBlogPage;

    @FindBy(xpath = "//div[contains(@id,'langnav')]//button[contains(@class,'close')]")
    private static WebElement miloFooterRegionSelPopupCloseBtn;
    
    @FindBy(xpath = "//footer[@id='feds-footernav']//nav")
    private static WebElement liveFooter;
    
    @FindAll({@FindBy(xpath = "//footer[@class='footer']/descendant::div[@class='footer-wrapper']"),
    	@FindBy(xpath="//footer[contains(@class,'footer')]")})
    private static WebElement miloFooter;


    public static Map<String, String> checkMiloPageComponents() {
        sAssert = new SoftAssert();
        Map<String, String> pageCompMap = new HashMap<>();
        List<WebElement> elList = new ArrayList<>();
        WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(7));

        System.out.println("01: Checking Geo Routing Modal on the page...");
        try {
            Thread.sleep(1500);
            GenericMethods.scrollToPageTop();
            wait1.until(ExpectedConditions.visibilityOf(miloGeoRoutingModalLoc));
            if (GenericMethods.checkVisibilityOfEle(miloGeoRoutingModalLoc, "")) {
                pageCompMap.put("grm", "PRESENT");
                try {
                    wait1.until(ExpectedConditions.visibilityOf(miloGeoRoutingModalCloseBtn));
                    GenericMethods.scrollToElement(miloGeoRoutingModalCloseBtn);
                    GenericMethods.clickUsingJS(miloGeoRoutingModalCloseBtn, "geo routing modal");
                } catch (Exception ex) {
                }
                waitUntilElementDisappears(miloGeoRoutingModalLoc);
                if (!GenericMethods.checkVisibilityOfEle(miloGeoRoutingModalLoc, "")) {
                    pageCompMap.put("grmStatus", "Pass");
                } else {
                    pageCompMap.put("grmStatus", "Fail");
                }
            } else {
                pageCompMap.put("grm", "NOT PRESENT");
                pageCompMap.put("grmStatus", "Fail");
            }
        } catch (Exception e) {
            pageCompMap.put("grm", "NOT PRESENT");
            pageCompMap.put("grmStatus", "Fail");
        }

        System.out.println("02: Checking Nav on the page...");
        String navOptions = "", navOptText = "";
        int navOptionsCount = 0;
        try {
            GenericMethods.scrollToPageTop();
            wait1.until(ExpectedConditions.visibilityOf(miloNav));
            if (GenericMethods.checkVisibilityOfEle(miloNav, "")) {
                pageCompMap.put("nav", "PRESENT");
                try {
                    if (GenericMethods.checkVisibilityOfEleWithHeight(miloNavHeader, "")) {
                        pageCompMap.put("navType", miloNavHeader.getAttribute("daa-lh").trim());
                    }
                } catch (Exception e) {
                }
                List<WebElement> navListOptions = new ArrayList<WebElement>();
                /*navListOptions.addAll(miloNavNonMenuOptions);
                navListOptions.addAll(miloNavMenuOptions);*/
                navListOptions.addAll(miloNavOptions);


                navListOptions = GenericMethods.getVisibleElementsInList(navListOptions);

                for (int i = 0; i < navListOptions.size(); i++) {
                    navOptText = GenericMethods.getElementTxtWithoutStyleData(navListOptions.get(i), "");
                    if (!navOptText.isBlank()) {
                        if (i == navListOptions.size() - 1) {
                            navOptions = navOptions + navOptText + ".";
                        } else {
                            navOptions = navOptions + navOptText + ", \n";
                        }
                        navOptionsCount++;
                    }
                }
                pageCompMap.put("navOptions", navOptions);
                if (navOptionsCount > 2) {
                    pageCompMap.put("navStatus", "Pass");
                } else {
                    pageCompMap.put("navStatus", "Fail");
                }
            } else {
                pageCompMap.put("nav", "NOT PRESENT");
                pageCompMap.put("navType", "NOT PRESENT");
                pageCompMap.put("navStatus", "Fail");
            }

        } catch (Exception e) {
            pageCompMap.put("nav", "NOT PRESENT");
            pageCompMap.put("navType", "NOT PRESENT");
            pageCompMap.put("navStatus", "Fail");
        }

        System.out.println("03: Checking Header breadcrumbs on the page...");
        String breadCrumbText = "";
        try {
            wait1.until(ExpectedConditions.visibilityOf(miloBreadCrumbs));
            if (GenericMethods.checkVisibilityOfEle(miloBreadCrumbs, "")) {
                pageCompMap.put("brcmbs", "PRESENT");

                List<WebElement> navBreadCrumbList = GenericMethods.getVisibleElementsInList(miloBreadCrumbsElements);
                for (int i = 0; i < navBreadCrumbList.size(); i++) {
                    if (i == navBreadCrumbList.size() - 1) {
                        breadCrumbText = breadCrumbText + GenericMethods.getElementTxt(navBreadCrumbList.get(i), "").replaceAll("&amp;", "&");
                    } else {
                        breadCrumbText = breadCrumbText + GenericMethods.getElementTxt(navBreadCrumbList.get(i), "").replaceAll("&amp;", "&") + "/";
                    }
                }

                pageCompMap.put("brcmbsTxt", breadCrumbText);
                pageCompMap.put("brcmbsStatus", "Pass");
            } else {
                pageCompMap.put("brcmbs", "NOT PRESENT");
                pageCompMap.put("brcmbsStatus", "FAIL");
            }
        } catch (Exception e) {
            pageCompMap.put("brcmbs", "NOT PRESENT");
            pageCompMap.put("brcmbsStatus", "FAIL");
        }

        System.out.println("04: Checking Footer on the page...");
        String footerType = "", footerClass = "";
        try {
        	WaitLibrary.iSleep(04);
            wait.until(ExpectedConditions.visibilityOf(miloFooter1));
            if (GenericMethods.checkVisibilityOfEle(miloFooter1, "")) {
                pageCompMap.put("footer", "PRESENT");
                try {
                    footerClass = miloFooter1.getAttribute("class").trim();
                    if (footerClass.contains("small")) {
                        footerType = "Small Footer";
                    } else {
                        footerType = "Large Footer";
                    }
                } catch (Exception e) {
                }
                pageCompMap.put("footerType", footerType);
                pageCompMap.put("footerStatus", "Pass");

            } else {
                pageCompMap.put("footer", "NOT PRESENT");
                pageCompMap.put("footerType", "NOT PRESENT");
                pageCompMap.put("footerStatus", "Fail");
            }
        } catch (Exception e) {
            pageCompMap.put("footer", "NOT PRESENT");
            pageCompMap.put("footerType", "NOT PRESENT");
            pageCompMap.put("footerStatus", "Fail");
        }

        System.out.println("05: Checking Region Selector on the page...");
        Boolean regionSelPresence = false, regionSelOpen = false, regionSelClose = false;
        try {
        	Thread.sleep(2000);
            wait.until(ExpectedConditions.visibilityOf(miloFooterRegionSel));
            if (GenericMethods.checkVisibilityOfEle(miloFooterRegionSel, "")) {
                pageCompMap.put("rgnslctr", "PRESENT");
                regionSelPresence = true;

                GenericMethods.scrollToElement(miloFooterRegionSel);
                GenericMethods.clickUsingJS(miloFooterRegionSelLink, "");

                Thread.sleep(4000);
                if (GenericMethods.checkVisibilityOfEle(miloFooterRegionSelPopup, "") || GenericMethods.checkVisibilityOfEle(miloFooterRegionSelPopupBlogPage, "")) {
                    pageCompMap.put("rgnslctrOpen", "WORKING");
                    regionSelOpen = true;

                    if (GenericMethods.checkVisibilityOfEle(miloFooterRegionSelPopupCloseBtn, "")) {
                        GenericMethods.clickUsingJS(miloFooterRegionSelPopupCloseBtn, "");
                        Thread.sleep(1000);
                        try {
                            wait1.until(ExpectedConditions.invisibilityOf(miloFooterRegionSelPopup));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (!GenericMethods.checkVisibilityOfEle(miloFooterRegionSelPopup, "")) {
                            pageCompMap.put("rgnslctrClose", "WORKING");
                            regionSelClose = true;
                        } else {
                            pageCompMap.put("rgnslctrClose", "NOT WORKING");
                        }
                    }else if (GenericMethods.checkVisibilityOfEle(miloFooterRegionSelPopupBlogPage, "") ){
                        GenericMethods.clickUsingJS(miloFooterRegionSelLink, "");
                        Thread.sleep(1000);
                        try {
                            wait1.until(ExpectedConditions.invisibilityOf(miloFooterRegionSelPopupBlogPage));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (!GenericMethods.checkVisibilityOfEle(miloFooterRegionSelPopupBlogPage, "")) {
                            pageCompMap.put("rgnslctrClose", "WORKING");
                            regionSelClose = true;
                        } else {
                            pageCompMap.put("rgnslctrClose", "NOT WORKING");
                        }
                    }
                    else {
                        pageCompMap.put("rgnslctrClose", "NOT WORKING");
                    }
                } else {
                    pageCompMap.put("rgnslctrOpen", "NOT WORKING");
                    pageCompMap.put("rgnslctrClose", "NOT WORKING");
                }
            } else {
                pageCompMap.put("rgnslctr", "NOT PRESENT");
                pageCompMap.put("rgnslctrOpen", "NOT WORKING");
                pageCompMap.put("rgnslctrClose", "NOT WORKING");
            }

            if (regionSelPresence && regionSelOpen && regionSelClose) {
                pageCompMap.put("rgnslctrStatus", "Pass");
            } else {
                pageCompMap.put("rgnslctrStatus", "Fail");
            }

        } catch (Exception e) {
            pageCompMap.put("rgnslctr", "NOT PRESENT");
            pageCompMap.put("rgnslctrOpen", "NOT WORKING");
            pageCompMap.put("rgnslctrClose", "NOT WORKING");
            pageCompMap.put("rgnslctrStatus", "Fail");
        }
        return pageCompMap;
    }
    
    public static void waitUntilElementDisappears(WebElement element) {
        try {
            WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(20));
            wait1.until(ExpectedConditions.invisibilityOf(element));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void scrollDownTillFooterIsDisplayed() {
        for (int i = 1; i <= 30; i++) {
            try {
                PageFactory.initElements(driver, GenericPage.class);
                GenericMethods.scrollToPageEnd();
                if (GenericMethods.checkVisibilityOfEle(liveFooter, "Footer") || GenericMethods.checkVisibilityOfEle(miloFooter, "Footer")) {
                    break;
                } else {
                    System.out.println("Waiting for footer to display !!!");
                    WaitLibrary.iWaitMs(1500);
                }
            } catch (Exception e) {
                System.out.println("Scrolling to Footer Region");
            }
        }
    }
    
    /*
     * This method will close the geo-popup modal
     * @param driver
     */
    @FindBy(xpath="//*[contains(@id,'localeModal') and contains(@class,'is-Open') or contains(@class,'dialog-modal locale-modal')]")
    private static WebElement geoPopupOpen;
    public static By miloGeoPopup = By.xpath("//*[contains(@id,'locale-modal') and contains(@class,'dialog-modal locale-modal')]");
    @FindBy(xpath = "//*[contains(@id,'locale-modal') and contains(@class,'dialog-modal locale-modal')]")
    private static WebElement geoPopupOpenMilo;
    @FindBy(xpath="//*[contains(@id,'locale-modal') and contains(@class,'dialog-modal locale-modal') or @id='localeModal']//*[contains(@aria-label,'Close')]")
    private static WebElement geoPopup;
    
    public static boolean isGeoRoutingPopUpPresent() {
        boolean isPresent = false;
        try {
            //WaitLibrary.eWaitUsingConditions(driver, 1, geoPopupOpen, "visible");
            if (GenericMethods.checkVisibilityOfElement(geoPopupOpen, "Geo Routing Modal")) {
                isPresent = true;
            }
        } catch (NoSuchElementException ex) {
            logger.error("ERROR: Geo Routing Modal missing on Page");
        }
        return isPresent;
    }
    
    public void closeGeoPopUpModal() {
        PageFactory.initElements(driver, GenericPage.class);
        if (isGeoRoutingPopUpPresent()) {
            logger.info("Geo Routing Modal Detected. Closing it.....");
            GenericMethods.scrollToElement(geoPopupOpen);
            //GenericLib.clickBttnOrLink(geoPopup, "Geo IP Pop-up");
            try {
                GenericMethods.clickUsingJS(geoPopup, "Geo Routing Modal Pop-up");
            } catch (Exception e) {
                logger.error("ERROR: Unable to close Geo Routing Modal");
            }
            logger.info("SUCCESS: Geo Routing Modalp closed.....");
        }

    }

}
