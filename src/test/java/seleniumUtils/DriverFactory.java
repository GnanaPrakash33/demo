package seleniumUtils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.CapabilityType;

public class DriverFactory {

	// Returns driver based on specified browser.
	public static WebDriver createDriver(String browser) {
		switch (browser.toLowerCase()) {
		case "edge":
			EdgeOptions edgeOptions = new EdgeOptions();
			edgeOptions.setCapability(CapabilityType.PAGE_LOAD_STRATEGY, "none");
			return new EdgeDriver(edgeOptions);

		case "chrome":
			ChromeOptions chromeOptions = new ChromeOptions();
			chromeOptions.setCapability(CapabilityType.PAGE_LOAD_STRATEGY, "none");
			return new ChromeDriver(chromeOptions);

		default:
			return null;
		}
	}
}