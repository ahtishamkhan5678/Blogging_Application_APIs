package com.mohammad.Services.Imp;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mohammad.Entities.Comment;
import com.mohammad.Entities.Post;
import com.mohammad.Exception.ResourceNotFoundException;
import com.mohammad.Payload.CommentDto;
import com.mohammad.Repo.CommentRepo;
import com.mohammad.Repo.PostRepo;
import com.mohammad.Services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private CommentRepo commentRepo;
	
    @Autowired
	private ModelMapper modelMapper;
	
	//// create Comments  Method below here ------->
	
	
	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {
		
		Post post=this.postRepo.findById(postId).orElseThrow(()->new 
				ResourceNotFoundException("Post", "Post Id", postId));
		
		 Comment comment=this.modelMapper.map(commentDto, Comment.class);
		 
		 comment.setPost(post);
		 
		 Comment saveComment=this.commentRepo.save(comment);
		
		return this.modelMapper.map(saveComment,CommentDto.class);
	}

	
	////delete Comments  Method below here ------->
	
	
	
	@Override
	public void deleteComment(Integer commentId) {
		 Comment com=this.commentRepo.findById(commentId).orElseThrow(()->new 
					ResourceNotFoundException("Comment", "Comment Id", commentId));
		 this.commentRepo.delete(com);
		 
		
	}

	
	
	
	
	
}
