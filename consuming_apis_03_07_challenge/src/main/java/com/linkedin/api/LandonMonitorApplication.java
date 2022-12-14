package com.linkedin.api;

import com.linkedin.api.azure.SentimentAnalysis;
import com.linkedin.api.twitter.StreamResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.linkedin.api.azure.AzureSentimentService;
import com.linkedin.api.twitter.TwitterStreamingService;

import java.io.IOException;

@Profile("!test")
@SpringBootApplication
public class LandonMonitorApplication implements CommandLineRunner {
	
	@Autowired
	private TwitterStreamingService twitterStreamingService;  //Invoco al servicio conectado a la API de Tweeter

	@Autowired
	private AzureSentimentService azureSentimentService; // Invoco al servicio conectado a Azure Cognitive

	@Bean
	public ObjectMapper mapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return mapper;
	}
	public static void main(String[] args) {
		SpringApplication.run(LandonMonitorApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		//1.  Subscribe a lambda function to the TwitterStreamingService's stream method
		this.twitterStreamingService.stream().subscribe(tweet -> {

			System.out.println("The tweet says: " + tweet);

			try {
				//2.  Within the lambda, deserialize the json from Twitter into a StreamResponse
				StreamResponse response = this.mapper().readValue(tweet, StreamResponse.class);
				//convierte json en objeto java

				// Jackson nos ayuda a llevar objetos java a json y json a objetos java como en el ejemplo de arriba.

				//3.  Using the AzureSentimentService, send the text to Cognitive Services for Sentiment Analysis
				SentimentAnalysis analysis = this.azureSentimentService
						.requestSentimentAnalysis(response.getData().getText(), "en");
				//Aqui le estamos pasando los datos del tweet al analizador de sentimiento

						//Aqui recibo el analisis y con un if mando el mensaje si fue positivo o negativo
				String message = analysis.getSentiment().equalsIgnoreCase("positive")
						? "The hotel received positive feedback on twitter!"
						: "The hotel received negative feedback on Twitter!";

				//4.  Print the result to the console

				System.out.println(message);

			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
			}

		});

	}

}
