package org.taz.core.hmm;
import com.opencsv.CSVReader;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.math3.stat.descriptive.rank.Percentile;
import org.taz.commons.util.CSVWriter;
import org.taz.commons.util.JFRReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by vithulan on 11/25/16.
 */
public class HMM {

    private JFRReader jfrReader;
    private CSVWriter csvWriter;
    private String savefilePath;
    private String scorefilePath = null;
    private String thresholdPath = null;

    public HMM(){
        jfrReader = JFRReader.getInstance();
        csvWriter = CSVWriter.getInstance();
        savefilePath = "/home/kokulan/projects/Product_taz/product-taz/components/org.taz.core/src/main/resources/hmm/";
    }
    public ArrayList<Double> generateScoreSeries(String filePath, String jfrType) {
        String ext = "";
        String fileName = String.format("%s%s", RandomStringUtils.randomAlphanumeric(8), ext);

        String outputfileDir = savefilePath + fileName;
        File dir = new File(outputfileDir);
        dir.mkdir();

        String outputfilePath = outputfileDir+"/"+jfrType;
        csvWriter.generateGCStates(jfrReader.getGarbageCollectionStates(filePath),outputfilePath);

        try {
            HMMPythonExecutor.callPythonHMM(outputfilePath,jfrType);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Python script runtime error");
        }

        scorefilePath = outputfilePath+"_hmm_score.csv";

        CSVReader reader = null;
        ArrayList<Double> scorelist = new ArrayList<Double>();
        try {
            reader = new CSVReader(new FileReader(scorefilePath));
            String [] nextLine;
            Double score;
            while ((nextLine = reader.readNext()) != null) {
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

    public ArrayList<Integer> generateAnomalyLabels(ArrayList<Double> scorelist, double threshold){
        //double threshold = getPercentileValue(scorelist, 10);

        System.out.println("Threshold = " + threshold);
        ArrayList<Integer> labelList = new ArrayList<>();
        for(double score : scorelist){
            if(score >= threshold){
                labelList.add(1);
                //System.out.println(1);
            }
            else{
                labelList.add(0);
                //System.out.println(0);
            }

        }

        return labelList;
    }

    public double getPercentileValue (ArrayList<Double> scorelist, double percentage){
        double[] scoreArray = new double[scorelist.size()];
        for(int i=0;i<scorelist.size();i++){
            scoreArray[i] = scorelist.get(i);
        }
        Percentile percentile = new Percentile();
        percentile.setData(scoreArray);
        return percentile.evaluate(percentage);
    }

    public double generateThreshold(ArrayList<Double> scorelist, double percentage){
        double threshold = 0.0;
        double sum = 0.0;
        double length = scorelist.size();
        for(double score : scorelist){
            sum = sum + score;
        }
        double average = sum/length;
        threshold = average + average*percentage/100;
        return threshold;
    }

    public  ArrayList<Double[]> getAnomlayTimes(ArrayList<Integer> labellist, int len){
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
                for(int j=i;times < len && j<labellist.size();j++){
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
}
