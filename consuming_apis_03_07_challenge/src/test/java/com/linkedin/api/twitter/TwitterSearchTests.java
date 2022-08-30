package com.linkedin.api.twitter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import reactor.core.publisher.Mono;

@SpringBootTest
class TwitterSearchTests {

	@Value("${TWITTER_BEARER_TOKEN}")
	private String bearerToken;
	
	private final static String API_TWITTER_ENDPOINT = "https://api.twitter.com";

	private final static String API_TWITTER_TWEETS_PATH = "/2/tweets/search/recent";
		
	@Test
	void restTemplateTest() {

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + this.bearerToken);

		String uri = UriComponentsBuilder.fromHttpUrl(API_TWITTER_ENDPOINT + API_TWITTER_TWEETS_PATH)
				.queryParam("query", "LinkedIn Learning")
				.build()
				.toUriString();
		
		HttpEntity<?> entity = new HttpEntity<Object>(headers);
		
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
		
		assertEquals(200, response.getStatusCodeValue());
	}

	@Test
	void webClientTest() throws InterruptedException {

		WebClient client = WebClient.create(API_TWITTER_ENDPOINT);
		
		Mono<ResponseEntity<String>> mono = client.get()
				.uri(API_TWITTER_TWEETS_PATH + "?query={query}", "LinkedIn Learning")
				.header("Authorization", "Bearer " + this.bearerToken)
				.retrieve()
				.toEntity(String.class);
		
		mono.subscribe(response -> {
			System.out.println(response.getBody());
			assertEquals(200, response.getStatusCodeValue());			
		});
		
		System.out.println("This should print first because its async");
		Thread.sleep(5000);
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
