package io.ologn.autotesting.browsers;

import java.awt.MouseInfo;
import java.io.File;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.sikuli.script.FindFailed;

import io.ologn.autotesting.testtemplates.TestTemplate;
import io.ologn.autotesting.utils.DirUtils;
import io.ologn.autotesting.utils.os.WindowsUtils;
import io.ologn.common.os.OsName;
import io.ologn.common.os.WinVersion;

/**
 * IE.
 * <br><br>
 * Before you begin:<br>
 * 1. TURN OFF PROTECTED MODE!!!<br>
 * 2. For IE 11 only, you will need to set a registry entry on the 
 * target computer so that the driver can maintain a connection to 
 * the instance of Internet Explorer it creates. For 32-bit Windows 
 * installations, the key you must examine in the registry editor is<br>
 * HKEY_LOCAL_MACHINE\SOFTWARE\Microsoft\Internet Explorer\Main\FeatureControl\FEATURE_BFCACHE. 
 * <br>For 64-bit Windows installations, the key is<br>
 * HKEY_LOCAL_MACHINE\SOFTWARE\Wow6432Node\Microsoft\Internet Explorer\Main\FeatureControl\FEATURE_BFCACHE. 
 * <br>Please note that the FEATURE_BFCACHE subkey may or may not be 
 * present, and should be created if it is not.<br>
 * Important: Inside this key, create a DWORD value named iexplore.exe 
 * with the value of 0.
 * 
 * @author sli
 */
public class IeBrowser extends TestTemplate implements Browser {
	
	public static final String RES_DIR = DirUtils.getResDir(IeBrowser.class);
	/**
	 * The directory storing platform-specific screenshots.
	 */
	public static final String IMG_DIR = getImgDir();
	public static final String DRIVER_NAME = "IEDriverServer.exe";
	
	/**
	 * With IE, you have to manually allow it to share 
	 * location.
	 */
	public static void shareLocation() {
		if (!browser.is(BrowserName.IE)) {
			return;
		}
		java.awt.Point originalMousePos =
				MouseInfo.getPointerInfo().getLocation();
		try {
			screen.click(IMG_DIR + "share_location.png");
		} catch (FindFailed e) {
			e.printStackTrace();
			System.out.println("[error] Allowing sharing location failed");
		}
		robot.mouseMove(originalMousePos.x, originalMousePos.y);
	}

	/**
	 * Sometimes with IE, you have to click on Allow blocked 
	 * content before continuing. 
	 * screen.wait() will block the IE driver if 
	 * run in the same thread.<br>
	 * Note: Right now this only works with Windows 8 or 8.1 
	 * because the screenshots contain Windows 8 UI elements.
	 */
	public static void allowBlockedContent() {
		if (!browser.is(BrowserName.IE)) {
			return;
		}
		new Thread() {
			@Override
			public void run() {
				try {
					screen.wait(IMG_DIR + "allow_blocked_content.png", 15);
					// screen.click() will click on the last match.
					screen.click();
				} catch (FindFailed e) {
					e.printStackTrace();
					System.out.println(
							"[error] Allow blocked content failed/");
				}
			}
		}.start();
	}

	@Override
	public WebDriver getWebDriver() {
		if (!OsName.WINDOWS.isCurrent()) {
			return null;
		}
		File ieDriver = new File(RES_DIR + DRIVER_NAME);
		System.setProperty("webdriver.ie.driver", ieDriver.getAbsolutePath());
		DesiredCapabilities capabilities = 
				DesiredCapabilities.internetExplorer();
		capabilities.setCapability(
			    InternetExplorerDriver.
			    INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
			    true);
		return new InternetExplorerDriver(capabilities);
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
		// No need to check the OS here
		return 55;
	}

	@Override
	public BrowserName getBrowserName() {
		return BrowserName.IE;
	}

	@Override
	public OsName[] getSupportedOsNames() {
		return new OsName[] {OsName.WINDOWS};
	}
	
	/**
	 * IEDriverServer.exe needs to be manually killed or it 
	 * will keep running in the background.
	 */
	@Override
	public void killWebDriver() {
		// No need to check the OS here
		WindowsUtils.taskkill(DRIVER_NAME);
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
		if (!OsName.WINDOWS.isCurrent()) {
			return null;
		}
		String imgDir = RES_DIR;
		switch (WinVersion.getCurrent()) {
		// TODO Add more Windows versions!
		case WIN_8:
		case WIN_8_1:
			imgDir += "win8" + File.separator;
			break;
		default:
			System.out.println("[error] The current Windows version "
					+ "is not supported.");
			break;
		}
		return imgDir;
	}
	
}
