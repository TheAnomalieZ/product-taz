package org.taz.commons.tests;


import au.com.bytecode.opencsv.CSVReader;
import org.taz.commons.constants.JFRConstants;
import org.taz.commons.parser.JFRParserV18;
import org.taz.commons.parser.events.JVMInformationEvent;
import org.taz.commons.parser.util.EventNode;
import org.taz.commons.util.JFRReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

/**
 * Created by Maninesan on 2/23/17.
 */
public class JFRParserV18Test {
    String jfrPath;
    @org.testng.annotations.BeforeMethod
    public void setUp() throws Exception {
            jfrPath ="/home/garth/product-taz/components/org.taz.commons/src/main/java/org/taz/commons/tests/normal.jfr";

    }

    @org.testng.annotations.AfterMethod
    public void tearDown() throws Exception {

    }



    @org.testng.annotations.Test
    public void testGetMemoryStates() throws Exception {
        //generate gc states from parser
        ArrayList<Integer> testlist = new JFRParserV18(jfrPath).getMemoryStates();

        //read gc states from CSV
        String statespath = TestConstants.states;
        CSVReader reader = null;
        ArrayList<Integer> states = new ArrayList<Integer>();
        try {
            reader = new CSVReader(new FileReader( statespath));
            String [] nextLine;
            int score;
            while ((nextLine = reader.readNext()) != null) {
                // nextLine[] is an array of values from the line
                score = Integer.parseInt(nextLine[0]);
                states.add(score);
            }
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        //check both
        assertEquals(states,testlist);
    }



    @org.testng.annotations.Test
    public void testGetHeapTimeSeries() throws Exception {
        //generate heap time series from parser
        ArrayList<Double> testlist = JFRReader.convertGCTimeSeries(new JFRParserV18(jfrPath).getGCTimeSeries());

        //read heap time series form CSV
        String heaptimeseriespaht = TestConstants.heaptimeseries;
        CSVReader reader = null;
        ArrayList<Double> heaptimeseries = new ArrayList<Double>();
        try {
            reader = new CSVReader(new FileReader( heaptimeseriespaht));
            String [] nextLine;
            Double score;
            while ((nextLine = reader.readNext()) != null) {
                // nextLine[] is an array of values from the line
                score = Double.parseDouble(nextLine[0]);
                heaptimeseries.add(score);
            }
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        //check both
        assertEquals(heaptimeseries,testlist);
    }

    /**
     * check to not null return
     * @throws Exception
     */
    @org.testng.annotations.Test
    public void testGetJVMInformationEvent() throws Exception {
        JVMInformationEvent je =new JFRParserV18(jfrPath).getJVMInformationEvent();
        assertNotNull(je);
    }

    

    @org.testng.annotations.Test
    public void testGetHotMethods() throws Exception {

    }



    /**
     * Test the CPULOAD event attributes
     * @throws Exception
     */
    @org.testng.annotations.Test
    public void testGetJFRAttributes() throws Exception {
        String[] a = new String[]{"(startTime)", "(endTime)", "(duration)",
                "(producer)","(producerURI)"
                ,"(eventType)"
                ,"(eventTypePath)"
                ,"jvmUser","jvmSystem", "machineTotal"};

        EventNode en =  new JFRParserV18(jfrPath).getJFRAttributes(JFRConstants.CPULOAD);
        Object[] arrayList= en.getAttributes().toArray();
        assertEquals(a,arrayList);


    }


}