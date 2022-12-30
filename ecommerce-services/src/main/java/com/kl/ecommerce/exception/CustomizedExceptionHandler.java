package com.kl.ecommerce.exception;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.kl.ecommerce.response.ExceptionHandlerResVO;

@ControllerAdvice
public class CustomizedExceptionHandler {

	Logger logger = LoggerFactory.getLogger(CustomizedExceptionHandler.class);

	@ExceptionHandler({ AuthenticationException.class, BadCredentialsException.class })
	public final ResponseEntity<ExceptionHandlerResVO> handleException(final AuthenticationException ex,
			final WebRequest request) {
		String message = ex.getLocalizedMessage();
		final ExceptionHandlerResVO exceptionHandlerResVO = new ExceptionHandlerResVO(false, new Date(), message,
				request.getDescription(false));
		return new ResponseEntity<ExceptionHandlerResVO>(exceptionHandlerResVO, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(AccessDeniedException.class)
	public final ResponseEntity<ExceptionHandlerResVO> handleException(final AccessDeniedException ex,
			final WebRequest request) {
		String message = ex.getLocalizedMessage();
		final ExceptionHandlerResVO exceptionHandlerResVO = new ExceptionHandlerResVO(false, new Date(), message,
				request.getDescription(false));
		return new ResponseEntity<ExceptionHandlerResVO>(exceptionHandlerResVO, HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public final ResponseEntity<ExceptionHandlerResVO> handleValidationExceptions(MethodArgumentNotValidException ex,
			final WebRequest request) {
		List<ObjectError> arr = ex.getBindingResult().getAllErrors();
		List<String> errors = new ArrayList<>();
		for (ObjectError n : arr) {
			errors.add(n.getDefaultMessage());
		}
		final ExceptionHandlerResVO exceptionHandlerResVO = new ExceptionHandlerResVO(false, new Date(), errors,
				request.getDescription(false));
		return new ResponseEntity<ExceptionHandlerResVO>(exceptionHandlerResVO, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public final ResponseEntity<ExceptionHandlerResVO> handleAllExceptions(
			final HttpRequestMethodNotSupportedException ex, final WebRequest request) {
		String message = "";
		if (ex.getCause() == null) {
			message = ex.getLocalizedMessage();
		} else if (ex.getCause().getCause() == null) {
			message = ex.getCause().getLocalizedMessage();
		} else {
			message = ex.getCause().getCause().getLocalizedMessage();
		}
		final ExceptionHandlerResVO exceptionHandlerResVO = new ExceptionHandlerResVO(false, new Date(), message,
				request.getDescription(false));
		return new ResponseEntity<ExceptionHandlerResVO>(exceptionHandlerResVO, HttpStatus.METHOD_NOT_ALLOWED);
	}

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ExceptionHandlerResVO> handleAllExceptions(final Exception ex,
			final WebRequest request) {
		String message = "";
		if (ex.getCause() == null) {
			message = ex.getLocalizedMessage();
		} else if (ex.getCause().getCause() == null) {
			message = ex.getCause().getLocalizedMessage();
		} else {
			message = ex.getCause().getCause().getLocalizedMessage();
		}
		final ExceptionHandlerResVO exceptionHandlerResVO = new ExceptionHandlerResVO(false, new Date(), message,
				request.getDescription(false));
		return new ResponseEntity<ExceptionHandlerResVO>(exceptionHandlerResVO, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	
}
