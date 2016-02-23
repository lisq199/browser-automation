package io.ologn.autotesting.testtemplates;

import org.junit.BeforeClass;

import io.ologn.autotesting.browsers.IeBrowser;

/**
 * Test template for IE. 
 * If you want to write a test using IE, extend 
 * this class.
 * @author sli
 */
public abstract class IeTestTemplate extends TestTemplate {
	
	@BeforeClass
	public static void beforeClass() {
		setBrowser(new IeBrowser());
	}

}
