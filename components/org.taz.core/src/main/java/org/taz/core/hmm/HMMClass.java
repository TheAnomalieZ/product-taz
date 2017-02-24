package org.taz.core.hmm;

import org.taz.commons.util.CSVWriter;
import org.taz.commons.util.JFRReader;

/**
 * Created by suve on 21/02/17.
 */
public class HMMClass {
    private JFRReader jfrReader;
    private CSVWriter csvWriter;
    private String savefilePath;
    private String scorefilePath = null;
    private String thresholdPath = null;

    public HMMClass(){
        jfrReader = JFRReader.getInstance();
        csvWriter = CSVWriter.getInstance();
        savefilePath = System.getProperty("user.dir") + "/components/org.taz.core/src/main/resources/";
    }
}
