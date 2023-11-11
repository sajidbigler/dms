package com.cts.dmsauth.api;

import java.util.List;

import com.cts.dmsauth.api.placeholder.Comment;
import com.cts.dmsauth.api.placeholder.Post;

public interface IPlaceholderService {
	
	void addPost(Post post);
	
	Post getPost(String docId, String author);
	
	void  postComment(Comment comment);
	
	List<Comment> getComment(String docId);

}
