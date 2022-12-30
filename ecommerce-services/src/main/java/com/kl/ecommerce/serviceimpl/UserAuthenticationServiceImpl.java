package com.kl.ecommerce.serviceimpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kl.ecommerce.constants.MessageStore;
import com.kl.ecommerce.entity.User;
import com.kl.ecommerce.repository.IUserRepository;
import com.kl.ecommerce.request.AuthenticationReqVO;
import com.kl.ecommerce.request.SignupReqVO;
import com.kl.ecommerce.response.LoginResVO;
import com.kl.ecommerce.service.IUserAuthenticationService;
import com.kl.ecommerce.service.IUserService;
import com.kl.ecommerce.util.JWTTokenUtil;

@Service
public class UserAuthenticationServiceImpl implements IUserAuthenticationService{

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private IUserService userService;

	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private JWTTokenUtil jwtTokenUtil;

	
	
	@Override
	public LoginResVO loginAuthentication(AuthenticationReqVO authenticationReqVO) throws Exception {
		authenticate(authenticationReqVO.getEmail(), authenticationReqVO.getPassword());
		final User user = userService.findUserByEmail((authenticationReqVO.getEmail()));
		
		/*
		 * final User userProfile = userService
		 * .findUserByEmail(authenticationReqVO.getEmail().toLowerCase())
		 * .orElseThrow(() -> new UsernameNotFoundException(
		 * "User Not Found with email: " + authenticationReqVO.getEmail()));
		 */
		
		final LoginResVO loginResponseVO = new LoginResVO();
		String status = "";
		userRepository.save(user);
		final String token = jwtTokenUtil.generateToken(user);
		loginResponseVO.setJwtToken(token);
		loginResponseVO.setId(user.getId());
		status = MessageStore.AUTHORIZED;
		loginResponseVO.setMessage(status);
		return loginResponseVO;
	}
	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}

	}
	@Override
	public String signup(SignupReqVO signupReqVO) {
		final Optional<User> userOptional = userRepository
				.findByEmail(signupReqVO.getEmail());
		if (userOptional.isPresent())
			return MessageStore.USER_EXIST;
		final User user= new User();
		//final UUID uuid = UUID.randomUUID();
		
		user.setName(signupReqVO.getName());
		user.setEmail(signupReqVO.getEmail());
		
		user.setPassword(encoder.encode(signupReqVO.getPassword()));
		userRepository.save(user);
		return MessageStore.USER_REGISTERED;
	}

}
