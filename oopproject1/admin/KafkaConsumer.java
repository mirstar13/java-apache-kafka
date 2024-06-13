package oopproject1.admin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class KafkaConsumer extends KafkaClient {
	public KafkaConsumer(KafkaTopic topic) {
		super(topic);
	}

	// METHODS

	@Override
	public <K,V> void sendMessage(K key, V value) {
		System.out.println("Error. Consumers don't send messages.");
	}

	@Override
	public List<KafkaMessage> receiveMessage(int consumerIndex) {
		KafkaTopic topic = this.getTopic();
		
		List<List<Integer>> partitionDistribution = topic.getPartitionDistribution();
		
		List<Integer> partitionRange = partitionDistribution.get(consumerIndex);
		List<KafkaMessage> messages = new ArrayList<>(partitionRange.size());

		List<KafkaPartition> partitions = topic.getPartitions();
		for (Integer partitionIndex : partitionRange) {
			if (partitionIndex == -1) {
				continue;
			}
			
			KafkaPartition currentPartition = partitions.get(partitionIndex);
			long partitionOffset = currentPartition.getOffset();


			KafkaMessage message = currentPartition.getMessageQueue().poll();
			currentPartition.setOffset(partitionOffset+1);

			if (message instanceof KafkaKeyedMessage) {
				KafkaKeyedMessage keyedMessage = (KafkaKeyedMessage) message;
				
				if (keyedMessage != null) {
					System.out.println("recieved message: " + keyedMessage.getIngestionTime() + " key: " + keyedMessage.getKey() + " value: " + keyedMessage.getValue());
					messages.add(keyedMessage);
				}

			} else {
				KafkaNonKeyedMessage nonKeyedMessage = (KafkaNonKeyedMessage) message;

				if (nonKeyedMessage != null) {
					System.out.println("recieved message: " + nonKeyedMessage.getIngestionTime() + " value: " + nonKeyedMessage.getValue());
					messages.add(nonKeyedMessage);
				}
			}
		}

		return messages;
	}
}
