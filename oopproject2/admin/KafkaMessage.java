package oopproject2.admin;

import java.util.Comparator;
import java.util.UUID;

public abstract class KafkaMessage {
    private long ingestionTime;
    private UUID messageId;

    protected KafkaMessage() {
        this.ingestionTime = System.currentTimeMillis();
        this.messageId = UUID.randomUUID();
    }

    // GETTERS

    public long getIngestionTime() {
        return ingestionTime;
    }

    public UUID getMessageId() {
        return messageId;
    }
}

class KafkaMessageComparator implements Comparator<KafkaMessage> {
    public int compare(KafkaMessage msg1, KafkaMessage msg2) {
        if (msg1.getIngestionTime() < msg2.getIngestionTime()) {
            return 1;
        } else if (msg1.getIngestionTime() > msg2.getIngestionTime()) {
            return -1;
        }

        return 0;
    }
}