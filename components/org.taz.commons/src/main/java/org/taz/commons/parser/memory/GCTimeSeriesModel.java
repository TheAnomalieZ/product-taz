package org.taz.commons.parser.memory;

import com.jrockit.mc.flightrecorder.spi.IView;
import org.taz.commons.constants.JFRConstants;
import org.taz.commons.parser.util.EventHandler;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class GCTimeSeriesModel extends EventHandler {
    private Map<Long,MemEvent> eventMap;
    private GCTimeHandler gcTimeHandler;
    private GCMemoryHandler gcMemoryHandler;
    private StateIdentifier stateIdentifier;

    public GCTimeSeriesModel(IView view){
        super(view, JFRConstants.GCHANDLER);
        eventMap = new LinkedHashMap<Long, MemEvent>();

        gcTimeHandler= new GCTimeHandler(view, eventMap);
        gcTimeHandler.configureEventGCTime();

        gcMemoryHandler= new GCMemoryHandler(view, eventMap);
        gcMemoryHandler.configureGCMemory();

    }

    public ArrayList<Integer> getStateSequence(){

        ArrayList<Integer> stateSequence;

        stateIdentifier= new StateIdentifier(eventMap);
        stateSequence = stateIdentifier.configureStates();

        return stateSequence;
    }

    public Map<Long,MemEvent> getGCFeatures(){
        return eventMap;
    }


}
