package cucumber.hooks;

import org.openqa.selenium.WebDriver;

import io.cucumber.java.After;
import seleniumUtils.DriverFactory;

public class WebDriverHook {
	private static WebDriver driver;

	public static WebDriver getDriver(String browser) {
		driver = DriverFactory.createDriver(browser);
		return driver;
	}

	@After
	public void afterScenario() {
		driver.quit();
	}
}
