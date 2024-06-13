package oopproject1.admin;

import java.util.List;

abstract public class KafkaClient {
	private KafkaTopic topic;

	// GETTERS AND SETTERS

	public KafkaClient(KafkaTopic topic) {
		this.topic = topic;
	}

	public KafkaTopic getTopic() {
		return topic;
	}

	// METHODS
	
	abstract <K,V> void sendMessage(K key, V value);

	abstract <K,V> List<KafkaMessage> receiveMessage(int consumerIndex);

}
