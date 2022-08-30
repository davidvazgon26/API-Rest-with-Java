package com.linkedin.api.twitter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;

@Service
public class TwitterStreamingService {

	@Value("${TWITTER_BEARER_TOKEN}")
	private String bearerToken;

	private final static String API_TWITTER_ENDPOINT = "https://api.twitter.com";

	private final static String API_TWITTER_STREAM_PATH = "/2/tweets/search/stream";

	@Autowired
	private WebClient.Builder builder;

	public Flux<String> stream() {

		//1.  Use the WebClient to connect to the stream and return the Flux from the method
		WebClient client = this.builder
				.baseUrl(API_TWITTER_ENDPOINT)
				.defaultHeaders(headers -> headers.setBearerAuth(this.bearerToken))
				.build();
		
		return client.get()
				.uri(API_TWITTER_STREAM_PATH)
				.retrieve()
				.bodyToFlux(String.class)
				.filter(tweet -> !tweet.isBlank());

		//2.  Using Postman, delete the client's existing rules and create a new rule for Landon Hotel
		
	}
}
