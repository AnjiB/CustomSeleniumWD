package com.anji.sel.rest.create;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Value {

	@JsonProperty("capabilities")
	private Capabilities capabilities;
	
	@JsonProperty("sessionId")
    private String sessionId;
}


