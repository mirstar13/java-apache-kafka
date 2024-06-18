package oopproject2.admin;

public class KafkaKeyedMessage<K, V> extends KafkaMessage {
    private K key;
    private V value;

    protected KafkaKeyedMessage(K key, V value) {
        super();

        this.key = key;
        this.value = value;

        if (key.equals("key")) {
            this.setStrMessage("ingestion_time = " + this.getIngestionTime() + " | " + value);
        } else {
            this.setStrMessage("ingestion_time = " + this.getIngestionTime() + " | " + "key = " + key + " | " + value);
        }
    }

    // GETTERS AND SETTERS

    protected K getKey() {
        return key;
    }

    protected V getValue() {
        return value;
    }

}
