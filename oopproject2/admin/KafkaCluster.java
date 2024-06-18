package oopproject2.admin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import oopproject2.data.RecordsFile;
import oopproject2.frontEnd.commands.CliCommand;

public class KafkaCluster {

	public static int DEFAULT_MAX_BROKERS = 10;
	public static int DEFAULT_MAX_TOPICS_PER_BROKER = 5;

	private List<KafkaBroker> brokers;
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
	public List<KafkaBroker> getBrokers() {
		return brokers;
	}

	private void setBrokers(ArrayList<KafkaBroker> brokers) {
		this.brokers = brokers;
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

	public boolean insertBroker(KafkaBroker broker) {

		for (int i = 0; i < brokerCount; i++) {
			if (brokers.get(i) != null && brokers.get(i).getHost().equals(broker.getHost())
					&& brokers.get(i).getPort() == broker.getPort()) {
				System.out.println(
						"Broker with host " + broker.getHost() + " and port " + broker.getPort() + " already exists");
				return false;
			}
		}
		if (brokerCount < maxBrokers) {
			brokers.add(broker);
			brokerCount++;
			return true;
		} else {
			System.out.println("Maximum number of brokers reached for this Cluster");
		}
		return false;
	}

	public Boolean removeBroker(String host, int port) {
		Boolean brokerDeleted = false;
		for (int i = 0; i < brokerCount; i++) {
			KafkaBroker broker = brokers.get(i);
			if (broker != null && broker.getHost().equals(host) && broker.getPort() == port) {
				brokers.remove(i);
				brokerCount--;
				brokerDeleted = true;

				System.out.println("Broker with host " + host + " and port " + port + " removed ");
				return brokerDeleted;
			}
		}

		System.out.println("Broker with host " + host + " and port   " + port + " not found");
		return brokerDeleted;
	}

	public KafkaBroker findBrokerByHostAndPort(String host, int port) {
		for (KafkaBroker broker : this.brokers) {
			if (broker.getHost() == host && broker.getPort() == port)
				return broker;
		}
		return null;
	}

	public boolean updateBrokerPort(String host, int port, int newPort) {
		boolean portUsed = false;
		boolean brokerUpdated = false;
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
				brokerUpdated = true;

				System.out.println(
						"Port of broker with host " + host + " and port " + port + " has been updated to " + newPort);

				return brokerUpdated;
			} else {
				System.out.println("Port " + newPort + " is already used by another broker");
				return brokerUpdated;
			}
		} else {
			System.out.println("Broker with host " + host + " and port " + port + " does not exist");
			return brokerUpdated;
		}
	}

	public KafkaTopic addTopic(String topicName, int numPartitions, int maxProducers, int maxConsumers,
			int replicationFactor,
			boolean keyed) {
		if (checkTopicExistence(topicName)) {
			return null;
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
				return null;
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
		return topic;
	}

	public boolean deleteTopic(String topicName) {
		boolean topicDeleted = false; // Track if topic is deleted from any broker
		outer: for (int i = 0; i < brokerCount; i++) {
			KafkaBroker broker = brokers.get(i);
			if (broker != null) {
				ArrayList<KafkaTopic> topics = broker.getTopics();
				for (int j = 0; j < broker.getTopicCount(); j++) {
					KafkaTopic topic = topics.get(j);
					if (topic != null && topic.getName().equals(topicName)) {
						broker.removeTopic(topicName);
						topicDeleted = true;
						break outer;
					}
				}
			}
		}

		if (topicDeleted) {
			System.out.println("Topic with name " + topicName + " deleted from the KafkaCluster");
		} else {
			System.out.println("Topic with name " + topicName + " not found in any broker");
		}

		return topicDeleted;
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
				ArrayList<KafkaTopic> topics = broker.getTopics();
				if (topics.size() == 0) {
					continue;
				}

				System.out.println("Broker  " + (i + 1) + " topics:");
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
						System.out.println("  Keyed: " + topics.get(n).isKeyed());
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

	public List<KafkaTopic> getTopics() {
		List<KafkaTopic> allTopics = new ArrayList<>();
		for (KafkaBroker broker : this.brokers) {
			List<KafkaTopic> topics = broker.getTopics();
			allTopics.addAll(topics);
		}

		return allTopics;
	}

	public List<KafkaProducer> getProducers() {
		List<KafkaProducer> allProducers = new ArrayList<>();
		List<KafkaTopic> allTopics = this.getTopics();
		for (KafkaTopic topic : allTopics) {
			KafkaProducer[] producers = topic.getProducers();
			allProducers.addAll(Arrays.asList(producers));
		}

		return allProducers;
	}

	public List<KafkaConsumer> getConsumers() {
		List<KafkaConsumer> allConsumers = new ArrayList<>();
		List<KafkaTopic> allTopics = this.getTopics();
		for (KafkaTopic topic : allTopics) {
			KafkaConsumer[] consumers = topic.getConsumers();
			allConsumers.addAll(Arrays.asList(consumers));
		}

		return allConsumers;
	}

	public List<KafkaClient> getClients() {
		List<KafkaClient> allClients = new ArrayList<>();

		List<KafkaProducer> allProducers = this.getProducers();
		List<KafkaConsumer> allConsumers = this.getConsumers();

		allClients.addAll(allProducers);
		allClients.addAll(allConsumers);

		return allClients;
	}

	public void insertFile(String fileName, String topicName, String keyName) {
		CliCommand insert = CliCommand.getCommands().get("insert");

		List<String> params = new ArrayList<>();
		params.add(fileName);
		params.add(topicName);
		params.add(keyName);

		insert.callback(this, params);
	}

	public void startReadingMessages() {
		CliCommand complete = CliCommand.getCommands().get("complete");

		complete.callback(this, null);
	}

	public boolean checkFileExistance(String fileName) {
		return RecordsFile.checkFileExistance(fileName);
	}
}
