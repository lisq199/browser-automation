package io.ologn.autotesting.testtemplates;

import org.openqa.selenium.interactions.HasTouchScreen;
import org.openqa.selenium.interactions.touch.TouchActions;

import io.ologn.autotesting.browsers.Browser;

/**
 * A test template for all the tests involving touch screen 
 * capabilities.
 * @author sli
 */
public abstract class TouchScreenTestTemplate extends TestTemplate {
	
	protected static TouchActions touchActions;
	
	/**
	 * Hides {@link TestTemplate#setBrowser(Browser)}. It also 
	 * initializes touchAction.
	 * @param newBrowser
	 */
	protected static void setBrowser(Browser newBrowser) {
		TestTemplate.setBrowser(newBrowser);
		checkTouchScreen();
		/*
		 * It has to be called after setBrowser(...) to make 
		 * sure that driver has been initialized.
		 */
		touchActions = new TouchActions(driver);
	}
	
	/**
	 * Check if the current WebDriver supports touch screen. 
	 * If not, an UnsupportedOperationException will be thrown.
	 */
	private static void checkTouchScreen()
			throws UnsupportedOperationException {
		if (!(driver instanceof HasTouchScreen)) {
			throw new UnsupportedOperationException("The current "
					+ "WebDriver does not implement HasTouchScreen.");
		}
	}
	
}
