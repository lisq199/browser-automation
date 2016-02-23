package io.ologn.autotesting.testtemplates;

import org.junit.BeforeClass;

import io.ologn.autotesting.browsers.SafariBrowser;

/**
 * Test template for Safari. 
 * If you want to write a test using Safari, extend 
 * this class.
 * @author sli
 */
public abstract class SafariTestTemplate extends TestTemplate {
	
	@BeforeClass
	public static void beforeClass() {
		setBrowser(new SafariBrowser());
	}
	
}
