package org.taz.commons.util;
/**
 * Created by  Maninesan on 12/06/16.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
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


    public void generateGCStates(ArrayList<Integer> list,File file){
        logger.info("GC state sequences");
        PrintWriter outfile = null;
        try {
            outfile = new PrintWriter(file);
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

    public void getAEGCSequence(ArrayList<Integer> list,File file){
        logger.info("AE GC state sequences");
        PrintWriter outfile = null;
        try {
            outfile = new PrintWriter(file);
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

    public void generatePauseTimeSeries(Map<Long,Long> series, File file){

        logger.info("Pause Time Series");
        PrintWriter outfile = null;
        try {
            outfile = new PrintWriter(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Long i=0L;
        Long temp;
        while(i<series.size()) {
            temp = series.get(++i);
            System.out.print(i+","+temp + "\n");
            outfile.append(i+","+temp + "\n");

        }
        outfile.close();
    }
}





