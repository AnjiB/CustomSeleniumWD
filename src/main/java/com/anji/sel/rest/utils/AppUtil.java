package com.anji.sel.rest.utils;

import java.io.IOException;
import java.util.Objects;

import com.fasterxml.jackson.databind.ObjectMapper;

public class AppUtil {
	
	private AppUtil() {};

	public static <T> T convertJsonToPojo(String json, Class<T> klass) {
		Objects.nonNull(klass);
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.readValue(json, klass);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;
	}
	
	

	public static void waitFor(long time) {

		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
