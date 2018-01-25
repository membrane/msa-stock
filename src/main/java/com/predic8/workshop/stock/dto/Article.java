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

	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}

	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}

	public String toString() {
		return "Article(articleId=" + articleId + ", quantity=" + quantity + ")";
	}
}