package com.kl.ecommerce.response;

import java.io.Serializable;

import lombok.Data;

@Data
public class LoginResVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String jwtToken;

	private Integer id;

	private String message;

}