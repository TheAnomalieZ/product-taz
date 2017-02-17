package org.taz.core.util;


import java.io.*;


/**
 * Created by garth on 2/3/17.
 */
public class Test {
    public static final String FILE_NAME = "./components/org.taz.core/src/main/python/hmm/HMM_RocVersion.py";
//    public static final String FILE_NAME = "./components/org.taz.core/src/main/python/mani.py";

    public static void main(String... args) throws InterruptedException {
        try {

            String execution = "python " + FILE_NAME +" " + 2 +" " + 2 +" " + 500 +" " + 8;
//          Process process1 = new ProcessBuilder("python", FILE_NAME, "" + 13, "" + 1, "" + 20, "" + 8).start();

//            new ProcessBuilder("python", FILE_NAME).start();

            Runtime r = Runtime.getRuntime();

            Process p = r.exec(execution);
            p.waitFor();
            getExecutionResult(p);
//            cleanUp();
            p.waitFor();



        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void cleanUp() {
//        new File(FILE_NAME).delete();
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