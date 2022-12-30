package com.kl.ecommerce.service;

import com.kl.ecommerce.request.AuthenticationReqVO;
import com.kl.ecommerce.request.SignupReqVO;
import com.kl.ecommerce.response.LoginResVO;

public interface IUserAuthenticationService {

	LoginResVO loginAuthentication(AuthenticationReqVO authenticationReqVO) throws Exception;

	String signup(SignupReqVO signupReqVO);

}