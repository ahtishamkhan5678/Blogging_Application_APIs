package com.mohammad.Security;

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
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;

@Component
public class JwtAuthenticationFilter  extends OncePerRequestFilter{
	
	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
	
		
		// get token from request;
		
		String requestToken=request.getHeader("Authorization");
		
		// Bearee 2333fdh;
		
		System.out.println(requestToken);
		
		String username=null;
		
		String token=null;
		
		if(requestToken!=null && requestToken.startsWith("Bearer")) {
			
			  token=requestToken.substring(7);
			  try {
				   
				  username= this.jwtTokenHelper.getUsernameFromToken(token);
				  
				} catch (IllegalArgumentException ex) {
					System.out.println("Unable to get Jwt Token !");
				}
			  
			  	  catch(ExpiredJwtException e ) {
			  		System.out.println("Jwt token has expired !");
			  	}
			      catch(MalformedJwtException e) {
			    	System.out.println("Invalid jwt !");
			    }
			  	
		}
		else {
			System.out.println("Jwt Token does not begin with Bearer!");
		}
		
		// once we get the token , now validate it;
		
		if(username!=null && SecurityContextHolder.getContext()
				.getAuthentication()==null) {
			
			UserDetails userDetails=this.userDetailsService.loadUserByUsername(username);
			
			if(this.jwtTokenHelper.validateToken(token, userDetails)) {
				
				// i.e if everything is correct  then perform this;
				// to set authication
				
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =new
						UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				
				
			}
			else {
				System.out.println("Invalid  Jwt token !");
			}
			
			
		}
		else {
			System.out.println("Username is null or Context is not Null !");
		}
		
		
		filterChain.doFilter(request, response);
		
		
	}

}
