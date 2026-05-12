package com.pm.patientservice.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

//This class lets us handle the cross-cutting concerns such as  error handling outside of
//controllers and our services. So basically centralizing our exception handling logic here.
@ControllerAdvice
public class GlobalExceptionHandler {
    //note:MethodArgumentNotValidException<-- This handles the validation errors that get triggered
//when JPA validates the Request DTOs in the request itself.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex)//This argument catches all the JPA errors like email not valid etc and going to put everything in this variable called 'ex'
    {
Map<String, String> errors= new HashMap<>();
ex.getBindingResult().getFieldErrors().
        forEach(error->errors.put(error.getField(), error.getDefaultMessage()));

 return ResponseEntity.badRequest().body(errors);
 //badRequest() means something is wrong with the request and it will return 400 status code to the calling client or frontend in a nice json format.
    }
}
