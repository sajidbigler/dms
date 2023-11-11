package com.cts.typicode.placeholder.ws;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import com.cts.dmsauth.api.IPlaceholderService;
import com.cts.dmsauth.api.placeholder.Comment;
import com.cts.dmsauth.api.placeholder.Post;
import com.cts.typicode.placeholder.config.PropertiesConfig;
import com.cts.typicode.placeholder.connector.RestConnector;
import com.cts.typicode.placeholder.util.PlaceholderUtils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PlaceholderServiceImpl implements IPlaceholderService {

	@Autowired
	private RestConnector connector;
	
	@Autowired
	private PropertiesConfig propsConfig;

	@Override
    @Retryable(value = {Exception.class}, maxAttempts = 3, backoff = @Backoff(200))
	public void addPost(Post post) {
		log.info("Calling Placeholder Post Rest Endpoint......");
		connector.postForEntity(propsConfig.getBaseUrlsMap().get("posts"), post, Post.class);
		log.info("Placeholder Post Rest Endpoint call success");
	}

	@Override
	public Post getPost(String docId, String author) {
		Post post = null;
		Map<String, String> params = new HashMap<>();
		params.put("docId", docId);
		params.put("author", author);
		try {
			Post[] posts = connector.getForEntity(
					PlaceholderUtils.buildURIWithParams(propsConfig.getBaseUrlsMap().get("posts"), params),
					Post[].class);
			post = posts[0];
		} catch (Exception e) {
			log.error("Error occured while calling Placeholder Rest Endpoint", e);
		}
		return post;
	}

	@Override
	public List<Comment> getComment(String docId) {
		List<Comment> comments = null;
		Map<String, String> params = new HashMap<>();
		params.put("docId", docId);
		try {
			Comment[] postComments = connector.getForEntity(
					PlaceholderUtils.buildURIWithParams(propsConfig.getBaseUrlsMap().get("comments"), params),
					Comment[].class);
			if (Objects.nonNull(postComments) && 0 < postComments.length) {
				comments = Arrays.asList(postComments);
			}
		} catch (Exception e) {
			log.error("Error occured while calling Placeholder Rest Endpoint", e);
		}
		return comments;
	}

	@Override
	public void postComment(Comment comment) {
		log.info("Calling Placeholder Comments Rest Endpoint......");
		connector.postForEntity(propsConfig.getBaseUrlsMap().get("comments"), comment, Comment.class);
		log.info("Placeholder Comments Rest Endpoint call success");
	}
	
	@Recover
    public void recover(Throwable throwable) throws Throwable {
        log.info("Service retry attempt over, service no available");
        throw new RuntimeException(throwable);
    }

}
