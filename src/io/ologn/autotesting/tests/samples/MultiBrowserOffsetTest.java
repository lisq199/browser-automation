package io.ologn.autotesting.tests.samples;

import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;

import io.ologn.autotesting.testtemplates.simple.MultiBrowserSimpleTemplate;

/**
 * This class demonstrates a basic case of finding the 
 * location of a web element and moving the mouse cursor 
 * to the web element with Robot, which is a much more 
 * reliable library than Sikuli.
 * @author sli
 */
public class MultiBrowserOffsetTest extends MultiBrowserSimpleTemplate {

	@Override
	public void test() {
		driver.get("http://google.com");
		WebElement searchButton = driver.findElement(By.name("btnK"));
		/*
		 * Get the location of the WebElement.
		 * Note: This is org.openqa.selenium.Point instead of java.awt.Point
		 */
		Point searchButtonPos = searchButton.getLocation();
		/*
		 * Use Robot to move the mouse cursor to the upper 
		 * left-hand corner of the search button.
		 * 
		 * Note: robot.mouseMove(x, y) moves the cursor to (x, y),
		 * while screen.mouseMove(x, y) moves the cursor by (x, y). 
		 * To use screen to move the mouse cursor, you should use 
		 * screen.mouseMove(new Location(x, y)) or 
		 * screen.hover(new Location(x, y)).
		 * 
		 * Quirk: 
		 * You need to add the Y offset of the corresponding browser 
		 * to find the exact location. The offset of each browser 
		 * is different. The reason for this is that the origin of the 
		 * coordinate is the upper left-hand corner of the screen, 
		 * so the height of the browser UI need to be added. 
		 * Since the recent update, you don't have to worry about 
		 * which browser you are using. Just add browser.getYOffset(). 
		 * This is also likely to cause another potential problems, 
		 * because this means: 
		 * 1. The browser window needs to be maximized (which it 
		 * already is). 
		 * 2. If the UI of the browser changes, the offset needs to 
		 * be updated. 
		 * 3. The offset is going to be different for different 
		 * versions of a browser, particularly IE. 
		 * 4. The offset is going to be different on high-DPI displays.
		 * 
		 * X offset is added just to be future proof.
		 * 
		 * See also: RobotCalibratorTest.java
		 * 
		 * There is also a method to move mouse to a WebElement, 
		 * namely TestTemplate.moveMouseTo(...) and 
		 * TestTemplate.moveMouseToCenterOf(...).
		 */
		robot.mouseMove(searchButtonPos.x + browser.getXOffset(),
				searchButtonPos.y + browser.getYOffset());
		/*
		 *  Wait 2.5 seconds to make it easier to see where the 
		 *  mouse cursor is.
		 */
		screen.wait(2.5);
	}

}
