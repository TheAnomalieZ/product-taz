package org.taz.core.clustering.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by vithulan on 1/16/17.
 */
public class CSVGenerator {
    private TreeMap<Long, Parameter> parameterTreeMap;
    private ArrayList<Long> gcIdList;
    private ArrayList<Long> timegap;
    private TreeMap<Long,Long> timeGapMap;

    public CSVGenerator(TreeMap<Long, Parameter> parameterTreeMap) {
        this.parameterTreeMap = parameterTreeMap;
        getTimegap();
    }

    public String generateCSV(){
        System.out.println("Generating csv..");
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName = "None";
        try {
            //String path = "files/clustering/attribute_tables/";
            PrintWriter outfile = new PrintWriter(new File(Properties.ATTRIBUTE_TABLE_FILEPATH+"clustering_attributes_"+timeStamp+".csv"));
            fileName = "clustering_attributes_"+timeStamp+".csv";
            for(Map.Entry<Long,Long> entry : timeGapMap.entrySet()){
                outfile.append(Long.toString(parameterTreeMap.get(entry.getKey()).getHeapUsed())+",");
                outfile.append(Long.toString(parameterTreeMap.get(entry.getKey()).getgCPauseDuration())+",");
                outfile.append(Long.toString(parameterTreeMap.get(entry.getKey()).getOldObjectSpaceUsed())+",");
                outfile.append(Long.toString(entry.getValue())+"\n");
            }
            outfile.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return fileName;
    }

    private void getTimegap() {
        timeGapMap = new TreeMap<>();
        /*timegap = new ArrayList<>();
        gcIdList = new ArrayList<>();*/
        for (Map.Entry<Long, Parameter> entry : parameterTreeMap.entrySet()) {
            long gcId = entry.getKey();
            long endTime = entry.getValue().getEndTime();
            if(parameterTreeMap.containsKey(gcId+1)){
                timeGapMap.put(gcId+1,parameterTreeMap.get(gcId+1).getStartTime()-endTime);
                //System.out.println((gcId+1)+" - "+(parameterTreeMap.get(gcId+1).getStartTime()-endTime));
                /*timegap.add(parameterTreeMap.get(gcId+1).getStartTime()-endTime);
                gcIdList.add(gcId);*/
            }

        }
    }

    public int getTotalPoints (){
        return timeGapMap.size();
    }


}
