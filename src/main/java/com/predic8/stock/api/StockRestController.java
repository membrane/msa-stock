package com.predic8.stock.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.predic8.stock.model.Stock;
import com.predic8.stock.event.Operation;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

@RequestMapping("/stocks")
@RestController
public class StockRestController {
	
	private final Map<String, Stock> stocks;
	private ObjectMapper mapper;
	private KafkaTemplate<String, Operation> kafka;

	public StockRestController(Map<String, Stock> articles, ObjectMapper mapper, KafkaTemplate<String, Operation> kafka) {
		this.stocks = articles;
		this.mapper = mapper;
		this.kafka = kafka;
	}

	@GetMapping
	public Collection<Stock> index() {
		return stocks.values();
	}


	@GetMapping("/{id}")
	public Stock get(@PathVariable String id) {
		return stocks.get(id);
	}


	@PutMapping("/{id}")
	public Stock put( @PathVariable String id, @RequestBody Stock stock) throws Exception {

		Stock old = stocks.get(id);

		old.setQuantity(stock.getQuantity());

		Operation op = new Operation("article","upsert",mapper.valueToTree(old));

		op.logSend();

		kafka.send("shop", op).get(200, MILLISECONDS);

		return old;
	}

	@GetMapping("/count")
	public long count() {
		return stocks.size();
	}
}