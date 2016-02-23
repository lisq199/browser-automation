package io.ologn.autotesting.tests.samples;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.By;

import io.ologn.autotesting.testtemplates.simple.FirefoxSimpleTemplate;
import io.ologn.autotesting.utils.TakeScreenshot;

/**
 * A sample test demonstrating how screenshots are taken.<br>
 * Firefox is chosen for this test because it's cross-platform. 
 * Other browsers should also work with the exact same code.
 * @author sli
 */
public class ScreenshotSampleTest extends FirefoxSimpleTemplate {
	
	public static final String DESKTOP_DIR =
			System.getProperty("user.home") + File.separator
			+ "Desktop" + File.separator;
	
	@Override
	public void test() {
		driver.get("http://google.com");
		// Take the 1st screenshot
		File searchButtonScreenshot = new File(DESKTOP_DIR
				+ "1_search_button.png");
		try {
			TakeScreenshot.ofWebElement(searchButtonScreenshot,
					By.name("btnK"));
			System.out.println("1st screenshot taken.");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("1st screenshot not taken.");
		}
		// Take the 2nd screenshot
		File logo = new File(DESKTOP_DIR + "2_logo.png");
		try {
			TakeScreenshot.ofWebElement(logo, By.id("hplogo"));
			System.out.println("2nd screenshot taken.");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("2nd screenshot not taken.");
		}
		// Take the 3rd screenshot
		File entireWebpage = new File(DESKTOP_DIR + "3_entire_webpage.png");
		try {
			TakeScreenshot.ofEntireWebpage(entireWebpage);
			System.out.println("3rd screenshot taken.");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("3rd screenshot not taken.");
		}
		// Take the 4th screenshot
		File entireScreen = new File(DESKTOP_DIR + "4_entire_screen.png");
		try {
			TakeScreenshot.ofEntireScreen(entireScreen);
			System.out.println("4th screenshot taken.");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("4th screenshot not taken.");
		}
		System.out.println("Screenshots saved. They should be on your "
				+ "desktop.");
	}
	
}
