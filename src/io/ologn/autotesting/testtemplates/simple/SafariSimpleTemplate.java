package io.ologn.autotesting.testtemplates.simple;

import org.junit.Test;

import io.ologn.autotesting.testtemplates.SafariTestTemplate;

/**
 * This class exists to make it easier to switch 
 * between simple templates
 * @author sli
 */
public abstract class SafariSimpleTemplate extends SafariTestTemplate {
	
	@Test
	public void testFirefox() {
		test();
	}
	
	public abstract void test();
	
}
