package com.exam.rest.webservices.restfulwebservice.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.exam.rest.webservices.restfulwebservice.user.UserNotFoundException;

@ControllerAdvice //make accessible to ALL controllers
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler{

@ExceptionHandler(Exception.class) //we want to handle all exceptions hence the Exception.class
public final ResponseEntity<ErrorDetails> handleAllExceptions(Exception ex, WebRequest request) throws Exception {
    ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));

    return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    
}

@ExceptionHandler(UserNotFoundException.class) //specific to user not found exception
public final ResponseEntity<ErrorDetails> handleUserNotFoundExceptions(Exception ex, WebRequest request) throws Exception {
    ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));

    return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.NOT_FOUND);
    
}

@Override //right click into super class to see all methods for customization
protected ResponseEntity<Object> handleMethodArgumentNotValid( //handle 400 bad request errors, where user object not meet valid specs
    			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

                    ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), "Total Errors: " + ex.getErrorCount()+ " First error:" + ex.getFieldError().getDefaultMessage(), request.getDescription(false));

                    return new ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST);

}
}
