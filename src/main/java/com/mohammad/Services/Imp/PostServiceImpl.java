package com.mohammad.Services.Imp;

import java.util.Date;
import java.util.List;
//import java.util.stream.Collector;
import java.util.stream.Collectors;

//import org.hibernate.grammars.hql.HqlParser.SortDirectionContext;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.mohammad.Entities.Category;
import com.mohammad.Entities.Post;
import com.mohammad.Entities.User;
import com.mohammad.Exception.ResourceNotFoundException;
import com.mohammad.Payload.PostDto;
import com.mohammad.Payload.PostResponse;
import com.mohammad.Repo.CategoryRepo;
import com.mohammad.Repo.PostRepo;
import com.mohammad.Repo.UserRepo;
import com.mohammad.Services.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	
	//////createPost--------->
	
	@Override
	public PostDto createPost(PostDto postDto,Integer userId, Integer categoryId) {
		
	    User user =this.userRepo.findById(userId).orElseThrow(()->
	    new ResourceNotFoundException("User","UserId",userId));
	    
	    Category category=this.categoryRepo.findById(categoryId).orElseThrow(()->
	    new ResourceNotFoundException("Category","categoryId",categoryId));
		
		Post post=this.modelMapper.map(postDto,Post.class);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		
		Post newPost=this.postRepo.save(post);
		
		
		return this.modelMapper.map(newPost, PostDto.class);
	}

	//////updatePost--------->
	
	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		
		Post post=this.postRepo.findById(postId).orElseThrow(()->
	    
	    new ResourceNotFoundException("Post","PostId",postId));
		
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		
		Post updatePost=this.postRepo.save(post);
		return this.modelMapper.map(updatePost, PostDto.class);
	}

	
	//////deletePost--------->
	
	@Override
	public void deletePost(Integer postId) {
		Post post=this.postRepo.findById(postId).orElseThrow(()->
	    
	    new ResourceNotFoundException("Post","PostId",postId));
		
		this.postRepo.delete(post);
		
	}

	//////getAllPost--------->
	
	@Override
	public PostResponse getAllPost(Integer pageNumber,Integer pageSize,String sortBy,String sortDir) {
	     
		Sort sort=null;
		if(sortDir.equalsIgnoreCase("asc")) {
			sort=Sort.by(sortBy).ascending();
		}
		else {
			sort=Sort.by(sortBy).descending();
		}
	
		Pageable p=PageRequest.of(pageNumber, pageSize, sort);// we can change the order by asc or desc;
		
        Page<Post> pagePost=this.postRepo.findAll(p);
        
        List<Post> allPosts=pagePost.getContent();
		
		List<PostDto> postDtos=allPosts.stream().map((post)->
		
		this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		PostResponse  postResponse=new PostResponse();
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setLastPage(pagePost.isLast());
		
		return  postResponse;
	}
	
	//////getPostById--------->

	@Override
	public PostDto getPostById(Integer postId) {
		
	    Post post=	this.postRepo.findById(postId).orElseThrow(()->
	    
	    new ResourceNotFoundException("Post","PostId",postId));
		
		return this.modelMapper.map(post,PostDto.class);
		
	}

	////// getPostsByCategory-------->
	
	@Override
	public List<PostDto> getPostsByCategory(Integer categoryId) {
		Category cat=this.categoryRepo.findById(categoryId).orElseThrow(()->
	    new ResourceNotFoundException("Category","category id",categoryId));
		
		List<Post>posts=this.postRepo.findByCategory(cat);
		
		List<PostDto> postDtos =  posts.stream().map((post) -> 
		this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		return postDtos;
	}

	//////getPostsByUser-------->
	
	@Override
	public List<PostDto> getPostsByUser(Integer userId) {
		User user=this.userRepo.findById(userId).orElseThrow(()->
	    new ResourceNotFoundException("User","userId",userId));
		
		List<Post>posts=this.postRepo.findByUser(user);
		
		List<PostDto> postDtos =  posts.stream().map((post) -> 
		/// silly was here :)
		this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		
		return postDtos;
	}

    //////searchPosts-------->
	
	@Override
	public List<PostDto> searchPosts(String keyword) {
		List<Post> posts=this.postRepo.searchByTitle("%"+keyword+"%");
		List<PostDto> postDtos =posts.stream().map((post) -> 
	
		this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		return postDtos;
	}

}
