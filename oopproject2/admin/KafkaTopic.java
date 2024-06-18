package oopproject2.admin;

import java.util.ArrayList;
import java.util.List;

public class KafkaTopic {

	private String name;
	private KafkaBroker owner;
	private ArrayList<KafkaPartition> partitions;
	private KafkaProducer[] producers;
	private KafkaConsumer[] consumers;
	private List<List<Integer>> partitionDistribution;
	private int replicationFactor;
	private boolean keyed;
	private int producerCount;
	private int consumerCount;
	private int maxProducers;
	private int maxConsumers;
	private int messageCount;

	public static final int DEFAUTL_NUM_PARTITIONS = 2;
	public static final int DEFAULT_REPLICATION_FACTOR = 1;
	public static final boolean DEFAULT_KEYED = false;
	public static final int DEFAULT_MAX_PRODUCERS = 10;
	public static final int DEFAULT_MAX_CONSUMERS = 10;

	// Constructor with default replication factor and keyed values
	public KafkaTopic(String name, int numPartitions, KafkaBroker owner, int maxProducers, int maxConsumers,
			int replicationFactor, boolean keyed) {
		this.name = name;
		this.partitions = new ArrayList<>(numPartitions);
		this.owner = owner;
		this.maxConsumers = maxConsumers;
		this.maxProducers = maxProducers;
		this.producers = new KafkaProducer[maxProducers];
		this.consumers = new KafkaConsumer[maxConsumers];
		this.replicationFactor = replicationFactor;
		this.keyed = keyed;
		this.partitionDistribution = distributePartitions(numPartitions, maxConsumers);
		this.messageCount = 0;
	}

	// Constructor with default max producers and consumers
	public KafkaTopic(String name, int numPartitions, KafkaBroker owner, int replicationFactor, boolean keyed) {
		this.name = name;
		this.partitions = new ArrayList<>(numPartitions);
		this.producers = new KafkaProducer[KafkaTopic.DEFAULT_MAX_PRODUCERS];
		this.consumers = new KafkaConsumer[KafkaTopic.DEFAULT_MAX_CONSUMERS];
		this.maxProducers = KafkaTopic.DEFAULT_MAX_PRODUCERS;
		this.maxConsumers = KafkaTopic.DEFAULT_MAX_CONSUMERS;
		this.owner = owner;
		this.replicationFactor = replicationFactor;
		this.keyed = keyed;
		this.partitionDistribution = distributePartitions(numPartitions, this.consumerCount);
		this.messageCount = 0;
	}

	// Constructor with default replication factor, keyed, max producers, and max
	// consumers

	public KafkaTopic(String name, int numPartitions, KafkaBroker owner) {
		this.name = name;
		this.partitions = new ArrayList<>(numPartitions);
		this.producers = new KafkaProducer[KafkaTopic.DEFAULT_MAX_PRODUCERS];
		this.consumers = new KafkaConsumer[KafkaTopic.DEFAULT_MAX_CONSUMERS];
		this.maxProducers = KafkaTopic.DEFAULT_MAX_PRODUCERS;
		this.maxConsumers = KafkaTopic.DEFAULT_MAX_CONSUMERS;
		this.replicationFactor = KafkaTopic.DEFAULT_REPLICATION_FACTOR;
		this.keyed = KafkaTopic.DEFAULT_KEYED;
		this.owner = owner;
		this.partitionDistribution = distributePartitions(numPartitions, this.consumerCount);
		this.messageCount = 0;
	}

	// GETTERS & SETTERS

	protected int getMessageCount() {
		return messageCount;
	}

	protected void setMessageCount(int availableMessages) {
		this.messageCount = availableMessages;
	}

	public List<List<Integer>> getPartitionDistribution() {
		return partitionDistribution;
	}

	public String getName() {
		return name;
	}

	private void setName(String name) {
		this.name = name;
	}

	private KafkaBroker getOwner() {
		return owner;
	}

	private void setOwner(KafkaBroker owner) {
		this.owner = owner;
	}

	protected ArrayList<KafkaPartition> getPartitions() {
		return partitions;
	}

	protected void setPartitions(ArrayList<KafkaPartition> partitions) {
		this.partitions = partitions;
	}

	public KafkaProducer[] getProducers() {
		return producers;
	}

	private void setProducers(KafkaProducer[] producers) {
		this.producers = producers;
	}

