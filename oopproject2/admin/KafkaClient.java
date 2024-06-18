package oopproject2.admin;

import java.util.List;
import java.util.UUID;

abstract public class KafkaClient {
	private KafkaTopic topic;
	private UUID id;

	// GETTERS AND SETTERS

	public KafkaClient(KafkaTopic topic) {
		this.topic = topic;
		this.id = UUID.randomUUID();
	}

	public KafkaTopic getTopic() {
		return topic;
	}

	public UUID getId() {
		return id;
	}

	// METHODS

	abstract <K, V> void sendMessage(K key, V value);

	abstract <K, V> List<KafkaMessage> receiveMessage(int consumerIndex);

}
