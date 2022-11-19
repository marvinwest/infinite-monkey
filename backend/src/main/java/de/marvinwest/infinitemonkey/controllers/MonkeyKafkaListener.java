package de.marvinwest.infinitemonkey.controllers;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import de.marvinwest.infinitemonkey.Constants;

@Component
public class MonkeyKafkaListener {

	@Autowired
	private SimpMessagingTemplate webSocket;
	
	@KafkaListener(topics = Constants.MONKEY_TOPIC)
	public void process(ConsumerRecord<String, String> consumer, @Payload String content) {
		this.webSocket.convertAndSend(Constants.WEBSOCKET_DESTINATION, content);
	}
	
}
