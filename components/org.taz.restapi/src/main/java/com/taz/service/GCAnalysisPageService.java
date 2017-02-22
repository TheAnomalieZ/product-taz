package com.taz.service;

import com.taz.data.GCEventModelService;
import com.taz.models.ClusteringAnomalyRegion;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.taz.commons.util.JFRReader;
import org.taz.core.clustering.ClusteringHandler;
import org.taz.core.clustering.util.Parameter;

import java.util.*;

/**
 * Created by K.Kokulan on 2/17/2017.
 */

@Service
public class GCAnalysisPageService {
    @Value("${service.file.path}")
    private String rootPath;

    private JFRReader jfrReader;

    public GCAnalysisPageService() {
        jfrReader = JFRReader.getInstance();
    }

    public void getPageData(String fileName, ModelMap model) {
        String filePath = rootPath + "/" + fileName;
        model.addAttribute("fileName", fileName);


        ArrayList<ClusteringAnomalyRegion> anomalyRegions = new ArrayList<>();

        ClusteringHandler clusteringHandler = new ClusteringHandler(filePath);
        clusteringHandler.setPercentile(98.0);
        TreeMap<Integer, Parameter> anomalyRegion = clusteringHandler.getAnomalyPointsRegion();
        StringBuilder anomalyScoreGraphData = new StringBuilder();
        double threshold = clusteringHandler.getPercentileValue();

        anomalyScoreGraphData.append("[");

        if(!anomalyRegion.isEmpty()){
            int region = 1;
            int count = 0;
            Parameter start = null;
            Parameter end = null;

            for(Map.Entry<Integer, Parameter> entry : anomalyRegion.entrySet()){
                Parameter parameter = entry.getValue();
                anomalyScoreGraphData.append("[" + parameter.getGcID() + "," + parameter.getAnomalyClassificationScore() + "," + threshold + "],");

                if(parameter.getAnomalyClassificationScore() >= threshold && count == 0){
                    start = parameter;
                    count++;
                } else if(parameter.getAnomalyClassificationScore() >= threshold){
                    count++;
                    end = parameter;
                }

                if(parameter.getAnomalyClassificationScore() < threshold && count != 0){
                    if(count > 5 && end != null) {
                        ClusteringAnomalyRegion clusteringAnomalyRegion = new ClusteringAnomalyRegion();
                        clusteringAnomalyRegion.setRegionID(region);
                        clusteringAnomalyRegion.setStartTime(start.getStartTime());
                        clusteringAnomalyRegion.setEndTime(end.getEndTime());
                        clusteringAnomalyRegion.setStartGCId(start.getGcID());
                        clusteringAnomalyRegion.setEndGCId(end.getGcID());

                        anomalyRegions.add(clusteringAnomalyRegion);
                        region++;
                    }
                    count = 0;
                    start = null;
                    end = null;
                }
            }
        }

        anomalyScoreGraphData.append("]");

        model.addAttribute("anomalyScore", anomalyScoreGraphData.toString());

        if(!anomalyRegions.isEmpty()){
            for(ClusteringAnomalyRegion clusteringAnomalyRegion : anomalyRegions) {
                long startTime = clusteringAnomalyRegion.getStartTime()/1000000;
                long endTime = clusteringAnomalyRegion.getEndTime()/1000000;

                LinkedHashMap<ArrayList<String>, Long> hotMethods = jfrReader.getHotMethods(filePath, startTime, endTime);
                LinkedHashMap<String, Double> hotMethodsPercentage = new LinkedHashMap<>();

                if (!hotMethods.isEmpty()) {
                    Long totalTime = 0L;

                    for (Map.Entry<ArrayList<String>, Long> entry : hotMethods.entrySet()) {
                        totalTime += entry.getValue();
                    }
                    for (Map.Entry<ArrayList<String>, Long> entry : hotMethods.entrySet()) {
                        Long time = entry.getValue();
                        double percentage = ((float) time / totalTime) * 100;
                        hotMethodsPercentage.put(entry.getKey().get(0), percentage);
                    }
                }
                clusteringAnomalyRegion.setHotMethodsPercentage(hotMethodsPercentage);
            }
        }
        model.addAttribute("hotMethods", anomalyRegions);
    }
}
