package com.kl.ecommerce.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kl.ecommerce.request.AuthenticationReqVO;
import com.kl.ecommerce.request.SignupReqVO;
import com.kl.ecommerce.response.ResponseVO;
import com.kl.ecommerce.service.IUserAuthenticationService;

@RestController
@CrossOrigin
@RequestMapping(path = "/api/v1/auth")
public class AuthenticationController extends BaseController {

	@Autowired
	private IUserAuthenticationService authenticationService;

	@PostMapping(value = "/login")
	public ResponseEntity<ResponseVO> createAuthenticationToken( @RequestBody final AuthenticationReqVO authenticationReqVO)
			throws Exception {
		final ResponseVO responseVO = new ResponseVO();
		return super.success(responseVO, authenticationService.loginAuthentication(authenticationReqVO));
	}

	@PostMapping(value = "/signup")
	public ResponseEntity<ResponseVO> signUp(@RequestBody final SignupReqVO signupReqVO) throws Exception {
		final ResponseVO responseVO = new ResponseVO();
		return super.success(responseVO, authenticationService.signup(signupReqVO));
	}
}
