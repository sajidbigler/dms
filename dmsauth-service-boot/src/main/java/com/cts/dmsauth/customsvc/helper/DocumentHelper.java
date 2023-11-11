package com.cts.dmsauth.customsvc.helper;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.cts.dmsauth.api.placeholder.Comment;
import com.cts.dmsauth.api.placeholder.Post;
import com.cts.dmsauth.api.response.DocumentDTO;
import com.cts.dmsauth.customsvc.entity.Document;
import com.cts.dmsauth.customsvc.util.DocumentUtil;

@Component
public class DocumentHelper {

	public Document buildDocumentUploadEntity(String docName, MultipartFile file, String createdBy) throws IOException {
		return Document.builder().name(docName).type(file.getContentType())
				.content(DocumentUtil.compressImage(file.getBytes())).createdBy(createdBy).build();

	}

	public List<DocumentDTO> buildDownloadAllDocuments(List<Document> documentList) {
		return documentList.parallelStream().map(document -> {
			return DocumentDTO.builder().docId(document.getId()).docName(document.getName()).docType(document.getType())
					.docContent(DocumentUtil.decompressImage(document.getContent())).build();
		}).collect(Collectors.toList());
	}

	public DocumentDTO buildDownloadDocument(Document document) throws IOException {
		return DocumentDTO.builder().docId(document.getId()).docName(document.getName()).docType(document.getType())
				.docContent(DocumentUtil.decompressImage(document.getContent())).build();

	}

	public void updateDocumentUploadEntity(Document document, String docName, MultipartFile file) throws IOException {
		document.setContent(DocumentUtil.compressImage(file.getBytes()));
		document.setName(docName);
		document.setType(file.getContentType());

	}

	public Post buildDocumentPost(int docId, String title, String author) {
		return Post.builder().docId(docId).author(author).title(title).build();
	}

	public Comment buildDocumentComment(Integer docId, String comments, String commentBy) {
		return Comment.builder().docId(docId).comments(comments).commentBy(commentBy).build();
	}
}
