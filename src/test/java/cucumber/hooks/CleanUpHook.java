package cucumber.hooks;

import java.io.File;

import io.cucumber.java.BeforeAll;
import utils.PropertiesReader;

public class CleanUpHook {
	@BeforeAll
	public static void cleanUp() {

		File screenshotsDirectory = new File(PropertiesReader.readProperty("screenshots.path"));
		if (screenshotsDirectory.isDirectory()) {
			for (File file : screenshotsDirectory.listFiles()) {
				if (file.isFile()) {
					file.delete();
				}
			}
		}
		
		deleteDirectoryContents(PropertiesReader.readProperty("screenshots.path"));
		deleteDirectoryContents(PropertiesReader.readProperty("cucumber.reports.path"));
		deleteDirectoryContents(PropertiesReader.readProperty("cucumber.retest-reports.path"));
		deleteDirectoryContents(PropertiesReader.readProperty("cucumber.retest-reports.path"));
		deleteDirectoryContents("logs");
	}
	
	public static void deleteDirectoryContents(String path)
	{
		File directory = new File(path);
		if (directory.isDirectory()) {
			for (File file : directory.listFiles()) {
				if (file.isFile()) {
					file.delete();
				}
			}
		}
	}
}
