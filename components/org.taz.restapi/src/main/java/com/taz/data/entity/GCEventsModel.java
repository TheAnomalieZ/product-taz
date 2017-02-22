package com.taz.data.entity;

import org.taz.commons.parser.events.GCReferenceProcessingEvent;
import org.taz.commons.parser.events.GarbageCollectionEvent;
import org.taz.commons.parser.events.HeapSummaryEvent;
import org.taz.commons.parser.events.MetaspaceSummaryEvent;

import java.util.ArrayList;

/**
 * Created by K.Kokulan on 12/26/2016.
 */
public class GCEventsModel {

    private String fileName;
    private ArrayList<String> gcIdList;
    private ArrayList<GarbageCollectionEvent> garbageCollectionEvent;
    private ArrayList<HeapSummaryEvent> heapSummaryEvent;
    private ArrayList<GCReferenceProcessingEvent> gcReferenceProcessingEvent;
    private ArrayList<MetaspaceSummaryEvent> metaspaceSummaryEvents;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public ArrayList<String> getGcIdList() {
        return gcIdList;
    }

    public void setGcIdList(ArrayList<String> gcIdList) {
        this.gcIdList = gcIdList;
    }

    public ArrayList<GarbageCollectionEvent> getGarbageCollectionEvent() {
        return garbageCollectionEvent;
    }

    public void setGarbageCollectionEvent(ArrayList<GarbageCollectionEvent> garbageCollectionEvent) {
        this.garbageCollectionEvent = garbageCollectionEvent;
    }

    public ArrayList<HeapSummaryEvent> getHeapSummaryEvent() {
        return heapSummaryEvent;
    }

    public void setHeapSummaryEvent(ArrayList<HeapSummaryEvent> heapSummaryEvent) {
        this.heapSummaryEvent = heapSummaryEvent;
    }

    public ArrayList<GCReferenceProcessingEvent> getGcReferenceProcessingEvent() {
        return gcReferenceProcessingEvent;
    }

    public void setGcReferenceProcessingEvent(ArrayList<GCReferenceProcessingEvent> gcReferenceProcessingEvent) {
        this.gcReferenceProcessingEvent = gcReferenceProcessingEvent;
    }

    public ArrayList<MetaspaceSummaryEvent> getMetaspaceSummaryEvents() {
        return metaspaceSummaryEvents;
    }

    public void setMetaspaceSummaryEvents(ArrayList<MetaspaceSummaryEvent> metaspaceSummaryEvents) {
        this.metaspaceSummaryEvents = metaspaceSummaryEvents;
    }
}
