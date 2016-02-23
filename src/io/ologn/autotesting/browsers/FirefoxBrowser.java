package io.ologn.autotesting.browsers;

import java.awt.MouseInfo;
import java.io.File;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.sikuli.script.FindFailed;

import io.ologn.autotesting.testtemplates.TestTemplate;
import io.ologn.autotesting.utils.DirUtils;
import io.ologn.common.os.MacVersion;
import io.ologn.common.os.OsName;
import io.ologn.common.os.WinVersion;

/**
 * Firefox.
 * @author sli
 */
public class FirefoxBrowser extends TestTemplate implements Browser {
	
	/**
	 * An easier way of accessing file separator
	 */
	public static final char s = File.separatorChar;
	public static final String RES_DIR =
			DirUtils.getResDir(FirefoxBrowser.class);
	/**
	 * The directory storing platform-specific screenshots.
	 */
	public static final String IMG_DIR = getImgDir();
	
	/**
	 * With Firefox, you have to manually allow it to share 
	 * location.
	 */
	public static void shareLocation() {
		if (!browser.is(BrowserName.FIREFOX)) {
			return;
		}
		try {
			screen.click(IMG_DIR + "share_location.png");
			screen.click();
		} catch (FindFailed e) {
			e.printStackTrace();
			System.out.println("[error] Allowing sharing location failed");
		}
	}

	/**
	 * If you are using Firefox with Silverlight, you'll need 
	 * to activate Silverlight every time even if you choose 
	 * Allow and Remember. Simply call this 
	 * method, and it will perform all the actions needed to 
	 * activate Silverlight.<br>
	 * Note: Right now this only works with Windows 8 or 8.1 
	 * because the screenshots contain Windows 8 UI elements.
	 * @deprecated Silverlight is on its way out.
	 */
	@Deprecated
	public static void activateSilverlight() {
		if (!browser.is(BrowserName.FIREFOX)) {
			return;
		}
		java.awt.Point originalMousePos =
				MouseInfo.getPointerInfo().getLocation();
		try {
			screen.click(IMG_DIR + "activate_silverlight.png");
			screen.click(IMG_DIR + "allow_now.png");
		} catch (FindFailed e) {
			e.printStackTrace();
			System.out.println("[error] Activating Silverlight failed.");
		}
		robot.mouseMove(originalMousePos.x, originalMousePos.y);
	}

	/**
	 * Firefox somehow doesn't support Windows Authentication 
	 * when launched with Selenium. So this method provides a 
	 * workaround by manually entering the username and password 
	 * in another thread.
	 * @param toBeTyped The string to be typed
	 */
	public static void enterWindowsAuthentication(final String toBeTyped) {
		if (!browser.is(BrowserName.FIREFOX)) {
			return;
		}
		new Thread() {
			@Override
			public void run() {
				screen.wait(2.5);
				screen.type(toBeTyped);
			}
		}.start();
	}

	@Override
	public WebDriver getWebDriver() {
		/*
		 * I'm printing this because both ChromeDriver and 
		 * InternetExplorerDriver will print something like 
		 * this when initialized. And for some reason, they 
		 * are both printing to stderr.
		 */
		System.err.println("Starting FirefoxDriver");
		return new FirefoxDriver();
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
		switch (OsName.getCurrent()) {
		case WINDOWS:
			return 71;
		case OS_X:
			return 103;
		case UNIX:
			/*
			 * This may not be worth supporting because of all the 
			 * Linux distributions. 
			 */
			return 0;	// TODO Add Unix/Linux support
		default:
			return 0;
		}
	}

	@Override
	public BrowserName getBrowserName() {
		return BrowserName.FIREFOX;
	}

	@Override
	public OsName[] getSupportedOsNames() {
		return OsName.MAINSTREAM_OS_NAMES;
	}

	@Override
	public void killWebDriver() {
		// Empty method
	}

	@Override
	public String toString() {
		return getBrowserName().toString();
	}
	
	/**
	 * Get the image directory for the current OS.
	 * @return
	 */
	private static String getImgDir() {
		String imgDir = RES_DIR;
		switch (OsName.getCurrent()) {
		case WINDOWS:
			imgDir += "win" + s;
			switch (WinVersion.getCurrent()) {
			// TODO Add more Windows versions!
			case WIN_8:
			case WIN_8_1:
				imgDir += "win8" + s;
				break;
			default:
				System.out.println("[error] The current Windows version "
						+ "is not supported.");
				break;
			}
			break;
		case OS_X:
			imgDir += "mac" + s;
			switch (MacVersion.getCurrent()) {
			// TODO Add more Mac versions!
			case OS_X_10_10:
				imgDir += "10.10" + s;
				break;
			default:
				System.out.println("[error] The current Mac version "
						+ "is not supported.");
				break;
			}
			break;
		case UNIX:
			imgDir += "unix" + s;	// TODO Add Unix/Linux support
			break;
		default:
			System.out.println("[error] The current OS is not supported.");
			return null;
		}
		return imgDir;
	}
	
}
