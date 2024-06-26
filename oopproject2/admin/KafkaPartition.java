package oopproject2.admin;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class KafkaPartition {

	private long offset;
	private ArrayList<KafkaReplica> replicas;
	private PriorityQueue<KafkaMessage> messageQueue;
	
	private final static long DEFAULT_OFFSET = 0;
    private static final int DEFAULT_NUM_REPLICAS = 3;

	protected KafkaPartition(int numReplicas) {
		this.offset = KafkaPartition.DEFAULT_OFFSET;
		this.messageQueue = new PriorityQueue<KafkaMessage>(new KafkaMessageComparator());
		this.replicas = new ArrayList<>(numReplicas);
	}

	// Constructor with default num replicas
	protected KafkaPartition() {
		this.offset = KafkaPartition.DEFAULT_OFFSET;
		this.replicas = new ArrayList<>(KafkaPartition.DEFAULT_NUM_REPLICAS);
	}

	// GETTERS AND SETTERS

	protected PriorityQueue<KafkaMessage> getMessageQueue() {
		return messageQueue;
	}

	private void setMessageQueue(PriorityQueue<KafkaMessage> messageQueue) {
		this.messageQueue = messageQueue;
	}

	protected long getOffset() {
		return offset;
	}

	protected void setOffset(long offset) {
		this.offset = offset;
	}

	protected ArrayList<KafkaReplica> getReplicas() {
		return replicas;
	}

	private void setReplicas(ArrayList<KafkaReplica> replicas) {
		this.replicas = replicas;
	}

	// METHODS

	protected void updateReplicaState() {
		for (KafkaReplica replica : replicas) {
			replica.incrementOffset();
		}
	}

	protected void updateReplicaState(KafkaMessage message) {
		for (KafkaReplica replica : replicas) {
			replica.addMessage(message);;
		}
	}
}