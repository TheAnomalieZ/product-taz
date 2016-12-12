package org.taz.commons.util;

/**
 * Created by  Maninesan on 12/06/16.
 */

import org.taz.commons.parser.JFRParser;
import org.taz.commons.parser.JFRParserV18;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.taz.commons.parser.memory.MemEvent;
import org.taz.commons.parser.util.EventNode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;

public class JFRReader {

    private static ArrayList<JFRParser> jfrList = new ArrayList<JFRParser>();
    private static JFRReader jfrReader;
    private static ArrayList<Integer> memoryList= new ArrayList<Integer>();
    private static HashSet<String> set = new HashSet<String>();

    private static final Logger logger = LoggerFactory.getLogger(JFRReader.class);

    public static JFRReader getInstance() {
        if(jfrReader==null)
            jfrReader = new JFRReader();
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


    public ArrayList<Integer> getGCStates(){
        logger.info("Reading GC Events");
        for (JFRParser parser:jfrList) {
            String index = Integer.toString(jfrList.indexOf(parser));
            ArrayList<Integer> stateSequence = parser.getMemoryStates();
            if (!set.contains(index)) {
                set.add(index);

                for (int i = 0; i < stateSequence.size(); i++) {
                    memoryList.add(stateSequence.get(i));
                }

            }
        }
        return memoryList;


    }
    public Map<Long,Long> getPauseTimeSeries(){
        for (JFRParser parser:jfrList) {
            return parser.getPauseTimeSeries();
        }
        return null;
    }

    public void refreshViewList(){
        logger.info("Erase old JFR loadings");
        jfrList = new ArrayList<JFRParser>();
        set = new HashSet<>();
    }
}
