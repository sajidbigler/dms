package com.cts.dmsauth.customsvc.controller;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cts.dmsauth.api.request.DocumentRequest;
import com.cts.dmsauth.api.request.PostCommentRequest;
import com.cts.dmsauth.api.response.DocumentDTO;
import com.cts.dmsauth.api.response.DownloadAllDocumentResponse;
import com.cts.dmsauth.api.response.DownloadDocumentResponse;
import com.cts.dmsauth.customsvc.service.DmsService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/dmsauthApi")
public class DmsController {

	@Autowired
	private DmsService service;

	@ApiOperation(value = "Upload Document")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 201, message = "Created"),
			@ApiResponse(code = 400, message = "Invalid Request"),
			@ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 500, message = "Internal server error")})
	@PostMapping("/upload")
	public ResponseEntity<?> uploadDocument(@RequestParam MultipartFile file, @RequestParam String title,
			@RequestParam String docName, @RequestParam String docId, @RequestParam String author) {
		log.info("-------dmsauthController::uploadDocument starts--------");
		if(org.apache.commons.lang3.StringUtils.isBlank(docName)){
			return new ResponseEntity<String>("please specify docname", HttpStatus.BAD_REQUEST);	
		}
		String status = service.uploadDocument(docName, file, title, docId, author);
		log.info("-------dmsauthController::uploadDocument ends--------");		
		return new ResponseEntity<>(status, org.apache.commons.lang3.StringUtils.isNotBlank(docId) ? HttpStatus.OK : HttpStatus.CREATED);
	}

	@ApiOperation(value = "Upload Document")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 500, message = "Internal server error")})
	@GetMapping("/documents")
	public ResponseEntity<DownloadAllDocumentResponse> downloadAllDocuments() {
		DownloadAllDocumentResponse response = DownloadAllDocumentResponse.builder().build();
		log.info("-------dmsauthController::downloadAllDocuments starts--------");
		List<DocumentDTO> documents = service.downloadAllDocuments();
		if(CollectionUtils.isEmpty(documents)) {
			response.setDocuments(documents);	
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}	
		log.info("-------dmsauthController::downloadAllDocuments returns--------");
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	}

	@ApiOperation(value = "Download Document")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 400, message = "Invalid Request"),
			@ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 500, message = "Internal server error")})
	@PostMapping("/document")
	public ResponseEntity<DownloadDocumentResponse> downloadDocument(@RequestBody DocumentRequest request) {
		log.info("-------dmsauthController::downloadDocument starts--------");
		DownloadDocumentResponse response = service.downloadDocument(request);
		if(Objects.nonNull(response)){
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}
		log.info("-------dmsauthController::downloadDocument returns--------");
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	}

	@ApiOperation(value = "Delete Document")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 204, message = "Document deleted"),
			@ApiResponse(code = 400, message = "Invalid Request"),
			@ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 500, message = "Internal server error")})
	@PostMapping("/delete")
	public void deleteDocument(@RequestBody DocumentRequest request) {
		log.info("-------dmsauthController::deleteDocument starts--------");
		service.deleteDocument(request);
		log.info("-------dmsauthController::deleteDocument ends--------");
		//return new ResponseEntity<void>(HttpStatus.ACCEPTED);
	}

	@ApiOperation(value = "Comment")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 201, message = "Created"),
			@ApiResponse(code = 400, message = "Invalid Request"),
			@ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 500, message = "Internal server error")})
	@PostMapping("/comment")
	public void postComment(@RequestBody PostCommentRequest request) {
		log.info("-------dmsauthController::postComment starts--------");
		service.postComment(request);
		log.info("-------dmsauthController::postComment ends--------");
	}

}
