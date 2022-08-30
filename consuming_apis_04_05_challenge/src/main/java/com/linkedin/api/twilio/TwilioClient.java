package com.linkedin.api.twilio;

import feign.Headers;
import feign.Param;
import feign.RequestLine;

public interface TwilioClient {

	//1. Add a method that calls Twilio's SMS API to send a text message
	
	@Headers("Content-Type: application/x-www-form-urlencoded")
	@RequestLine("POST /2010-04-01/Accounts/{AccountSid}/Calls.json")
	public void sendVoiceMessage(@Param("AccountSid") String accountSid,
			@Param("To") String to,
			@Param("From") String from,
			@Param("Twiml") String twiml);
}
