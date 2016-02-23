package io.ologn.autotesting.webdrivers;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.interactions.HasTouchScreen;
import org.openqa.selenium.interactions.TouchScreen;
import org.openqa.selenium.remote.CommandExecutor;
import org.openqa.selenium.remote.RemoteTouchScreen;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * RemoteWebDriver doesn't support touch screen. Performing 
 * anything involving to TouchAction will crash the program 
 * if RemoteWebDriver is used. Android (and potentially iOS) 
 * should use this class instead of RemoteWebDriver. All 
 * the constructors of RemoteWebDriver are recreated here 
 * and it can be used just like a RemoteWebDriver.
 * @author sli
 */
public class TouchScreenDriver extends RemoteWebDriver 
		implements HasTouchScreen {
	
	/**
	 * {@link TouchScreen} required by {@link HasTouchScreen}.
	 */
	private TouchScreen touch = new RemoteTouchScreen(getExecuteMethod());
	
	protected TouchScreenDriver() {
		super();
	}
	
	public TouchScreenDriver(CommandExecutor executor,
            Capabilities desiredCapabilities,
            Capabilities requiredCapabilities) {
		super(executor, desiredCapabilities, requiredCapabilities);
	}
	
	public TouchScreenDriver(CommandExecutor executor,
            Capabilities desiredCapabilities) {
		super(executor, desiredCapabilities);
	}
	
	public TouchScreenDriver(Capabilities desiredCapabilities) {
		super(desiredCapabilities);
	}
	
	public TouchScreenDriver(java.net.URL remoteAddress,
            Capabilities desiredCapabilities,
            Capabilities requiredCapabilities) {
		super(remoteAddress, desiredCapabilities, requiredCapabilities);
	}
	
	public TouchScreenDriver(java.net.URL remoteAddress,
            Capabilities desiredCapabilities) {
		super(remoteAddress, desiredCapabilities);
	}
	
	@Override
	public TouchScreen getTouch() {
		return touch;
	}
	
}
