package utils;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Test.BaseTest;


public class WaitLibrary extends BaseTest{

	public static void iSleep(int secs) {
		try {
			Thread.sleep(secs * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void iWaitMs(int msecs) {
		try {
			Thread.sleep(msecs);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static boolean isElementDisplayed(WebElement locator, String name)
	{
		try{
			wait.until(ExpectedConditions.visibilityOf(locator));
			if(locator.isDisplayed())
			{
				logger.pass(name + " is displayed");
				return true;
			}
			else{
				logger.fail(name + " is  not displayed");
				return false;
			}
		}
		catch(Exception e)
		{
			return false;
		}
	}
	
	public static void waitVisibility(WebElement e) {
	    wait.until(ExpectedConditions.visibilityOf(e));
	}

	public static void waitElementToBeClickable(WebElement e ) {
	    wait.until(ExpectedConditions.elementToBeClickable(e));
	}
	/*********************/
	public static WebElement eWaitUsingConditions(WebDriver driver, int secs, WebElement ele, String action) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(secs));
		WebElement e1 = null;
		ExpectedCondition<WebElement> condition = null;
		switch (action) {
		case "visible":
			condition = ExpectedConditions.visibilityOf(ele);
			break;
		case "clickable":
			condition = ExpectedConditions.elementToBeClickable(ele);
			break;
		case "refresh":
			ExpectedConditions.refreshed(ExpectedConditions.stalenessOf(ele));
		}
		try {
			e1 = wait.until(ExpectedConditions.refreshed(condition));
		} catch (Exception e) {
			System.err.println(ele + " element is not visible !!!");
		}
		return e1;
	}

	public static void wait(int timeDuration)
	{
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeDuration));
	}
	
	
}

