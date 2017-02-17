package org.taz.core.util;

import com.opencsv.CSVReader;
import org.datavec.api.util.MathUtils;
import org.taz.commons.util.CSVWriter;
import org.taz.commons.util.JFRReader;
import org.apache.commons.lang.RandomStringUtils;
import org.taz.core.autoencoder.AEPythonExecutor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Created by Maninesan on 2/16/17.
 */
public class AETest {
    private JFRReader jfrReader;
    private CSVWriter csvWriter;
    private String savefilePath;
    private String scorefilePath = null;


    public AETest() {
        jfrReader = JFRReader.getInstance();
        csvWriter = CSVWriter.getInstance();
        savefilePath = System.getProperty("user.dir") + "/components/org.taz.core/src/main/resources/";
    }
    /*
    * Method to get the anomaly scores from autoencoder
    *
    */
    public ArrayList<Double> generateScoreSeries(String filePath) {
        String ext = "";
        String fileName = String.format("%s%s", RandomStringUtils.randomAlphanumeric(8), ext);

        String outputfileDir = savefilePath + fileName;
        File dir = new File(outputfileDir);
        dir.mkdir();

        String outputfilePath = outputfileDir+"/gctime";
        csvWriter.generateGCTimeSeries(jfrReader.getHeapTimeSeries(filePath),outputfilePath);

        try {
            AEPythonExecutor.callPythonAE(outputfilePath);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Python script runtime error");
        }

        scorefilePath = outputfilePath+"_ae_score.csv";

        CSVReader reader = null;
        ArrayList<Double> scorelist = new ArrayList<Double>();
        try {
            reader = new CSVReader(new FileReader( scorefilePath));
            String [] nextLine;
            Double score;
            while ((nextLine = reader.readNext()) != null) {
                // nextLine[] is an array of values from the line
                score = Double.parseDouble(nextLine[0]);
                System.out.println(score) ;
                scorelist.add(score);
            }
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        return scorelist;

    }

    public ArrayList<Integer> getAnomalyTimes(String scorefilePath) {
        CSVReader reader = null;
        ArrayList<Double> scorelist = new ArrayList<Double>();
        ArrayList<Double[]> anomalies = new ArrayList<Double[]>();
        try {
            reader = new CSVReader(new FileReader(scorefilePath));
            String[] nl;
            Double score;
            while ((nl = reader.readNext()) != null) {
                score = Double.parseDouble(nl[0]);
                scorelist.add(score);
            }
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        double sum = 0;
        for (double i : scorelist) {
            sum += i;
        }
        System.out.println(sum);
        sum *= 0.90;
        ArrayList<Double> sortlist = scorelist;
        Collections.sort(sortlist);

            double count = 0;
            int i = 0;
            double item = 0;
            while (count >= sum) {
                item = sortlist.get(i++);
                count += item;
            }
            System.out.println(item + ' ' + i);

            ArrayList<Integer> labellist = new ArrayList<Integer>();
            for (double d : scorelist) {
                if (d > item) {
                    labellist.add(0);
                } else {
                    labellist.add(1);
                }
            }
            return labellist;

        }

    }