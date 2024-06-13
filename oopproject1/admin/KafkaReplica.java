package oopproject1.admin;

public class KafkaReplica {

	private KafkaBroker broker;
	private KafkaPartition partition;

	public KafkaReplica(KafkaBroker broker, KafkaPartition partition) {
		this.broker = broker;
		this.partition = partition;
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
}
