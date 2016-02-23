package io.ologn.autotesting.testtemplates;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;

import org.junit.AfterClass;
import org.junit.Assume;
import org.junit.BeforeClass;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.sikuli.script.ImagePath;
import org.sikuli.script.Screen;

import io.ologn.autotesting.browsers.Browser;
import io.ologn.autotesting.browsers.HtmlUnitBrowser;
import io.ologn.autotesting.utils.SeleniumUtils;
import io.ologn.autotesting.utils.os.MacUtils;
import io.ologn.common.os.OsName;

/**
 * Everything in this class is likely to be used in every test. 
 * All tests should extend this class.
 * @author sli
 */
public abstract class TestTemplate {

	/*
	 * browser and driver will be changed according to the 
	 * current browser. 
	 * screen and robot will never be changed.
	 */
	protected static Browser browser;
	protected static WebDriver driver;
	protected static final Screen screen = new Screen();
	protected static final Robot robot = getRobot();
	
	private static final int ROBOT_INIT_MAX_RETRY_ATTEMPTS = 3;
	
	static {
		/*
		 * Set the default bundle path to user.dir. If the 
		 * bundle path is not set, Sikuli will actually use 
		 * use.dir by default, but with a warning message. 
		 * So this line is pretty much just for the purpose 
		 * of silencing a warning. 
		 * Before Sikuli 1.1.0 the correct way to do this was: 
		 * ImageLocator.setBundlePath(System.getProperty("user.dir")); 
		 * Sikuli 1.1.0 deprecated ImageLocator.
		 */
		ImagePath.setBundlePath(System.getProperty("user.dir"));
	}
	
	/**
	 * Initialize the browser and {@link WebDriver}.
	 * <br>
	 * By default, the WebDriver is set to an HtmlUnitDriver. 
	 * Feel free to replace it when you extend this class.
	 */
	@BeforeClass
	public static void beforeClass() {
		setBrowser(new HtmlUnitBrowser());
	}
	
	/**
	 * Quit the {@link WebDriver} and kill the WebDriver process
	 */
	@AfterClass
	public static void afterClass() {
		if (driver != null) {
			driver.quit();
		}
		// It has to be called after driver.quit()
		if (browser != null) {
			browser.killWebDriver();
		}
	}
	
	/**
	 * Set the browser for the current test. It also check 
	 * the supported OSes and set the corresponding 
	 * {@link WebDriver}. Therefore, It can be 
	 * used to initialize the browser, and it can be used 
	 * to switch browsers on the fly should you choose to.
	 * @param newBrowser
	 */
	protected static void setBrowser(Browser newBrowser) {
		// Quit the existing WebDriver
		if (driver != null) {
			driver.quit();
		}
		// Set the new browser
		browser = newBrowser;
		// Check the new browser's supported OSes
		checkOs();
		// Replace driver with the new browser's WebDriver
		driver = browser.getWebDriver();
		// Set the default implicit wait
		SeleniumUtils.setImplicitWait(SeleniumUtils.DEFAULT_WAIT);
		// Maximize the browser window
		browser.maximizeWindow(driver);
		MacUtils.highlightBrowserWindow();
	}
	
	/**
	 * Move the mouse cursor to a {@link WebElement} with 
	 * horizontal and vertical offsets.
	 * @param element
	 * @param xOffset
	 * @param yOffset additional Y offset after the browser 
	 * Y offset has been added.
	 */
	protected static void moveMouseTo(WebElement element,
			int xOffset, int yOffset) {
		// Get the location of the WebElement
		Point elementPos = element.getLocation();
		// Move the mouse to the WebElement
		robot.mouseMove(elementPos.x + browser.getXOffset() + xOffset,
				elementPos.y + browser.getYOffset() + yOffset);
	}
	
	/**
	 * Move the mouse cursor to the center of a {@link WebElement}.
	 * @param element
	 */
	protected static void moveMouseToCenterOf(WebElement element) {
		Dimension elementSize = element.getSize();
		moveMouseTo(element, elementSize.width / 2, elementSize.height / 2);
	}
	
	/**
	 * Move the mouse cursor to the center of the screen.
	 */
	protected static void moveMouseToScreenCenter() {
		// Get the size of the screen
		java.awt.Dimension screenSize = Toolkit.getDefaultToolkit()
				.getScreenSize();
		// Move the mouse to the center of the screen
		robot.mouseMove(screenSize.width / 2, screenSize.height / 2);
	}
	
	/**
	 * Left click the mouse.
	 */
	protected static void leftClick() {
		robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
	}
	
	/**
	 * Right click the mouse.
	 */
	protected static void rightClick() {
		robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
		robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
	}
	
	/**
	 * Double click the left mouse button.
	 */
	protected static void doubleClick() {
		multiClick(2);
	}
	
	/**
	 * Click the left mouse button multiple times.
	 * @param n The number of clicks
	 */
	protected static void multiClick(int n) {
		for (int i = 0; i < n; i++) {
			leftClick();
		}
	}
	
	/**
	 * Click on a {@link WebElement} with the mouse. Use this 
	 * method if {@link WebElement#click()} doesn't work.<br>
	 * The X and Y offsets are relative to the upper left-hand 
	 * corner of the WebElement.
	 * @param element The WebElement to be clicked
	 * @param xOffset The horizontal offset
	 * @param yOffset The vertical offset
	 */
	protected static void leftClick(WebElement element,
			int xOffset, int yOffset) {
		// Get the original mouse position
		java.awt.Point originalPos = MouseInfo.getPointerInfo().getLocation();
		moveMouseTo(element, xOffset, yOffset);
		leftClick();
		// Put the mouse back to its original position
		robot.mouseMove(originalPos.x, originalPos.y);
	}
	
	/**
	 * Overloaded version of {@link #leftClick(WebElement, int, int)} 
	 * that clicks on the center of the {@link WebElement}.
	 * @param element
	 */
	protected static void leftClick(WebElement element) {
		Dimension elementSize = element.getSize();
		leftClick(element, elementSize.width / 2, elementSize.height / 2);
	}

	/**
	 * Initialize the robot. This method exists because 
	 * the robot is final.
	 * @return
	 */
	private static Robot getRobot() {
		for (int i = 1; i <= ROBOT_INIT_MAX_RETRY_ATTEMPTS; i++) {
			try {
				return new Robot();
			} catch (AWTException e) {
				//e.printStackTrace();
				System.out.println("[error] Robot initialization "
						+ "failed. Retry attempt " + i + " of "
						+ ROBOT_INIT_MAX_RETRY_ATTEMPTS + ".");
			}
		}
		// All the retry attemps have failed.
		System.out.println("[error] Max number of retry attemps reached. "
				+ "Robot initialization failed.");
		return null;
	}
	
	/**
	 * Check supported operating systems of the current 
	 * browser. If the current OS is not supported, the 
	 * test will be skipped with an error message.
	 */
	private static void checkOs() {
		// Get the browser's supported OSes
		OsName[] osNames = browser.getSupportedOsNames();
		// Check if the current OS is one of the supported OSes
		boolean supported = OsName.currentIs(osNames);
		if (!supported) {
			// Generate an error message
			StringBuilder errMsg = new StringBuilder("Test skipped because "
					+ browser + " only supports: ");
			for (OsName osName : osNames) {
				errMsg.append(osName + ", ");
			}
			errMsg.delete(errMsg.length() - 2, errMsg.length());
			errMsg.append('.');
			// Use Assume instead of Assert to avoid failing the test.
			Assume.assumeTrue(errMsg.toString(), supported);
		}
	}
	
}
