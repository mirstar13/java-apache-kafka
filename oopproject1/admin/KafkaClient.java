package oopproject1.admin;

import java.util.List;

abstract public class KafkaClient {
	private KafkaTopic topic;

	public KafkaClient(KafkaTopic topic) {
		this.topic = topic;
	}

	public KafkaTopic getTopic() {
		return topic;
	}

	private void setTopic(KafkaTopic topic) {
		this.topic = topic;
	}

	// METHODS

	abstract <K,V> void sendMessage(K key, V value);

	abstract List<KafkaMessage> receiveMessage(int consumerIndex);

}
