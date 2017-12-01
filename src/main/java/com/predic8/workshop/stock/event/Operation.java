package com.predic8.workshop.stock.event;

import com.fasterxml.jackson.databind.JsonNode;

public class Operation {
	private String action;
	private String type;
	private JsonNode object;

	public Operation(String action, String type, JsonNode object) {
		this.action = action;
		this.type = type;
		this.object = object;
	}

	public String getAction() {
		return this.action;
	}

	public String getType() {
		return this.type;
	}

	public JsonNode getObject() {
		return this.object;
	}

	public String toString() {
		return "Operation(action=" + action + ", type=" + type + ", object=" + object + ")";
	}
}