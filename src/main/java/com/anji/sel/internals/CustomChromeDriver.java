package com.anji.sel.internals;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;

import static java.util.Objects.nonNull;
import org.openqa.selenium.chrome.ChromeDriverService;

import com.anji.framework.api.impl.ApiResponseImpl;
import com.anji.sel.rest.create.CreateSession;
import com.anji.sel.rest.create.CreateSessionResponse;
import com.anji.sel.rest.create.GenericHttpClient;
import com.anji.sel.rest.elem.ElementResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomChromeDriver implements CustomWebDriver {

	private String url;

	private int port;

	private String sessionId;

	private ChromeDriverService service;

	public CustomChromeDriver(String path) {
		nonNull(path);
		this.port = getFreePort();
		// very basic configuration
		service = new ChromeDriverService.Builder().usingDriverExecutable(new File(path)).usingPort(port).build();

		try {

			service.start();

			this.url = "http://localhost:" + String.valueOf(this.port);

			CreateSession createSession = new CreateSession(this.url);

			// I am sending no extra capabilities
			String body = "{\n" + "    \"capabilities\":{}\n" + "}";

			ApiResponseImpl<CreateSessionResponse> createSessionResponse = createSession.createSession(body);

			if (createSessionResponse.getResponseCode() == 200) {

				System.out.println("Session created");

				CreateSessionResponse createSesResObj = createSessionResponse.getResponse();

				this.sessionId = createSesResObj.getValue().getSessionId();

				System.setProperty("com.anji.wb.base.url", url);

			} else {
				throw new Exception(
						"Sessions not created, check for logs:\n " + createSessionResponse.getResponseAsString());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static int getFreePort() {
		try (ServerSocket socket = new ServerSocket(0)) {
			socket.setReuseAddress(true);
			return socket.getLocalPort();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public void quit() {
		service.stop();
	}

	@Override
	public void loadUrl(String urlString) {

		String body = "{\"url\": \"%s\"}";

		try {
			CustomWebDriver.isValidURL(urlString);
			GenericHttpClient gp = new GenericHttpClient(this.url);
			ApiResponseImpl<String> resp = gp.postReqest(String.format(body, urlString),
					String.format("/session/%s/url", this.sessionId));
			if (resp.getResponseCode() == 200) {
				System.out.println("URL launched successfully");
			} else {
				throw new Exception("URL is not launced, check for logs\n" + resp.getResponseAsString());
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	@Override
	public CustomWebElement findElement(CustomBy by, String locator) {

		String body = String.format("{\"using\": \"%s\", \"value\": \"%s\"}", by.getStrategy(), locator);

		String elementId = null;

		try {
			GenericHttpClient gp = new GenericHttpClient(this.url);
			ApiResponseImpl<String> resp = gp.postReqest(body, String.format("/session/%s/element", this.sessionId));
			if (resp.getResponseCode() == 200) {
				System.out.println("Element found successfully");

				System.out.println("Resp: " + resp.getResponseAsString());

				ElementResponse value = new ObjectMapper().readValue(resp.getResponseAsString(), ElementResponse.class);

				for (String key : value.getValue().getElementId().keySet()) {
					elementId = (String) value.getValue().getElementId().get(key);
				}
				return new ChromeElement(this.sessionId, elementId);

			} else {
				throw new Exception("Element is not found or no such element\n" + resp.getResponseAsString());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

}
