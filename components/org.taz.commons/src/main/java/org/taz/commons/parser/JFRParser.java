package org.taz.commons.parser;

import com.jrockit.mc.flightrecorder.spi.IView;
import org.taz.commons.exceptions.AttributeNotFoundException;
import org.taz.commons.exceptions.EventNotFoundException;
import org.taz.commons.parser.events.*;
import org.taz.commons.parser.memory.MemEvent;
import org.taz.commons.parser.models.GCEventsModel;
import org.taz.commons.parser.util.EventNode;

import java.util.*;

public interface JFRParser {

    /**
     * Method to retrieve memory state sequence
     * @return ArrayList object of states
     */
    public ArrayList<Integer> getMemoryStates();

    /**
     * Method to retrieve all GC related attributes
     * @return Map of gcID and MemEvent
     */
    public Map<Long,MemEvent> getGCEvents();

    /**
     * Get all JFR Attributes
     * @return ArrayList object of EventNodes
     */
    public ArrayList<EventNode> getAllJFRAttributes();

    /**
     * Get attribute list
     * @param eventName Event name that is required to get
     * @return EventNode object with attributes
     * @throws EventNotFoundException thrown if eventName is not found in JFR
     */
    public EventNode getJFRAttributes(String eventName) throws EventNotFoundException;

    /**
     * Method to retrieve paust time series
     * @return List of pausetime interval
     */
    public ArrayList<Short> getPauseTimeSeries();

    /**
     * Method to retrieve Heap time series
     * @return List of heaptime interval
     */
    public ArrayList<Double> getHeapTimeSeries();


    /**
     * Method to retrieve whole gc time series
     * @return Map of time and heap and pause time
     */
    public ArrayList<Double> getGCTimeSeries();

    /**
     * Method to retrieve CPU Events
     * @return Map of time and pausetime interval
     */
    public ArrayList<CPULoadEvent> getCPUEvents();

    /**
     * Method to retrieve Heap summary Events
     * @return Map of time and pausetime interval
     */
    public HashMap<String, Object> getOverviewPageEvents();

    /**
     * Method to retrieve over view Events
     * @return event Map
     */
    public ArrayList<HeapSummaryEvent> getHeapSummaryEvents();

    /**
     * Method to retrieve Heap summary Events
     * @return Map of time and pausetime interval
     */
    public ArrayList<GarbageCollectionEvent> getGarbageCollectionEvents();

    /**
     * Method to retrieve JVM Information Event
     * @return Map of time and pausetime interval
     */
    public JVMInformationEvent getJVMInformationEvent();


    /**
     * Method to retrieve JVM Information Event List
     * @return Map of time and pausetime interval
     */
    public ArrayList<JVMInformationEvent> getJVMInformationEventList();

    /**
     * Method to retrieve initial System property Event List
     * @return Arraylist of init sys property event
     */
    public ArrayList<InitialSystemPropertyEvent> getInitialSystemPropertyEventList();

    /**
     * Method to retrieve initial System property Event List
     * @return Arraylist of recording setting event
     */
    public ArrayList<RecordingSettingEvent> getRecordingSettingEventList();

    /**
     * Method to retrieve GC Event Model
     * @return GC Event Model
     */
    public GCEventsModel getGCEventModel();

    /**
     * Method to retrieve hot Methods
     * @return Likedlist
     */
    public LinkedHashMap<ArrayList<String>,Long> getHotMethods(long startTime, long endTime);

    /**
     * Initiate Flight recorder parser
     * @param path file path
     */
    public void initParser(String path);

    /**
     * Get attribute values from an event
     * @param eventName Event from where attributes will be received
     * @param attributes List of attributes that are required
     * @return Map contains Attribute name and value of list contains attribute values
     * @throws EventNotFoundException thrown if given event name is not found in JFR
     * @throws AttributeNotFoundException thrown if given attribute is not found in given event
     */
    public Map<String,ArrayList<Object>> getAttributeValues(String eventName, ArrayList<String> attributes)
            throws EventNotFoundException, AttributeNotFoundException;

    public IView getIView();


    /**
     * Method to retrieve GC states
     * @return List of GC states
     */
    ArrayList<Integer> getGCStates();
}