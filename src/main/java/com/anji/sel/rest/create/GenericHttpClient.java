package com.anji.sel.rest.create;

import com.anji.framework.api.builder.RequestBuilder;
import com.anji.framework.api.impl.ApiResponseImpl;
import com.anji.framework.api.impl.restassured.RestAssuredApiServiceImpl;

public class GenericHttpClient extends RestAssuredApiServiceImpl {

	public GenericHttpClient(String basePath) {
		super(basePath);
	}
	
	public ApiResponseImpl<String> postReqest(Object body, String baseUrl) throws Exception {
		
		RequestBuilder builder = RequestBuilder.builder().pathUrl(baseUrl)
				.requestObject(body).build();
		
		return new ApiResponseImpl<String>(post(builder), String.class);
	}

}
