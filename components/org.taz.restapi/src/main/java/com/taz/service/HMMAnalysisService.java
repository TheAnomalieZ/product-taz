package com.taz.service;

import com.taz.models.AnomalyRegion;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.taz.commons.parser.events.HeapSummaryEvent;
import org.taz.commons.parser.events.RecordingEvent;
import org.taz.commons.util.JFRReader;
import org.taz.core.hmm.HMM;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by kokulan on 2/23/17.
 */

@Service
public class HMMAnalysisService {
    @Value("${service.file.path}")
    private String rootPath;

    private JFRReader jfrReader;

    public HMMAnalysisService() {
        jfrReader = JFRReader.getInstance();
    }

    public void getPageData(String fileName, ModelMap model) {
        String filePath = rootPath + "/" + fileName;
        model.addAttribute("fileName", fileName);

        HMM hmm = new HMM();
//        ArrayList<Double> scoreList  = hmm.generateScoreSeries(filePath,"App2_gctime");
        ArrayList<Double> scoreList  = hmm.generateScoreSeries(filePath,"App2_gctime");
        double threshold = hmm.getPercentileValue(scoreList, 5);
        ArrayList<Integer> labelList = hmm.generateAnomalyLabels(scoreList, threshold);
        ArrayList<Double[]> regions    = hmm.getAnomlayTimes(labelList, 10);

        ArrayList<AnomalyRegion> anomalyRegions = new ArrayList<>();

        StringBuilder graphData = new StringBuilder();
        graphData.append("[");
        int count = 0;
        for(Double entry: scoreList){
            graphData.append("[" + count + "," + entry + "," + threshold + "],");
            count++;
        }

        graphData.append("]");

        model.addAttribute("anomalyScore", graphData.toString());


        if(!regions.isEmpty()){
            RecordingEvent recordingEvent = jfrReader.getRecordingEvent(filePath);
            long JFRStartTime = recordingEvent.getStartTime()/1000000;
            AnomalyRegion aeAnomalyRegion = null;
            int regionCount = 1;

            for(Double [] region : regions) {
                aeAnomalyRegion = new AnomalyRegion();

                long startTime = region[0].longValue() * 1000 + JFRStartTime;
                long endTime = region[1].longValue() * 1000 + JFRStartTime;

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
                aeAnomalyRegion.setRegionID(regionCount);
                aeAnomalyRegion.setStartTime(startTime);
                aeAnomalyRegion.setEndTime(endTime);
                aeAnomalyRegion.setStartGCId(region[0].longValue());
                aeAnomalyRegion.setEndGCId(region[1].longValue());
                aeAnomalyRegion.setHotMethodsPercentage(hotMethodsPercentage);

                anomalyRegions.add(aeAnomalyRegion);
            }
        }
        model.addAttribute("hotMethods", anomalyRegions);


        ArrayList<HeapSummaryEvent> heapSummaryEvents = jfrReader.getHeapSummaryDashboard(filePath);

        StringBuilder heapSummaryData = new StringBuilder();
        if (!heapSummaryEvents.isEmpty()) {
            heapSummaryData.append("[");

            for (HeapSummaryEvent heapSummaryEvent : heapSummaryEvents) {
                double heapUsed = Double.parseDouble(heapSummaryEvent.getHeapUsed()) / (1024 * 1024);
                double committedHeap = Double.parseDouble(heapSummaryEvent.getHeapSpaceCommittedSize()) / (1024 * 1024);
                long time = heapSummaryEvent.getStartTimestamp() / 1000000;

                heapSummaryData.append("[" + time + ',' + committedHeap + ',' + heapUsed + "],");
            }

            heapSummaryData.append("]");
        }

        model.addAttribute("heapUsedData", heapSummaryData.toString());

    }
}
