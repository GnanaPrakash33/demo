package cucumber.hooks;

import java.util.HashMap;
import java.util.Stack;

import io.cucumber.core.cli.Main;
import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Scenario;
import utils.Utility;

public class RetestHook {

	public static HashMap<String, Integer> retryLimit = new HashMap<>();
	public static Stack<String> retestScenariosStack = new Stack<>();
	public static final int MAX_RETRY_LIMIT = 3;

	@After
	public void retryIfScenarioFails(Scenario scenario) {
		if (scenario.isFailed()) {
			String scenarioTagName = scenario.getSourceTagNames().toArray(new String[0])[0];
			if (!retestScenariosStack.contains(scenarioTagName))
				retestScenariosStack.add(scenarioTagName);
		}
	}

	@AfterAll
	public static void run() {
		while (!retestScenariosStack.isEmpty()) {
			String failedScenarioTagName = retestScenariosStack.pop();

			if (retryLimit.containsKey(failedScenarioTagName)) {
				Integer retriesLeft = retryLimit.get(failedScenarioTagName);
				if (retriesLeft == 0) {
					Utility.enablePrintingInConsole();
					System.out.println("  Retest limit reached, Skipping scenario.");
					Utility.disablePrintingInConsole();
					return;
				} else {
					retryLimit.put(failedScenarioTagName, retriesLeft - 1);
					retestScenario(failedScenarioTagName);
				}
			} else {
				retryLimit.put(failedScenarioTagName, MAX_RETRY_LIMIT - 1);
				retestScenario(failedScenarioTagName);
			}
		}
	}

	public static void retestScenario(String failedScenarioTagName) {
		Utility.enablePrintingInConsole();
		System.out.println("  Scenario with tag " + failedScenarioTagName + " failed, Attempting retest #"
				+ (3 - retryLimit.get(failedScenarioTagName)));

		String[] argv = { "--glue", "cucumber", "--tags", failedScenarioTagName, // Tag to rerun
				"--plugin",
				"html:target/cucumber-retest-reports/cucumber-retest-report-"
						+ failedScenarioTagName.replaceAll("@", "") + "-attempt~"
						+ (RetestHook.MAX_RETRY_LIMIT - retryLimit.get(failedScenarioTagName)) + ".html",
				"--plugin",
				"json:target/cucumber-retest-reports/CucumberRetestReport-" + failedScenarioTagName.replaceAll("@", "")
						+ "-attempt~" + (RetestHook.MAX_RETRY_LIMIT - retryLimit.get(failedScenarioTagName)) + ".json",
				"src/test/resources/features" };

		Utility.disablePrintingInConsole();

		byte exitstatus = Main.run(argv, Thread.currentThread().getContextClassLoader());

		Utility.enablePrintingInConsole();
		if (exitstatus == 0)
			System.out.println("  Retest succeeded for tag " + failedScenarioTagName);
	}
}
