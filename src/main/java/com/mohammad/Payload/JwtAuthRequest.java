package com.mohammad.Payload;

import lombok.Data;

@Data
public class JwtAuthRequest {

	private String username;// email hai !
	
	private String password;
	
	
}
