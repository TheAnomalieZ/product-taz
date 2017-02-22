package org.taz.commons.parser.handlers;

import com.jrockit.mc.flightrecorder.spi.IEvent;
import com.jrockit.mc.flightrecorder.spi.IView;
import org.taz.commons.constants.JFRConstants;
import org.taz.commons.constants.TAZConstants;
import org.taz.commons.parser.events.CPULoadEvent;
import org.taz.commons.parser.events.GarbageCollectionEvent;
import org.taz.commons.parser.events.HeapSummaryEvent;
import org.taz.commons.parser.events.JVMInformationEvent;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by K.Kokulan on 2/15/2017.
 */
public class OverviewDataHandler {
    private IView view;
    private ArrayList<HeapSummaryEvent> heapSummaryEventsList;
    private ArrayList<CPULoadEvent> cpuLoadEventsList;
    private ArrayList<GarbageCollectionEvent> garbageCollectionEventsList;
    private JVMInformationEvent jvmInformationEvent;

    public OverviewDataHandler(IView iView){
        this.view = iView;
        heapSummaryEventsList = new ArrayList<>();
        cpuLoadEventsList = new ArrayList<>();
        garbageCollectionEventsList = new ArrayList<>();
    }


    public HashMap<String, Object> getEventsMap() {
        HashMap<String, Object> eventMap = new HashMap<>(4);

        for (IEvent event : view) {
            if(JFRConstants.HEAPSUMMARY.equals(event.getEventType().getName())){
                HeapSummaryEvent heapSummaryEvent = new HeapSummaryEvent();

                heapSummaryEvent.setStartTimestamp(event.getStartTimestamp());
                heapSummaryEvent.setEndTimestamp(event.getEndTimestamp());

                heapSummaryEvent.setHeapUsed(event.getValue(JFRConstants.HEAPUSED).toString());
                heapSummaryEvent.setHeapSpaceCommittedSize(event.getValue(JFRConstants.HEAP_SPACE_COMMITTED_SIZE).toString());
                heapSummaryEventsList.add(heapSummaryEvent);
            }
            eventMap.put(TAZConstants.HEAP_SUMMARY_EVENT, heapSummaryEventsList);

            if(JFRConstants.CPULOAD.equals(event.getEventType().getName())){
                CPULoadEvent cpuLoadEvent = new CPULoadEvent();

                cpuLoadEvent.setStartTimestamp(event.getStartTimestamp());
                cpuLoadEvent.setEndTimestamp(event.getEndTimestamp());

                cpuLoadEvent.setJvmUser(event.getValue(JFRConstants.JVMUSER).toString());
                cpuLoadEvent.setJvmSystem(event.getValue(JFRConstants.JVMSYSTEM).toString());
                cpuLoadEvent.setMachineTotal(event.getValue(JFRConstants.MACHINETOTAL).toString());

                cpuLoadEventsList.add(cpuLoadEvent);
            }

            eventMap.put(TAZConstants.CPU_LOAD_EVENT, cpuLoadEventsList);

            if(JFRConstants.GARBAGECOLLECTION.equals(event.getEventType().getName())) {
                GarbageCollectionEvent garbageCollectionEvent = new GarbageCollectionEvent();

                garbageCollectionEvent.setStartTimestamp(event.getStartTimestamp());
                garbageCollectionEvent.setEndTimestamp(event.getEndTimestamp());

                garbageCollectionEvent.setGcId(event.getValue(JFRConstants.GCID).toString());
                garbageCollectionEvent.setName(event.getValue(JFRConstants.GCNAME).toString());
                garbageCollectionEvent.setCause(event.getValue(JFRConstants.GCREASON).toString());
                garbageCollectionEvent.setSumOfPauses(event.getValue(JFRConstants.GCPAUSETIME).toString());
                garbageCollectionEvent.setLongestPause(event.getValue(JFRConstants.GC_LONGEST_PAUSE).toString());

                garbageCollectionEventsList.add(garbageCollectionEvent);
            }

            eventMap.put(TAZConstants.GC_EVENT, garbageCollectionEventsList);

            if(JFRConstants.JVM_INFORMATION.equals(event.getEventType().getName())){
                jvmInformationEvent = new JVMInformationEvent();

                jvmInformationEvent.setStartTimestamp(event.getStartTimestamp());
                jvmInformationEvent.setEndTimestamp(event.getEndTimestamp());

                jvmInformationEvent.setJvmVersion(event.getValue(JFRConstants.JVM_VERSION).toString());
                jvmInformationEvent.setJvmStartTime(event.getValue(JFRConstants.JVM_START_TIME).toString());
                jvmInformationEvent.setJvmName(event.getValue(JFRConstants.JVM_NAME).toString());
                jvmInformationEvent.setJvmArguments(event.getValue(JFRConstants.JVM_ARGUMENTS).toString());
                jvmInformationEvent.setJavaArguments(event.getValue(JFRConstants.JAVA_ARGUMENTS).toString());
                jvmInformationEvent.setJvmFlags(event.getValue(JFRConstants.JVM_FLAGS).toString());
            }

            eventMap.put(TAZConstants.JVM_INFORMATION, jvmInformationEvent);
        }

        return eventMap;
    }
}
