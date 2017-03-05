package org.taz.commons.tests;


import org.taz.commons.constants.JFRConstants;
import org.taz.commons.parser.JFRParserV18;
import org.taz.commons.parser.util.EventNode;

import static org.testng.Assert.assertEquals;

/**
 * Created by Maninesan on 2/23/17.
 */
public class JFRParserV18Test {
    @org.testng.annotations.BeforeMethod
    public void setUp() throws Exception {

    }

    @org.testng.annotations.AfterMethod
    public void tearDown() throws Exception {

    }

    @org.testng.annotations.Test
    public void testGetMemoryStates1() throws Exception {

    }

    @org.testng.annotations.Test
    public void testGetHeapTimeSeries1() throws Exception {

    }

    @org.testng.annotations.Test
    public void testGetPauseTimeSeries1() throws Exception {

    }

    @org.testng.annotations.Test
    public void testGetJVMInformationEvent1() throws Exception {

    }

    @org.testng.annotations.Test
    public void testGetHotMethods1() throws Exception {

    }

    @org.testng.annotations.Test
    public void testGetJFRAttributes() throws Exception {
        String[] a = new String[]{"(startTime)", "(endTime)", "(duration)",
                "(producer)","(producerURI)"
                ,"(eventType)"
                ,"(eventTypePath)"
                ,"jvmUser","jvmSystem", "machineTotal"};

        EventNode en =  new JFRParserV18("/home/garth/FYP/product-taz/components/org.taz.commons/src/main/java/org/taz/commons/tests/normal.jfr").getJFRAttributes(JFRConstants.CPULOAD);
        Object[] arrayList= en.getAttributes().toArray();
        assertEquals(a,arrayList);


    }


}