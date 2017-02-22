package com.taz.models;

import java.util.LinkedHashMap;

/**
 * Created by K.Kokulan on 2/21/2017.
 */
public class ClusteringAnomalyRegion {
    private int regionID;
    private long startTime;
    private long endTime;
    private long startGCId;
    private long endGCId;

    public long getStartGCId() {
        return startGCId;
    }

    public void setStartGCId(long startGCId) {
        this.startGCId = startGCId;
    }

    public long getEndGCId() {
        return endGCId;
    }

    public void setEndGCId(long endGCId) {
        this.endGCId = endGCId;
    }

    private LinkedHashMap<String, Double> hotMethodsPercentage;

    public int getRegionID() {
        return regionID;
    }

    public void setRegionID(int regionID) {
        this.regionID = regionID;
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

    public LinkedHashMap<String, Double> getHotMethodsPercentage() {
        return hotMethodsPercentage;
    }

    public void setHotMethodsPercentage(LinkedHashMap<String, Double> hotMethodsPercentage) {
        this.hotMethodsPercentage = hotMethodsPercentage;
    }
}

