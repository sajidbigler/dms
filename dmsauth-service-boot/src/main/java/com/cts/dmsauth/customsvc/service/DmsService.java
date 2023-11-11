package com.cts.dmsauth.customsvc.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.cts.dmsauth.api.request.DocumentRequest;
import com.cts.dmsauth.api.request.PostCommentRequest;
import com.cts.dmsauth.api.response.DocumentDTO;
import com.cts.dmsauth.api.response.DownloadAllDocumentResponse;
import com.cts.dmsauth.api.response.DownloadDocumentResponse;

public interface DmsService {

	String uploadDocument(String docName, MultipartFile file, String title, String docId, String author);

	List<DocumentDTO> downloadAllDocuments();

	DownloadDocumentResponse downloadDocument(DocumentRequest request);

	String deleteDocument(DocumentRequest request);

	String postComment(PostCommentRequest request);

}
