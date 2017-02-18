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
    public ArrayList<Double> generateScoreSeries(String filePath, String jfrType) {
        String ext = "";
        String fileName = String.format("%s%s", RandomStringUtils.randomAlphanumeric(8), ext);

        String outputfileDir = savefilePath + fileName;
        File dir = new File(outputfileDir);
        dir.mkdir();

        String outputfilePath = outputfileDir+"/"+jfrType;
        csvWriter.generateGCTimeSeries(jfrReader.getHeapTimeSeries(filePath),outputfilePath);

        try {
            AEPythonExecutor.callPythonAE(outputfilePath,jfrType);
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

    public ArrayList<Integer> getAnomalyTimes( ArrayList<Double>  scorelist) {

        ArrayList<Double>  anomalyScores = new ArrayList<Double>();
        for(double a:scorelist){
            anomalyScores.add(a);
        }

        double sum = 0;
        for (double i : scorelist) {
            sum += i;
        }
        System.out.println(sum);
        sum *= 0.80;
        System.out.println(sum);
        ArrayList<Double> sortlist = scorelist;
        Collections.sort(sortlist);

        double count = 0;
        int i = 0;
        double item = 0;
        while (count < sum) {
            item = sortlist.get(i++);
//            System.out.println(item);
            count += item;
        }
        System.out.println(item );
        System.out.println(--i);

        ArrayList<Integer> labellist = new ArrayList<Integer>();
        for (double d : anomalyScores) {
            if (d > item) {
                labellist.add(0);
                System.out.println(0);

            } else {
                labellist.add(1);
                System.out.println(1);

            }
        }
        return labellist;

    }

    public static void calculatePercentiles( ArrayList<Double>  scorelist) {
        for (int i = 0; i < scorelist.size(); i++) {
            int count = 0;
            int start = i;
            if (i > 0) {
                while (i > 0 && scorelist.get(i) == scorelist.get(i - 1)) {
                    count++;
                    i++;
                }
            }
            double perc = ((start - 0) + (0.5 * count));
            perc = perc / (scorelist.size() - 1);
            for (int k = 0; k < count + 1; k++)
                System.out.println("Percentile for value " + (start + k + 1)
                        + " = " + perc * 100);
        }
    }

}