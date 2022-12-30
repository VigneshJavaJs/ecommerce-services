package com.kl.ecommerce.request;

import lombok.Data;

@Data
public class SignupReqVO {
	private String name;
	private String email;
	private String password;
}