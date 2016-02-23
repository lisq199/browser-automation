package io.ologn.autotesting.utils;

import java.io.File;
import java.security.InvalidParameterException;

/**
 * Utils for directories
 * @author sli
 */
public class DirUtils {
	
	/**
	 * An easier way of accessing file separator.
	 */
	public static final char s = File.separatorChar;
	
	/**
	 * Get the standard resource directory for a class in 
	 * this project.<br>
	 * For this project, all the resources should be under the 
	 * res folder, and the name of the subfolders should match 
	 * the package name and class name. For example, if a class 
	 * is called com.example.newproject.tests.testrail.Test1, 
	 * then its resources should be stored in 
	 * res/tests/testrail/Test1.
	 * @param c
	 * @return The resource directory of the class
	 */
	public static String getResDir(Class<?> c) {
		if (c == null) return null;
		String dir = c.getCanonicalName();
		// Get the index of the third dot
		int thirdDotIndex = 0;
		for (int i = 0; i < 3; i++) {
			thirdDotIndex = dir.indexOf('.', thirdDotIndex + 1);
			if (thirdDotIndex < 0) {
				// If there are less than 3 dots in the class name
				throw new InvalidParameterException(
						c + " invalid for getting the res folder.");
			}
		}
		return "res" + s + dir.substring(thirdDotIndex + 1)
				.replace('.', s) + s;
	}

}
