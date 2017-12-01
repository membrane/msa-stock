package com.predic8.workshop.stock.dto;

public class Article {
	private String article;
	private long quantity;

	public Article() {
	}

	public String getArticle() {
		return this.article;
	}

	public long getQuantity() {
		return this.quantity;
	}

	public String toString() {
		return "Article(article=" + article + ", quantity=" + quantity + ")";
	}
}