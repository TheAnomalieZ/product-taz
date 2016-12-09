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

public class CSVWriter {
    private static final Logger logger = LoggerFactory.getLogger(JFRReader.class);

    private static File courseCSV = new File("/home/mani/jfr/GCStates.csv");

    private static File trainCSV = new File("/home/mani/jfr/manitrain.csv");

    private static ArrayList<Integer> list= new ArrayList<Integer>();

    private static CSVWriter csvWriter = new CSVWriter();
    private static HashSet<String> set = new HashSet<String>();

    public static CSVWriter getInstance() {
        return csvWriter;
    }

    private CSVWriter() {
    }


    public void getGCStates(ArrayList<Integer> stateSequence, String index) {
        if (!set.contains(index)) {
            set.add(index);

            for (int i = 0; i < stateSequence.size(); i++) {
                list.add(stateSequence.get(i));
            }

        }
    }

    public void getHMMGCSequence(){
        logger.info("HMM GC state sequences");
        PrintWriter outfile = null;
        try {
            outfile = new PrintWriter(trainCSV);
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


    public void getAEGCSequence(){
        logger.info("AE GC state sequences");
        PrintWriter outfile = null;
        try {
            outfile = new PrintWriter(trainCSV);
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
}





