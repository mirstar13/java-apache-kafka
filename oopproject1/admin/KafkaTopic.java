package oopproject1.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

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

	private static final int DEFAULT_REPLICATION_FACTOR = 1;
	private static final boolean DEFAULT_KEYED = false;
	private static final int DEFAULT_MAX_PRODUCERS = 10;
	private static final int DEFAULT_MAX_CONSUMERS = 10;

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
		this.owner = owner;
		this.partitionDistribution = distributePartitions(numPartitions, this.consumerCount);
	}

	// GETTERS & SETTERS

	protected List<List<Integer>> getPartitionDistribution() {
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

	private int getDEFAULT_REPLICATION_FACTOR() {
		return DEFAULT_REPLICATION_FACTOR;
	}

	private boolean isDEFAULT_KEYED() {
		return DEFAULT_KEYED;
	}

	private int getDEFAULT_MAX_PRODUCERS() {
		return DEFAULT_MAX_PRODUCERS;
	}

	private int getDEFAULT_MAX_CONSUMERS() {
		return DEFAULT_MAX_CONSUMERS;
	}

	// METHODS

	public void addProducer(KafkaProducer producer) {
		if (producerCount >= maxProducers) {
			System.out.println(" MAXIMUM NUMBER OF PRODUCERS FOR THIS TOPIC");
			return;
		}

		producers[producerCount] = producer;
		producerCount++;
	}

	public void addConsumer(KafkaConsumer consumer) {
		if (consumerCount >= maxConsumers) {
			System.out.println("MAXIMUM NUMBER OF CONSUMERS FOR THIS TOPIC");
			return;
		}

		consumers[consumerCount] = consumer;
		consumerCount++;
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
}
