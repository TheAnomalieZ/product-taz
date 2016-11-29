package org.taz.commons.parser;

import com.jrockit.mc.flightrecorder.FlightRecording;
import com.jrockit.mc.flightrecorder.FlightRecordingLoader;
import com.jrockit.mc.flightrecorder.spi.IEventType;
import com.jrockit.mc.flightrecorder.spi.IView;
import org.taz.commons.constants.TAZConstants;
import org.taz.commons.exceptions.EventNotFoundException;
import org.taz.commons.parser.JFRParser;
import org.taz.commons.parser.memory.GCTimeSeriesModel;
import org.taz.commons.parser.util.EventNode;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by vithulan on 11/29/16.
 */
public class JFRParserV18 implements JFRParser {

    final String FILE_PATH;
    private IView iView = null;
    Collection<IEventType> ieventtypes;

    public JFRParserV18(String path) {
        FILE_PATH = path;
        initParser(this.FILE_PATH);
    }

    public ArrayList<Integer> getMemoryStates() {
        GCTimeSeriesModel gcTimeSeriesModel = new GCTimeSeriesModel(iView);
        return gcTimeSeriesModel.getStateSequence();
    }

    public ArrayList<EventNode> getAllJFRAttributes() {
        ArrayList<EventNode> eventNodes = new ArrayList<EventNode>();
        for(IEventType iEventType : ieventtypes){
            EventNode eventNode = new EventNode();
            eventNode.setEventName(iEventType.getName());
            if(iEventType.getDescription()!=null){
                eventNode.setDescription(iEventType.getDescription());
            }
            else
                eventNode.setDescription(TAZConstants.NONE);
            eventNode.setColor(iEventType.getColor().toString());
            eventNode.setId(iEventType.getId());
            eventNode.setAttributes(iEventType.getFieldIdentifiers());
            eventNodes.add(eventNode);
        }
        return eventNodes;
    }

    public EventNode getJFRAttributes(String eventName) throws EventNotFoundException {
        for(IEventType iEventType : ieventtypes){
            EventNode eventNode = new EventNode();
            if(iEventType.getName().equals(eventName)){
                eventNode.setEventName(iEventType.getName());
                if(iEventType.getDescription()!=null){
                    eventNode.setDescription(iEventType.getDescription());
                }
                else
                    eventNode.setDescription(TAZConstants.NONE);
                eventNode.setColor(iEventType.getColor().toString());
                eventNode.setId(iEventType.getId());
                eventNode.setAttributes(iEventType.getFieldIdentifiers());
                return eventNode;
            }
        }
        throw new EventNotFoundException(eventName);
    }

    public void initParser(String path) {
        FlightRecording recording = FlightRecordingLoader.loadFile(new File(FILE_PATH));
        iView = recording.createView();
        ieventtypes = iView.getEventTypes();
    }
}
