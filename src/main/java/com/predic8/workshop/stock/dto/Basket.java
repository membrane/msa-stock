package com.predic8.workshop.stock.dto;

import lombok.Data;

import java.util.List;

@Data
public class Basket {
	private String uuid;
	private String customer;
	private List<Article> items;
}