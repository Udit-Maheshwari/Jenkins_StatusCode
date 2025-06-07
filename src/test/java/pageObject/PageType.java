package pageObject;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import Test.BaseTest;
import utils.GenericMethods;

public class PageType extends BaseTest {
	
	public PageType() {
		PageFactory.initElements(driver, this);
	}
	
	
	 //----------------Verify A.com pages type is milo or dexter---------------------//
  	@FindBy(xpath = "//*[self::div[@daa-lh='s1'] or self::*[contains(@class,'milo')] or self::*[contains(@daa-lh,'milo')]]")
  	private static WebElement miloPageSection;
  	
  	public String verifyCurrentPageTypeIsMilo_Dexter() {
  		String pageType = "";
  		try {
  			GenericMethods.scrollToMidPage();
  			if(GenericMethods.checkVisibilityOfEle(miloPageSection, "")) {
  				pageType = "MILO";
  			}else {
  				pageType = "Dexter";
  			}
  		} catch (Exception e) {
  			pageType = "Dexter";
  			e.printStackTrace();
  		}
  		return pageType;
  	}
}
