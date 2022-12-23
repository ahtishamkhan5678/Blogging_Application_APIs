package com.mohammad.Payload;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.mohammad.Entities.Role;

//import jakarta.validation.constraints.Email;
//import jakarta.validation.constraints.NotEmpty;
//import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDTO {
	
	
	private int id;
	
	
	@NotEmpty
	@Size(min = 4, message="User name must be min of 4 characters!")
	private String name;
	
	@Email(message=" Email Address Not Valid Write Correct Email Address")
	private String email;
	
	@NotEmpty
	@Size(min=3,max=10 ,message="Provide correct length of Password! i.e( min-3 and max-10)")
	private String password;
	
	@NotEmpty
	private String about;
	
	private  Set<RoleDto> roles=new HashSet<>();

}
