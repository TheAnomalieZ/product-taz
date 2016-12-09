package org.taz.commons.util;

/**
 * Created by  Maninesan on 12/06/16.
 */

import org.taz.commons.parser.JFRParser;
import org.taz.commons.parser.JFRParserV18;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.taz.commons.parser.util.EventHandlerFactory;

import java.util.ArrayList;

public class JFRReader {

    private static ArrayList<JFRParser> jfrList = new ArrayList<JFRParser>();
    private static JFRReader jfrReader = new JFRReader();
    private static CSVWriter csvWriter;
    private static EventHandlerFactory eventHandlerFactory;

    private static final Logger logger = LoggerFactory.getLogger(JFRReader.class);

    public static JFRReader getInstance() {
        logger.debug("");
        csvWriter = CSVWriter.getInstance();
        eventHandlerFactory = new EventHandlerFactory();
        return jfrReader;
    }

    private JFRReader() {
    }

    public void readJFR(ArrayList<String> filePaths){
        for (String filePath:filePaths) {
            logger.info("Loading file"+ filePath);
            jfrList.add(new JFRParserV18(filePath));
        }
    }


    public ArrayList<JFRParser> getJFRRecording() {
        if (jfrList.size() == 0){
            logger.warn("No JFR loaded");
            return null;
        }else{
            return jfrList;
        }
    }


    public void getGCStates(int select){
        logger.info("Reading GC Events for HMM");
        for (JFRParser parser:jfrList) {
            String index = Integer.toString(jfrList.indexOf(parser));
            ArrayList<Integer> stateSequence = parser.getMemoryStates();
            csvWriter.getGCStates(stateSequence,index);
        }

        if(select ==0){
            csvWriter.getHMMGCSequence();
        }else{
            csvWriter.getAEGCSequence();
        }

    }


    public void refreshViewList(){
        logger.info("Erase old JFR loadings");
        jfrList = new ArrayList<JFRParser>();
    }
}
