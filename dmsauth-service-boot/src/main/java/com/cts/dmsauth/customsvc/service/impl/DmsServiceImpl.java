package com.cts.dmsauth.customsvc.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import com.cts.customsvc.base.exceptions.AppException;
import com.cts.dmsauth.api.IPlaceholderService;
import com.cts.dmsauth.api.placeholder.Post;
import com.cts.dmsauth.api.request.DocumentRequest;
import com.cts.dmsauth.api.request.PostCommentRequest;
import com.cts.dmsauth.api.response.DocumentDTO;
import com.cts.dmsauth.api.response.DownloadDocumentResponse;
import com.cts.dmsauth.customsvc.entity.Document;
import com.cts.dmsauth.customsvc.helper.DocumentHelper;
import com.cts.dmsauth.customsvc.respository.DmsRepository;
import com.cts.dmsauth.customsvc.service.DmsService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DmsServiceImpl implements DmsService {

	@Autowired
	private DmsRepository repository;

	@Autowired
	private DocumentHelper helper;

	@Autowired
	private IPlaceholderService placeholderService;

	@Override
	@Transactional
	public String uploadDocument(String docName, MultipartFile file, String title, String docId, String author) {
		log.info("------dmsauthServiceImpl::uploadDocument starts-------- ");
		Document document = null; 
		String status = null;
		try {
			if(!"application/pdf".equals(file.getContentType())) {
				throw new AppException("Incorrect file type, PDF required.");
			}

			if (StringUtils.isNotBlank(docId)) {
				Optional<Document> documentOptional = repository.findById(Long.valueOf(docId));
				if (documentOptional.isPresent()) {
					document = documentOptional.get();
					helper.updateDocumentUploadEntity(document, docName, file);
					status = "Document updated Successfullu !";
				}
			} else {
				document = repository.save(helper.buildDocumentUploadEntity(docName, file, author));
				placeholderService.addPost(helper.buildDocumentPost(document.getId().intValue(), title, author));
				status = "Document uploaded successfully !";
			}
		} catch (Exception e) {
			log.error("------Exception occured in dmsauthServiceImpl::uploadDocument-------- ", e);
			//throw new AppException(e);
		}
		log.info("------dmsauthServiceImpl::uploadDocument ends-------- ");
		return status;
	}

	@Override
	public List<DocumentDTO> downloadAllDocuments() {
		log.info("------dmsauthServiceImpl::uploadDocument starts-------- ");
		List<DocumentDTO> documents = null;
		try {
			List<Document> documentList = repository.findAll();
			if (!CollectionUtils.isEmpty(documentList)) {
				documents = helper.buildDownloadAllDocuments(documentList);
			} else {
				documents = Collections.emptyList();
			}
		} catch (Exception e) {
			log.error("------Exception occured in dmsauthServiceImpl::downloadAllDocuments-------- ", e);
			//throw new AppException(e);
		}

		log.info("------dmsauthServiceImpl::uploadDocument returns-------- ");
		return documents;

	}

	@Override
	public DownloadDocumentResponse downloadDocument(DocumentRequest request) {
		log.info("------dmsauthServiceImpl::uploadDocument starts-------- ");
		DownloadDocumentResponse response = DownloadDocumentResponse.builder().build();
		try {
			Optional<Document> documentOptional = repository.findById(request.getDocId());
			if (documentOptional.isPresent()) {
				DocumentDTO dto = helper.buildDownloadDocument(documentOptional.get());
				Post post = placeholderService.getPost(String.valueOf(documentOptional.get().getId()),
						documentOptional.get().getCreatedBy());
				if (Objects.nonNull(post)) {
					post.setComments(placeholderService.getComment(String.valueOf(documentOptional.get().getId())));
					dto.setPost(post);
				}
				response.setDocumentDTO(dto);
			}
		} catch (Exception e) {
			log.error("------Exception occured in dmsauthServiceImpl::downloadDocument-------- ", e);
			//throw new AppException(e);
		}
		log.info("------dmsauthServiceImpl::downloadDocument returns-------- ");
		return response;
	}

	@Override
	public String deleteDocument(DocumentRequest request) {
		log.info("------dmsauthServiceImpl::deleteDocument starts-------- ");
		String status = null;
		try {
			repository.deleteById(request.getDocId());
			status = "Document deleted successfully !";
		} catch (Exception e) {
			log.error("------Exception occured in dmsauthServiceImpl::deleteDocument--------", e);
			status = "Document is not found !";
			//throw new AppException(e);
		}
		log.info("------dmsauthServiceImpl::deleteDocument ends-------- ");
		return status;
	}

	@Override
	public String postComment(PostCommentRequest request) {
		log.info("------dmsauthServiceImpl::postComment starts-------- ");
		String status = null;
		try {
			placeholderService.postComment(
					helper.buildDocumentComment(request.getDocId(), request.getComments(), request.getCommentBy()));
			status = "post comments successfully !";
		} catch (Exception e) {
			log.error("------Exception occured in dmsauthServiceImpl::postComment--------", e);
			status = " comments not posted !";
			//throw new AppException(e);
		}
		log.info("------dmsauthServiceImpl::postComment ends-------- ");
		return status;
	}

}
