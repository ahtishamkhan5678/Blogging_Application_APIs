package com.mohammad.Payload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {
	
	private Integer categoryId;
	
	@NotBlank
	@Size(min=4 , message="Min size of Category Title is 4 characters!")
	private String categoryTitle;
	
	@NotBlank
	@Size(min=10,message="Min size of Category Description is 10 characters!")
	private String categoryDescription;

}
