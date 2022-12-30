package com.kl.ecommerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.kl.ecommerce.entity.User;



@Repository
public interface IUserRepository extends JpaRepository<User, Integer> {

	//@Query(value = NamedQueries.GET_USER_BY_EMAIL)
	
	@Query(value = "SELECT * FROM user u WHERE u.email = ?1 ", nativeQuery = true)
	public User findUserByEmail(String emailId);

	Optional<User> findByEmail(String email);

}
