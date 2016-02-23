package io.ologn.autotesting.tests.samples;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.sikuli.script.FindFailed;

import io.ologn.autotesting.testtemplates.IeTestTemplate;
import io.ologn.autotesting.utils.DirUtils;

/**
 * A simple test with IE
 * @author sli
 */
public class IeSampleTest extends IeTestTemplate {
	
	public static final String RES_DIR =
			DirUtils.getResDir(IeSampleTest.class);
	
	/**
	 * If you want to override {@code before()} in 
	 * {@link io.ologn.autotesting.testtemplates.TestTemplate},
	 * be sure to call {@code super.before()}.
	 */
	@Before
	public void before() {
		driver.get("http://google.com");
	}
	
	/**
	 * Search for "avocado" and see if the search is successful.
	 */
	@Test
	public void testQuery() {
        WebElement query = driver.findElement(By.name("q"));
        // Type "avocado" into the search field and hit Enter.
        /*
         * Notice that IE is typing extremely slowly. It would 
         * be a better idea to use screen.type("avocado\n").
         */
        query.sendKeys("avocado\n");
        // Wait for the search result to appear for 10 seconds max.
        try {
			screen.wait(RES_DIR + "avocado.png", 10);
		} catch (FindFailed e) {
			e.printStackTrace();
			Assert.fail("Search for 'Avocado' failed");
		}
	}
	
	/**
	 * Check if the website's title is "Google".
	 */
	@Test
	public void testTitle() {
		/*
		 * Sometimes the wait is necessary, but here it's not. 
		 * The wait here is simply to make it easier to see 
		 * the web page.
		 */
		screen.wait(2.5);
		Assert.assertEquals("Title is not 'Google'",
				"Google", driver.getTitle());
	}
	
}
