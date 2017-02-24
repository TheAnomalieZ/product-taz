package org.taz.core.tests;


import java.io.*;


/**
 * Created by garth on 2/3/17.
 */
public class PythonTest {
    public static final String FILE_NAME = "./components/org.taz.core/src/main/python/hmm/HMM_RocVersion.py";

    public static void main(String... args) throws InterruptedException {
        try {
            String execution = "python " + FILE_NAME +" " + 2 +" " + 2 +" " + 500 +" " + 8;
            Runtime r = Runtime.getRuntime();

            Process p = r.exec(execution);
            p.waitFor();
            getExecutionResult(p);
            p.waitFor();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void getExecutionResult(Process process1) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(process1.getInputStream()));
        String line = "";

        while ((line = reader.readLine()) != null)
        {
            System.out.println(line);
        }
        reader.close();
    }



}
