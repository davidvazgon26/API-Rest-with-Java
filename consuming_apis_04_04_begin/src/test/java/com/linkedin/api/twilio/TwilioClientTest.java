package com.linkedin.api.twilio;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

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
	
	
	}
	
}
