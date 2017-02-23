package org.taz.core.hmm;

import java.io.*;

/**
 * Created by suve on 21/02/17.
 */
public class HMMPythonExecutor {
    public static final String FILE_NAME = "./components/org.taz.core/src/main/python/hmm/HMMApp1/output.py";

    public static void callPythonHMM(String fileName, String jfrType) throws IOException {
        String filePath = fileName;
        //String modelPath = "./components/org.taz.core/src/main/python/hmm/HMMApp1/model.pkl";
        String modelPath = "./files/hmm/train/app1/model1.pkl";
        //System.out.println(filePath);
        File f = new File(filePath);
        if(f.exists() && !f.isDirectory()) {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            if (br.readLine() == null) {
                System.out.println("No data points, and file empty");
                return;
            }
            //call python script
            try {
                String execution = "python " + FILE_NAME + " " + "30" + " " + "8030" + " " + filePath +" "+ modelPath;
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
