package org.taz.commons.parser.memory;

import com.jrockit.mc.flightrecorder.spi.IView;
import org.taz.commons.constants.JFRConstants;
import org.taz.commons.parser.info.RecordingEventHandler;
import org.taz.commons.parser.util.EventHandler;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class GCTimeSeriesModel extends EventHandler {
    private Map<Long,MemEvent> eventMap;
    private GCTimeHandler gcTimeHandler;
    private GCMemoryHandler gcMemoryHandler;
    private RecordingEventHandler recordingEventHandler;
    private StateIdentifier stateIdentifier;
    private GCPauseTimeSeries pauseTimeModel;

    private long startTime;
    private long duration;



    public GCTimeSeriesModel(IView view){
        super(view, JFRConstants.GCHANDLER);
        eventMap = new TreeMap<Long, MemEvent>();

        gcTimeHandler= new GCTimeHandler(view, eventMap);
        gcTimeHandler.configureEventGCTime();

        gcMemoryHandler= new GCMemoryHandler(view, eventMap);
        gcMemoryHandler.configureGCMemory();

        recordingEventHandler = new RecordingEventHandler(view);
        startTime = recordingEventHandler.getRecordingEvent().getStartTime();
        duration = recordingEventHandler.getRecordingEvent().getDuration();


    }

    public Map<Long,Double> getPauseTimeSeries(){
        Map<Long,Double> pauseTimeSeries;
        pauseTimeModel = new GCPauseTimeSeries(eventMap,startTime,duration);
        pauseTimeSeries = pauseTimeModel.configureTimeSeries();

        return pauseTimeSeries;
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
