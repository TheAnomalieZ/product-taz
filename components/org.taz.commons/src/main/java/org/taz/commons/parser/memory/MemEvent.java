package org.taz.commons.parser.memory;

import org.taz.commons.constants.JFRConstants;
import org.taz.commons.parser.util.JFREvent;

public class MemEvent extends JFREvent {

    private long gcId;
    private long usedHeap;
    private long startHeap;
    private long endHeap;
    private long committedHeap;

    private long pauseTime;
    private String type;
    private String cause;
    private String longestPause;
    private long duration;

    public long getCommittedHeap() {
        return committedHeap;
    }

    public void setCommittedHeap(long committedHeap) {
        this.committedHeap = committedHeap;
    }

    public long getStartHeap() {
        return startHeap;
    }

    public void setStartHeap(long startHeap) {
        this.startHeap = startHeap;
    }

    public long getEndHeap() {
        return endHeap;
    }

    public void setEndHeap(long endHeap) {
        this.endHeap = endHeap;
    }

    public MemEvent(){
        super(JFRConstants.GARBAGECOLLECTION);
    }

    public long getGcId() {
        return gcId;
    }

    public long getUsedHeap() {
        return usedHeap;
    }

    public void setGcId(long gcId) {
        this.gcId = gcId;
    }

    public void setUsedHeap(long usedHeap) {
        this.usedHeap = usedHeap;
    }

    public long getPauseTime() {
        return pauseTime;
    }

    public void setPauseTime(long pauseTime) {
        this.pauseTime = pauseTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getLongestPause() {
        return longestPause;
    }

    public void setLongestPause(String longestPause) {
        this.longestPause = longestPause;
    }
}
