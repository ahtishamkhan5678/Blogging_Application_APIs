package com.mohammad.Services.Imp;

import java.util.List;

import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mohammad.Entities.Category;
import com.mohammad.Exception.ResourceNotFoundException;
import com.mohammad.Payload.CategoryDto;
import com.mohammad.Repo.CategoryRepo;
import com.mohammad.Services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	// create method 
	
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		
		Category cat=this.modelMapper.map(categoryDto, Category.class);
		
		Category addedCat=this.categoryRepo.save(cat);
	
		return this.modelMapper.map(addedCat, CategoryDto.class);
	}

	//update method;
	
	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		
		Category cat =this.categoryRepo.findById(categoryId).orElseThrow(()->new 
				ResourceNotFoundException("Category", "Category Id", categoryId));
		
		cat.setCategoryTitle(categoryDto.getCategoryTitle());
		cat.setCategoryDescription(categoryDto.getCategoryDescription());
		
		Category updateCat=this.categoryRepo.save(cat);
		
		return this.modelMapper.map(updateCat, CategoryDto.class);
	}
	
	

	//delete method;
	
	
	@Override
	public void deleteCategory(Integer categoryId) {
	
		Category cat=this.categoryRepo.findById(categoryId).orElseThrow(()->new 
				ResourceNotFoundException("Category", "Category Id", categoryId));
		this.categoryRepo.delete(cat);
		
	}

	// get singlecategory method;
	
	
	@Override
	public CategoryDto getCategory(Integer categoryId) {
		
		Category cat=this.categoryRepo.findById(categoryId).orElseThrow(()->new 
				ResourceNotFoundException("Category", "Category Id", categoryId));
		
		return this.modelMapper.map(cat, CategoryDto.class);
	}

	//get all methods;
	
	@Override
	public List<CategoryDto> getAllCategories() {
		
		List <Category> categories=this.categoryRepo.findAll();
		
		// syntax will return the list of Category;
		List<CategoryDto>catDtos=categories.stream().map((cat)->this.modelMapper.map(cat, CategoryDto.class))
		.collect(Collectors.toList());
		
		return catDtos;
	}

}
