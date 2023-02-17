package com.anji.sel.internals;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public interface CustomWebDriver {
	
	public void quit();

	public void loadUrl(String string);
	
	public CustomWebElement findElement(CustomBy by, String locator);
	
	static boolean isValidURL(String url) throws MalformedURLException, URISyntaxException {
	    try {
	        new URL(url).toURI();
	        return true;
	    } catch (MalformedURLException e) {
	        return false;
	    } catch (URISyntaxException e) {
	        return false;
	    }
	}
}
