package io.ologn.autotesting.browsers;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;

import io.ologn.autotesting.utils.DirUtils;
import io.ologn.autotesting.utils.os.WindowsUtils;
import io.ologn.common.os.OlognOs;
import io.ologn.common.os.OsName;

/**
 * Chrome.
 * <br><br>
 * Before you begin on Windows:<br>
 * Open regedit.exe and navigate to: 
 * HKEY_LOCAL_MACHINE\Software\JavaSoft 
 * and create a key called Prefs if it's not already there.
 * 
 * @author sli
 */
public class ChromeBrowser implements Browser {
	
	public static final String RES_DIR =
			DirUtils.getResDir(ChromeBrowser.class);
	public static final String WIN_DRIVER_NAME = "chromedriver.exe";
	public static final String MAC_DRIVER_NAME = "chromedriver";
	
	/**
	 * Get the file for Chrome driver executable.
	 * @return
	 */
	public static File getDriverExecutable() {
		String driverName = RES_DIR;
		switch (OsName.getCurrent()) {
		case WINDOWS:
			driverName += "win" + File.separator + WIN_DRIVER_NAME;
			break;
		case OS_X:
			driverName += "mac" + File.separator + MAC_DRIVER_NAME;
			break;
		default:
			return null;
		}
		File driverExecutable = new File(driverName);
		// If it's a Mac, the file is not executable by default
		if (OsName.OS_X.isCurrent()) {
			// Make the file executable using chmod
			try {
				OlognOs.executeCommands(null, "chmod a+x "
						+ driverExecutable.getAbsolutePath());
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("[error] Changing "
						+ driverExecutable.getAbsolutePath()
						+ " to executable failed. The test may still be able"
						+ " to continue if " + MAC_DRIVER_NAME + "is "
						+ "already executable.");
			}
		}
		return driverExecutable;
	}

	@Override
	public WebDriver getWebDriver() {
		ChromeDriverService service = new ChromeDriverService.Builder()
				.usingDriverExecutable(getDriverExecutable())
				.usingAnyFreePort()
				.build();
		/*
		 * The following ChromeOptions is added because, if 
		 * chromedriver is not up to date, it can prevent 
		 * an error message.
		 */
		ChromeOptions options = new ChromeOptions();
		options.addArguments("test-type");
		return new ChromeDriver(service, options);
		/*
		 * With the new ChromeDriver constructor, there is 
		 * no need to manually start the service.
		 */
		//service.start();
		//return new RemoteWebDriver(service.getUrl(), DesiredCapabilities.chrome());
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
			return 61;
		case OS_X:
			return 96;
		default:
			return 0;
		}
	}

	@Override
	public BrowserName getBrowserName() {
		return BrowserName.CHROME;
	}

	@Override
	public OsName[] getSupportedOsNames() {
		return new OsName[] {OsName.WINDOWS, OsName.OS_X};
	}
	
	/**
	 * chromedriver.exe needs to be manually killed or it 
	 * will keep running in the background.<br>
	 * UPDATE: since the recent update of ChromeDriver, it's 
	 * claimed that chromedriver.exe will be automatically 
	 * killed when driver.quit() is called, and most of the 
	 * time it's true. But sometimes it still needs to be 
	 * manually killed.
	 */
	@Override
	public void killWebDriver() {
		switch (OsName.getCurrent()) {
		case WINDOWS:
			WindowsUtils.taskkill(WIN_DRIVER_NAME);
			break;
		default:
			// On Mac the process doesn't need to be killed.
			break;
		}
	}

	@Override
	public String toString() {
		return getBrowserName().toString();
	}

}
