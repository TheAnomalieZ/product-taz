package org.taz.core.util;

import org.taz.commons.util.JFRReader;

/**
 * Created by Maninesan on 2/16/17.
 */
public class AETest {
    String filePath = "";
    String fileName = "";
    JFRReader jfrReader = JFRReader.getInstance();
    jfrReader.getHeapTimeSeries(filePath);

    String savefilePath =  System.getProperty("user.dir")+"/components/org.taz.core/src/main/resources/"+fileName;


}
