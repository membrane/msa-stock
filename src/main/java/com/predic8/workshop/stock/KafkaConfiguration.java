package com.predic8.workshop.stock;


import com.predic8.workshop.stock.event.Operation;
import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.Collection;

@EnableKafka
@Configuration
public class KafkaConfiguration {
	@Bean
	public ConsumerFactory<String, Operation> consumerFactory(KafkaProperties kafkaProperties) {
		return new DefaultKafkaConsumerFactory<>(kafkaProperties.buildConsumerProperties(), new StringDeserializer(), new JsonDeserializer<>(Operation.class));
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, Operation> kafkaListenerContainerFactory(ConsumerFactory<String, Operation> consumerFactory) {
		ConcurrentKafkaListenerContainerFactory<String, Operation> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory);

		factory.getContainerProperties().setConsumerRebalanceListener(new ConsumerRebalanceListener() {
			@Override
			public void onPartitionsRevoked(Collection<TopicPartition> collection) {

			}

			@Override
			public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
				System.out.print("Assigned to: ");

				for (TopicPartition partition : partitions) {
					System.out.print(partition + " ");
				}
			}
		});

		return factory;
	}
}