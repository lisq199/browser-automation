package io.ologn.autotesting.tests.samples;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import io.ologn.autotesting.testtemplates.AndroidTestTemplate;

/**
 * A simple test with Selendroid (Selenium for Android).
 * 
 * Before you begin:
 * TODO Add setup process
 * 
 * @author sli
 */
public class AndroidSampleTest extends AndroidTestTemplate {
	
	@Before
	public void before() {
		driver.get("http://google.com");
        screen.wait(2.5);
	}
	
	@Test
	public void testQuery() {
		WebElement query = driver.findElement(By.name("q"));
        query.sendKeys("avocado\n");
        screen.wait(2.5);
        /*
         * Image recognition with Sikuli is not available 
         * on Android. So the web page title is checked here 
         * to see if the search is successful.
         */
        Assert.assertTrue("Title does not contain 'avocado'",
        		driver.getTitle().toLowerCase().contains("avocado"));
	}
	
	@Test
	public void testTitle() {
		/*
		 * The wait here is actually necessary because 
		 * the page loads a lot slower on an Android device.
		 */
		Assert.assertEquals("Title is not 'Google'",
				"Google", driver.getTitle());
	}
	
}
