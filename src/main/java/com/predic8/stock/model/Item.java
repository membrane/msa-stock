package com.predic8.stock.model;

public class Item {

	private String articleId;
	private long quantity;

	public Item() {
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
		return "Item(articleId=" + articleId + ", quantity=" + quantity + ")";
	}
}