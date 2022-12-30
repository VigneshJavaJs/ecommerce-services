package com.kl.ecommerce.serviceimpl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

//import com.kl.ecommerce.entity.User;
import com.kl.ecommerce.service.IUserService;


@Component
public class AuthUserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private IUserService userService;

	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {

		final com.kl.ecommerce.entity.User userModel = userService.findUserByEmail(username);
		if (userModel != null) {

			return new User(userModel.getEmail(), userModel.getPassword(), new ArrayList<>());
		} else {
			throw new UsernameNotFoundException("User not found with email: " + username);
		}

	}

}

