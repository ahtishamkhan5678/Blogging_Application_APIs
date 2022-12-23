package com.mohammad.Services;

import java.util.List;

import com.mohammad.Entities.Post;
import com.mohammad.Payload.PostDto;
import com.mohammad.Payload.PostResponse;

public interface PostService {

	// create 
	
	PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);
	
	
	
	// update
	PostDto updatePost(PostDto postDto,Integer postId);
	
	
	
	
	//delete
	
	void deletePost(Integer postId);
	
	
	
	
	//get all post with pagination;
	
	PostResponse getAllPost(Integer pageNumber,Integer pageSize, String sortBy,String sortDir);
	
	
	
	// get single post
	
	PostDto  getPostById(Integer postId);
	
	
	
	
	//get all post by category;
	
	List<PostDto> getPostsByCategory(Integer categoryId);
	
	
	// get all post by user;
	List<PostDto> getPostsByUser(Integer userId);
	
	
	// search the post
	List<PostDto> searchPosts(String keyword);
	
	
	
}
