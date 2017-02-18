package org.taz.core.autoencoder;

import java.io.*;

/**
 * Created by Maninesan on 2/18/17.
 */
public class AETrain {
    public static final String FILE_NAME = "./components/org.taz.core/src/main/python/Spearmint/spearmint/main.py";
    public static final String FILE_PATH = "./components/org.taz.core/src/main/python/autoencoder/experiments/";
    public static void setupTraining(String jfrType){
        String outputfileDir = FILE_PATH + jfrType;
        File dir = new File(outputfileDir);
        dir.mkdir();
    }
    public static void callAETrain(String jfrType) throws IOException {
        String filePath = jfrType;
        System.out.println(filePath);
        File f = new File(filePath);
        if(f.exists() && !f.isDirectory()) {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            if (br.readLine() == null) {
                System.out.println("No data points, and file empty");
                return;
            }
            //call python script
            try {
                String execution = "python " + FILE_NAME+" "+ filePath +" "+"App1_gctime";
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
        }else{
            System.out.println("File not found");
            return;
        }

    }
    private static void getExecutionResult(Process process1) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(process1.getInputStream()));
        String line = "";
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
        reader.close();
    }

}
