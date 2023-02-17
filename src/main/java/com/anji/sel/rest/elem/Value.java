package com.anji.sel.rest.elem;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;

public class Value {

	private Map<String, Object> elementId = new HashMap<>();

	@JsonAnySetter
	public void setElementId(String key, Object value) {
		elementId.put(key, value);
	}

	@JsonAnyGetter
	public Map<String, Object> getElementId() {
		return elementId;
	}

}
