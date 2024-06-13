package oopproject1.admin;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class KafkaProducer extends KafkaClient {
	private int messageCount;

	public KafkaProducer(KafkaTopic topic) {
		super(topic);

		this.messageCount = 0;
	}
	
	// METHODS

	@Override
	public <K,V> void sendMessage(K key, V value) {
		if (!this.getTopic().isKeyed()) {
			KafkaNonKeyedMessage<V> kafkaMessage = new KafkaNonKeyedMessage<V>(value);
			
			this.getTopic().getPartitions().get(messageCount % this.getTopic().getPartitions().size()).getMessageQueue().add(kafkaMessage);
			messageCount++;

			System.out.println("Sent message " + value);
			return;
		}

		KafkaKeyedMessage<K,V> kafkaMessage = new KafkaKeyedMessage<K,V>(key, value);

		String strKey = (String) key;
		this.getTopic().getPartitions().get(hash(strKey, this.getTopic().getPartitions().size())).getMessageQueue().add(kafkaMessage);

		System.out.println("Sent message with key " + key + " and value\n" + value);
	}

	@Override
	public List<KafkaMessage> receiveMessage(int consumerIndex) {
		String message = "Error. Producers don't receive messages";
		List<KafkaMessage> messages = new ArrayList<>();
		messages.add(new KafkaNonKeyedMessage<String>(message));
		return messages;
	}

	public static int hash(String key, int numPartitions) {
		int sum = 0;
		for (int i = 0; i < key.length(); i++)
			sum += (int) key.charAt(i); // Add ASCII value of each character
		return sum % numPartitions; // Take modulo numPartitions
	}
}