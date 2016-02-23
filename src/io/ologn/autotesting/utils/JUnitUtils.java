package io.ologn.autotesting.utils;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import io.ologn.common.os.OsName;

/**
 * Utilities for running JUnit tests
 * @author sli
 */
public class JUnitUtils {
	
	/**
	 * The file name of test log
	 */
	public static final String TEST_LOG_NAME = "test.log";
	
	/**
	 * Run all the jUnit test classes in the given array.
	 * @param testClasses
	 */
	public static void runTests(Class<?>... testClasses) {
		// Run the test classes using JUnit's built-in method
		JUnitCore junit = new JUnitCore();
		Result result = junit.run(testClasses);
		// Print the result
		printResult(result, System.out);
		// Ask if the user wants to save the test log
		System.out.print("\nRun finished. Save test log? (Y/N) ");
		Scanner stdin = new Scanner(System.in);
		char decision = stdin.nextLine().charAt(0);
		if (decision == 'y' || decision == 'Y') {
			try {
				File testLog = new File(TEST_LOG_NAME);
				saveResult(result, testLog, true);
				System.out.println("Test log saved at "
						+ testLog.getAbsolutePath() + ".");
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("[error] Result not saved successfully.");
			}
		}
		System.out.println("You can now close this window.");
		stdin.close();
		// Sometimes the program doesn't quit at this point
		System.exit(0);
	}
	
	/**
	 * Convert a {@link Result} to a String, 
	 * since there is no toString() method.
	 * @param result
	 * @return
	 */
	public static String resultToString(Result result) {
		StringBuilder r = new StringBuilder("[result]"
				+ "\nFailureCount: " + result.getFailureCount()
				+ "\nIgnoreCount: " + result.getIgnoreCount()
				+ "\nRunCount: " + result.getRunCount()
				+ "\nRunTime: " + result.getRunTime() + " ms"
				+ "\nSuccessful: " + result.wasSuccessful());
		if (result.getFailureCount() > 0) {
			r.append("\nFailures: ");
			List<Failure> failures = result.getFailures();
			for (int i = 0; i < failures.size(); i++) {
				r.append("\n    " + (i + 1) + ". "
						+ failures.get(i).toString());
			}
		}
		return r.toString();
	}
	
	/**
	 * Print a {@link Result} to a PrintStream.
	 * @param result
	 * @param ps
	 */
	public static void printResult(Result result, PrintStream ps) {
		ps.println(resultToString(result));
	}
	
	/**
	 * Save a {@link Result} to a file
	 * @param result
	 * @param resultFile
	 * @param append
	 * @throws IOException 
	 */
	public static void saveResult(Result result, File resultFile,
			boolean append) throws IOException {
		String resultString = TimestampUtils.getCurrentTimestamp()
				+ "\n" + resultToString(result);
		if (!OsName.currentIs(OsName.OS_X, OsName.UNIX)) {
			/*
			 * Replace \n with OS specific line separator if the 
			 * current OS is not OS X or Unix-like.
			 */
			resultString = resultString.replace("\n",
					System.getProperty("line.separator"));
		}
		// Save the result to the result file
		FileUtils.writeStringToFile(resultFile, resultString, append);
	}
	
}
