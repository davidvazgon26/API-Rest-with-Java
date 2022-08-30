package com.linkedin.api.twilio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.linkedin.api.azure.SentimentAnalysisResponse;
import com.linkedin.api.azure.TextAnalyticsRequest;
import com.linkedin.api.azure.TextDocument;

import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;

@ActiveProfiles("test")
@SpringBootTest
class AzureSentimentClientTest {

	@Value("${AZURE_API_KEY}")
	private String azureApiKey;

	private static final String AZURE_ENDPOINT = "https://landon-hotel-feedback.cognitiveservices.azure.com";

	@Test
	void testFeignPositiveSentiment() throws IOException, InterruptedException {
		
		TextDocument document = new TextDocument("1","I love the Landon Hotel!", "en");
		TextAnalyticsRequest requestBody = new TextAnalyticsRequest();
		requestBody.getDocuments().add(document);
		
		SentimentAnalysisResponse analysis = null;
		
		assertNotNull(analysis);
		assertEquals("positive", analysis.getDocuments().get(0).getSentiment());
	}

}
