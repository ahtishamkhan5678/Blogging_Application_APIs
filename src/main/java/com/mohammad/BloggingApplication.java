package com.mohammad;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.mohammad.Config.AppConstants;
import com.mohammad.Entities.Role;
import com.mohammad.Repo.RoleRepo;

@SpringBootApplication
public class BloggingApplication implements CommandLineRunner {
	
	@Autowired
	private PasswordEncoder pE;

	@Autowired
	private RoleRepo roleRepo;
	
	public static void main(String[] args) {
		SpringApplication.run(BloggingApplication.class, args);
	}
	
	@Bean
	public ModelMapper modelMapper() {
	  
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(this.pE.encode("aman@123"));
		
		try {
			Role role=new Role();
			role.setId(AppConstants.ADMIN_USER);
			role.setName("ROLE_ADMIN");
			
			Role role1=new Role();
			role1.setId(AppConstants.NORMAL_USER);
			role1.setName("ROLE_NORMAL");
			
			 List<Role> roles=List.of(role,role1);
			 
			 List<Role> result= this.roleRepo.saveAll(roles);
			 
			 result.forEach(r->{
				 System.out.println(r.getName());
			 });
		}
		catch( Exception e) {
			e.printStackTrace();
		}
		
	}

}
