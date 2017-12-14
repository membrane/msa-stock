package com.predic8.workshop.stock.dto;

public class Article {
	// TODO uuid
	private String articleId;
	private long quantity;

	public Article() {
	}

	public String getArticleId() {
		return this.articleId;
	}

	public long getQuantity() {
		return this.quantity;
	}

	public String toString() {
		return "Article(articleId=" + articleId + ", quantity=" + quantity + ")";
	}
}