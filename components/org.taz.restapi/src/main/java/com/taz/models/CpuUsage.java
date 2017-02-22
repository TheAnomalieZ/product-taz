package com.taz.models;

import java.util.ArrayList;

/**
 * Created by K.Kokulan on 12/3/2016.
 */
public class CpuUsage {
    private double max;
    private double avg;
    private String cpuUsageData;
    private ArrayList<Double> data;

    public String getCpuUsageData() {
        return cpuUsageData;
    }

    public void setCpuUsageData(String cpuUsageData) {
        this.cpuUsageData = cpuUsageData;
    }

    public CpuUsage(){
        this.max = 0;
        this.avg = 0;
        this.data = new ArrayList<>(1);
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getAvg() {
        return avg;
    }

    public void setAvg(double avg) {
        this.avg = avg;
    }

    public ArrayList<Double> getData() {
        return data;
    }

    public void setData(double data) {
        this.data.add(0,data);
    }
}
