package com.predic8.workshop.stock.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Stock {
	private String uuid;
    private long quantity;
}