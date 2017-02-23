package org.taz.core.clustering;

import org.apache.commons.math3.stat.descriptive.rank.Percentile;
import org.taz.core.clustering.util.CSVGenerator;
import org.taz.core.clustering.util.Parameter;
import org.taz.core.clustering.util.ParserAPI;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by vithulan on 2/20/17.
 */
public class ClusteringHandler {
    private OpticsOF opticsOF;
    private ArrayList<Double> anomalyScores;
    private double percentilePercentage;

    public ClusteringHandler(String file_path) {
        createOpticsOfInstance(file_path);
        percentilePercentage = 90.0;
    }

    private OpticsOF createOpticsOfInstance(String file_path){
        ParserAPI parserAPI = new ParserAPI(file_path);
        TreeMap<Long, Parameter> parameterTreeMap = parserAPI.generateAttributeTable();
        CSVGenerator csvGenerator = new CSVGenerator(parameterTreeMap);
        String file = csvGenerator.generateCSV();
        int totalPoints = csvGenerator.getTotalPoints();
        opticsOF = new OpticsOF(totalPoints,file);
        opticsOF.setParameterTreeMap(parameterTreeMap);
        runOpticsOF();
        return opticsOF;
    }

    private void runOpticsOF(){
        this.anomalyScores = opticsOF.generateAnomalyScore();
    }

    public TreeMap<Integer, Parameter> getAnomalyPoints(){
        TreeMap<Long, Parameter> parameterTreeMap = opticsOF.getParameterTreeMap();
        TreeMap<Integer, Parameter> anomalyMap = new TreeMap<>();
        double percentileVal = getPercentileValue();
        int i = 0;
        for(Map.Entry<Long,Parameter> entry : parameterTreeMap.entrySet()){
            Parameter parameter = entry.getValue();
            if(parameter.getAnomalyScore()>=percentileVal){
                anomalyMap.put(i,parameter);
                i++;
            }
        }
        return anomalyMap;      //// TODO: 2/20/17 Need to check if its null. If its null NO ANOMALY detected
    }

    public TreeMap<Integer, Parameter> getAnomalyPointsRegion(){
        TreeMap<Long, Parameter> parameterTreeMap = opticsOF.getParameterTreeMap();
        TreeMap<Integer, Parameter> anomalyRegionMap = new TreeMap<>();
        double percentileVal = getPercentileValue();
        System.out.println(percentileVal);
        int i = 0;
        for(Map.Entry<Long,Parameter> entry : parameterTreeMap.entrySet()){
            Parameter parameter = entry.getValue();
            if(parameter.getAnomalyScore()>=percentileVal){
                parameter.setAnomalyClassificationScore(parameter.getAnomalyScore());
                anomalyRegionMap.put(i,parameter);
            }
            else{
                parameter.setAnomalyClassificationScore(parameter.getAnomalyScore());
                anomalyRegionMap.put(i,parameter);
            }
            i++;
        }
        return anomalyRegionMap;
    }

    /**
     * Get percentile value of dataset
     * @return percentile value
     */
    public double getPercentileValue (){
        double[] anomalyScoreArray = new double[anomalyScores.size()];
        for(int i=0;i<anomalyScores.size();i++){
            anomalyScoreArray[i] = anomalyScores.get(i);
        }
        Percentile percentile = new Percentile();
        percentile.setData(anomalyScoreArray);
        return percentile.evaluate(this.percentilePercentage);
    }
    public void setPercentile(double percentilePercentage){
        this.percentilePercentage = percentilePercentage;
    }

    public ArrayList<Double> getAnomalyScores() {
        return anomalyScores;
    }
}
