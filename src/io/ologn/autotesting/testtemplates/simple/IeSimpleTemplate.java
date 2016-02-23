package io.ologn.autotesting.testtemplates.simple;

import org.junit.Test;

import io.ologn.autotesting.testtemplates.IeTestTemplate;

/**
 * This class exists to make it easier to switch 
 * between simple templates
 * @author sli
 */
public abstract class IeSimpleTemplate extends IeTestTemplate {
	
	@Test
	public void testIe() {
		test();
	}
	
	public abstract void test();
	
}
