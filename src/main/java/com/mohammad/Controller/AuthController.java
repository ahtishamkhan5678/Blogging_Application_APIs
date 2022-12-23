package com.mohammad.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mohammad.Exception.ApiException;
import com.mohammad.Payload.CategoryDto;
import com.mohammad.Payload.JwtAuthRequest;
import com.mohammad.Payload.JwtAuthResponse;
import com.mohammad.Payload.UserDTO;
import com.mohammad.Security.JwtTokenHelper;
import com.mohammad.Services.UserService;

@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {
	
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserService userService;
	
	
	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(
			@RequestBody JwtAuthRequest request) throws Exception{
		
		this.authenticate(request.getUsername(),request.getPassword());
		
		UserDetails userDetails=this.userDetailsService.loadUserByUsername(request.getUsername());
		
		String token=this.jwtTokenHelper.generateToken(userDetails);
		
		JwtAuthResponse response= new JwtAuthResponse();
		response.setToken(token);
		return new ResponseEntity<JwtAuthResponse>(response,HttpStatus.OK);
		
		
	}


	private void authenticate(String username, String password) throws Exception {
		
		UsernamePasswordAuthenticationToken  authenticationToken=new 
				UsernamePasswordAuthenticationToken(username, password);

		try {
			this.authenticationManager.authenticate(authenticationToken);
		}
		catch(BadCredentialsException e){
			System.out.println("Invalid Details !!!");
			throw new ApiException("Invalid Username or Password !!");
		}
		
		
		
	}
	
	// register new User Api below here!
	@PostMapping("/register")
	public ResponseEntity<UserDTO> registerUser(@RequestBody UserDTO userDto ){
	
	 UserDTO registeredUser=	this.userService.registerNewUser(userDto);
	 
	 return new ResponseEntity<UserDTO>(registeredUser,HttpStatus.CREATED);
	}

}
