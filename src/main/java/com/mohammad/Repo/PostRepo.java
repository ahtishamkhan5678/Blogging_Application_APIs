package com.mohammad.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mohammad.Entities.Category;
import com.mohammad.Entities.Post;
import com.mohammad.Entities.User;

public interface PostRepo extends JpaRepository<Post, Integer> {

	// a method to get all post by one user below here!
	//custom finder!
	
	List<Post> findByUser(User user);
	
	List<Post> findByCategory(Category category);
	
	@Query("select p from Post p where p.title like:key")
	List<Post> searchByTitle(@Param("key") String  title);
	
}
