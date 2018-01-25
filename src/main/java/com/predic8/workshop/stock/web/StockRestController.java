package com.predic8.workshop.stock.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.predic8.workshop.stock.dto.Article;
import com.predic8.workshop.stock.dto.Stock;
import com.predic8.workshop.stock.error.NotFoundException;
import com.predic8.workshop.stock.event.Operation;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@RequestMapping("/stocks")
@RestController
public class StockRestController {
	private final Map<String, Stock> articles;

	private ObjectMapper mapper;

	private KafkaTemplate<String, Operation> kafka;

	public StockRestController(Map<String, Stock> articles, ObjectMapper mapper, KafkaTemplate kafka) {
		this.articles = articles;
		this.mapper = mapper;
		this.kafka = kafka;
	}

	@GetMapping
	public List<Stock> index() {
		return articles.entrySet().stream().map(Entry::getValue).collect(toList());
	}

	@GetMapping("/{uuid}")
	public Stock show(@PathVariable String uuid) {
		Stock stock = articles.get(uuid);

		if (stock == null) {
			throw new NotFoundException();
		}

		return stock;
	}

	@PatchMapping("/{uuid}")
	public void setStock(@PathVariable String uuid, @RequestBody Stock neu) throws Exception {
		Stock stock = articles.get(uuid);

		if (stock == null) {
			throw new NotFoundException();
		}

		Stock updateBo = new Stock();
		updateBo.setQuantity(neu.getQuantity());
		updateBo.setUuid(uuid);

		Operation op = new Operation("article", "update", mapper.valueToTree(updateBo));

		op.logSend();

		kafka.send("shop", op).get(100, TimeUnit.MILLISECONDS);


	}

	@GetMapping("/count")
	public long count() {
		return articles.size();
	}
}