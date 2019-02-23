package com.predic8.stock.model;

import java.util.List;

public class Basket {

	private String uuid;
	private List<Item> items;

	public Basket() {
	}

	public String getUuid() {
		return this.uuid;
	}

	public List<Item> getItems() {
		return this.items;
	}

	public String toString() {
		return "Basket(uuid=" + uuid + " , items=" + items + ")";
	}
}