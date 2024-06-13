package oopproject1.admin;

public class KafkaNonKeyedMessage<V> extends KafkaMessage {
    private V value;
    
    // GETTERS AND SETTERS

    protected V getValue(){
        return value;
    }

    public KafkaNonKeyedMessage(V value){
        super();
        
        this.value = value;
    }
}
