package cucumber.hooks;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import io.cucumber.java.AfterStep;
import io.cucumber.java.BeforeStep;
import io.cucumber.java.Scenario;

public class ScreenshotHook {
	public static Path mostRecentScreenshotTakenPath;

	@BeforeStep
	public void beforeStep() {
		mostRecentScreenshotTakenPath = null;
	}

	@AfterStep
	public void afterStep(Scenario scenario) {
		if (mostRecentScreenshotTakenPath == null)
			return;

		try {
			byte[] fileBytes = Files.readAllBytes(mostRecentScreenshotTakenPath);
			scenario.attach(fileBytes, "image/png", mostRecentScreenshotTakenPath.getFileName().toString());
		} catch (IOException ignored) {
		}
	}
}
