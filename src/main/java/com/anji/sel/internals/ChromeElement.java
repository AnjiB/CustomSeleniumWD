package com.anji.sel.internals;

import com.anji.framework.api.impl.ApiResponseImpl;
import com.anji.sel.rest.create.GenericHttpClient;

public class ChromeElement implements CustomWebElement {

	private GenericHttpClient gp;

	private String partUri;

	public ChromeElement(String sessionId, String elementId) {
		this.gp = new GenericHttpClient(System.getProperty("com.anji.wb.base.url"));
		this.partUri = String.format("/session/%s/element/%s", sessionId, elementId);

	}

	@Override
	public void click() {

		String body = "{ }";
		try {
			ApiResponseImpl<String> resp = gp.postReqest(body, partUri + "/click");
			if (resp.getResponseCode() == 200) {
				System.out.println("command executed successfully");
			} else {
				throw new Exception("command is failed to execute, check for logs:\n" + resp.getResponseAsString());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void sendText(String text) {

		String body = String.format("{\"text\": \"%s\"}", text);
		try {
			ApiResponseImpl<String> resp = gp.postReqest(body, partUri + "/value");
			if (resp.getResponseCode() == 200) {
				System.out.println("command executed successfully");
			} else {
				throw new Exception("command is failed to execute, check for logs:\n" + resp.getResponseAsString());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
