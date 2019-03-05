package com.predic8.stock.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.predic8.stock.model.Stock;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ShopListener {

	private final ObjectMapper mapper;
	private final Map<String, Stock> stocks;
	private final NullAwareBeanUtilsBean beanUtils;

	public ShopListener(ObjectMapper mapper, Map<String, Stock> stocks, NullAwareBeanUtilsBean beanUtils) {
		this.mapper = mapper;
		this.stocks = stocks;
		this.beanUtils = beanUtils;
	}

	@KafkaListener(topics = "shop")
	public void listen(Operation op) throws Exception {
		System.out.println("op = " + op);

		if (!op.getBo().equals("article"))
			return;

		op.logReceive();

		Stock stock = mapper.treeToValue( op.getObject(), Stock.class);

		switch (op.getAction()) {
			case "upsert":
				stocks.put(stock.getUuid(), stock);
				break;
			case "remove":
				stocks.remove(stock.getUuid());
				break;
		}
	}
}