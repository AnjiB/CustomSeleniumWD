package com.anji.sel.rest.create;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Capabilities {

	@JsonProperty("acceptInsecureCerts")
	private boolean acceptInsecureCerts;
	
	@JsonProperty("browserName")
	private String browserName;
	
	@JsonProperty("browserVersion")
	private String browserVersion;
	
	@JsonProperty("chrome")
	private Object chrome;
	
	@JsonProperty("goog:chromeOptions")
	private Object googleChromeOptions;
	
	@JsonProperty("networkConnectionEnabled")
	private boolean networkConnectionEnabled;
	
	@JsonProperty("pageLoadStrategy")
	private String pageLoadStrategy;
	
	@JsonProperty("platformName")
	private String platformName;
	
	@JsonProperty("proxy")
	private Object proxy;

	@JsonProperty("setWindowRect")
	private boolean setWindowRect;
	
	@JsonProperty("strictFileInteractability")
	private boolean strictFileInteractability;
	
	@JsonProperty("timeouts")
	private Object timeouts;
	
	@JsonProperty("unhandledPromptBehavior")
	private String unhandledPromptBehavior;
	
	@JsonProperty("webauthn:extension:credBlob")
	private boolean webauthnExtensionCredBlob;
	
	@JsonProperty("webauthn:extension:largeBlob")
	private boolean webauthnExtensionLargeBlob;
	
	@JsonProperty("webauthn:virtualAuthenticators")
	private boolean webauthnVirtualAuthenticators;
}
