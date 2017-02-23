package org.taz.core.hmm;

import org.taz.commons.util.CSVWriter;

/**
 * Created by suve on 23/02/17.
 */
public class HMMConstants {
    public static final String FILE_PATH = "./components/org.taz.core/src/main/python/trainHmm.py";
    public static String MODEL_PATH;
    private CSVWriter csvWriter = CSVWriter.getInstance();

//    public HMMConstants(String path){
//        this.MODEL_PATH = path;
//    }
    public static void setModelPath(String path){
        MODEL_PATH = path;

    }

}
