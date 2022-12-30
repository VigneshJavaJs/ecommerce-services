package com.kl.ecommerce.response;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class ExceptionHandlerResVO {
	private Boolean success;
	private Date timestamp;
	private Object message;
	private String details;

	public ExceptionHandlerResVO(Boolean success, Date timestamp, String message, String details) {
		super();
		this.success = success;
		this.timestamp = timestamp;
		this.message = message;
		this.details = details;
	}

	public ExceptionHandlerResVO(Boolean success, Date timestamp, List<String> message, String details) {
		super();
		this.success = success;
		this.timestamp = timestamp;
		this.message = message;
		this.details = details;
	}


}
