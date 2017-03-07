package org.taz.commons.tests;


import au.com.bytecode.opencsv.CSVReader;
import org.mockito.Mock;
import org.taz.commons.constants.JFRConstants;
import org.taz.commons.exceptions.EventNotFoundException;
import org.taz.commons.parser.JFRParserV18;
import org.taz.commons.parser.events.JVMInformationEvent;
import org.taz.commons.parser.util.EventNode;
import org.taz.commons.util.JFRReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

/**
 * Created by Maninesan on 2/23/17.
 */


public class JFRParserV18Test {

    @Mock
    EventNode eventNode = mock(EventNode.class);

    private JFRParserV18 jfrParser;

    private final String jfrPath = "/home/tests/normal.jfr";
    private final String statesPath = "/home/tests/normal_states.csv";
    private final String eventName1 = JFRConstants.CPULOAD;
    private final String eventName2 = JFRConstants.HEAPSUMMARY;
    private final String faulteventName = "Excetiion check";


    @org.testng.annotations.BeforeMethod
    public void setUp() throws Exception {
        jfrParser =new JFRParserV18(jfrPath);

        when(eventNode.getDescription()).thenReturn("");
    }


    /**
     * Test the CPU Load event attributes
     * Test Path [1,2,4,5]
     * @throws Exception
     */
    @org.testng.annotations.Test
    public void testGetJFRAttributes1() throws Exception {
        //create expected String array
        String[] expectedArray = new String[]{"(startTime)", "(endTime)", "(duration)",
                "(producer)","(producerURI)"
                ,"(eventType)"
                ,"(eventTypePath)"
                ,"jvmUser","jvmSystem", "machineTotal"};

        //call CPU Load event node
        EventNode en =  jfrParser.getJFRAttributes(eventName1);

        //get the actual result from CPUload node
        Object[] outputArray= en.getAttributes().toArray();

        //test
        assertEquals(outputArray,expectedArray);
    }

    /**
     * Test the Heap Summary event attributes
     *  Test Path [1,2,3,5]
     * @throws Exception
     */

    @org.testng.annotations.Test
    public void testGetJFRAttributes3() throws Exception {
        //call the expected result from mock event node object
        String expectedDescription = eventNode.getDescription();

        //call Heap summary event node
        EventNode en =jfrParser.getJFRAttributes(eventName2);

        //get the actual description result
        String description = en.getDescription();

        //test
        assertEquals(description,expectedDescription);

    }

    /**
     * Test the expected exception in getJFRAttributes method
     *  Test Path [1,6]
     * @throws Exception
     */

    @org.testng.annotations.Test(expectedExceptions = EventNotFoundException.class)
    public void testGetJFRAttributes2() throws Exception {
        //call faulty event name to throw exception
        jfrParser.getJFRAttributes(faulteventName);
    }

    /**
     * check the getMemoryStates
     * @throws Exception
     */
    @org.testng.annotations.Test
    public void testGetMemoryStates() throws Exception {
        //generate gc states from parser
        ArrayList<Integer> testlist = new JFRParserV18(jfrPath).getMemoryStates();

        //read gc states from CSV
        String statespath = statesPath;
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

    /**
     * check the getHeapTimeSeries method
     * @throws Exception
     */
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







//    @Test
//    public void mockFinalClassTest() {
//        AFinalClass tested = PowerMockito.mock(AFinalClass.class);
//
//        final String testInput = "A test input";
//        final String mockedResult = "Mocked final echo result - " + testInput;
//        Mockito.when(tested.echoString(testInput)).thenReturn(mockedResult);
//
//        // Assert the mocked result is returned from method call
//        Assert.assertEquals(tested.echoString(testInput), mockedResult);
//    }
//
//    @Test
//    public void mockStaticClassTest() {
//        PowerMockito.mockStatic(AStaticClass.class);
//
//        final String testInput = "A test input";
//        final String mockedResult = "Mocked static echo result - " + testInput;
//        Mockito.when(AStaticClass.echoString(testInput)).thenReturn(mockedResult);
//
//        // Assert the mocked result is returned from method call
//        Assert.assertEquals(AStaticClass.echoString(testInput), mockedResult);
//    }



}