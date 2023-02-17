package com.anji.sel.internals;

public enum CustomBy {

	XPATH("xpath"), CSS("css selector");
	
	private String strategy;
	
	private CustomBy(String strategy) {
		this.strategy = strategy;
	}
	
	public String getStrategy() {
		return this.strategy;
	}
}
