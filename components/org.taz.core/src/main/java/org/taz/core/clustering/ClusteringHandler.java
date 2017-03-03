package org.taz.core.clustering;

import org.apache.commons.math3.stat.descriptive.rank.Percentile;
import org.taz.core.clustering.util.CSVGenerator;
import org.taz.core.clustering.util.Parameter;
import org.taz.core.clustering.util.ParserAPI;
import org.taz.core.clustering.util.Properties;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
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
        percentilePercentage = 80.0;
    }

    /**
     * This instantiation is for research purposes
     * @param file_path
     * @param method
     */
    public ClusteringHandler(String file_path, String method){
        ParserAPI parserAPI = new ParserAPI(file_path);
        TreeMap<Long, Parameter> parameterTreeMap = parserAPI.generateAttributeTableForTest();
        CSVGenerator csvGenerator = new CSVGenerator(parameterTreeMap);
        //this.setPercentile(90.0);
        if(method.equals("attribute")) {
            System.out.println("Running attribute test");
            for (int i = 0; i < 8; i++) {
                String file = csvGenerator.generateTestCSVs(i);
                opticsOF = new OpticsOF(csvGenerator.getTotalPoints(), file);
                opticsOF.setParameterTreeMap(parameterTreeMap);
                this.anomalyScores = opticsOF.generateAnomalyScoreForTest();
            }
        }
        if(method.equals("minpoint")){
            System.out.println("Running minpoint test");
            String file = csvGenerator.generateCSV();
            opticsOF = new OpticsOF(csvGenerator.getTotalPoints(),file);
            opticsOF.setParameterTreeMap(parameterTreeMap);
        }

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

    public TreeMap<Integer, Parameter> getAnomalyPointsRegionPrec(){
        TreeMap<Long, Parameter> parameterTreeMap = opticsOF.getParameterTreeMap();
        System.out.println("Map size "+parameterTreeMap.size());
        TreeMap<Integer, Parameter> anomalyRegionMap = new TreeMap<>();
        double percentileVal = getPercentileValue();
        System.out.println(percentileVal);
        int i = 0;
        int count = 0;
        HashMap<Integer, Parameter> tempMap = new HashMap<>();
        for(Map.Entry<Long,Parameter> entry : parameterTreeMap.entrySet()){
            Parameter parameter = entry.getValue();

            if(parameter.getAnomalyScore()>=percentileVal){
                count++;
                tempMap.put(i,parameter);
                if(tempMap.size()==10){
                    System.out.println("TEMP MAP"+tempMap.size());
                    System.out.println("Anomaly Map before "+anomalyRegionMap.size());
                    for(Map.Entry<Integer,Parameter> entry1 : tempMap.entrySet()){
                        Parameter parameter1 = entry1.getValue();
                        parameter1.setAnomalyUpdatedScore(entry1.getValue().getAnomalyScore());
                        anomalyRegionMap.put(entry1.getKey(),parameter1);
                        count--;
                    }
                    System.out.println("Anomaly Map after"+anomalyRegionMap.size());
                    /*parameter.setAnomalyUpdatedScore(parameter.getAnomalyScore());
                    anomalyRegionMap.put(i,parameter);*/
                    count = 0;
                    tempMap = new HashMap<>();
                }

            }
            else{
                if(tempMap.size()>0){
                    count=0;
                    System.out.println("Anomaly Map before else "+anomalyRegionMap.size());
                    for(Map.Entry<Integer,Parameter> entry1 : tempMap.entrySet()){
                        System.out.println("TEMP MAPPPPP"+tempMap.size());
                        Parameter parameter1 = entry1.getValue();
                        parameter1.setAnomalyUpdatedScore(percentileVal-percentileVal*0.1);
                        anomalyRegionMap.put(entry1.getKey(),parameter1);
                    }
                    System.out.println("Anomaly Map After else "+anomalyRegionMap.size());
                    tempMap = new HashMap<>();
                }
                parameter.setAnomalyUpdatedScore(parameter.getAnomalyScore());
                anomalyRegionMap.put(i,parameter);
            }
            i++;
        }
        System.out.println(anomalyRegionMap.size());
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

    public void percentileValueforTest(double start, int iterations,int gap){
        ArrayList<Double> anomalyScores = this.getAnomalyScores();
        for(int i=0;i<iterations;i++){
            this.setPercentile(start+i*gap);
            double value = this.getPercentileValue();
            try {
                PrintWriter outfile = new PrintWriter(new File(Properties.PERCENTILE_TEST_FILEPATH+"Percentile_Test_"+(start+i)+".csv"));
                for(double anomalyScore : anomalyScores){
                    if(anomalyScore>value){
                        outfile.write(anomalyScore+"\n");
                    }
                    else{
                        outfile.write(0.0+"\n");
                    }
                }
                outfile.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void runMinpointTest(int startMinPoint, int iterations, int gap) {
        opticsOF.runMinPointTest(startMinPoint,iterations,gap);
    }
}
