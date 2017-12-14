package com.predic8.workshop.stock.dto;

import java.util.List;

public class Basket {

	private String uuid;
	private List<Article> items;

	public Basket() {
	}

	public String getUuid() {
		return this.uuid;
	}

	public List<Article> getItems() {
		return this.items;
	}

	public String toString() {
		return "Basket(uuid=" + uuid + " , items=" + items + ")";
	}
}