package org.taz.commons.util;
/**
 * Created by  Maninesan on 12/06/16.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.taz.commons.parser.events.CPULoadEvent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;

public class CSVWriter {
    private static final Logger logger = LoggerFactory.getLogger(JFRReader.class);

    private static CSVWriter csvWriter;

    public static CSVWriter getInstance() {

        if(csvWriter==null)
            csvWriter = new CSVWriter();
        return csvWriter;
    }

    private CSVWriter() {
    }


    public void generateGCStates(ArrayList<Integer> list,String fileName){
        logger.info("GC state sequences");
        PrintWriter outfile = null;
        try {
            outfile = new PrintWriter(new File(fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int i=0;
        while(i<list.size()) {
            System.out.print(list.get(i) + "\n");
            outfile.append(list.get(i++).toString() + "\n");
        }
        outfile.close();

    }

    public void getAEGCSequence(ArrayList<Integer> list,File fileName){
        logger.info("AE GC state sequences");
        PrintWriter outfile = null;
        try {
            outfile = new PrintWriter(fileName+".csv");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int i=0;
        while(i<list.size()){
            for (int j = 0; j < 30; j++) {
                if(i>=list.size()){
                    break;
                }else if (j == 0) {
                    System.out.print(list.get(i));
                    outfile.append(list.get(i++).toString());
                } else if (j == 29) {
                    System.out.print("," + list.get(i) + "\n");
                    outfile.append("," + list.get(i++).toString() + "\n");
                } else {
                    System.out.print("," + list.get(i));
                    outfile.append("," + list.get(i++).toString());
                }
            }
        }


        outfile.close();

    }

    public void generatePauseTimeSeries(Map<Long,Double> series, String fileName){

        logger.info("Pause Time Series");
        PrintWriter outfile = null;
        try {
            outfile = new PrintWriter(new File(fileName+"_pausetime.csv"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Long i=0L;
        double temp;
        for (Map.Entry<Long, Double> a : series.entrySet()) {
            temp = a.getValue();
            System.out.print(temp + "\n");
            outfile.append(temp + "\n");
        }
        outfile.close();
    }

    public void generatePauseTimeSeries(ArrayList<Short> series, String fileName){

        logger.info("Pause Time Series");
        PrintWriter outfile = null;
        try {
            outfile = new PrintWriter(new File(fileName+"_pausetime.csv"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for (Short a : series) {;
            System.out.print(a + "\n");
            outfile.append(a + "\n");
        }
        outfile.close();
    }

    public void generateCPUEvent(ArrayList<CPULoadEvent> cpuLoadEvents, String fileName){

        logger.info("CPU Event Series");
        PrintWriter outfile = null;
        try {
            outfile = new PrintWriter(new File(fileName+"_CPU_Event.csv"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Long i=0L;
        String  temp;
        for (CPULoadEvent iterator : cpuLoadEvents) {
            temp = iterator.getJvmUser()+','+iterator.getMachineTotal()+","+iterator.getJvmSystem();
            System.out.print(temp + "\n");
            outfile.append(temp + "\n");
        }
        outfile.close();
    }



    public void generateGCTimeSeries(ArrayList<Double> series, String fileName){

        logger.info("GC Time Series");
        PrintWriter outfile = null;
        try {
            outfile = new PrintWriter(new File(fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        for (double a : series) {
            System.out.print(a + "\n");
            outfile.append(a + "\n");
        }
        outfile.close();
    }


    public void generateGCAttributes(ArrayList<ArrayList<String>> list, String fileName){

        logger.info("All GC attributes");
        PrintWriter outfile = null;
        try {
            outfile = new PrintWriter(new File(fileName+"_gc_at.csv"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        for(ArrayList<String> sublist:list){
            for(String attri:sublist){
                if(sublist.indexOf(attri)==0){
                    System.out.print(attri);
                    outfile.append(attri);
                }else{
                    System.out.print(","+attri);
                    outfile.append(","+attri);
                }
            }
            System.out.print("\n");
            outfile.append("\n");
        }
        outfile.close();
    }


}





