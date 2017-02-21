package org.taz.core.autoencoder;

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
public class AE {
    private JFRReader jfrReader;
    private CSVWriter csvWriter;
    private String savefilePath;
    private String scorefilePath = null;
    private String thresholdPath = null;


    public AE() {
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
        thresholdPath = outputfilePath+"_threshold.csv";

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

        try {
            reader = new CSVReader(new FileReader( thresholdPath));
            String [] nl;
            Double t;
            while ((nl = reader.readNext()) != null) {
                t = Double.parseDouble(nl[0]);
                System.out.println(t) ;
                scorelist.add(t);
            }
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        return scorelist;

    }

    /*
    * Method to do the threashold calculation for the anomaly scores derive from autoencoder
    *
    */
    public ArrayList<Integer> getAnomalyLabels( ArrayList<Double>  scorelist) {
        int lastIndex = scorelist.size()-1;
        double th = scorelist.get(lastIndex);
        scorelist.remove(lastIndex);
        ArrayList<Integer> labellist = new ArrayList<>();
        for(double a:scorelist){
            if (a > th) {
                labellist.add(0);
                System.out.println(0);
            } else {
                labellist.add(1);
                System.out.println(1);
            }
        }
        return labellist;

    }

    /*
    * Method to get the anomaly time periods
    *
    */
    public  ArrayList<Double[]> getAnomlayTimes(ArrayList<Integer> labellist){
        Double[] time = new Double[2];
        ArrayList<Double[]> anomalies = new ArrayList<>();
        int order = 0;
        int i=0;
        while(i<labellist.size()){
            int item = labellist.get(i);

            if(item==0){
                time[0] =Double.valueOf(i);
                int times =0;
                int last = 0;
                for(int j=i;times<10 && j<labellist.size();j++){
                    if(labellist.get(j)==1){
                        times++;
                    }else{
                        times=0;
                    }
                    last = j;
                }
                time[1] = Double.valueOf(--last);
                i=last;
                anomalies.add(time);
                time = new Double[2];
            }else{
                i++;
            }
        }
        for(Double[] w:anomalies){
            System.out.println(w[0]+"  "+w[1]);
        }
        return anomalies;
    }

    /*
    * Method to calculate percentile values
    *
    */
    public void calculatePercentile( ArrayList<Double>  scorelist){
        ArrayList<Double>  anomalyScores = new ArrayList<Double>();
        for(double a:scorelist){
            anomalyScores.add(a);
        }

        double sum = 0;
        for (double i : scorelist) {
            sum += i;
        }
        System.out.println(sum);
        sum *= 0.90;
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
    }


}