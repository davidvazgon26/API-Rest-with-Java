package com.linkedin.api;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.linkedin.api.azure.AzureSentimentService;
import com.linkedin.api.azure.SentimentAnalysis;
import com.linkedin.api.twilio.TwilioClient;
import com.linkedin.api.twitter.StreamResponse;
import com.linkedin.api.twitter.TwitterStreamingService;

import feign.Feign;
import feign.auth.BasicAuthRequestInterceptor;
import feign.form.FormEncoder;

@Profile("!test")
@SpringBootApplication
public class LandonMonitorApplication implements CommandLineRunner {
	
	
	@Value("${TWILIO_SID}")
	private String twilioSid;

	@Value("${TWILIO_AUTH_TOKEN}")
	private String twilioAuthToken;
	
	@Value("${TO_NUMBER}")
	private String toNumber;
	
	private static final String fromNumber = "+18172032143";
	
	private final static String TWILIO_API_DOMAIN = "https://api.twilio.com";

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
	
	@Bean
	public TwilioClient twilioClient() {
		
		BasicAuthRequestInterceptor interceptor = new BasicAuthRequestInterceptor(twilioSid, twilioAuthToken);
		
		return Feign.builder()
				.requestInterceptor(interceptor)
				.encoder(new FormEncoder())
				.target(TwilioClient.class, TWILIO_API_DOMAIN);
					
	}
	
	public static void main(String[] args) {
		SpringApplication.run(LandonMonitorApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		//1.  Subscribe a lambda function to the TwitterStreamingService's stream method
		this.twitterStreamingService.stream().subscribe(tweet -> {

			System.out.println("The tweet says: " + tweet);

			try {

				//2.  Within the lambda, deserialize the json from Twitter into a StreamResponse
				StreamResponse response = this.mapper().readValue(tweet, StreamResponse.class);
				
				
				//3.  Using the AzureSentimentService, send the text to Cognitive Services for Sentiment Analysis
				SentimentAnalysis analysis = this.azureSentimentService
						.requestSentimentAnalysis(response.getData().getText(), "en");

				String message = analysis.getSentiment().equalsIgnoreCase("positive")
						? "The hotel received positive feedback on twitter!"
						: "The hotel received negative feedback on Twitter!";
				
				//4.  Print the result to the console
				System.out.println(message);
				
				//5.  Use the TwilioClient to send the message regarding the feedback received.
				this.twilioClient().sendTextMessage(
						twilioSid, 
						toNumber, 
						fromNumber, 
						message);

				
			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
			}

		});
		
	}

}
