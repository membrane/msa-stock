package com.predic8.workshop.stock;

import com.predic8.workshop.stock.dto.Stock;
import com.predic8.workshop.stock.event.NullAwareBeanUtilsBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@SpringBootApplication
@EnableDiscoveryClient
public class StockApplication {
	@Bean
	public Map<String, Stock> articles() {
		return new ConcurrentHashMap<>(2_000_000);
	}

	@Bean
	public NullAwareBeanUtilsBean beanUtils() {
		return new NullAwareBeanUtilsBean();
	}

	@Value("${spring.sleuth.web.skipPattern}")
	private String skipPattern;

	public static void main(String[] args) {
		SpringApplication.run(StockApplication.class, args);
	}
}