package com.predic8.workshop.stock.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.predic8.workshop.stock.dto.Basket;
import com.predic8.workshop.stock.dto.Stock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class ShopListener {
	private final KafkaTemplate<String, Operation> kafkaTemplate;
	private final ObjectMapper objectMapper;
	private final Map<String, Stock> articles;

	@KafkaListener(id = "stock-listener",
			topicPartitions =
					{ @TopicPartition(topic = "shop",
							partitionOffsets = @PartitionOffset(partition = "0", initialOffset = "0"))})
	public void listen(Operation operation) throws IOException {
		switch (operation.getType()) {
			case "article":
				handleArticle(operation.getAction(), objectMapper.convertValue(operation.getObject(), Stock.class));
				return;
			case "basket":
				handleBasket(objectMapper.convertValue(operation.getObject(), Basket.class));
				return;
			default:
				log.info("Unknown type: {}", operation.getType());
		}
	}

	private void handleArticle(String action, Stock stock) {
		switch (action) {
			case "create":
			case "update":
				articles.put(stock.getUuid(), stock);
				break;
			default:
				log.info("Unknown action: {}", action);
		}
	}

	private void handleBasket(Basket basket) {
		basket
			.getItems()
			.stream()
			.map(item ->
				new Operation("update", "article", objectMapper.valueToTree(
					new Stock(item.getArticle(), articles.get(item.getArticle()).getQuantity() - item.getQuantity())))
			)
			.forEach(operation -> kafkaTemplate.send("shop", operation));
	}
}