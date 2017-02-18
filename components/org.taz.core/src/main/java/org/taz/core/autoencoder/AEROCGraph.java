package org.taz.core.autoencoder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Maninesan on 2/11/17.
 */
public class AEROCGraph {

    public static final String FILE_NAME = "./components/org.taz.core/src/main/python/autoencoder/TestValidation2.py";

    public static void main(String... args) throws InterruptedException {
        try {
            String execution = "python " + FILE_NAME;
            Runtime r = Runtime.getRuntime();
            Process p = r.exec(execution);
//            p.waitFor();
            getExecutionResult(p);
            p.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
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
