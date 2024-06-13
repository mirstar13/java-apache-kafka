package oopproject1.admin;

import java.util.Comparator;

public abstract class KafkaMessage {
    private long ingestionTime;

    protected KafkaMessage() {
        this.ingestionTime = System.currentTimeMillis();
    }

    // GETTERS

    public long getIngestionTime() {
        return ingestionTime;
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