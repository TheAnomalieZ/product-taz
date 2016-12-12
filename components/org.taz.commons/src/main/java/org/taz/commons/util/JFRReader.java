package org.taz.commons.util;

/**
 * Created by  Maninesan on 12/06/16.
 */

import org.taz.commons.parser.JFRParser;
import org.taz.commons.parser.JFRParserV18;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.taz.commons.parser.memory.MemEvent;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;

public class JFRReader {

    private static Map<String,JFRParser> jfrList = new LinkedHashMap<String,JFRParser>();
    private static JFRReader jfrReader;
    private CSVWriter csvWriter;
    private static ArrayList<Integer> memoryList= new ArrayList<Integer>();
    private static HashSet<String> set = new HashSet<String>();

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
            csvWriter.generatePauseTimeSeries(parser.getValue().getPauseTimeSeries(),parser.getKey());
        }
    }

    public void getGCAttributes(){
        for (Map.Entry<String, JFRParser> parser :jfrList.entrySet()) {
            ArrayList<ArrayList<String>> attriList = gcAttributes(parser.getValue().getGCEvents());
            csvWriter.generateGCAttributes(attriList,parser.getKey());
        }
    }

    public ArrayList<ArrayList<String>> gcAttributes(Map<Long,MemEvent> map){
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
