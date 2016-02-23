package io.ologn.autotesting.testtemplates.simple;

import org.junit.Test;

import io.ologn.autotesting.testtemplates.ChromeTestTemplate;

/**
 * This class exists to make it easier to switch 
 * between simple templates
 * @author sli
 */
public abstract class ChromeSimpleTemplate extends ChromeTestTemplate {
	
	@Test
	public void testChrome() {
		test();
	}
	
	public abstract void test();
	
}
