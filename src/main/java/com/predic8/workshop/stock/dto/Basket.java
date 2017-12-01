package com.predic8.workshop.stock.dto;

import java.util.List;

public class Basket {
	private String uuid;
	private String customer;
	private List<Article> items;

	public Basket() {
	}

	public String getUuid() {
		return this.uuid;
	}

	public String getCustomer() {
		return this.customer;
	}

	public List<Article> getItems() {
		return this.items;
	}

	public String toString() {
		return "Basket(uuid=" + uuid + ", customer=" + customer + ", items=" + items + ")";
	}
}