package org.taz.commons.parser.memory;

import com.jrockit.mc.flightrecorder.spi.IView;
import org.taz.commons.constants.JFRConstants;
import org.taz.commons.parser.EventHandler;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class GCTimeSeriesModel extends EventHandler {
//    private IView view;
    private Map<Long,MemEvent> eventMap;

    public GCTimeSeriesModel(IView view){
        super(view, JFRConstants.GCHANDLER);
        eventMap = new LinkedHashMap<Long, MemEvent>();
    }

    public ArrayList<Integer> getStateSequence(){
        GCTimeHandler gcTimeHandler;
        GCMemoryHandler gcMemoryHandler;
        StateIdentifier stateIdentifier;
        ArrayList<Integer> stateSequence;
        gcTimeHandler= new GCTimeHandler(view, eventMap);
        gcTimeHandler.configureEventGCTime();

        gcMemoryHandler= new GCMemoryHandler(view, eventMap);
        gcMemoryHandler.configureGCMemory();

        stateIdentifier= new StateIdentifier(eventMap);
        stateSequence = stateIdentifier.configureStates();

        return stateSequence;
    }

    public void getOutputTwo(){

    }
}
