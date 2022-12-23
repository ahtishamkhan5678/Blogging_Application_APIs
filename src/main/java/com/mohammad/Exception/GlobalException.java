package com.mohammad.Exception;


import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
//import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.mohammad.Payload.ApiResponse;

@RestControllerAdvice     // control the exception global level!
public class GlobalException {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	// simple method below
	public ResponseEntity<ApiResponse> resourceNotExceptionHanlder(ResourceNotFoundException ex){
		// taking the message here!
		String message=ex.getMessage();
		
		//making api respone and passing the request according to condition;
		
		ApiResponse apiResponse=new ApiResponse(message,false);
		
		return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.NOT_FOUND);
		
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String,String>> handleMethodArgsNotValidException(MethodArgumentNotValidException ex){
		
		Map<String,String> resp=new HashMap<>();
		
		ex.getBindingResult().getAllErrors().forEach((error)->{
			// type cast the error to field error below here!
			
			String fieldName=((FieldError)error).getField();
			
			String message=error.getDefaultMessage();
			
			resp.put(fieldName, message);
		});
		
		return new ResponseEntity<Map<String,String>>(resp,HttpStatus.BAD_REQUEST);
	}
	
	
	@ExceptionHandler(ApiException.class)
	// simple method below
	public ResponseEntity<ApiResponse> handleApiException(ApiException ex){
		// taking the message here!
		String message=ex.getMessage();
		
		//making api respone and passing the request according to condition;
		
		ApiResponse apiResponse=new ApiResponse(message,true);
		
		return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.BAD_REQUEST);
		
	}
	

}
