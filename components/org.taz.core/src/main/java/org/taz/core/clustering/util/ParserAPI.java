package org.taz.core.clustering.util;

import org.taz.commons.exceptions.AttributeNotFoundException;
import org.taz.commons.exceptions.EventNotFoundException;
import org.taz.commons.parser.JFRParserV18;
import org.taz.commons.parser.util.EventNode;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by vithulan on 1/16/17.
 */
public class ParserAPI {
    private final String FILE_PATH = "/home/vithulan/JFRs/JFR_Collection/JFR/CEP/cep-2016_12_17_06_16_26.jfr";
    JFRParserV18 jfrParser;
    public ParserAPI(){
        jfrParser = new JFRParserV18(FILE_PATH);
    }

    public void getAttributes(){
        ArrayList<EventNode> eventNodes = jfrParser.getAllJFRAttributes();
        for (EventNode eventNode : eventNodes){
            try {
                Map<String, ArrayList<Object>> eventMap = jfrParser.getAttributeValues(eventNode.getEventName(),new ArrayList<String>(eventNode.getAttributes()));
                System.out.println("====="+eventNode.getEventName()+"=======");
                for (Map.Entry<String,ArrayList<Object>> entry : eventMap.entrySet()){
                    System.out.println(entry.getKey());
                    for(Object value : entry.getValue()){
                        System.out.println(value.toString());
                    }
                }
            } catch (EventNotFoundException e) {
                e.printStackTrace();
            } catch (AttributeNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
