package com.kl.ecommerce.controller;

import org.springframework.http.ResponseEntity;

import com.kl.ecommerce.response.ResponseVO;

public class BaseController {

	public ResponseEntity<ResponseVO> success(final ResponseVO responseVO, final Object response) {
		responseVO.setSuccess(true);
		responseVO.setData(response);
		responseVO.setExecutionTime();
		return ResponseEntity.ok(responseVO);

	}
}
