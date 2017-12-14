package com.predic8.workshop.stock;

import com.predic8.workshop.stock.dto.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.consul.discovery.ConsulDiscoveryClient;
import org.springframework.cloud.sleuth.metric.SpanMetricReporter;
import org.springframework.cloud.sleuth.zipkin.HttpZipkinSpanReporter;
import org.springframework.cloud.sleuth.zipkin.ZipkinProperties;
import org.springframework.cloud.sleuth.zipkin.ZipkinRestTemplateCustomizer;
import org.springframework.cloud.sleuth.zipkin.ZipkinSpanReporter;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import zipkin.Span;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@SpringBootApplication
@EnableDiscoveryClient
public class StockApplication {
	@Bean
	public Map<String, Stock> articles() {
		return new ConcurrentHashMap<>();
	}

//	@Autowired
//	public DiscoveryClient discovery;

	@Value("${spring.sleuth.web.skipPattern}")
	private String skipPattern;

	public static void main(String[] args) {
		SpringApplication.run(StockApplication.class, args);
	}

//	@Bean
//	@ConditionalOnMissingBean
//	public ZipkinSpanReporter reporter(SpanMetricReporter reporter, ZipkinProperties zipkin,
//									   ZipkinRestTemplateCustomizer customizer) {
//		RestTemplate rest = new RestTemplate();
//		customizer.customize(rest);
//
//		List<ServiceInstance> instances = discovery.getInstances("zipkin-service");
//
//		if (instances.size() == 0) {
//			return null;
//		}
//
//		ServiceInstance instance = instances.get(0);
//
//		String uri = instance.getUri().toString();
//
//		System.out.println("instance.getUri().toString() = " + uri);
//
//		return new HttpZipkinSpanReporter(rest, uri, zipkin.getFlushInterval(), reporter);
//	}
}