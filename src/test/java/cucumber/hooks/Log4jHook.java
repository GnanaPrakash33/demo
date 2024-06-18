package cucumber.hooks;

import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.Scenario;
import utils.LogUtil;

public class Log4jHook {
	
	public static String currentlyExecutingStepName;
	
	@BeforeAll
	public static void setUp()
	{
		LogUtil.initializeLogger();
	}
	
	@Before
	public static void logScenario(Scenario scenario)
	{
		LogUtil.info("Executing Scenario: " + scenario.getName());
	}
	
	@AfterStep
	public static void logScenarioStep(Scenario scenario)
	{
		if(scenario.isFailed())
			LogUtil.error("Error at step: " + currentlyExecutingStepName + ", " + scenario.getStatus());
		else
			LogUtil.info("Step Success: " + currentlyExecutingStepName + ", " + scenario.getStatus());
	}
}
