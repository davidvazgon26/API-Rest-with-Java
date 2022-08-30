package com.linkedin.api.twilio;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import feign.Feign;
import feign.auth.BasicAuthRequestInterceptor;
import feign.form.FormEncoder;

@ActiveProfiles("test")
@SpringBootTest
class TwilioClientTest {

	@Value("${TWILIO_SID}")
	private String twilioSid;

	@Value("${TWILIO_AUTH_TOKEN}")
	private String twilioAuthToken;
	
	@Value("${TO_NUMBER}")
	private String toNumber;
	
	private static final String fromNumber = "+18172032143";
	
	private final static String TWILIO_API_DOMAIN = "https://api.twilio.com";

	@Test
	void twilioVoiceMessageTest() {
	
		BasicAuthRequestInterceptor interceptor = new BasicAuthRequestInterceptor(twilioSid, twilioAuthToken);
		
		TwilioClient client = Feign.builder()
				.requestInterceptor(interceptor)
				.encoder(new FormEncoder())
				.target(TwilioClient.class, TWILIO_API_DOMAIN);
		
		client.sendVoiceMessage(twilioSid, toNumber, fromNumber, "<Response><Say>Hello from LinkedIn Learning!</Say></Response>");
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
	
}
