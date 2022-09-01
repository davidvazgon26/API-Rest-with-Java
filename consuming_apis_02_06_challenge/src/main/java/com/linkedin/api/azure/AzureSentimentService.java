package com.linkedin.api.azure;

import java.io.IOException;
import java.net.ProxySelector;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import com.fasterxml.jackson.databind.JsonNode;
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
	
	private static final String AZURE_ENDPOINT = "https://landon-hotel-feedback-davg.cognitiveservices.azure.com";

	private static final String API_KEY_HEADER_NAME = "Ocp-Apim-Subscription-Key";

	private static final String CONTENT_TYPE = "Content-Type";

	private static final String APPLICATION_JSON = "application/json";

	public SentimentAnalysis requestSentimentAnalysis(String text, String language) throws IOException, InterruptedException {

		//Realizar llamada a Azure, recuperar la respuesta, crear el objeto de analisis de sentimiento a retornar

		//Creamos 2 objetos un TextDocument y un TextAnalyticsRequest que almacenara la informacion que pasaremos en la solicitud HTTP
		TextDocument document = new TextDocument("1", text, language);
		TextAnalyticsRequest requestBody = new TextAnalyticsRequest();
		requestBody.getDocuments().add(document);

		//Creamos en una variable de tipo string la ruta que utilizaremos de Cognitive de Azure.
		String endpoint = AZURE_ENDPOINT + "text/analitycs/v3.0/sentiment";

		//Creando mi cliente
		HttpClient client = HttpClient.newBuilder()
				.version(Version.HTTP_2)
				.proxy(ProxySelector.getDefault())
				.connectTimeout(Duration.ofSeconds(5))
				.build();

		//Haciendo la solicitud.
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(endpoint))
				.header(API_KEY_HEADER_NAME, this.azureApiKey)
				.header(CONTENT_TYPE, APPLICATION_JSON)
				.POST(BodyPublishers.ofString(mapper.writeValueAsString(requestBody))) //Con Jackson ObjectMapper (mapper) creo la cadena tipo Json y le paso los datos de mi variable requestBody
				.timeout(Duration.ofSeconds(5))
				.build();

		//Tengo mi cliente, tengo mi solicitud, ya estoy listo para enviarlo al servicio de Cognitive Azure en una llamada sincrona
		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

		//Compruebo si recibi una respuesta exitosa o algo fallo para capturar el error

		if(response.statusCode() != 200){
			System.out.println(response.body());
			throw new RuntimeException("Algo salio mal en el llamado a la API");
		}

		// Si no ocurrio ningun error continuo analisando el sentimiento recibido

		String sentimentValue = this.mapper
				.readValue(response.body(), JsonNode.class) // Para deserializar la cadena Json
				.get("documents")
				.get(0)
				.get("sentiment")
				.asText();

		//Tomo los valores obtenidos para pasarlos a nuestra clase de SentimentAnalysis y crear una instancia (separo procesos) para poder retornar el valor final
		SentimentAnalysis analysis = new SentimentAnalysis(document, sentimentValue);

		//Hint:  Find the path for the sentiment endpoint in the Text Analytics API Reference
		
		//Hint:  Use the TextAnalyticsRequest and TextDocument objects to build the request
		
		//Hint:  Use Jackson's ObjectMapper and JsonNode to handle the response
				
		return analysis;

	}
}
