package com.mohammad.Services;

import java.util.List;

import com.mohammad.Payload.UserDTO;

public interface UserService {
	// making method below here related to application!
	
	UserDTO registerNewUser(UserDTO userDto);
	
	UserDTO createUser(UserDTO user);
	
	UserDTO updateUser(UserDTO user,Integer userId);
	
	UserDTO getUserById(Integer userId);
	
	List<UserDTO> getAllUsers();
	
	void deleteUser(Integer userId);
	

}
