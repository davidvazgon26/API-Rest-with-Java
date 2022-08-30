package com.linkedin.api.azure;

import java.util.ArrayList;
import java.util.List;

public class SentimentAnalysisResponse {

	private List<TextDocumentScore> documents = new ArrayList<>();

	public List<TextDocumentScore> getDocuments() {
		return documents;
	}

	public void setDocuments(List<TextDocumentScore> documents) {
		this.documents = documents;
	}

	static class TextDocumentScore {

		private String id;

		private double score;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public double getScore() {
			return score;
		}

		public void setScore(double score) {
			this.score = score;
		}

	}

}
