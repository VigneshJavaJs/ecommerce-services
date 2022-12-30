package com.kl.ecommerce.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kl.ecommerce.entity.User;
import com.kl.ecommerce.repository.IUserRepository;
import com.kl.ecommerce.service.IUserService;

@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	IUserRepository userRepository;

	@Override
	public User findUserByEmail(String username) {
		return userRepository.findUserByEmail(username);
	}

}
