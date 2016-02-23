package io.ologn.autotesting.utils.os;

import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.ologn.common.os.OlognOs;
import io.ologn.common.os.OsName;

/**
 * Utils for Windows
 * @author sli
 */
public class WindowsUtils {
	
	/**
	 * Use taskkill to kill a task by name
	 * @param name The name of the process
	 */
	public static void taskkill(String name) {
		if (!OsName.WINDOWS.isCurrent()) {
			return;
		}
		try {
			OlognOs.executeCommands(null, "taskkill /F /IM " + name);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("[error] " + name
					+ " not killed successfully.");
		}
	}
	
	/**
	 * Get the name of the default browser.<br>
	 * Credit: <a href="http://stackoverflow.com/questions/15852885/method-returning-default-browser-as-a-string#answers-header">
	 * link</a>
	 * @return
	 */
	public static String getDefaultBrowser() {
		if (!OsName.WINDOWS.isCurrent()) {
			return null;
		}
        // Get registry where we find the default browser
        Process process = null;
		try {
			process = Runtime.getRuntime().exec(
					"REG QUERY HKEY_CLASSES_ROOT\\"
					+ "http\\shell\\open\\command");
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
        Scanner kb = new Scanner(process.getInputStream());
        while (kb.hasNextLine()) {
            /*
             * Get output from the terminal, and replace all '\' 
             * with '/' (makes regex a bit more manageable)
             */
            String registry = (kb.nextLine()).replaceAll("\\\\", "/")
            		.trim();
            // Extract the default browser
            Matcher matcher = Pattern.compile("/(?=[^/]*$)(.+?)[.]")
            		.matcher(registry);
            if (matcher.find()) {
                // Scanner is no longer needed if match is found, so close it
                kb.close();
                String defaultBrowser = matcher.group(1);

                // Capitalize first letter and return String
                defaultBrowser = defaultBrowser.substring(0, 1)
                		.toUpperCase()
                		+ defaultBrowser.substring(
                				1, defaultBrowser.length());
                return defaultBrowser;
            }
        }
        // Match wasn't found, still need to close Scanner
        kb.close();
        return null;
	}

}
