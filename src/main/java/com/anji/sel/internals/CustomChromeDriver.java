package com.anji.sel.internals;

import static com.anji.sel.rest.utils.AppUtil.convertJsonToPojo;
import static java.util.Objects.nonNull;

//import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
//import java.util.Objects;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DaemonExecutor;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.Executor;
//import org.openqa.selenium.chrome.ChromeDriverService;

import com.anji.framework.api.impl.ApiResponseImpl;
import com.anji.sel.rest.create.CreateSessionResponse;
import com.anji.sel.rest.create.GenericHttpClient;
import com.anji.sel.rest.elem.ElementResponse;

public class CustomChromeDriver implements CustomWebDriver {

	private String url;

	private int port;

	private String sessionId;

	//private ChromeDriverService service;

	private CommandLine cmd;

	private Executor executor;

	private GenericHttpClient gp;

	public CustomChromeDriver(String path) {
		nonNull(path);
		this.port = getFreePort();
		if(port == 0)
			throw new IllegalArgumentException("Valid port is not available, try again");
		// very basic configuration
		//service = new ChromeDriverService.Builder().usingDriverExecutable(new File(path)).usingPort(port).build();

		try {
			// service.start();
			
			startDriver(path, "--port=" + String.valueOf(port));
			this.url = "http://localhost:" + String.valueOf(this.port);
			gp = new GenericHttpClient(this.url);
			// Not Sending any capabilities
			String body = "{\n" + "    \"capabilities\":{}\n" + "}";
			ApiResponseImpl<String> createSessionResponse = gp.postReqest(body, "/session");
			if (createSessionResponse.getResponseCode() == 200) {
				System.out.println("Session created");
				CreateSessionResponse createSesResObj = convertJsonToPojo(createSessionResponse.getResponseAsString(),
						CreateSessionResponse.class);
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
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public void quit() {
		// service.stop();
		try {
			ApiResponseImpl<String> createSessionResponse = gp.postReqest("{ }", "/shutdown");
			if (createSessionResponse.getResponseCode() == 200) {
				System.out.println("Destroyed successfully!!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void loadUrl(String urlString) {

		String body = "{\"url\": \"%s\"}";
		try {
			CustomWebDriver.isValidURL(urlString);
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
			ApiResponseImpl<String> resp = gp.postReqest(body, String.format("/session/%s/element", this.sessionId));
			if (resp.getResponseCode() == 200) {
				System.out.println("Element found successfully");
				System.out.println("Resp: " + resp.getResponseAsString());
				ElementResponse value = convertJsonToPojo(resp.getResponseAsString(), ElementResponse.class);
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

	private void startDriver(String driverPath, String... args) {
		cmd = new CommandLine(driverPath);
		cmd.addArguments(args, false);
		DefaultExecuteResultHandler handler = new DefaultExecuteResultHandler();
		executor = new DaemonExecutor();
		try {
			executor.execute(cmd, handler);
			Thread.sleep(1000);
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

}
