package com.cts.dmsauth.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DownloadDocumentResponse extends BaseResponse{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private DocumentDTO documentDTO;

}
