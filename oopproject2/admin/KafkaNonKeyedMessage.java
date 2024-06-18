package oopproject2.admin;

public class KafkaNonKeyedMessage<V> extends KafkaMessage {
    private V value;
    
    public KafkaNonKeyedMessage(V value){
        super();    
        this.value = value;

    }
    
    // GETTERS AND SETTERS

    @Override
    public String toString() {
        return "id = " + this.getMessageId() + " | " + "ingestion_time = " + this.getIngestionTime() + " | " + value;
    }

    protected V getValue(){
        return value;
    }

}
