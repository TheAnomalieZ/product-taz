package org.taz.commons.parser.util;

public abstract class JFREvent {
    private long startTimestamp;
    private long endTimestamp;
    private String EVENT_TYPE;

    public JFREvent(String eventType) {
        this.EVENT_TYPE = eventType;
    }

    public long getStartTimestamp() {
        return startTimestamp;
    }

    public void setStartTimestamp(long startTimestamp) {
        this.startTimestamp = startTimestamp;
    }

    public long getEndTimestamp() {
        return endTimestamp;
    }

    public void setEndTimestamp(long endTimestamp) {
        this.endTimestamp = endTimestamp;
    }

    @Override
    public String toString(){
        return "Event Type="+this.EVENT_TYPE ;
    }

}