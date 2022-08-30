package com.linkedin.api.twitter;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootTest
class TwitterStreamTest {

	@Value("${TWITTER_BEARER_TOKEN}")
	private String bearerToken;

	private final static String API_TWITTER_ENDPOINT = "https://api.twitter.com";

	private final static String API_TWITTER_STREAM_PATH = "/2/tweets/search/stream";

	private final static String API_TWITTER_STREAM_RULES_PATH = "/2/tweets/search/stream/rules";

	@Autowired
	private WebClient.Builder builder;
	
	@Test
	void webClientTest() throws InterruptedException, IOException {

		WebClient client = builder
				.baseUrl(API_TWITTER_ENDPOINT)
				.defaultHeaders(headers -> headers.setBearerAuth(bearerToken))
				.build();
		
		StreamRuleRequest ruleRequest = new StreamRuleRequest();
		ruleRequest.addRule("LinkedIn", "LinkedIn Tag");
		
		client.post()
			.uri(API_TWITTER_STREAM_RULES_PATH)
			.bodyValue(ruleRequest)
			.retrieve()
			.toBodilessEntity()
			.subscribe(response -> {
				
				client.get()
				.uri(API_TWITTER_STREAM_PATH)
				.retrieve()
				.bodyToFlux(String.class)
				.filter(body -> !body.isBlank())
				.subscribe(json -> {
					
					System.out.println(json);
					
				});
				
			});
		
		System.in.read();
		
	}
	
	
	
	
	
	
	
	
	
	
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
