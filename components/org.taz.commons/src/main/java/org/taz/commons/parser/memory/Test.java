package org.taz.commons.parser.memory;

import org.taz.commons.parser.JFRParser;
import org.taz.commons.parser.JFRParserV18;

/**
 * Created by  Maninesan on 12/12/16.
 */
public class Test {
    public static void main(String[] args) {
        String filepath ="/home/mani/anomaly.jfr";

        JFRParser parser = new JFRParserV18(filepath);
        parser.getPauseTimeSeries();
    }
}
