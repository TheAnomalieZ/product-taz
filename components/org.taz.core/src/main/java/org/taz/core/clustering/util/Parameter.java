package org.taz.core.clustering.util;


/**
 * Created by vithulan on 1/16/17.
 */
public class Parameter {
    private long heapUsed;
    private long gCPauseDuration;
    private long oldObjectSpaceUsed;
    private long startTime;
    private long endTime;
    private long gcID;
    private double anomalyScore;
    private double anomalyClassificationScore;

    public double getAnomalyClassificationScore() {
        return anomalyClassificationScore;
    }

    public void setAnomalyClassificationScore(double anomalyClassificationScore) {
        this.anomalyClassificationScore = anomalyClassificationScore;
    }

    public double getAnomalyScore() {
        return anomalyScore;
    }

    public void setAnomalyScore(double anomalyScore) {
        this.anomalyScore = anomalyScore;
    }



    public long getGcID() {
        return gcID;
    }

    public void setGcID(long gcID) {
        this.gcID = gcID;
    }



    public long getHeapUsed() {
        return heapUsed;
    }

    public void setHeapUsed(long heapUsed) {
        this.heapUsed = heapUsed;
    }

    public long getgCPauseDuration() {
        return gCPauseDuration;
    }

    public void setgCPauseDuration(long gCPauseDuration) {
        this.gCPauseDuration = gCPauseDuration;
    }

    public long getOldObjectSpaceUsed() {
        return oldObjectSpaceUsed;
    }

    public void setOldObjectSpaceUsed(long oldObjectSpaceUsed) {
        this.oldObjectSpaceUsed = oldObjectSpaceUsed;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

}