	public KafkaConsumer[] getConsumers() {
		return consumers;
	}

	private void setConsumers(KafkaConsumer[] consumers) {
		this.consumers = consumers;
	}

	protected int getReplicationFactor() {
		return replicationFactor;
	}

	private void setReplicationFactor(int replicationFactor) {
		this.replicationFactor = replicationFactor;
	}

	public boolean isKeyed() {
		return keyed;
	}

	private void setKeyed(boolean keyed) {
		this.keyed = keyed;
	}

	private int getProducerCount() {
		return producerCount;
	}

	private void setProducerCount(int producerCount) {
		this.producerCount = producerCount;
	}

	private int getConsumerCount() {
		return consumerCount;
	}

	private void setConsumerCount(int consumerCount) {
		this.consumerCount = consumerCount;
	}	

	// METHODS

	public KafkaProducer addProducer(KafkaProducer producer) {
		if (producerCount >= maxProducers) {
			System.out.println(" MAXIMUM NUMBER OF PRODUCERS FOR THIS TOPIC");
			return null;
		}

		producers[producerCount] = producer;
		producerCount++;
		return producer;
	}

	public boolean removeProducer(int index) {
		boolean isDeleted = false;
		if (index > producerCount) {
			System.out.println("Invalid producer index");
			return isDeleted;
		} else if (index == producerCount) {
			producers[producerCount] = null;
			producerCount--;
			isDeleted = true;
			return isDeleted;
		}
		
		producerCount--;

		for (int i = index; i < producerCount ; i++) {
			producers[i] = producers[i+1];
		}
		producers[producerCount] = null;
		
		isDeleted = true;
		return isDeleted;
	}

	public KafkaConsumer addConsumer(KafkaConsumer consumer) {
		if (consumerCount >= maxConsumers) {
			System.out.println("MAXIMUM NUMBER OF CONSUMERS FOR THIS TOPIC");
			return null;
		}

		consumers[consumerCount] = consumer;
		consumerCount++;
		return consumer;
	}

	public boolean removeConsumer(int index) {
		boolean isDeleted = false;
		if (index > consumerCount) {
			System.out.println("Invalid consumer index");
			return isDeleted;
		} else if (index == producerCount) {
			consumers[consumerCount] = null;
			consumerCount--;
			isDeleted = true;
			return isDeleted;
		}
		
		consumerCount--;
		
		for (int i = index; i < consumerCount ; i++) {
			consumers[i] = consumers[i+1];
		}
		consumers[consumerCount] = null;

		isDeleted = true;
		return isDeleted;
	}

	public void checkValidPositiveInteger(int parameter) {
		if (parameter <= 0) {
			System.out.println("Replication factor and number of partitions must be positive");
		}
	}

	public static List<List<Integer>> distributePartitions(int numPartitions, int numConsumers) {
		List<List<Integer>> partitionRanges = new ArrayList<>();
		if (numPartitions >= numConsumers) {
			// Calculate the number of partitions each consumer should ideally handle
			int partitionsPerConsumer = numPartitions / numConsumers;
			int remainder = numPartitions % numConsumers; // Handle the case when numPartitions is not evenly divisible
															// by numConsumers

			// Initialize the starting partition index
			int startPartition = 0;

			// Distribute partitions to consumers
			for (int i = 0; i < numConsumers; i++) {
				int endPartition = startPartition
						+ (remainder-- > 0 ? partitionsPerConsumer : partitionsPerConsumer - 1);

				List<Integer> partitionRange = new ArrayList<>();
				partitionRange.add(startPartition);
				partitionRange.add(endPartition);
				partitionRanges.add(partitionRange);

				// Update the starting partition index for the next consumer
				startPartition = endPartition + 1;

				// Wrap around if the endPartition exceeds the total number of partitions
				if (startPartition >= numPartitions) {
					startPartition %= numPartitions;
				}
			}
		} else {
			// There are more consumers than partitions
			// Some consumers will receive one partition and the rest will receive none
			for (int i = 0; i < numConsumers; i++) {
				List<Integer> partitionRange = new ArrayList<>();
				partitionRange.add(i < numPartitions ? i : -1);
				partitionRange.add(i < numPartitions ? i : -1);
				partitionRanges.add(partitionRange);
			}
		}

		return partitionRanges;
	}

	@Override
	public String toString() {
		return this.getName();
	}
}
