package io.ologn.autotesting.browsers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import io.ologn.common.os.OsName;

/**
 * HtmlUnit.
 * @author sli
 */
public class HtmlUnitBrowser implements Browser {

	@Override
	public WebDriver getWebDriver() {
		return new HtmlUnitDriver();
	}
	
	@Override
	public boolean hasWindow() {
		return false;
	}
	
	@Override
	public int getXOffset() {
		// N/A for HtmlUnit
		return 0;
	}

	@Override
	public int getYOffset() {
		// N/A for HtmlUnit
		return 0;
	}

	@Override
	public BrowserName getBrowserName() {
		return BrowserName.HTMLUNIT;
	}

	@Override
	public OsName[] getSupportedOsNames() {
		// It supports all OSes that can run Java.
		return OsName.values();
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
