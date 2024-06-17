package oopproject1.admin;

import java.util.PriorityQueue;

public class KafkaReplica {

	private KafkaBroker broker;
	private KafkaPartition partition;
	private PriorityQueue<KafkaMessage> messageQueue;
	private long offset;

	public KafkaReplica(KafkaBroker broker, KafkaPartition partition) {
		this.broker = broker;
		this.partition = partition;
		this.messageQueue = partition.getMessageQueue();
		this.offset = partition.getOffset();
	}

	// GETTERS AND SETTERS
	
	protected KafkaBroker getBroker() {
		return broker;
	}

	private void setBroker(KafkaBroker broker) {
		this.broker = broker;
	}

	private KafkaPartition getPartition() {
		return partition;
	}

	private void setPartition(KafkaPartition partition) {
		this.partition = partition;
	}

	private PriorityQueue<KafkaMessage> getMessageQueue(){
		return messageQueue;
	}

	private void setMessageQueue(PriorityQueue<KafkaMessage> messageQueue) {
		this.messageQueue = messageQueue;
	}

	private long getOffset() {
		return offset;
	}

	private void setOffset(long offset) {
		this.offset = offset;
	}

	// METHODS

	protected void incrementOffset() {
		offset++;
	}

	protected void addMessage(KafkaMessage message) {
		messageQueue.add(message);
	}
}
