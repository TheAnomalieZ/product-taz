package com.taz.graph_models;

import java.util.ArrayList;

/**
 * Created by K.Kokulan on 12/3/2016.
 */
public class GcData {
    private double max;
    private double avg;
    private ArrayList<Double> data;
    private int gaugeMax = 100;

    public GcData(){
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

    public int getGaugeMax() {
        return gaugeMax;
    }

    public void setGaugeMax(int gaugeMax) {
        this.gaugeMax = gaugeMax;
    }
}
