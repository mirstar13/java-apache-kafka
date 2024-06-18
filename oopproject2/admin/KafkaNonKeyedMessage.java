package oopproject2.admin;

public class KafkaNonKeyedMessage<V> extends KafkaMessage {
    private V value;
    
    public KafkaNonKeyedMessage(V value){
        super();    
        this.value = value;

        this.setStrMessage("ingestion_time = " + this.getIngestionTime() + " | " + value);
    }
    
    // GETTERS AND SETTERS

    protected V getValue(){
        return value;
    }

}
