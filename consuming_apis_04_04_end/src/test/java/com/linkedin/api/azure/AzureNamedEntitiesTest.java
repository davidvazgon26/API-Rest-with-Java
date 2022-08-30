package com.linkedin.api.azure;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AzureNamedEntitiesTest {

	@Value("${AZURE_API_KEY}")
	private String azureApiKey;

	private static final String AZURE_ENDPOINT = "https://landon-hotel-feedback.cognitiveservices.azure.com";
	
	private static final String AZURE_ENDPOINT_PATH = "/text/analytics/v3.0/entities/recognition/general";

	private static final String API_KEY_HEADER_NAME = "Ocp-Apim-Subscription-Key";

	private static final String CONTENT_TYPE = "Content-Type";

	private static final String APPLICATION_JSON = "application/json";
	
	private static final String EXAMPLE_JSON  = "{"
			+ "  \"documents\": ["
			+ "    {"
			+ "      \"language\": \"en\","
			+ "      \"id\": \"1\","
			+ "      \"text\": \"The Landon Hotel was found in 1952 London by Arthur Landon after World War II.\""
			+ "    }"
			+ "  ]"
			+ "}";

	@Test
	public void getEntities() throws IOException, InterruptedException {
		
		// 1.  Create a client 
		HttpClient client = HttpClient.newHttpClient();
		
		// 2.  Create the request
		HttpRequest request = HttpRequest.newBuilder()
				.header(CONTENT_TYPE, APPLICATION_JSON)
				.header(API_KEY_HEADER_NAME, azureApiKey)
				.uri(URI.create(AZURE_ENDPOINT + AZURE_ENDPOINT_PATH))
				.POST(BodyPublishers.ofString(EXAMPLE_JSON))
				.build();
		
		// 3.  Send the request and receive response
		HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
		
		// 4.  Work with the response
		assertEquals(200, response.statusCode());
		System.out.println(response.body());
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
