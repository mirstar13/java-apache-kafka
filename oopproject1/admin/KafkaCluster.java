package oopproject1.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class KafkaCluster {

	private ArrayList<KafkaBroker> brokers;
	private static int DEFAULT_MAX_BROKERS;
	private int brokerCount;
	private int maxBrokers;
	private int maxTopicsPerBroker;

	public KafkaCluster(int maxBrokers, int maxTopicsPerBroker) {
		this.maxBrokers = maxBrokers;
		this.maxTopicsPerBroker = maxTopicsPerBroker;
		this.brokerCount = 0;

		this.brokers = new ArrayList<>(maxBrokers);

	}

	// getters & setters
	public ArrayList<KafkaBroker> getBrokers() {
		return brokers;
	}

	private void setBrokers(ArrayList<KafkaBroker> brokers) {
		this.brokers = brokers;
	}

	private int getDEFAULT_MAX_BROKERS() {
		return DEFAULT_MAX_BROKERS;
	}

	private void setDEFAULT_MAX_BROKERS(int dEFAULT_MAX_BROKERS) {
		DEFAULT_MAX_BROKERS = dEFAULT_MAX_BROKERS;
	}

	private int getBrokerCount() {
		return brokerCount;
	}

	private void setBrokerCount(int brokerCount) {
		this.brokerCount = brokerCount;
	}

	private int getMaxBrokers() {
		return maxBrokers;
	}

	private void setMaxBrokers(int maxBrokers) {
		this.maxBrokers = maxBrokers;
	}

	private int getMaxTopicsPerBroker() {
		return maxTopicsPerBroker;
	}

	private void setMaxTopicsPerBroker(int maxTopicsPerBroker) {
		this.maxTopicsPerBroker = maxTopicsPerBroker;
	}

	// methods

	public void insertBroker(KafkaBroker broker) {

		for (int i = 0; i < brokerCount; i++) {
			if (brokers.get(i) != null && brokers.get(i).getHost().equals(broker.getHost())
					&& brokers.get(i).getPort() == broker.getPort()) {
				System.out.println(
						"Broker with host " + broker.getHost() + " and port " + broker.getPort() + " already exists");
				return;
			}
		}
		if (brokerCount < maxBrokers) {
			brokers.add(broker);
			brokerCount++;
		} else {
			System.out.println("Maximum number of brokers reached for this Cluster");
		}
	}

	public void removeBroker(String host, int port) {
		boolean brokerFound = false;
		for (int i = 0; i < brokerCount; i++) {
			KafkaBroker broker = brokers.get(i);
			if (broker != null && broker.getHost().equals(host) && broker.getPort() == port) {
				brokerFound = true;

				brokers.remove(i);
				brokerCount--; // Decrement brokerCount

				System.out.println("Broker with host " + host + " and port " + port + " removed ");
				break;
			}
		}
		if (!brokerFound) {
			System.out.println("Broker with host " + host + " and port   " + port + " not found");
		}
	}

	KafkaBroker findBrokerByHostAndPort(String host, int port) {
		for (KafkaBroker broker : this.brokers) {
			if (broker.getHost() == host && broker.getPort() == port)
				return broker;
		}
		return null;
	}

	public void updateBrokerPort(String host, int port, int newPort) {
		boolean portUsed = false;
		KafkaBroker brokerToUpdate = null;
		for (int i = 0; i < brokerCount; i++) {
			if (brokers.get(i) != null && brokers.get(i).getPort() == newPort) {
				portUsed = true;
				break;
			}
		}
		for (int i = 0; i < brokerCount; i++) {
			if (brokers.get(i) != null && brokers.get(i).getHost().equals(host) && brokers.get(i).getPort() == port) {
				brokerToUpdate = brokers.get(i);
				break;
			}
		}

		if (brokerToUpdate != null) {
			if (!portUsed) {
				brokerToUpdate.setPort(newPort);
				System.out.println(
						"Port of broker with host " + host + " and port " + port + " has been updated to " + newPort);
			} else {
				System.out.println("Port " + newPort + " is already used by another broker");
			}
		} else {
			System.out.println("Broker with host " + host + " and port " + port + " does not exist");
		}
	}

	public void addTopic(String topicName, int numPartitions, int maxProducers, int maxConsumers, int replicationFactor,
			boolean keyed) {
		if (checkTopicExistence(topicName)) {
			return;
		}

		KafkaBroker chosenBroker = null;
		for (int i = this.brokerCount - 1; i > 0; i--) {
			if (this.brokers.get(i).getTopicCount() == this.brokers.get(i - 1).getTopicCount()) {
				if (i - 1 == 0) {
					chosenBroker = this.brokers.get(i - 1);
					break;
				}
				continue;
			} else if (this.brokers.get(i).getTopicCount() < this.brokers.get(i - 1).getTopicCount()) {
				chosenBroker = this.brokers.get(i);
				break;
			}

		}

		ArrayList<KafkaPartition> partitions = new ArrayList<>(numPartitions);
		for (int i = 0; i < numPartitions; i++) {
			ArrayList<KafkaReplica> replicas = new ArrayList<>(replicationFactor);
			if (replicationFactor <= brokerCount) {
				partitions.add(new KafkaPartition(replicationFactor));
			} else {
				System.out.println("The reprication factor is greater than the number of Brokers!!");
				return;
			}
			for (int j = 0; j < replicationFactor; j++) {
				Random random = new Random();
				int randomNumber = random.nextInt(brokerCount);
				replicas.add(new KafkaReplica(brokers.get(randomNumber), partitions.get(i)));
			}
		}

		KafkaTopic topic = new KafkaTopic(topicName, numPartitions, chosenBroker, maxProducers, maxConsumers,
				replicationFactor, keyed);

		topic.setPartitions(partitions);
		chosenBroker.addTopic(topic);
	}

	public void deleteTopic(String topicName) {
		boolean topicDeleted = false; // Track if topic is deleted from any broker
		for (int i = 0; i < brokerCount; i++) {
			KafkaBroker broker = brokers.get(i);
			if (broker != null) {
				ArrayList<KafkaTopic> topics = broker.getTopics();
				for (int j = 0; j < broker.getTopicCount(); j++) {
					KafkaTopic topic = topics.get(j);
					if (topic != null && topic.getName().equals(topicName)) {
						broker.removeTopic(topicName);
						topicDeleted = true; // Set flag to true if topic is deleted
						break; // Exit inner loop once topic is found and deleted
					}
				}
			}
		}
		if (topicDeleted) {
			System.out.println("Topic with name " + topicName + " deleted from the KafkaCluster");
		} else {
			System.out.println("Topic with name " + topicName + " not found in any broker");
		}
	}

	public KafkaTopic findTopicByName(String topicName) {
		for (int i = 0; i < brokerCount; i++) {
			KafkaBroker broker = brokers.get(i);
			if (broker != null) {
				ArrayList<KafkaTopic> topics = broker.getTopics();
				for (int j = 0; j < broker.getTopicCount(); j++) {
					if (topics.get(j).getName().equals(topicName)) {
						return topics.get(j);
					}
				}
			}
		}
		return null;
	}

	public void listAllBrokers() {
		System.out.println("List of all Brokers in the KafkaCluster");
		int i;
		for (i = 0; i < brokerCount; i++) {
			KafkaBroker broker = brokers.get(i);
			if (broker != null) {
				System.out.println(
						"Broker: " + (i + 1) + "  Host =" + broker.getHost() + ", Port =  " + broker.getPort());
			}
		}
	}

	public void listAllTopicsAcrossBrokers() {
		System.out.println("List of all topics across Brokers in the KafkaCluster");
		int i;
		for (i = 0; i < brokerCount; i++) {
			KafkaBroker broker = brokers.get(i);
			if (brokers.get(i) != null) {
				System.out.println("Broker  " + (i + 1) + " topics:");
				ArrayList<KafkaTopic> topics = broker.getTopics();
				for (int j = 0; j < broker.getTopicCount(); j++) {
					System.out.println("-" + topics.get(j).getName());
				}

			}

		}

	}

	public void listAllTopicsAcrossBrokers(boolean includeDetails) {
		System.out.println("List of all topics across brokers with details:");
		for (int i = 0; i < brokerCount; i++) {
			KafkaBroker broker = brokers.get(i);
			if (broker != null) {
				ArrayList<KafkaTopic> topics = broker.getTopics();
				for (int n = 0; n < broker.getTopicCount(); n++) {
					System.out.println("Topic: " + topics.get(n).getName());
					if (includeDetails) {
						System.out.println("  Owner: " + broker.getHost() + ":" + broker.getPort());
						System.out.println("  Partitions: " + topics.get(n).getPartitions().size());
						System.out.println("  Replication Factor: " + topics.get(n).getReplicationFactor());

					}
				}
			}
		}
	}

	public boolean checkBrokerExistence(String host, int port) {
		int i;
		for (i = 0; i < brokerCount; i++) {
			KafkaBroker broker = brokers.get(i);
			if (brokers.get(i) != null && broker.getHost().equals(host) && broker.getPort() == port) {
				System.out.println("The Broker with host " + host + " and port" + port + "exists");
				return true;
			}
		}
		System.out.println("The Broker with host " + host + " and port" + port + "doesn't exist");
		return false;
	}

	public boolean checkTopicExistence(String topicName) {
		int i;
		for (i = 0; i < brokerCount; i++) {
			KafkaBroker broker = brokers.get(i);
			if (broker != null && broker.getTopics() != null) {
				ArrayList<KafkaTopic> topics = broker.getTopics();
				for (int j = 0; j < broker.getTopicCount(); j++) {
					if (topics.get(j) != null && topics.get(j).getName().equals(topicName)) {
						System.out.println("Topic with name  " + topicName + "  already exists in the KafkaCluster");
						return true;
					}
				}

			}
		}
		return false;
	}

}
