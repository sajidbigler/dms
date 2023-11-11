package com.cts.dmsauth.api.placeholder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

	private Integer docId;

	private String commentBy;

	private String comments;

}
