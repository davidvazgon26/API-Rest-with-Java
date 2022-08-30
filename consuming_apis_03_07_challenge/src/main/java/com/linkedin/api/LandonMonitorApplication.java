package com.linkedin.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.linkedin.api.azure.AzureSentimentService;
import com.linkedin.api.twitter.TwitterStreamingService;

@Profile("!test")
@SpringBootApplication
public class LandonMonitorApplication implements CommandLineRunner {
	
	@Autowired
	private TwitterStreamingService twitterStreamingService;

	@Autowired
	private AzureSentimentService azureSentimentService;
	
	@Bean
	public ObjectMapper mapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return mapper;
	}
	
	public static void main(String[] args) {
		SpringApplication.run(LandonMonitorApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		//1.  Subscribe a lambda function to the TwitterStreamingService's stream method
		
		//2.  Within the lambda, deserialize the json from Twitter into a StreamResponse
		
		//3.  Using the AzureSentimentService, send the text to Cognitive Services for Sentiment Analysis
		
		//4.  Print the result to the console

	}

}
