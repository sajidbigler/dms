package com.cts.dmsauth.api.request;

import lombok.Data;

@Data
public class PostCommentRequest {
	
	private Integer docId;

	private String commentBy;

	private String comments;

}
