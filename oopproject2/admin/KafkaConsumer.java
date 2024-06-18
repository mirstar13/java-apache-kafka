package oopproject2.admin;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import oopproject2.data.LinkedStack;

public class KafkaConsumer extends KafkaClient {
	private LinkedStack<KafkaMessage> messageStack;

	public KafkaConsumer(KafkaTopic topic) {
		super(topic);
		this.messageStack = new LinkedStack<>();
	}

	// METHODS

	@Override
	public <K, V> void sendMessage(K key, V value) {
		System.out.println("Error. Consumers don't send messages.");
	}

	@Override
	@SuppressWarnings("unchecked")
	public <K, V> List<KafkaMessage> receiveMessage(int consumerIndex) {
		KafkaTopic topic = this.getTopic();

		List<List<Integer>> partitionDistribution = topic.getPartitionDistribution();

		List<Integer> partitionRange = partitionDistribution.get(consumerIndex);
		List<KafkaMessage> messages = new ArrayList<>();

		List<KafkaPartition> partitions = topic.getPartitions();
		for (Integer partitionIndex : partitionRange) {
			if (partitionIndex == -1) {
				continue;
			}
			KafkaPartition currentPartition = partitions.get(partitionIndex);

			while (!currentPartition.getMessageQueue().isEmpty()) {
				KafkaMessage message = currentPartition.getMessageQueue().poll();

				if (message instanceof KafkaKeyedMessage) {
					KafkaKeyedMessage<K, V> keyedMessage = (KafkaKeyedMessage<K, V>) message;

					if (keyedMessage != null) {
						messages.add(keyedMessage);
						messageStack.push(keyedMessage);
					}

				} else {
					KafkaNonKeyedMessage<V> nonKeyedMessage = (KafkaNonKeyedMessage<V>) message;

					if (nonKeyedMessage != null) {
						messages.add(nonKeyedMessage);
						messageStack.push(nonKeyedMessage);
					}
				}

				currentPartition.updateReplicaState();
			}
		}

		return messages;
	}

	public void writeMessageStackToFile(String fileName) {
		if (messageStack.isEmpty()) {
			return;
		}

		String workingDirectory = System.getProperty("user.dir");
		String pathToData = "oopproject1" + File.separator + "data" + File.separator + "consumer_messages";
		String absoluteFilePath;

		absoluteFilePath = workingDirectory + File.separator + pathToData + File.separator + fileName;

		try {
			File messagesFile = new File(absoluteFilePath);
			messagesFile.createNewFile();

			BufferedWriter writer = new BufferedWriter(new FileWriter(messagesFile));

			while (!messageStack.isEmpty()) {
				writer.write(messageStack.pop().getStrMessage());
			}

			writer.close();
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}
}
