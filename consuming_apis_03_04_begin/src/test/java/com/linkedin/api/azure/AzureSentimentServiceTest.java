package com.linkedin.api.azure;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AzureSentimentServiceTest {

	@Autowired
	private AzureSentimentService sentimentService;
	
	@Test
	void testPositiveSentiment() throws IOException, InterruptedException {
		
		SentimentAnalysis analysis = this.sentimentService.requestSentimentAnalysis("I love wild animals and their habitat!", "en");
		assertNotNull(analysis);
		assertEquals("positive", analysis.getSentiment());
	}

	@Test
	void testNegativeSentiment() throws IOException, InterruptedException {
		
		SentimentAnalysis analysis = this.sentimentService.requestSentimentAnalysis("Pollution is horrible, we need to stop it!", "en");
		assertNotNull(analysis);
		assertEquals("negative", analysis.getSentiment());
	}
}
