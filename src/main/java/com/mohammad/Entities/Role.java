package com.mohammad.Entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Role {
  
	@Id
	
	private int id; // we give the id by default;
	
	private String name;

	
}
