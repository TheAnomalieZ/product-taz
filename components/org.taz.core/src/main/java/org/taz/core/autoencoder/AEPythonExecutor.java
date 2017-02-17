package org.taz.core.autoencoder;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Maninesan on 2/5/17.
 */
public class AEPythonExecutor {
    public static final String FILE_NAME = "./components/org.taz.core/src/main/python/autoencoder/output.py";

    public static void main(String... args) throws InterruptedException, IOException {
             callPythonAE("App1_gcstate_train");
    }

    public static void callPythonAE(String fileName) throws IOException {
        String filePath = fileName;
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
