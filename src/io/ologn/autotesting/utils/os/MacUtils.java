package io.ologn.autotesting.utils.os;

import java.awt.MouseInfo;

import io.ologn.autotesting.testtemplates.TestTemplate;
import io.ologn.common.os.OsName;

/**
 * Utils for OS X.
 * @author sli
 */
public class MacUtils extends TestTemplate {
	
	/**
	 * On Mac, Chrome and Safari window will not be the active 
	 * window for some reason. As a workaround, I'm letting every 
	 * test on Mac click on the center of the screen before 
	 * doing anything to highlight the window.<br>
	 * Note: If you are running tests from Eclipse, put it to 
	 * fullscreen mode (NOT maximized).
	 */
	public static void highlightBrowserWindow() {
		if (!OsName.OS_X.isCurrent()) {
			return;
		}
		screen.wait(0.5);
		// Get the original mouse position
		java.awt.Point originalPos = MouseInfo.getPointerInfo().getLocation();
		// Click on the screen center
		moveMouseToScreenCenter();
		leftClick();
		// Put the mouse back
		robot.mouseMove(originalPos.x, originalPos.y);
	}
	
}
