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
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class ShopListener {
	private final KafkaTemplate<String, Operation> kafka;
	private final ObjectMapper mapper;
	private final Map<String, Stock> articles;
	private final NullAwareBeanUtilsBean nullAwareUtils;

	public ShopListener(KafkaTemplate<String, Operation> kafka, ObjectMapper mapper, Map<String, Stock> articles, NullAwareBeanUtilsBean nullAwareUtils) {
		this.kafka = kafka;
		this.mapper = mapper;
		this.articles = articles;
		this.nullAwareUtils = nullAwareUtils;
	}

	@KafkaListener(id = "stock-listener",
		topicPartitions =
			{@TopicPartition(topic = "shop",
				partitionOffsets = @PartitionOffset(partition = "0", initialOffset = "0"))})
	public void listen(Operation op) throws Exception {
		op.logReceive();

		switch (op.getBo()) {
			case "article":
				handleArticle(op.getAction(), mapper.convertValue(op.getObject(), Stock.class));
				return;
			case "basket":
				handleBasket(mapper.convertValue(op.getObject(), Basket.class));
		}
	}

	private void handleArticle(String action, Stock stock) throws Exception {
		switch (action) {
			case "create":
				articles.put(stock.getUuid(), stock);
				break;
			case "update":
				Stock old = articles.get(stock.getUuid());

				//  dest / src
				nullAwareUtils.copyProperties(old, stock);

				articles.put(stock.getUuid(), old);

				break;
			case "delete":
				articles.remove(stock.getUuid());
				break;
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