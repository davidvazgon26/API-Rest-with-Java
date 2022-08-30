package com.linkedin.api;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class PreJava11Example {

	@Value("${AZURE_API_KEY}")
	private String azureApiKey;
	
	private String azureEndpoint = "https://landon-hotel-feedback.cognitiveservices.azure.com";
	
	private static final String API_KEY_HEADER_NAME = "Ocp-Apim-Subscription-Key";

	@Test
	void callTextAnalyticsTest() throws IOException {
		
		URL url = new URL(String.format("%s%s", this.azureEndpoint, "/text/analytics/v3.0/entities/recognition/general"));
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", "application/json");
		connection.setRequestProperty(API_KEY_HEADER_NAME, azureApiKey);
		connection.setDoOutput(true);
		
		connection.getOutputStream().write("{\"documents\":[{\"id\":\"1\",\"text\":\"The Landon Hotel was found in 1952 London by Arthur Landon after World War II.\"}]}}".getBytes());
		connection.getOutputStream().close();
		
		InputStream iStream = connection.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(iStream));
		StringBuilder responseText = new StringBuilder();
		
		String line;
		
		while ((line = reader.readLine()) != null) {
			responseText.append(line);
			responseText.append("\r");
		}

		reader.close();
	
		assertNotNull(responseText.toString());
		System.out.println(responseText.toString());
	}

}
