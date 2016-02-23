package io.ologn.autotesting.utils;

import java.nio.file.Paths;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;

import io.ologn.autotesting.browsers.BrowserName;
import io.ologn.autotesting.testtemplates.TestTemplate;
import io.ologn.common.os.OsName;

/**
 * Calculates the offsets of browser UI.<br>
 * Inspired by: <a href="http://stackoverflow.com/questions/14655581/selenium-webdriver-and-html-window-location-by-using-java">
 * link</a><br>
 * The solution in the link doesn't seem to work, but 
 * it's where I got the idea from.
 * @see {@link io.ologn.autotesting.tests.samples.RobotCalibratorTest}
 * @author sli
 */
public class RobotCalibrator extends TestTemplate {
	
	public static final String RES_DIR =
			DirUtils.getResDir(RobotCalibrator.class);
	/**
	 * The URL to the local html.
	 */
	public static final String LOCAL_URL = Paths.get(
			RES_DIR + "RobotCalibrator.html").toUri().toString();
	/**
	 * Safari is having trouble opening local files, so I uploaded 
	 * the same file to my UVic web space.
	 */
	public static final String REMOTE_URL =
			"http://web.uvic.ca/~lisiqi/latitude/RobotCalibrator.html";
	
	/**
	 * Get the horizontal and vertical offsets of the 
	 * browser UI stored in a org.openqa.selenium.Point.
	 * @param driver
	 * @return
	 */
	public static Point calibrate() {
		// Safari can't open local files
		driver.get(browser.is(BrowserName.SAFARI) ? REMOTE_URL : LOCAL_URL);
		// Get the location of the top left-hand corner and 
		// the bottom right-hand corner
		Point bottomRight = driver.findElement(
				By.className("bottom-right")).getLocation();
		Point topLeft = driver.findElement(
				By.className("top-left")).getLocation();
		// Get the window position
		Point windowPos = driver.manage().window().getPosition();
		// Get the size of the browser window
		Dimension windowSize = driver.manage().window().getSize();
		// Calculate the actual position of the top left-hand 
		// corner of the web page
		int x = windowPos.x + topLeft.x;
		int y = windowPos.y + windowSize.height - bottomRight.y;
		switch (OsName.getCurrent()) {
		case WINDOWS:
			// For some reason the measurement is a bit off on Windows
			x += 8;
			y -= 9;
			break;
		case OS_X:
			// On Mac the measurement is accurate by itself.
			break;
		case UNIX:
			// TODO Add Unix/Linux support
			break;
		default:
			break;
		}
		return new Point(x, y);
	}
	
	/**
	 * Note: this method assumes maximized browser window.<br>
	 * Get only the vertical offset of the browser UI. When 
	 * the window is maximized, the horizontal offset is 
	 * usually 0.<br>
	 * Also, this doesn't apply to Mac, because Mac doesn't 
	 * really support "maximizing" the window. It can only 
	 * resize the window to fit the screen at best, meaning 
	 * the menu \bar is still on the screen. For the same 
	 * reason, it can be assumed that it also doesn't apply 
	 * to some Linux distributions.
	 * @return
	 */
	public static int calibrateForMaximizedWindow() {
		driver.get(browser.is(BrowserName.SAFARI) ? REMOTE_URL : LOCAL_URL);
		Point bottomRight = driver.findElement(
				By.className("bottom-right")).getLocation();
		// Get the size of the browser window
		Dimension windowSize = driver.manage().window().getSize();
		return windowSize.height - bottomRight.y - 17;
	}
	
}