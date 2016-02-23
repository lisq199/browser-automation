package io.ologn.autotesting.tests.samples;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.sikuli.script.FindFailed;

import io.ologn.autotesting.testtemplates.FirefoxTestTemplate;
import io.ologn.autotesting.utils.DirUtils;

/**
 * A simple test with Firefox.
 * @see {@link IeSampleTest} for more documentation.
 * @author sli
 */
public class FirefoxSampleTest extends FirefoxTestTemplate {
	
	public static final String RES_DIR =
			DirUtils.getResDir(FirefoxSampleTest.class);
	
	@Before
	public void before() {
		driver.get("http://google.com");
	}
	
	@Test
	public void testQuery() {
        WebElement query = driver.findElement(By.name("q"));
        query.sendKeys("avocado\n");
        try {
			screen.wait(RES_DIR + "avocado.png", 10);
		} catch (FindFailed e) {
			e.printStackTrace();
			Assert.fail("Search for 'Avocado' failed");
		}
	}
	
	@Test
	public void testTitle() {
		screen.wait(2.5);
		Assert.assertEquals("Title is not 'Google'",
				"Google", driver.getTitle());
	}
	
}
