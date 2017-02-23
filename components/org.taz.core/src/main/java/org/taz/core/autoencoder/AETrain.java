package org.taz.core.autoencoder;

import org.apache.commons.io.FileUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.taz.commons.util.CSVWriter;
import org.taz.commons.util.JFRReader;

import java.io.*;

/**
 * Created by Maninesan on 2/18/17.
 */
public class AETrain {
    public static final String SPEARMINT_PATH = "./components/org.taz.core/src/main/python/Spearmint/spearmint/main.py";
    public static final String FILE_PATH = "./components/org.taz.core/src/main/python/autoencoder/experiments/";
    public static final String DATA_PATH = "./components/org.taz.core/src/main/python/autoencoder/data/";
    public static final String FILE_ONE = "./files/autoencoder/trainingexample/o.py";
    public static final String FILE_TWO = "./files/autoencoder/trainingexample/config.json";

    private JFRReader jfrReader;
    private CSVWriter csvWriter;



    public AETrain(){
        jfrReader = JFRReader.getInstance();
        csvWriter = CSVWriter.getInstance();
    }

    /**
     * Setup the folders and mongodb for training
     * @param jfrType
     */
    public void setupTraining(String jfrType){
        String outputfileDir = FILE_PATH + jfrType;
        File dir = new File(outputfileDir);
        dir.mkdir();

        JSONParser parser = new JSONParser();
        try {

            Object obj = parser.parse(new FileReader(FILE_TWO));

            JSONObject jsonObject = (JSONObject) obj;
            System.out.println(jsonObject);
            jsonObject.remove("experiment-name");
            jsonObject.put("experiment-name",jfrType);
            try (FileWriter file = new FileWriter(FILE_TWO)) {

                file.write(jsonObject.toJSONString());
                file.flush();

            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }


        File source1 = new File(FILE_ONE);
        File source2 = new File(FILE_TWO);
        File dest = new File(outputfileDir);
        try {
            FileUtils.copyFileToDirectory(source1, dest);
            FileUtils.copyFileToDirectory(source2, dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Call for Training of autoencoder
     * @param filePath jfr file path
     * @param jfrType System name
     */
    public void callAETrain(String filePath, String jfrType){
        try {
            String outputfilePath = FILE_PATH+jfrType;

            csvWriter.generateGCTimeSeries(jfrReader.getHeapTimeSeries(filePath),DATA_PATH);

            setupTraining(jfrType);


            String execution = "python " + SPEARMINT_PATH +" "+ outputfilePath;
            Runtime r = Runtime.getRuntime();
            Process p = r.exec(execution);
            getExecutionResult(p);
            p.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("Python script ERROR");
        }



    }

    /**
     * Show python outputs in java std out
     * @param process1
     * @throws IOException
     */
    private  void getExecutionResult(Process process1) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(process1.getInputStream()));
        String line = "";
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
        reader.close();
    }

}
