package io.ologn.autotesting.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Utils for timestamps
 * @author sli
 */
public class TimestampUtils {
	
	/**
	 * Get the current timestamp in the default format.
	 * @return
	 */
	public static String getCurrentTimestamp() {
		return getCurrentTimestamp(new SimpleDateFormat(
				"MM/dd/yyyy HH:mm:ss"));
	}
	
	/**
	 * Get the current timestamp by a specified {@link DateFormat}.
	 * @param dateFormat
	 * @return
	 */
	public static String getCurrentTimestamp(DateFormat dateFormat) {
		return dateFormat.format(new Date());
	}
	
}
