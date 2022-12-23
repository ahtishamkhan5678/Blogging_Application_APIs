package com.mohammad.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mohammad.Entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer> {
	

}
