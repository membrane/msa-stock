package com.predic8.workshop.stock.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.predic8.workshop.stock.dto.Basket;
import com.predic8.workshop.stock.dto.Stock;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

@Service
public class ShopListener {

	private Logger log = Logger.getLogger(String.valueOf(ShopListener.class));

	private final KafkaTemplate<String, Operation> kafka;
	private final ObjectMapper mapper;
	private final Map<String, Stock> articles;

	public ShopListener(KafkaTemplate<String, Operation> kafka, ObjectMapper mapper, Map<String, Stock> articles) {
		this.kafka = kafka;
		this.mapper = mapper;
		this.articles = articles;
	}

	@KafkaListener(id = "stock-listener",
		topicPartitions =
			{@TopicPartition(topic = "shop",
				partitionOffsets = @PartitionOffset(partition = "0", initialOffset = "0"))})
	public void listen(Operation op) throws IOException {

		op.logReceive();

		switch (op.getBo()) {
			case "article":
				handleArticle(op.getAction(), mapper.convertValue(op.getObject(), Stock.class));
				return;
			case "basket":
				handleBasket(mapper.convertValue(op.getObject(), Basket.class));
		}
	}

	private void handleArticle(String action, Stock stock) {
		switch (action) {
			case "create":
			case "update":
				articles.put(stock.getUuid(), stock);
				break;
			case "delete":
				articles.remove(stock.getUuid());
		}
	}

	private void handleBasket(Basket basket) {
		basket
			.getItems()
			.stream()
			.map(item ->
				new Operation("article", "update", mapper.valueToTree(
					new Stock(item.getArticleId(), articles.get(item.getArticleId()).getQuantity() - item.getQuantity())))
			)
			.forEach(op -> {
				try {
					op.logSend();

					kafka.send("shop", op).get(100, TimeUnit.MILLISECONDS);
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
	}
}