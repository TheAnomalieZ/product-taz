package com.taz.service;

import com.taz.models.AnomalyRegion;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.taz.commons.parser.events.GarbageCollectionEvent;
import org.taz.commons.parser.events.HeapSummaryEvent;
import org.taz.commons.parser.events.RecordingEvent;
import org.taz.commons.util.JFRReader;
import org.taz.core.hmm.HMM;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
            ArrayList<GarbageCollectionEvent> garbageCollectionEvents = jfrReader.getGarbageCollectionEventDashboard(filePath);

            int startGcId = Integer.parseInt(garbageCollectionEvents.get(0).getGcId());

            long JFRStartTime = recordingEvent.getStartTime()/1000000;
            AnomalyRegion aeAnomalyRegion = null;
            int regionCount = 1;

            for(Double [] region : regions) {
                aeAnomalyRegion = new AnomalyRegion();

                int startGc = startGcId + region[0].intValue();
                int endValue = startGcId + region[1].intValue();

                long startTime = 0L;
                long endTime = 0L;

                if(garbageCollectionEvents.get(startGc) != null) {
                    startTime = garbageCollectionEvents.get(startGc).getStartTimestamp()/ 1000000;
                    endTime = garbageCollectionEvents.get(endValue).getStartTimestamp() / 1000000;
                }

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
                        hotMethodsPercentage.put(entry.getKey().get(0), round(percentage, 2));
                    }
                }
                aeAnomalyRegion.setRegionID(regionCount);
                aeAnomalyRegion.setStartTime(startTime);
                aeAnomalyRegion.setEndTime(endTime);
                aeAnomalyRegion.setStartGCId(region[0].longValue());
                aeAnomalyRegion.setEndGCId(region[1].longValue());
                aeAnomalyRegion.setHotMethodsPercentage(hotMethodsPercentage);

                regionCount++;
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

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
