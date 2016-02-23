package io.ologn.autotesting.browsers;

import org.openqa.selenium.WebDriver;

import io.ologn.autotesting.webdrivers.TouchScreenDriver;
import io.ologn.common.os.OsName;
import io.selendroid.SelendroidCapabilities;

/**
 * Android.
 * @author sli
 */
public class AndroidBrowser implements Browser {
	
	/**
	 * The official website of Selendroid says the {@link WebDriver} 
	 * should be {@link SelendroidDriver}, which doesn't exist. So I 
	 * created {@link TouchScreenDriver} myself, which is basically 
	 * a {@link org.openqa.selenium.remote.RemoteWebDriver} that 
	 * implements {@link org.openqa.selenium.interactions.HasTouchScreen}.
	 */
	@Override
	public WebDriver getWebDriver() {
		return new TouchScreenDriver(SelendroidCapabilities.android());
	}
	
	@Override
	public boolean hasWindow() {
		return false;
	}
	
	@Override
	public int getXOffset() {
		// N/A for Android
		return 0;
	}

	@Override
	public int getYOffset() {
		// N/A for Android
		return 0;
	}
	
	@Override
	public BrowserName getBrowserName() {
		return BrowserName.ANDROID;
	}

	@Override
	public OsName[] getSupportedOsNames() {
		/*
		 * Theoretically, it supports all the OSes that can run 
		 * both Java and Android SDK.
		 */
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
	
}
