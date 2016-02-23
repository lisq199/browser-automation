package io.ologn.autotesting.utils;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;

import io.ologn.autotesting.testtemplates.TestTemplate;

/**
 * Utilities of Selenium
 * @author sli
 */
public class SeleniumUtils extends TestTemplate {
	
	/**
	 * The short implicit wait time in seconds
	 */
	public static final int SHORT_WAIT = 5;
	/**
	 * The default implicit wait time in seconds
	 */
	public static final int DEFAULT_WAIT = 15;
	/**
	 * The long implicit wait time in seconds
	 */
	public static final int LONG_WAIT = 30;
	
	/**
	 * Set implicit wait. This method exists to replace 
	 * the cumbersome method that is built in.
	 * @param timeInSeconds
	 */
	public static void setImplicitWait(long timeInSeconds) {
		driver.manage().timeouts().implicitlyWait(
				timeInSeconds, TimeUnit.SECONDS);
	}
	
	/**
	 * An easy way of calling 
	 * {@link JavascriptExecutor#executeScript(String, Object...)}. 
	 * For detailed documentation, open the declaration above, or 
	 * check out <a href="http://selenium.googlecode.com/git/docs/api/java/org/openqa/selenium/JavascriptExecutor.html#executeScript%28java.lang.String,%20java.lang.Object...%29">
	 * JavascriptExecutor</a>
	 * @param script
	 * @param args
	 * @return
	 * @throws UnsupportedOperationException
	 */
	public static Object executeScript(String script, Object... args)
			throws UnsupportedOperationException {
		if (driver instanceof JavascriptExecutor) {
			return ((JavascriptExecutor) driver).executeScript(script, args);
		} else {
			throw new UnsupportedOperationException("The current "
					+ "WebDriver does not implement JavascriptExecutor.");
		}
	}
	
	/**
	 * An easy way of calling 
	 * {@link JavascriptExecutor#executeAsyncScript(String, Object...)}. 
	 * For detailed documentation, open the declaration above, or 
	 * check out <a href="http://selenium.googlecode.com/git/docs/api/java/org/openqa/selenium/JavascriptExecutor.html#executeAsyncScript%28java.lang.String,%20java.lang.Object...%29">
	 * JavascriptExecutor</a>
	 * @param script
	 * @param args
	 * @return
	 * @throws UnsupportedOperationException
	 */
	public static Object executeAsyncScript(String script, Object... args)
			throws UnsupportedOperationException {
		if (driver instanceof JavascriptExecutor) {
			return ((JavascriptExecutor) driver)
					.executeAsyncScript(script, args);
		} else {
			throw new UnsupportedOperationException("The current "
					+ "WebDriver does not implement JavascriptExecutor.");
		}
	}
	
	/**
	 * Check if a WebElement exists.
	 * @param by
	 * @return
	 */
	public static boolean doesElementExist(By by) {
		setImplicitWait(SHORT_WAIT);
		try {
			return !driver.findElements(by).isEmpty();
		} finally {
			setImplicitWait(DEFAULT_WAIT);
		}
	}
	
	/**
	 * Check if a WebElement that should be displayed is displayed.
	 * @param by
	 * @param notFound Error message to be displayed if the web 
	 * element is not found
	 * @param foundButNotDisplayed Error message to be displayed 
	 * if the web element is found but not displayed
	 */
	public static void verifyDisplayedElement(By by, String notFound,
			String foundButNotDisplayed) {
		setImplicitWait(SHORT_WAIT);
		try {
			Assert.assertTrue(foundButNotDisplayed,
					driver.findElement(by).isDisplayed());
		} catch (NoSuchElementException e) {
			e.printStackTrace();
			Assert.fail(notFound);
		} finally {
			setImplicitWait(DEFAULT_WAIT);
		}
	}
	
}
