package oopproject1.admin;

public class KafkaKeyedMessage<K,V> extends KafkaMessage {
    private K key;
    private V value;

    // GETTERS AND SETTERS
    protected K getKey() {
        return key;
    }

    protected V getValue(){
        return value;
    }
    
    // METHODS
    protected KafkaKeyedMessage(K key, V value){
        super();
        
        this.key = key;
        this.value = value;
    }
}
