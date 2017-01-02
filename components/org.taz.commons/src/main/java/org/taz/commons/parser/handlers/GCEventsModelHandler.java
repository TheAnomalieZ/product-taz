package org.taz.commons.parser.handlers;

import com.jrockit.mc.flightrecorder.spi.IEvent;
import com.jrockit.mc.flightrecorder.spi.IView;
import org.taz.commons.constants.JFRConstants;
import org.taz.commons.parser.events.*;
import org.taz.commons.parser.models.GCEventsModel;
import org.taz.commons.parser.util.JFREvent;

import java.util.ArrayList;

/**
 * Created by K.Kokulan on 12/26/2016.
 */
public class GCEventsModelHandler {
    private IView view;
    private ArrayList<String> gcIdList;
    private ArrayList<GarbageCollectionEvent> garbageCollectionEventList;
    private ArrayList<HeapSummaryEvent> heapSummaryEventList;
    private ArrayList<GCReferenceProcessingEvent> gcReferenceProcessingEventList;
    private ArrayList<MetaspaceSummaryEvent> metaspaceSummaryEventsList;

    public GCEventsModelHandler(IView view) {
        this.view = view;
    }

    public GCEventsModel getGCEventModel() {
        GCEventsModel gcEventsModel = new GCEventsModel();
        GarbageCollectionEvent garbageCollectionEvent;
        HeapSummaryEvent heapSummaryEvent;
        GCReferenceProcessingEvent referenceProcessingEvent;
        MetaspaceSummaryEvent metaspaceSummaryEvent;
        gcIdList = new ArrayList<>();
        garbageCollectionEventList = new ArrayList<>();
        heapSummaryEventList = new ArrayList<>();
        gcReferenceProcessingEventList = new ArrayList<>();
        metaspaceSummaryEventsList = new ArrayList<>();

        for (IEvent event : view) {

            if (JFRConstants.GARBAGECOLLECTION.equals(event.getEventType().getName())) {
                garbageCollectionEvent = new GarbageCollectionEvent();

                String gcId = event.getValue(JFRConstants.GCID).toString();
                if (!gcIdList.contains(gcId)) {
                    gcIdList.add(gcId);
                }
                garbageCollectionEvent.setGcId(gcId);
                garbageCollectionEvent.setName(event.getValue(JFRConstants.NAME).toString());
                garbageCollectionEvent.setCause(event.getValue(JFRConstants.GCREASON).toString());
                garbageCollectionEvent.setSumOfPauses(event.getValue(JFRConstants.GCPAUSETIME).toString());
                garbageCollectionEvent.setLongestPause(event.getValue(JFRConstants.GC_LONGEST_PAUSE).toString());
                garbageCollectionEvent.setStartTimestamp(event.getStartTimestamp());
                garbageCollectionEvent.setEndTimestamp(event.getEndTimestamp());
                garbageCollectionEvent.setDuration(event.getDuration());

                garbageCollectionEventList.add(garbageCollectionEvent);

            } else if (JFRConstants.HEAPSUMMARY.equals(event.getEventType().getName())) {
                heapSummaryEvent = new HeapSummaryEvent();

                heapSummaryEvent.setStartTimestamp(event.getStartTimestamp());
                heapSummaryEvent.setEndTimestamp(event.getEndTimestamp());

                heapSummaryEvent.setGcId(event.getValue(JFRConstants.GCID).toString());
                heapSummaryEvent.setWhen(event.getValue(JFRConstants.WHEN).toString());
                heapSummaryEvent.setHeapSpaceStart(event.getValue(JFRConstants.HEAP_SPACE_START).toString());
                heapSummaryEvent.setHeapSpaceCommittedEnd(event.getValue(JFRConstants.HEAP_SPACE_COMMITTED_END).toString());
                heapSummaryEvent.setHeapUsed(event.getValue(JFRConstants.HEAPUSED).toString());
                heapSummaryEvent.setHeapSpaceCommittedSize(event.getValue(JFRConstants.HEAP_SPACE_COMMITTED_SIZE).toString());
                heapSummaryEvent.setHeapSpaceReservedEnd(event.getValue(JFRConstants.HEAP_SPACE_RESERVED_END).toString());
                heapSummaryEvent.setHeapSpaceReservedSize(event.getValue(JFRConstants.HEAP_SPACE_RESERVED_SIZE).toString());

                heapSummaryEventList.add(heapSummaryEvent);
            } else if (JFRConstants.GC_REFER_PROCESSING.equals(event.getEventType().getName()) || JFRConstants.GC_REFER_STATICS.equals(event.getEventType().getName())) {
                referenceProcessingEvent = new GCReferenceProcessingEvent();

                referenceProcessingEvent.setGcId(event.getValue(JFRConstants.GCID).toString());
                referenceProcessingEvent.setCount(event.getValue(JFRConstants.COUNT).toString());
                referenceProcessingEvent.setType(event.getValue(JFRConstants.TYPE).toString());

                gcReferenceProcessingEventList.add(referenceProcessingEvent);
            } else if (JFRConstants.METASPACE_SUMMARY.equals(event.getEventType().getName())) {
                metaspaceSummaryEvent = new MetaspaceSummaryEvent();

                metaspaceSummaryEvent.setGcId(event.getValue(JFRConstants.GCID).toString());
                metaspaceSummaryEvent.setWhen(event.getValue(JFRConstants.WHEN).toString());
                metaspaceSummaryEvent.setClassSpaceCommitted(event.getValue("classSpace:committed").toString());
                metaspaceSummaryEvent.setClassSpaceReserved(event.getValue("classSpace:reserved").toString());
                metaspaceSummaryEvent.setClassSpaceUsed(event.getValue("classSpace:used").toString());
                metaspaceSummaryEvent.setDataSpaceCommitted(event.getValue("dataSpace:committed").toString());
                metaspaceSummaryEvent.setDataSpaceReserved(event.getValue("dataSpace:reserved").toString());
                metaspaceSummaryEvent.setDataSpaceUsed(event.getValue("dataSpace:used").toString());
                metaspaceSummaryEvent.setMetaspaceCommitted(event.getValue("metaspace:committed").toString());
                metaspaceSummaryEvent.setMetaspaceReserved(event.getValue("metaspace:reserved").toString());
                metaspaceSummaryEvent.setMetaspaceUsed(event.getValue("metaspace:used").toString());
                metaspaceSummaryEvent.setGcThreshold(event.getValue("gcThreshold").toString());

                metaspaceSummaryEventsList.add(metaspaceSummaryEvent);
            }
        }

        gcEventsModel.setGcIdList(gcIdList);
        gcEventsModel.setGarbageCollectionEvent(garbageCollectionEventList);
        gcEventsModel.setGcReferenceProcessingEvent(gcReferenceProcessingEventList);
        gcEventsModel.setHeapSummaryEvent(heapSummaryEventList);
        gcEventsModel.setMetaspaceSummaryEvents(metaspaceSummaryEventsList);

        return gcEventsModel;
    }
}
