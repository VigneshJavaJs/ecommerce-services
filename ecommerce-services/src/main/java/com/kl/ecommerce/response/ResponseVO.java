package com.kl.ecommerce.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseVO {

	private boolean success;

	private Object data;

	private long executionTime;

	public void setExecutionTime() {
		this.executionTime = System.currentTimeMillis() - getExecutionTime();
	}

}