package io.ologn.autotesting.browsers;

import java.awt.Toolkit;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;

import io.ologn.common.os.OsName;

/**
 * An interface for all supported browsers for the 
 * purpose of handling the differences between browsers.
 * @author sli
 */
public interface Browser {
	
	/**
	 * Get the {@link WebDriver} of the corresponding browser
	 * @return
	 */
	public WebDriver getWebDriver();
	
	/**
	 * If the browser has a GUI, then the window should be 
	 * maximized automatically before every test. Maximizing 
	 * a window that doesn't exist will cause an error.<br>
	 * Note: Mobile devices are not considered to have a 
	 * window.
	 * @return if the browser has a window
	 */
	public boolean hasWindow();
	
	/**
	 * Maximize the browser window. If a browser requires its 
	 * own implementation, override this method.
	 * @param driver
	 */
	public default void maximizeWindow(WebDriver driver) {
		if (!hasWindow()) {
			return;
			//driver.manage().window().maximize();
		}
		/*
		 * Mac doesn't really support "maximizing" the window. When 
		 * (before Yosemite) the "maximize" button is clicked or 
		 * (since Yosemite) the title bar is double clicked, the 
		 * window is not necessarily maximized. Instead, it will 
		 * change the window to the "appropriate" size. So if it's 
		 * running on a Mac, the window will be "manually" maximized.
		 */
		if (OsName.OS_X.isCurrent()) {
			// Get the screen size
			java.awt.Dimension screenSize =
					Toolkit.getDefaultToolkit().getScreenSize();
			// Put the window to the top left-hand corner
			driver.manage().window().setPosition(new Point(0, 0));
			// Set the window size to screen size
			driver.manage().window().setSize(new Dimension(
					screenSize.width, screenSize.height));
		} else {
			driver.manage().window().maximize();
		}
	}
	
	/**
	 * Get the X offset off the corresponding browser. 
	 * For all the major browsers, it is 0. It's included 
	 * here just to be more future proof.
	 * @return
	 */
	public int getXOffset();
	
	/**
	 * Get the Y offset of the corresponding browser. 
	 * Return 0 if it's not applicable.
	 * @return
	 */
	public int getYOffset();
	
	/**
	 * Get the browser's corresponding {@link BrowserName}
	 * @return
	 */
	public BrowserName getBrowserName();
	
	/**
	 * Check if the browser is one or one of multiple specified 
	 * {@link BrowserName}s.
	 * @param names
	 * @return
	 */
	public default boolean is(BrowserName... names) {
		BrowserName currentName = getBrowserName();
		for (BrowserName name : names) {
			if (currentName == name) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Get all the supported operating systems as an array of 
	 * {@link OsName}
	 * @return
	 */
	public OsName[] getSupportedOsNames();
	
	/**
	 * Kill the WebDriver process. If a browser's WebDriver 
	 * doesn't need to be killed manually, leave this method 
	 * empty.
	 */
	public void killWebDriver();
	
	/*
	 * There's no way of forcing the implementation of 
	 * toString(). When you implement Browser, please 
	 * override toString(). The following is just a 
	 * reference.
	
	@Override
	public String toString() {
		return getBrowserName().toString();
	}
	
	 */
	
}
