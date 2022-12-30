package com.kl.ecommerce.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.kl.ecommerce.util.JWTTokenUtil;

public class JWTRequestFilter extends OncePerRequestFilter {

	@Autowired
	private UserDetailsService authUserDetailsService;

	@Autowired
	private JWTTokenUtil JwtTokenUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		final Integer tokenSubStringIndex = 7;
		final String authorizationHeader = request.getHeader("Authorization");
		String userName = null;
		String token = null;

		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {

			token = authorizationHeader.substring(tokenSubStringIndex);
			userName = JwtTokenUtil.getUsernameFromToken(token);
		} else {
			logger.warn("JWT Token does not begin with Bearer Strings");
		}
		if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			final UserDetails userDetails = this.authUserDetailsService.loadUserByUsername(userName);
			if (this.JwtTokenUtil.validateToken(token, userDetails)) {
				final UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

			}
		}

		filterChain.doFilter(request, response);

	}
	
    @Override
    protected boolean shouldNotFilterAsyncDispatch() {
        return false;
    }

    @Override
    protected boolean shouldNotFilterErrorDispatch() {
        return false;
    }

}
