package io.ologn.autotesting.testtemplates;

import io.ologn.autotesting.browsers.AndroidBrowser;
import io.selendroid.SelendroidConfiguration;
import io.selendroid.SelendroidLauncher;

import org.junit.AfterClass;
import org.junit.BeforeClass;

/**
 * Test template for Android. 
 * If you want to write a test using Android devices, 
 * extend this class.
 * @author sli
 */
public abstract class AndroidTestTemplate extends TouchScreenTestTemplate {
	
	/**
	 * Launching Selendroid driver requires a SelendroidLauncher.
	 */
	private static SelendroidLauncher selendroidLauncher = null;
	
	@BeforeClass
	public static void beforeClass() {
		if (selendroidLauncher != null) {
			selendroidLauncher.stopSelendroid();
	    }
		SelendroidConfiguration config = new SelendroidConfiguration();
		selendroidLauncher = new SelendroidLauncher(config);
	    selendroidLauncher.launchSelendroid();
	    setBrowser(new AndroidBrowser());
	}
	
	@AfterClass
	public static void afterClass() {
		TestTemplate.afterClass();
		if (selendroidLauncher != null) {
			selendroidLauncher.stopSelendroid();
	    }
	}
	
}
