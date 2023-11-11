package com.cts.dmsauth.api.placeholder;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post {

	private Integer docId;

	private String author;

	private String title;
	
	private List<Comment> comments;

}
