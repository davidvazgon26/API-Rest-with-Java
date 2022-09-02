package com.linkedin.api.twilio;

import com.fasterxml.jackson.databind.introspect.TypeResolutionContext;
import feign.Feign;
import feign.auth.BasicAuthRequestInterceptor;
import feign.form.FormEncoder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
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
	
	private static final String fromNumber = "+12182316543";
	
	private final static String TWILIO_API_DOMAIN = "https://api.twilio.com";

	@Test
	void twilioVoiceMessageTest() {

		BasicAuthRequestInterceptor interceptor = new BasicAuthRequestInterceptor(twilioSid, twilioAuthToken);

		TwilioClient client = Feign.builder()
				.requestInterceptor(interceptor)
				.encoder(new FormEncoder())
				.target(TwilioClient.class, TWILIO_API_DOMAIN);

		client.sendVoiceMessage("AC319f934bfbfbcb4898581272ac2f6f41","<Response><Say>Hello from LinkedIn Learning!</Say></Response>","+528718567920","+12182316543");
		//client.sendVoiceMessage(twilioSid,"<Response><Say>Hello from LinkedIn Learning!</Say></Response>",toNumber,fromNumber);



	}
	
}
