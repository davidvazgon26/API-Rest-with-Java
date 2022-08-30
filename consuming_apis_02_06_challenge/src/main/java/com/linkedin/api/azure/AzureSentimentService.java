package com.linkedin.api.azure;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class AzureSentimentService {

	@Value("${AZURE_API_KEY}")
	private String azureApiKey;
		
	@Autowired
	private ObjectMapper mapper;
	
	private static final String AZURE_ENDPOINT = "https://landon-hotel-feedback.cognitiveservices.azure.com";

	private static final String API_KEY_HEADER_NAME = "Ocp-Apim-Subscription-Key";

	private static final String CONTENT_TYPE = "Content-Type";

	private static final String APPLICATION_JSON = "application/json";

	public SentimentAnalysis requestSentimentAnalysis(String text, String language) throws IOException, InterruptedException {

		//Hint:  Find the path for the sentiment endpoint in the Text Analytics API Reference
		
		//Hint:  Use the TextAnalyticsRequest and TextDocument objects to build the request
		
		//Hint:  Use Jackson's ObjectMapper and JsonNode to handle the response
				
		return null;

	}
}
