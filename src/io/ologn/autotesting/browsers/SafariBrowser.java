package io.ologn.autotesting.browsers;

import java.io.File;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.openqa.selenium.safari.SafariDriver;

import io.ologn.autotesting.utils.DirUtils;
import io.ologn.common.os.MacVersion;
import io.ologn.common.os.OsName;

public class SafariBrowser implements Browser {
	
	public static final String RES_DIR =
			DirUtils.getResDir(SafariBrowser.class);
	public static final String EXTENSION_NAME = "SafariDriver.safariextz";
	private static final int DRIVER_INIT_MAX_RETRY_ATTEMPTS = 3;
	
	static {
		// Set the system properties for the SafariDriver extensions.
		File extension = new File(RES_DIR + EXTENSION_NAME);
		System.setProperty("webdriver.safari.driver",
				extension.getAbsolutePath());
		System.setProperty("webdriver.safari.noinstall", "false");
	}

	@Override
	public WebDriver getWebDriver() {
		/*
		 * SafariDriver has a default timeout of 10 seconds for 
		 * some reason. However, launching Safari can sometimes 
		 * take longer than 10 seconds, causing an 
		 * UnreachableBrowserException. When this happens, it 
		 * will retry automatically.
		 */
		for (int i = 1; i <= DRIVER_INIT_MAX_RETRY_ATTEMPTS + 1; i++) {
			try {
				return new SafariDriver();
			} catch (UnreachableBrowserException e) {
				//e.printStackTrace();
				System.out.println("[error] SafariDriver initialization "
						+ "failed. Retry attempt "
						+ i + " of " + DRIVER_INIT_MAX_RETRY_ATTEMPTS + ".");
			}
		}
		// All the retry attempts have failed
		System.out.println("[error] Max number of retry attempt reached. "
				+ "SafariDriver initialization failed.");
		return null;
	}

	@Override
	public boolean hasWindow() {
		return true;
	}

	@Override
	public int getXOffset() {
		return 0;
	}

	@Override
	public int getYOffset() {
		/*
		 * No need to check OS here. 
		 * The tabs bar of Safari is assumed to be opened.
		 */
		switch (MacVersion.getCurrent()) {
		// TODO Add more Mac versions!
		case OS_X_10_10:
			return 86;
		default:
			return 0;
		}
	}

	@Override
	public BrowserName getBrowserName() {
		return BrowserName.SAFARI;
	}

	@Override
	public OsName[] getSupportedOsNames() {
		// Apple stopped supporting Safari for Windows in 2012.
		return new OsName[] {OsName.OS_X};
	}

	@Override
	public void killWebDriver() {
		// Empty method
	}
	
	@Override
	public String toString() {
		return getBrowserName().toString();
	}

}
