package com.predic8.stock;

import com.predic8.stock.model.Stock;
import com.predic8.stock.event.NullAwareBeanUtilsBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.springframework.boot.SpringApplication.run;

@EnableDiscoveryClient
@SpringBootApplication
public class StockApplication {

	@Bean
	public Map<String, Stock> stocks() {
		return new ConcurrentHashMap<>();
	}

	@Bean
	public NullAwareBeanUtilsBean beanUtils() {
		return new NullAwareBeanUtilsBean();
	}

	public static void main(String[] args) {
		run(StockApplication.class, args);
	}
}