package org.taz.commons.util;

/**
 * Created by  Maninesan on 12/06/16.
 */

import org.taz.commons.constants.TAZConstants;
import org.taz.commons.parser.JFRParser;
import org.taz.commons.parser.JFRParserV18;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.taz.commons.parser.events.*;
import org.taz.commons.parser.memory.MemEvent;
import org.taz.commons.parser.models.GCEventsModel;


import java.util.*;

public class JFRReader {

    private static Map<String,JFRParser> jfrList = new LinkedHashMap<String,JFRParser>();
    private static JFRReader jfrReader;
    private CSVWriter csvWriter;
    private static ArrayList<Integer> memoryList= new ArrayList<Integer>();
    private static HashSet<String> set = new HashSet<String>();
    private static JFRParser parser;

    private static final Logger logger = LoggerFactory.getLogger(JFRReader.class);

    public static JFRReader getInstance() {
        if(jfrReader==null)
            jfrReader = new JFRReader();
        return jfrReader;
    }

    private JFRReader() {
        csvWriter = CSVWriter.getInstance();
    }

    public void readJFR(Map<String,String> filePaths){
        for (Map.Entry<String, String> fileEntry :filePaths.entrySet()) {
            logger.info("Loading file"+ fileEntry.getValue());
            String fileName = fileEntry.getKey();
            int index = fileName.lastIndexOf('.');
            fileName = fileName.substring(0,index);
            jfrList.put(fileName,new JFRParserV18(fileEntry.getValue()));
        }
    }

    public JFRParserV18 readSingleJFR(String filePath){
            logger.info("Loading file"+ filePath);
            String fileName = filePath;
            int index = fileName.lastIndexOf('.');
            fileName = fileName.substring(0,index);
            return new JFRParserV18(filePath);
    }

    public Map<String,JFRParser>  getJFRRecording() {
        if (jfrList.size() == 0){
            logger.warn("No JFR loaded");
            return null;
        }else{
            return jfrList;
        }
    }


    public  void getGCStates(){
        logger.info("Reading GC Events");
        for (Map.Entry<String, JFRParser> parser :jfrList.entrySet()) {
            ArrayList<Integer> stateSequence = parser.getValue().getMemoryStates();
            csvWriter.generateGCStates(stateSequence,parser.getKey());
        }

    }
    public void getPauseTimeSeries(){
        for (Map.Entry<String, JFRParser> parser :jfrList.entrySet()) {
                csvWriter.generatePauseTimeSeries(parser.getValue().getPauseTimeSeries(), parser.getKey());
        }
    }
    public void getGCTimeSeries(){
        for (Map.Entry<String, JFRParser> parser :jfrList.entrySet()) {
            csvWriter.generateGCTimeSeries(parser.getValue().getGCTimeSeries(), parser.getKey());
        }
    }



    public void getCPUEvents(){
        for (Map.Entry<String, JFRParser> parser :jfrList.entrySet()) {
            csvWriter.generateCPUEvent(parser.getValue().getCPUEvents(), parser.getKey());
        }
    }

    public ArrayList<HeapSummaryEvent> getHeapSummaryDashboard(String filePath){
        parser = readSingleJFR(filePath);
        return parser.getHeapSummaryEvents();
    }

    public ArrayList<CPULoadEvent> getCPUEventsDashboard(String filePath){
        parser = readSingleJFR(filePath);
        return parser.getCPUEvents();
    }

    public ArrayList<GarbageCollectionEvent> getGarbageCollectionEventDashboard(String filePath){
        parser = readSingleJFR(filePath);
        return parser.getGarbageCollectionEvents();
    }

    public JVMInformationEvent getJVMInformationEventDashboard(String filePath){
        parser = readSingleJFR(filePath);
        return parser.getJVMInformationEvent();
    }
    public ArrayList<JVMInformationEvent> getJVMInformationEventListDashboard(String filePath){
        parser = readSingleJFR(filePath);
        return parser.getJVMInformationEventList();
    }

    public ArrayList<InitialSystemPropertyEvent> getInitSystemPropertyEventList(String filePath){
        parser = readSingleJFR(filePath);
        return parser.getInitialSystemPropertyEventList();
    }

    public ArrayList<RecordingSettingEvent> getRecordingSettingList(String filePath){
        parser = readSingleJFR(filePath);
        return parser.getRecordingSettingEventList();
    }

    public GCEventsModel getGCEventModel(String filePath){
        parser = readSingleJFR(filePath);
        return parser.getGCEventModel();
    }

    public HashMap<String, Object> getEventsForOverviewPage(String filePath){
        HashMap<String, Object> eventMap = new HashMap<>(4);
        parser = readSingleJFR(filePath);
        eventMap.put(TAZConstants.HEAP_SUMMARY_EVENT, parser.getHeapSummaryEvents());
        eventMap.put(TAZConstants.CPU_LOAD_EVENT, parser.getCPUEvents());
        eventMap.put(TAZConstants.GC_EVENT, parser.getGarbageCollectionEvents());
        eventMap.put(TAZConstants.JVM_INFORMATION, parser.getJVMInformationEvent());
        return eventMap;
    }

    public void getGCAttributes(){
        for (Map.Entry<String, JFRParser> parser :jfrList.entrySet()) {
            ArrayList<ArrayList<String>> attriList = convertGCAttributes(parser.getValue().getGCEvents());
            csvWriter.generateGCAttributes(attriList,parser.getKey());
        }
    }

    private ArrayList<Integer> convertPauseTimeSeries(Map<Long,Double> series){
        ArrayList<Integer> convertedSeries = new ArrayList<Integer>();
        Map<Long,Double> tempSeries = series;
        for(Map.Entry<Long,Double> point:tempSeries.entrySet()) {
            if(point.getValue()>0){
                convertedSeries.add(1);
            }else{
                convertedSeries.add(0);
            }
        }
        return convertedSeries;
    }

    private ArrayList<ArrayList<String>> convertGCAttributes(Map<Long,MemEvent> map){
        ArrayList<ArrayList<String>> attriList = new ArrayList<ArrayList<String>>();
        ArrayList<String> tempList = new ArrayList<String>();
        tempList.add("GCId");
        tempList.add("Type");
        tempList.add("Cause");
        tempList.add("UsedHeap");
        tempList.add("PauseTime");
        attriList.add(tempList);
        for (Map.Entry<Long, MemEvent> memEvent :map.entrySet()) {
            MemEvent me = memEvent.getValue();
            tempList = new ArrayList<String>();
            tempList.add(Long.toString(me.getGcId()));
            tempList.add(me.getType());
            tempList.add(me.getCause());
            tempList.add(Long.toString(me.getUsedHeap()));
            tempList.add(Long.toString(me.getPauseTime()));

            attriList.add(tempList);

        }
        return attriList;
    }


    public void refreshViewList(){
        logger.info("Erase old JFR loadings");
        jfrList = new LinkedHashMap<String,JFRParser>();
    }
}
