package com.mohammad.Services.Imp;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mohammad.Config.AppConstants;
import com.mohammad.Entities.Role;
import com.mohammad.Entities.User;
import com.mohammad.Exception.ResourceNotFoundException;
import com.mohammad.Payload.UserDTO;
import com.mohammad.Repo.RoleRepo;
import com.mohammad.Repo.UserRepo;
import com.mohammad.Services.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	
    @Autowired
	private UserRepo userRepo;
    
    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private PasswordEncoder pE;
    
    @Autowired
    private RoleRepo roleRepo;
	
	@Override
	public UserDTO createUser(UserDTO userDto) {
		
		User user=this.dtoToUser(userDto);
	    User savedUser=this.userRepo.save(user);
	    return this.userTODto(savedUser);
	
	}

	@Override
	public UserDTO updateUser(UserDTO userDto, Integer userId) {
		// updating user details;
		User user=this.userRepo.findById(userId)
				.orElseThrow(()-> new ResourceNotFoundException("User","id",userId));
		
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());
		
		User upadateUser=this.userRepo.save(user);
		UserDTO userDto1 =this.userTODto(upadateUser);
		
		return userDto ;
	}

	@Override
	public UserDTO getUserById(Integer userId) {
		
		User user=this.userRepo.findById(userId)
				.orElseThrow(()-> new ResourceNotFoundException("User", "Id", userId));
		
		return this.userTODto(user);
	}

	@Override
	public List<UserDTO> getAllUsers() {
	
		 List<User>users=this.userRepo.findAll();
		 
		 List<UserDTO> userDtos=users.stream().map(user->this.userTODto(user)).collect(Collectors.toList());
		
		 return userDtos;
	}

	@Override
	public void deleteUser(Integer userId) {
		  
	 User users = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "Id", userId));
	
	 this.userRepo.delete(users);
		
		
	}
   // making method to convert  entity->dto and vice versa
	private User dtoToUser(UserDTO userDto) {
		User users=this.modelMapper.map(userDto, User.class);
		
		// creating manulally;
		
//		
//		users.setId(userDto.getId());
//		users.setName(userDto.getName());
//		users.setEmail(userDto.getEmail());
//		users.setAbout(userDto.getAbout());
//		users.setPassword(userDto.getPassword());
		
		return users;
		
		
	}
	public UserDTO userTODto(User user) {
		UserDTO userDto=this.modelMapper.map(user, UserDTO.class);
		
		
//		userDto.setId(user.getId());
//		userDto.setName(user.getName());
//		userDto.setEmail(user.getEmail());
//		userDto.setPassword(user.getPassword());
//		userDto.setAbout(user.getAbout());
		return userDto;
		
	}

	// implements of newUser below here!
	
	@Override
	public UserDTO registerNewUser(UserDTO userDto) {
		
		User user=this.modelMapper.map(userDto, User.class);
		
		// encoded  the password below here!
		
		user.setPassword(this.pE.encode(user.getPassword()));
		
		// roles;
		
		 Role role = this.roleRepo.findById(AppConstants.NORMAL_USER).get();
		 user.getRoles().add(role);
		 
		User newUser= this.userRepo.save(user);
		
		return this.modelMapper.map(newUser, UserDTO.class);
	}


	
	

}
