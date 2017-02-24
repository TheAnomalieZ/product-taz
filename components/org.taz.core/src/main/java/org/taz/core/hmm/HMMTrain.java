package org.taz.core.hmm;

import org.apache.commons.lang.RandomStringUtils;
import org.python.antlr.ast.Num;
import org.python.antlr.ast.While;
import org.taz.commons.util.CSVWriter;
import org.taz.commons.util.JFRReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by suve on 23/02/17.
 */
public class HMMTrain {
    private JFRReader jfrreader;
    private CSVWriter csvWriter;
    private static final String APP_FILE_PATH = "./files/hmm/train/";
    private static final String DATA_FILE_PATH = "./files/hmm/train/";
    private static final String SCRIPT_FILE_PATH = "./components/org.taz.core/src/main/python/hmm/HMMApp1/trainHMM.py";
    private String savefilePath;

    public HMMTrain(){
        jfrreader = JFRReader.getInstance();
        csvWriter = CSVWriter.getInstance();

    }

    public boolean startHMMTraining(String filepath, String appName){
        boolean done = false;
        String ext = "";
        String fileName = String.format("%s%s", RandomStringUtils.randomAlphanumeric(8), ext);

        String outputfileDir = APP_FILE_PATH + appName;
        File dir = new File(outputfileDir);
        dir.mkdir();

        String outputfilePath = outputfileDir + "/" + appName;
        String modelsavepath = outputfileDir + "/";
        String modelpath = modelsavepath + "model" + appName + ".pkl";
        HMMConstants.setModelPath(modelpath);
        System.out.println(outputfileDir);
        csvWriter.generateGCStates(jfrreader.getGarbageCollectionStates(filepath),outputfilePath);
        try{
            String execution = "python " + SCRIPT_FILE_PATH + " " + outputfilePath + " " + modelsavepath + " " + appName;
            //String execution = "python " + SCRIPT_FILE_PATH + " " + outputfilePath + " " + HMMConstants.MODEL_PATH + " " + appName;
            Runtime runtime = Runtime.getRuntime();
            Process process = runtime.exec(execution);
            getExecutionResults(process);
            process.waitFor();
            done = true;

        }catch(IOException e){
            e.printStackTrace();
        }catch(InterruptedException e){
            e.printStackTrace();
        }

        return done;
    }

    public void getExecutionResults(Process process) throws IOException{
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String output = "";
        while((output = bufferedReader.readLine()) != null){
            System.out.println(output);
        }
        bufferedReader.close();
    }
}
