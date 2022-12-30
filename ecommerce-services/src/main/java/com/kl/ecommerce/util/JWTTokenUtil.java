package com.kl.ecommerce.util;

import java.io.Serializable;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.kl.ecommerce.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;

@Component
@Data
public class JWTTokenUtil implements Serializable {

	@Value("${jwt.secret}")
	private String secret;

	// generate token for user
	public String generateToken(final User user) {
		final Map<String, Object> claims = new HashMap<>();
		return doGenerateToken(claims, user.getEmail());
	}

	private String doGenerateToken(final Map<String, Object> claims, final String subject) {
		final String signingKeyB64 = Base64.getEncoder().encodeToString(secret.getBytes());
		return Jwts.builder().setClaims(claims).setSubject(subject)
				.setExpiration(new Date(System.currentTimeMillis() + 5 * 60 * 60 * 1000))
				.setIssuedAt(new Date(System.currentTimeMillis())).signWith(SignatureAlgorithm.HS256, signingKeyB64)
				.compact();
	}

	// validate token
	public Boolean validateToken(final String token, final UserDetails userDetails) {
		final String username = getUsernameFromToken(token);
		final Claims claims = getAllClaimsFromToken(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	// retrieve username from jwt token
	public String getUsernameFromToken(final String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	// check if the token has expired
	private Boolean isTokenExpired(final String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	// retrieve expiration date from jwt token
	public Date getExpirationDateFromToken(final String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public <T> T getClaimFromToken(final String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	// for retrieveing any information from token we will need the secret key
	private Claims getAllClaimsFromToken(final String token) {
		String signingKeyB64 = Base64.getEncoder().encodeToString(secret.getBytes());
		return Jwts.parser().setSigningKey(signingKeyB64).parseClaimsJws(token).getBody();
	}

	/*
	 * public String getSecret() { return secret; }
	 * 
	 * public void setSecret(final String secret) { JWTTokenUtil.secret = secret; }
	 */
}

