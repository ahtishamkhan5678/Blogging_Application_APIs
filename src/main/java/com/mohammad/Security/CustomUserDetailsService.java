package com.mohammad.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mohammad.Entities.User;
import com.mohammad.Exception.ResourceNotFoundException;
import com.mohammad.Repo.UserRepo;

@Service
public  class CustomUserDetailsService  implements UserDetailsService{

	@Autowired
	private UserRepo userRepo;
	
	
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	
		//loding user from database by username;
	 User user=	this.userRepo.findByEmail(username).orElseThrow(()->new 
				ResourceNotFoundException("User", "email :"+username,0));
		
		return user;
	}

}
