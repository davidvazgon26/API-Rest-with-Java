package com.linkedin.api.twilio;

import com.linkedin.api.azure.SentimentAnalysisResponse;
import com.linkedin.api.azure.TextAnalyticsRequest;

import feign.Headers;
import feign.Param;
import feign.RequestLine;

public interface AzureSentimentClient {

	@RequestLine("POST /text/analytics/v3.0/sentiment")
	@Headers({"Ocp-Apim-Subscription-Key: {apiKey}","Content-Type: application/json"})
	public SentimentAnalysisResponse analyze(@Param("apiKey") String apiKey, TextAnalyticsRequest request);
	
}
