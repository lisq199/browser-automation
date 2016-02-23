package io.ologn.autotesting.tests.samples;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.touch.FlickAction;

import io.ologn.autotesting.testtemplates.AndroidTestTemplate;

/**
 * This class demonstrates simple use cases of 
 * TouchActions with Selendroid.
 * @author sli
 */
public class AndroidTouchActionsSampleTest extends AndroidTestTemplate {
	
	public static final String VIEWER_URL = 
			"http://vmgetrain1.latitudegeo.com/html5demo";
	public static final String HIDE_BUTTON_CSS =
			"div.logview-header-buttons > button:nth-child(2)";
	public static final String FIFTH_LOG_CSS =
			"div.logview > div.logview-inner > table > tbody > tr:nth-child(5)";
	
	@Test
	public void test() {
		// Go to the GVH
		driver.get(VIEWER_URL);
		// It's recommended to wait 15 seconds for Android
		screen.wait(15.0);
		// Open the log
		/*
		 * To open the log, the user should hold down their 
		 * finger on the title bar. Here's how to implement 
		 * that with TouchActions.
		 */
		touchActions.down(10, 10).perform();
		screen.wait(5.0);
		touchActions.up(10, 10).perform();
		// You now should see log is opened.
		screen.wait(2.5);
		// Flick on the table
		/*
		 * Flicking on the whole table, i.e. tbody, will not work, 
		 * because it only works with small elements, which 
		 * is why I'm letting it flick on a row of the table.
		 */
		WebElement fifthLog = null;
		try {
			fifthLog = driver.findElement(By.cssSelector(FIFTH_LOG_CSS));
		} catch (NoSuchElementException e) {
			e.printStackTrace();
			Assert.fail("Fifth log not found");
		}
		touchActions.flick(fifthLog, 0, 500, 
				FlickAction.SPEED_NORMAL).perform();
		// Close the log by tapping on the hide button
		WebElement hideButton = null;
		try {
			hideButton = driver.findElement(By.cssSelector(HIDE_BUTTON_CSS));
		} catch (NoSuchElementException e) {
			e.printStackTrace();
			Assert.fail("Hide button not found");
		}
		// Using hideButton.click() can achieve the same result
		touchActions.singleTap(hideButton).perform();
		screen.wait(2.5);
	}
	
}
