package io.ologn.autotesting.utils;

import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.Augmenter;

import io.ologn.autotesting.testtemplates.TestTemplate;

/**
 * Utils for taking screenshots.
 * @author sli
 */
public class TakeScreenshot extends TestTemplate {
	
	/**
	 * Take a screenshot of a rectangular area and save it to a 
	 * file in png format. If the file already exists, it will 
	 * be overwritten.
	 * @param file
	 * @param r the specified {@link Rectangle}
	 * @throws IOException
	 */
	public static void ofRectangularArea(File file, Rectangle r)
			throws IOException {
		BufferedImage image = robot.createScreenCapture(r);
		ImageIO.write(image, "png", file);
	}
	
	/**
	 * Overloaded version of {@link #ofRectangularArea(File, Rectangle)}.
	 * @param file
	 * @param x the x coordinate of the top left-hand corner of the rectangle
	 * @param y the y coordinate of the top left-hand corner of the rectangle
	 * @param width the width of the rectangle
	 * @param height the height of the rectangle
	 * @throws IOException
	 */
	public static void ofRectangularArea(File file,
			int x, int y, int width, int height) throws IOException {
		ofRectangularArea(file, new Rectangle(x, y, width, height));
	}
	
	/**
	 * Take a screenshot of the entire screen and save it to a 
	 * file in png format. If the file already exists, it will 
	 * be overwritten.
	 * @param file
	 * @throws IOException
	 */
	public static void ofEntireScreen(File file) throws IOException {
		ofRectangularArea(file, new Rectangle(
				Toolkit.getDefaultToolkit().getScreenSize()));
	}
	
	/**
	 * Take a screenshot of the entire webpage and save it to a 
	 * file in png format. If the file already exists, it will 
	 * be overwritten.
	 * @param file
	 * @throws IOException
	 * @throws UnsupportedOperationException
	 */
	public static void ofEntireWebpage(File file)
			throws IOException, UnsupportedOperationException {
		/*
		 * The sample code on Selenium's official website 
		 * (http://docs.seleniumhq.org/docs/04_webdriver_advanced.jsp#taking-a-screenshot) 
		 * is outdated. RemoteWebDriver now implements TakesScreenshot 
		 * (not to be confused with this class, TakeScreenshot).
		 */
		byte[] data = null;
		if (driver instanceof TakesScreenshot) {
			// If the driver already implements TakesScreenshot
			data = ((TakesScreenshot) driver)
					.getScreenshotAs(OutputType.BYTES);
		} else {
			// Else, it needs to be "augmented"
			WebDriver augmentedDriver = new Augmenter().augment(driver);
			try {
				data = ((TakesScreenshot) augmentedDriver)
						.getScreenshotAs(OutputType.BYTES);
			} catch (ClassCastException e) {
				throw new UnsupportedOperationException("The current "
						+ "WebDriver cannot be used to take screenshots "
						+ "of a web page.");
			}
		}
		FileUtils.writeByteArrayToFile(file, data, false);
	}
	
	/**
	 * Take a screenshot of the rectangular area between two 
	 * specified {@link java.awt.Point}s and save it to a 
	 * file in png format. If the file already exists, it will 
	 * be overwritten.<br>
	 * Note: The two points don't have to be in a specific order.
	 * @param file
	 * @param p1
	 * @param p2
	 * @throws IOException
	 */
	public static void betweenPoints(File file,
			java.awt.Point p1, java.awt.Point p2) throws IOException {
		// Create a Rectangle with the 2 specified points.
		Rectangle r = new Rectangle(p1);
		r.add(p2);
		ofRectangularArea(file, r);
	}
	
	/**
	 * Take a screenshot of the rectangular area between two 
	 * specified {@link java.awt.Point}s and save it to a 
	 * file in png format. If the file already exists, it will 
	 * be overwritten.<br>
	 * Note: The two points don't have to be in a specific order.
	 * @param file
	 * @param x1 x coordinate of the first point
	 * @param y1 y coordinate of the first point
	 * @param x2 x coordinate of the second point
	 * @param y2 y coordinate of the second point
	 * @throws IOException
	 */
	public static void betweenPoints(File file,
			int x1, int y1, int x2, int y2) throws IOException {
		betweenPoints(file, new java.awt.Point(x1, y1),
				new java.awt.Point(x2, y2));
	}
	
	/**
	 * Take a screenshot of the rectangular area between two 
	 * specified {@link org.openqa.selenium.Point}s and save it 
	 * to a file in png format. If the file already exists, it 
	 * will be overwritten.<br>
	 * Note: The two points don't have to be in a specific order.
	 * @param file
	 * @param p1
	 * @param p2
	 * @throws IOException
	 */
	public static void betweenPoints(File file, Point p1, Point p2)
			throws IOException {
		betweenPoints(file, p1.x, p1.y, p2.x, p2.y);
	}

	/**
	 * Take a screenshot of a {@link WebElement} and save it to a 
	 * file in png format. If the file already exists, it will 
	 * be overwritten.
	 * @param file
	 * @param element
	 * @throws IOException
	 */
	public static void ofWebElement(File file, WebElement element)
			throws IOException {
		// Get the location of the WebElement
		Point elementPos = element.getLocation();
		// Get the size of the WebElement
		Dimension elementSize = element.getSize();
		ofRectangularArea(file, elementPos.x + browser.getXOffset(),
				elementPos.y + browser.getYOffset(),
				elementSize.width, elementSize.height);
	}

	/**
	 * Take a screenshot of a {@link WebElement} and save it to a 
	 * file in png format. If the file already exists, it will 
	 * be overwritten.
	 * @param file
	 * @param by
	 * @throws IOException
	 */
	public static void ofWebElement(File file, By by) throws IOException {
		ofWebElement(file, driver.findElement(by));
	}
	
}
