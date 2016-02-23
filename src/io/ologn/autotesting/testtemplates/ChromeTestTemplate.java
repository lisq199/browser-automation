package io.ologn.autotesting.testtemplates;

import org.junit.BeforeClass;

import io.ologn.autotesting.browsers.ChromeBrowser;

/**
 * Test template for Chrome. 
 * If you want to write a test using Chrome, extend 
 * this class.
 * @author sli
 */
public abstract class ChromeTestTemplate extends TestTemplate {
	
	@BeforeClass
	public static void beforeClass() {
		setBrowser(new ChromeBrowser());
	}

}
