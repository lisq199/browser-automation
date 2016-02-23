package io.ologn.autotesting;

import io.ologn.autotesting.utils.JUnitUtils;

/**
 * 
 * @author sli
 */
public class Main {
	
	/**
	 * EDIT THIS!
	 * This array contains all the test classes to be run.
	 */
	public static final Class<?>[] TEST_CLASSES = new Class<?>[] {
		io.ologn.autotesting.tests.samples.IeSampleTest.class,
		io.ologn.autotesting.tests.samples.FirefoxSampleTest.class,
		io.ologn.autotesting.tests.samples.ChromeSampleTest.class,
		io.ologn.autotesting.tests.samples.MultiBrowserOffsetTest.class,
		io.ologn.autotesting.tests.samples.AndroidSampleTest.class,
	};
	
	public static void main(String[] args) {
		JUnitUtils.runTests(TEST_CLASSES);
	}
	
}
