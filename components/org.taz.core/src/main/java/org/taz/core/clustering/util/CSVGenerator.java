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

    /**
     * Generate CSV file of attributes for clustering
     * @return created CSV's file path
     */
    public String generateCSV(){
        System.out.println("Generating csv..");
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName = "None";
        try {
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

    public String generateTestCSVs(int i){
        if(i==0) {
            System.out.println("Generating test csv..");
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String fileName = "None";
            try {
                PrintWriter outfile = new PrintWriter(new File(Properties.ATTRIBUTE_TABLE_FILEPATH + "heap_duration_old_meta_" + timeStamp + ".csv"));
                fileName = "heap_duration_old_meta_" + timeStamp + ".csv";
                for (Map.Entry<Long, Long> entry : timeGapMap.entrySet()) {
                    outfile.append(Long.toString(parameterTreeMap.get(entry.getKey()).getHeapUsed()) + ",");
                    outfile.append(Long.toString(parameterTreeMap.get(entry.getKey()).getgCPauseDuration()) + ",");
                    outfile.append(Long.toString(parameterTreeMap.get(entry.getKey()).getOldObjectSpaceUsed()) + ",");
                    outfile.append(Long.toString(parameterTreeMap.get(entry.getKey()).getMetaspaceUsed()) + "\n");
                    //outfile.append(Long.toString(entry.getValue()) + "\n");
                }
                outfile.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return fileName;
        }
        if(i==1) {
            System.out.println("Generating test csv..");
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String fileName = "None";
            try {
                PrintWriter outfile = new PrintWriter(new File(Properties.ATTRIBUTE_TABLE_FILEPATH + "heap_duration_old_" + timeStamp + ".csv"));
                fileName = "heap_duration_old_" + timeStamp + ".csv";
                for (Map.Entry<Long, Long> entry : timeGapMap.entrySet()) {
                    outfile.append(Long.toString(parameterTreeMap.get(entry.getKey()).getHeapUsed()) + ",");
                    outfile.append(Long.toString(parameterTreeMap.get(entry.getKey()).getgCPauseDuration()) + ",");
                    outfile.append(Long.toString(parameterTreeMap.get(entry.getKey()).getOldObjectSpaceUsed()) + "\n");
                    //outfile.append(Long.toString(entry.getValue()) + "\n");
                }
                outfile.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return fileName;
        }
        if(i==2) {
            System.out.println("Generating test csv..");
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String fileName = "None";
            try {
                PrintWriter outfile = new PrintWriter(new File(Properties.ATTRIBUTE_TABLE_FILEPATH + "heap_duration_" + timeStamp + ".csv"));
                fileName = "heap_duration_" + timeStamp + ".csv";
                for (Map.Entry<Long, Long> entry : timeGapMap.entrySet()) {
                    outfile.append(Long.toString(parameterTreeMap.get(entry.getKey()).getHeapUsed()) + ",");
                    outfile.append(Long.toString(parameterTreeMap.get(entry.getKey()).getgCPauseDuration()) + "\n");
                    //outfile.append(Long.toString(parameterTreeMap.get(entry.getKey()).getOldObjectSpaceUsed()) + ",");
                    //outfile.append(Long.toString(entry.getValue()) + "\n");
                }
                outfile.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return fileName;
        }
        if(i==3) {
            System.out.println("Generating test csv..");
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String fileName = "None";
            try {
                PrintWriter outfile = new PrintWriter(new File(Properties.ATTRIBUTE_TABLE_FILEPATH + "heap_old_" + timeStamp + ".csv"));
                fileName = "heap_old_" + timeStamp + ".csv";
                for (Map.Entry<Long, Long> entry : timeGapMap.entrySet()) {
                    outfile.append(Long.toString(parameterTreeMap.get(entry.getKey()).getHeapUsed()) + ",");
                    //outfile.append(Long.toString(parameterTreeMap.get(entry.getKey()).getgCPauseDuration()) + ",");
                    outfile.append(Long.toString(parameterTreeMap.get(entry.getKey()).getOldObjectSpaceUsed()) + "\n");
                   // outfile.append(Long.toString(entry.getValue()) + "\n");
                }
                outfile.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return fileName;
        }
        if(i==4) {
            System.out.println("Generating test csv..");
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String fileName = "None";
            try {
                PrintWriter outfile = new PrintWriter(new File(Properties.ATTRIBUTE_TABLE_FILEPATH + "heap_meta_" + timeStamp + ".csv"));
                fileName = "heap_meta_" + timeStamp + ".csv";
                for (Map.Entry<Long, Long> entry : timeGapMap.entrySet()) {
                    outfile.append(Long.toString(parameterTreeMap.get(entry.getKey()).getHeapUsed()) + ",");
                    outfile.append(Long.toString(parameterTreeMap.get(entry.getKey()).getMetaspaceUsed()) + "\n");
                    //outfile.append(Long.toString(parameterTreeMap.get(entry.getKey()).getOldObjectSpaceUsed()) + ",");
                   // outfile.append(Long.toString(entry.getValue()) + "\n");
                }
                outfile.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return fileName;
        }
        if(i==5) {
            System.out.println("Generating test csv..");
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String fileName = "None";
            try {
                PrintWriter outfile = new PrintWriter(new File(Properties.ATTRIBUTE_TABLE_FILEPATH + "heap_duration_meta_" + timeStamp + ".csv"));
                fileName = "heap_duration_meta_" + timeStamp + ".csv";
                for (Map.Entry<Long, Long> entry : timeGapMap.entrySet()) {
                    outfile.append(Long.toString(parameterTreeMap.get(entry.getKey()).getHeapUsed()) + ",");
                    outfile.append(Long.toString(parameterTreeMap.get(entry.getKey()).getgCPauseDuration()) + ",");
                    outfile.append(Long.toString(parameterTreeMap.get(entry.getKey()).getMetaspaceUsed()) + "\n");
                   // outfile.append(Long.toString(entry.getValue()) + "\n");
                }
                outfile.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return fileName;
        }
        if(i==6) {
            System.out.println("Generating test csv..");
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String fileName = "None";
            try {
                PrintWriter outfile = new PrintWriter(new File(Properties.ATTRIBUTE_TABLE_FILEPATH + "heap_duration_old_gap_" + timeStamp + ".csv"));
                fileName = "heap_duration_old_gap_" + timeStamp + ".csv";
                for (Map.Entry<Long, Long> entry : timeGapMap.entrySet()) {
                    outfile.append(Long.toString(parameterTreeMap.get(entry.getKey()).getHeapUsed()) + ",");
                    outfile.append(Long.toString(parameterTreeMap.get(entry.getKey()).getOldObjectSpaceUsed()) + ",");
                    outfile.append(Long.toString(parameterTreeMap.get(entry.getKey()).getgCPauseDuration()) + ",");
                    outfile.append(Long.toString(entry.getValue()) + "\n");
                }
                outfile.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return fileName;
        }
        if(i==7) {
            System.out.println("Generating test csv..");
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String fileName = "None";
            try {
                PrintWriter outfile = new PrintWriter(new File(Properties.ATTRIBUTE_TABLE_FILEPATH + "heap_duration_gap_" + timeStamp + ".csv"));
                fileName = "heap_duration_gap_" + timeStamp + ".csv";
                for (Map.Entry<Long, Long> entry : timeGapMap.entrySet()) {
                    outfile.append(Long.toString(parameterTreeMap.get(entry.getKey()).getHeapUsed()) + ",");
                    outfile.append(Long.toString(parameterTreeMap.get(entry.getKey()).getgCPauseDuration()) + ",");
                   // outfile.append(Long.toString(parameterTreeMap.get(entry.getKey()).getOldObjectSpaceUsed()) + ",");
                    outfile.append(Long.toString(entry.getValue()) + "\n");
                }
                outfile.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return fileName;
        }
        return "None";
    }

    private void getTimegap() {
        timeGapMap = new TreeMap<>();
        for (Map.Entry<Long, Parameter> entry : parameterTreeMap.entrySet()) {
            long gcId = entry.getKey();
            long endTime = entry.getValue().getEndTime();
            if(parameterTreeMap.containsKey(gcId+1)){
                timeGapMap.put(gcId+1,parameterTreeMap.get(gcId+1).getStartTime()-endTime);
            }

        }
    }

    public int getTotalPoints (){
        return timeGapMap.size();
    }


}
