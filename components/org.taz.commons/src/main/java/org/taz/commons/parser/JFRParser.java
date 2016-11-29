package org.taz.commons.parser;

import org.taz.commons.exceptions.EventNotFoundException;
import org.taz.commons.parser.util.EventNode;

import java.util.ArrayList;

public interface JFRParser {

    /**
     * Method to retrieve memory state sequence
     * @return ArrayList object of states
     */
    public ArrayList<Integer> getMemoryStates();

    /**
     * Get all JFR Attributes
     * @return ArrayList object of EventNodes
     */
    public ArrayList<EventNode> getAllJFRAttributes();

    /**
     * Get attribute list
     * @param eventName Event name that is required to get
     * @return EventNode object with attributes
     * @throws EventNotFoundException throws if eventName is not found
     */
    public EventNode getJFRAttributes(String eventName) throws EventNotFoundException;

    /**
     * Initiate Flight recorder parser
     * @param path file path
     */
    public void initParser(String path);
    //public
}