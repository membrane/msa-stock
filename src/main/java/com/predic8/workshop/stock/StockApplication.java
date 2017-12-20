package com.predic8.workshop.stock;

import com.predic8.workshop.stock.dto.Stock;
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