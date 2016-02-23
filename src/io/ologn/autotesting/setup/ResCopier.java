package io.ologn.autotesting.setup;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;

/**
 * Run this class for to copy the res folder from 
 * com.geocortex.autotesting to your new project.<br>
 * Before running setup, making a backup is recommended.<br>
 * Note:<br>
 * <ul>
 * <li>This class is only meant to be run in Eclipse!</li>
 * <li>Right now it only copies the res folder over to the 
 * new project</li>
 * <li>This class was created in an attempt to make the setup 
 * automatic. The setup is still not automatic, but this class 
 * still works.</li>
 * </ul>
 * @author sli
 */
public class ResCopier {
	
	/**
	 * An array containing all the names of directories that 
	 * are to be copied to the new project. If other 
	 * directories are also supposed to be copied in the 
	 * future, they should be added to this array. 
	 */
	public static final String[] TO_BE_COPIED = {"res"};
	/**
	 * An easier way of accessing file separator.
	 */
	public static final char s = File.separatorChar;
	
	private static void copyRes(Scanner stdin) {
		System.out.println("Note: This class is only meant to be run "
				+ "in Eclipse! Making a backup before running the setup "
				+ "is recommended!");
		// Get the new project name from user input
		System.out.print("Please enter the name of your new project: ");
		String newProjectName = stdin.nextLine().trim();
		File newProjectDir = new File(".." + s + newProjectName);
		// Check if the new project is valid
		if (!newProjectDir.exists()) {
			// If the new project doesn't exist
			System.out.println(newProjectName + " doesn't exist.");
			return;
		} else if (!newProjectDir.isDirectory()) {
			// If the new project is not a directory
			System.out.println(newProjectName + " is not a directory.");
			return;
		}
		// Check if any of the files in TO_BE_COPIED already exist
		for (String dirName : TO_BE_COPIED) {
			// Check if dirName exists in the old proejct
			if (!new File(dirName).exists()) {
				System.out.println(dirName + " in the old project "
						+ "doesn't exist. It won't be copied.");
				continue;
			}
			// The "hypothetical" directory (it can also be a file)
			File newDir = new File(".." + s + newProjectName + s + dirName);
			if (newDir.exists()) {	// If the directory/file already exists
				if (newDir.isDirectory()) {
					// If it's a directory, then it can be merged
					System.out.print("A directory named " + dirName
							+ " already exists in " + newProjectName
							+ ". Do you want to continue and merge "
							+ "the two directories? (Y/N) ");
					char decision = stdin.nextLine().trim().charAt(0);
					if (!(decision == 'y' || decision == 'Y')) {
						System.out.println("Copying aborted.");
						return;
					}
				} else if (newDir.isFile()) {
					// If it's a file, the file has to be deleted to continue
					System.out.println("A file named " + dirName
							+ " already exists in " + newProjectName
							+ ". Do you want to delete that file "
							+ "in order to continue? (Y/N) ");
					char decision = stdin.nextLine().trim().charAt(0);
					if (decision == 'y' || decision == 'Y') {
						newDir.delete();
						System.out.println(dirName + " deleted.");
					} else {
						System.out.println("Copying aborted.");
						return;
					}
				}
			} else {
				System.out.println(newDir.toString()
						+ " doesn't exist. It will be created.");
			}
		}
		// Copy each file in TO_BE_COPIED
		System.out.println("Copying started...");
		for (int i = 0; i < TO_BE_COPIED.length; i++) {
			String dirName = TO_BE_COPIED[i];
			File oldDir = new File(dirName);
			File newDir = new File(".." + s + newProjectName + s + dirName);
			try {
				FileUtils.copyDirectory(oldDir, newDir);
				System.out.println("(" + (i + 1) + " of " 
						+ TO_BE_COPIED.length + ") " + dirName + " copied.");
			} catch (IOException e) {
				//e.printStackTrace();
				System.out.println("(" + (i + 1) + " of " 
						+ TO_BE_COPIED.length + ") " + "Copying " 
						+ oldDir.getAbsolutePath() + " to " 
						+ newDir.getAbsolutePath() + " failed.");
				continue;
			}
		}
		System.out.println("Copying finished.");
	}
	
	public static void main(String[] args) {
		Scanner stdin = new Scanner(System.in);
		copyRes(stdin);
		stdin.close();
	}
	
}
