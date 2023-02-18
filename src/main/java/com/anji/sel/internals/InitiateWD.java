package com.anji.sel.internals;

import static com.anji.sel.rest.utils.AppUtil.waitFor;

public class InitiateWD {

	public static void main(String[] args) {

		CustomWebDriver driver = null;
		try {
			driver = new CustomChromeDriver("/Users/anjiboddupally/Downloads/my_drivers/chr/chromedriver");
			driver.loadUrl("http://www.google.com");
			waitFor(5000);
			CustomWebElement acceptAll = driver.findElement(CustomBy.XPATH, "//div[text()='Accept all']");
			if (acceptAll != null)
				acceptAll.click();
			CustomWebElement searchBox = driver.findElement(CustomBy.CSS, "input[title='Search']");
			searchBox.sendText("Whats the matter with ChatGPT");
			// did not implement a method to move the focus out, so click on Google logo
			// to shift the focus from google search box.
			CustomWebElement image = driver.findElement(CustomBy.XPATH, "//img[@alt='Google']");
			image.click();

			CustomWebElement searchButton = driver.findElement(CustomBy.XPATH, "(//input[@value='Google Search'])[2]");
			searchButton.click();
			waitFor(5000);
		} finally {
			driver.quit();
		}
	}
}
