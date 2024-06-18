package oopproject2.admin;

import java.util.ArrayList;

public class KafkaBroker {

	private String host;
	private int brokerId;
	private int myBrokerId;
	private int port;
	private ArrayList<KafkaTopic> topics;
	private int topicCount;
	private int maxTopics;

	public KafkaBroker(String host, int port, int maxTopics) {
		this.host = host;
		this.port = port;
		this.maxTopics = maxTopics;
		this.topics = new ArrayList<>(maxTopics);
	}

	// getters & setters
	public String getHost() {
		return host;
	}

	private void setHost(String host) {
		this.host = host;
	}

	private int getBrokerId() {
		return brokerId;
	}

	private void setBrokerId(int brokerId) {
		this.brokerId = brokerId;
	}

	private int getMyBrokerId() {
		return myBrokerId;
	}

	private void setMyBrokerId(int myBrokerId) {
		this.myBrokerId = myBrokerId;
	}

	public int getPort() {
		return port;
	}

	protected void setPort(int port) {
		this.port = port;
	}

	public ArrayList<KafkaTopic> getTopics() {
		return topics;
	}

	private void setTopics(ArrayList<KafkaTopic> topics) {
		this.topics = topics;
	}

	protected int getTopicCount() {
		return topicCount;
	}

	private void setTopicCount(int topicCount) {
		this.topicCount = topicCount;
	}

	protected int getMaxTopics() {
		return maxTopics;
	}

	private void setMaxTopics(int maxTopics) {
		this.maxTopics = maxTopics;
	}

	// METHODS

	public boolean isValidPort(int port) {
		if (port < 0 || port > 65535)
			return false;
		else
			return true;

	}

	public boolean isValidHost(String host) {
		String[] splitHost = host.split(".");
		for (String part : splitHost) {
			int num = Integer.parseInt(part);
			if (num < 0 || num > 255) {
				return false;
			}
		}
		return true;
	}

	public void addTopic(KafkaTopic topic) {
		if (topicCount >= maxTopics) {
			System.out.println("Cannot add topics");
			return;
		}

		int i;
		for (i = 0; i < topicCount; i++) {
			if (topics.get(i).getName().equals(topic.getName())) {
				System.out.println("Topic   " + topic.getName() + "    already exists for this broker");
				return;
			}
		}

		topics.add(topic);
		topicCount++;
	}

	public void removeTopic(String topicName) {
		int i;
		for (i = 0; i < topicCount; i++) {
			if (topics.get(i) != null && topics.get(i).getName().equals(topicName)) {
				topics.remove(i);

				topicCount--;
				System.out.println("Topic with name  " + topicName + "  removed from the broker");
				return;
			}
		}

		System.out.println("Topic with name   " + topicName + "  not found");
	}

	public void listAllTopics() {
		int topicCount = 1;
		System.out.println("List of Topics for Broker  " + this.host + ":" + this.port + ":");
		for (int i = 0; i < topicCount; i++) {
			System.out.println("Topic" + topicCount + topics.get(i).getName());
			topicCount++;

		}

	}

	public void listAllTopics(boolean includeDetails) {

		if (includeDetails) {
			System.out.println("List of Topics per Broker " + this.host + ":" + this.port);
			int count = 1;
			for (int i = 0; i < topicCount; i++) {
				System.out.println("Topic" + count + ":" + topics.get(i).getName());
				System.out.println("Partitions");
				ArrayList<KafkaPartition> partitions = topics.get(i).getPartitions();
				for (int j = 0; j < partitions.size(); j++) {
					System.out.println("  Partiion" + (j + 1) + ":");
					System.out.println("   Replicas:");
					ArrayList<KafkaReplica> replicas = partitions.get(j).getReplicas();
					for (int w = 0; w < replicas.size(); w++) {
						System.out.println("    Replica" + (w + 1) + ":");
						KafkaBroker currentBroker = replicas.get(w).getBroker();
						System.out.println("    Replica" + (w + 1) + ": " + currentBroker.getHost() + ":"
								+ currentBroker.getPort());

					}

				}
			}
		} else {
			listAllTopics();
		}
	}

	@Override
	public String toString() {
		return this.getHost() + ":" + this.getPort();
	}

}
