package io.ologn.autotesting.testtemplates;

import org.junit.BeforeClass;

import io.ologn.autotesting.browsers.FirefoxBrowser;

/**
 * Test template for Firefox. 
 * If you want to write a test using Firefox, extend 
 * this class.
 * @author sli
 */
public abstract class FirefoxTestTemplate extends TestTemplate {
	
	@BeforeClass
	public static void beforeClass() {
		setBrowser(new FirefoxBrowser());
	}
	
}
