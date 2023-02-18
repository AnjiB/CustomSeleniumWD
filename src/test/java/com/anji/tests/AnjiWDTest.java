package com.anji.tests;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import static com.anji.sel.rest.utils.AppUtil.waitFor;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.anji.sel.internals.CustomBy;
import com.anji.sel.internals.CustomChromeDriver;
import com.anji.sel.internals.CustomWebDriver;
import com.anji.sel.internals.CustomWebElement;

public class AnjiWDTest {

	private static CustomWebDriver driver;

	@BeforeAll
	public static void setUp() {
		Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
		String driverPath = path.toString() + "/src/main/resources/drivers/chromedriver";
		driver = new CustomChromeDriver(driverPath);
		driver.loadUrl("http://www.google.com");
	}

	/*
	 * This test is actually not testing anything.
	 */
	@Test
	public void testMyWDImpl() {
		waitFor(5000);
		CustomWebElement acceptAll = driver.findElement(CustomBy.XPATH, "//div[text()='Accept all']");
		if (acceptAll != null)
			acceptAll.click();
		CustomWebElement searchBox = driver.findElement(CustomBy.CSS, "input[title='Search']");
		searchBox.sendText("Whats the matter with ChatGPT");
		// Did not implement a method to move the focus out, so click on Google logo
		// to shift the focus from google search box.
		CustomWebElement image = driver.findElement(CustomBy.XPATH, "//img[@alt='Google']");
		image.click();
		CustomWebElement searchButton = driver.findElement(CustomBy.XPATH, "(//input[@value='Google Search'])[2]");
		searchButton.click();
		waitFor(5000);
	}

	@AfterAll
	public static void cleanUp() {
		driver.quit();
	}
}
