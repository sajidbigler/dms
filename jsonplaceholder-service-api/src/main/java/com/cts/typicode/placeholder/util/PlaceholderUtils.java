package com.cts.typicode.placeholder.util;

import java.net.URI;
import java.util.Map;

import org.springframework.web.util.UriComponentsBuilder;

public class PlaceholderUtils {

	public static URI buildURIWithParams(String url, Map<String, String> params) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromUri(URI.create(url));
		params.forEach((k, v) -> builder.queryParam(k, v));
		return builder.build().toUri();
	}

}
