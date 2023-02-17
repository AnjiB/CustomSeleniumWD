package com.anji.sel.rest.create;

import com.anji.framework.api.builder.RequestBuilder;
import com.anji.framework.api.impl.ApiResponseImpl;
import com.anji.framework.api.impl.restassured.RestAssuredApiServiceImpl;

public class CreateSession extends RestAssuredApiServiceImpl {

	public CreateSession(String basePath) {
		super(basePath);
	}

	
	public ApiResponseImpl<CreateSessionResponse> createSession(String body) throws Exception {
		
		RequestBuilder builder = RequestBuilder.builder().pathUrl("/session")
				.requestObject(body).build();
		
		return new ApiResponseImpl<CreateSessionResponse>(post(builder), CreateSessionResponse.class);
	
	}
	
}
