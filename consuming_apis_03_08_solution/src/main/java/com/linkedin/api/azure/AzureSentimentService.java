package com.linkedin.api.azure;

import java.io.IOException;
import java.net.ProxySelector;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
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

		TextDocument document = new TextDocument("1",text,language);
		TextAnalyticsRequest requestBody = new TextAnalyticsRequest();
		requestBody.getDocuments().add(document);
		
		String endpoint = AZURE_ENDPOINT + "/text/analytics/v3.0/sentiment";

		HttpClient client = HttpClient.newBuilder()
				.version(Version.HTTP_2)
				.proxy(ProxySelector.getDefault())
				.connectTimeout(Duration.ofSeconds(5))
				.build();
		
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(endpoint))
				.header(API_KEY_HEADER_NAME, this.azureApiKey)
				.header(CONTENT_TYPE, APPLICATION_JSON)
				.POST(BodyPublishers.ofString(mapper.writeValueAsString(requestBody)))
				.timeout(Duration.ofSeconds(5))
				.build();
		
		HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
		
		if(response.statusCode() != 200) {
			System.out.println(response.body());
			throw new RuntimeException("An issue occurred making the API call");
		}
	
		String sentimentValue = this.mapper
				.readValue(response.body(), JsonNode.class)
				.get("documents")
				.get(0)
				.get("sentiment")
				.asText();

		SentimentAnalysis analysis = new SentimentAnalysis(document, sentimentValue);
		
		return analysis;

	}
}
