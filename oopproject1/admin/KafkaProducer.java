package oopproject1.admin;

import java.util.ArrayList;
import java.util.List;

public class KafkaProducer extends KafkaClient {
	public KafkaProducer(KafkaTopic topic) {
		super(topic);
	}
	
	// METHODS

	@Override
	public <K,V> void sendMessage(K key, V value) {
		if (!this.getTopic().isKeyed()) {
			KafkaNonKeyedMessage<V> kafkaMessage = new KafkaNonKeyedMessage<V>(value);
			int messageCount = this.getTopic().getMessageCount();

			int partionIndex = messageCount % this.getTopic().getPartitions().size();
			KafkaPartition currePartition = this.getTopic().getPartitions().get(partionIndex);

			currePartition.getMessageQueue().add(kafkaMessage);
			this.getTopic().setMessageCount(++messageCount);
			
			currePartition.updateReplicaState(kafkaMessage);

			return;
		}

		KafkaKeyedMessage<K,V> kafkaMessage = new KafkaKeyedMessage<K,V>(key, value);

		String strKey = (String) key;
		int partitionIndex = hash(strKey, this.getTopic().getPartitions().size());
		
		KafkaPartition currePartition = this.getTopic().getPartitions().get(partitionIndex);
		currePartition.getMessageQueue().add(kafkaMessage);

		currePartition.updateReplicaState(kafkaMessage);
	}

	@Override
	public <K,V> List<KafkaMessage> receiveMessage(int consumerIndex) {
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