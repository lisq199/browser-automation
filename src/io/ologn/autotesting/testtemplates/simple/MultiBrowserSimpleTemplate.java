package io.ologn.autotesting.testtemplates.simple;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import io.ologn.autotesting.testtemplates.ChromeTestTemplate;
import io.ologn.autotesting.testtemplates.FirefoxTestTemplate;
import io.ologn.autotesting.testtemplates.IeTestTemplate;
import io.ologn.autotesting.testtemplates.SafariTestTemplate;
import io.ologn.autotesting.testtemplates.TestTemplate;

/**
 * This test template makes it easier for you to write tests 
 * for multiple browsers (Chrome, Firefox and IE on Windows, 
 * and Chrome, Firefox and Safari on Mac). All you need 
 * to do is extend this class, and implement one method, which 
 * is going to be your test case.
 * @author sli
 */
// Make sure the tests are run in alphebetical order
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public abstract class MultiBrowserSimpleTemplate extends TestTemplate {
	
	/**
	 * Close the browser after each test, because every 
	 * test is using a different browser.
	 */
	@After
	public void after() {
		/*
		 * Since every @Test method is calling test(), it may look like 
		 * a good idea to call test() here. However, if test() is called 
		 * here, multiple exceptions will be thrown, causing the test 
		 * to fail. Normally, if the current OS is not supported, the 
		 * test will be skipped without failing.
		 */
		// Quit the driver
		if (driver != null) {
			driver.quit();
		}
		/*
		 * Killing the WebDriver can take a few seconds, 
		 * and it doesn't affect the next test. So I'm 
		 * running it in another thread.
		 */
		new Thread() {
			@Override
			public void run() {
				if (browser != null) {
					browser.killWebDriver();
				}
			}
		}.start();
	}
	
	/**
	 * Hide {@link TestTemplate#afterClass()}. 
	 * The driver has already been stopped, so there is 
	 * no need to stop it again.
	 */
	@AfterClass
	public static void afterClass() {
		// Empty method
	}
	
	/**
	 * Run the test method with Chrome
	 */
	@Test
	public void testChrome() {
		// Initialization for Chrome
		ChromeTestTemplate.beforeClass();
		test();
	}
	
	/**
	 * Run the test method with Firefox
	 */
	@Test
	public void testFirefox() {
		// Initialization for Firefox
		FirefoxTestTemplate.beforeClass();
		test();
	}
	
	/**
	 * Run the test method with IE
	 */
	@Test
	public void testIe() {
		// Initialization for IE
		IeTestTemplate.beforeClass();
		test();
	}
	
	/**
	 * Run the test method with Safari
	 */
	@Test
	public void testSafari() {
		// Initialization for Safari
		SafariTestTemplate.beforeClass();
		test();
	}
	
	/**
	 * This is the only method that needs to be implemented. 
	 * All the testing should go into this method.
	 */
	public abstract void test();

}
