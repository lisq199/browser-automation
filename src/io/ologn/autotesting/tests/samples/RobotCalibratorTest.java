package io.ologn.autotesting.tests.samples;

import org.junit.AfterClass;
import org.openqa.selenium.Point;

import io.ologn.autotesting.browsers.BrowserName;
import io.ologn.autotesting.browsers.IeBrowser;
import io.ologn.autotesting.testtemplates.simple.MultiBrowserSimpleTemplate;
import io.ologn.autotesting.utils.RobotCalibrator;

/**
 * Run this test to see what the UI offset of each browser is, 
 * and update the Y offset of each browser accordingly.
 * @see {@link RobotCalibrator}
 * @author sli
 */
public class RobotCalibratorTest extends MultiBrowserSimpleTemplate {
	
	@Override
	public void test() {
		if (browser.is(BrowserName.IE)) {
			/*
			 * With IE, you have to click on Allow blocked content 
			 * before continuing.
			 */
			IeBrowser.allowBlockedContent();
		}
		// Distinguish between 3 browsers
		System.out.println("********** " + browser + " **********");
		System.out.println("Assuming maximized window "
				+ "(it doesn't apply to Mac and some Linux distributions):");
		/*
		 * This result is only accurate if the browser window 
		 * is maximized.
		 */
		int yOffset = RobotCalibrator.calibrateForMaximizedWindow();
		System.out.println("Y offset: " + yOffset);
		System.out.println("Assuming any window position and size:");
		// This result is always accurate.
		Point p = RobotCalibrator.calibrate();
		System.out.println("X offset: " + p.x + ", Y offset: " + p.y);
		robot.mouseMove(p.x, p.y);
		/*
		 * The mouse should be at the upper left-hand corner of 
		 * the web page. 
		 * This wait is for the purpose of making it easier to 
		 * see where the mouse is.
		 */
		screen.wait(1.5);
	}
	
	@AfterClass
	public static void afterClass() {
		System.out.println("Done!");
	}

}
