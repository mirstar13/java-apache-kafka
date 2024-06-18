package oopproject2.admin;

public class KafkaKeyedMessage<K, V> extends KafkaMessage {
    private K key;
    private V value;

    protected KafkaKeyedMessage(K key, V value) {
        super();

        this.key = key;
        this.value = value;
    }

    // GETTERS AND SETTERS

    @Override
    public String toString() {
        if (key.equals("key")) {
            return "id = " + this.getMessageId() + " | " + "ingestion_time = " + this.getIngestionTime() + " | " + value;
        } else {
            return "id = " + this.getMessageId() + " | " + "ingestion_time = " + this.getIngestionTime() + " | " + "key = " + key + " | " + value;
        }
    }

    protected K getKey() {
        return key;
    }

    protected V getValue() {
        return value;
    }

}
