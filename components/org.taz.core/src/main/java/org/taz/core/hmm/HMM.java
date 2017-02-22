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
        savefilePath = System.getProperty("user.dir") + "/components/org.taz.core/src/main/resources/hmm/";
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

}
