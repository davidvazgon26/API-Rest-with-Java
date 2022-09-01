package com.linkedin.api.azure;

public class  SentimentAnalysis {

	//Mis 2 propiedades, docuemnt y sentiment

	private TextDocument document;

	private String sentiment;

	// Constructores
	
	public SentimentAnalysis() {  //Constructor por default
		
	}

	public SentimentAnalysis(TextDocument document) {
		this.document = document;
	}  //Constructor con un solo argumento

	public SentimentAnalysis(TextDocument document, String sentiment) { //Este es el constructor que recibe mis argumentos de document y sentiment
		this.document = document;
		this.sentiment = sentiment.toLowerCase().trim();
	}

	//Getters y Setters

	public TextDocument getDocument() {
		return document;
	}

	public void setDocument(TextDocument document) {
		this.document = document;
	}

	public String getSentiment() {
		return sentiment;
	}

	public void setSentiment(String sentiment) {
		this.sentiment = sentiment;
	}

}
