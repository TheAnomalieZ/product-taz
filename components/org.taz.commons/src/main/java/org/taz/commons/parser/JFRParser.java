package org.taz.commons.parser;

import org.taz.commons.exceptions.AttributeNotFoundException;
import org.taz.commons.exceptions.EventNotFoundException;
import org.taz.commons.parser.memory.MemEvent;
import org.taz.commons.parser.util.EventNode;

import java.util.ArrayList;
import java.util.Map;

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
     * @return Map of time and pausetime interval
     */
    public Map<Long,Double> getPauseTimeSeries();
    /**
     * Method to retrieve whole gc time series
     * @return Map of time and heap and pause time
     */
    public Map<Long,ArrayList<Double>> getGCTimeSeries();

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
}