package org.taz.commons.parser.memory;

import com.jrockit.mc.flightrecorder.spi.IView;
import org.taz.commons.constants.JFRConstants;
import org.taz.commons.parser.handlers.RecordingEventHandler;
import org.taz.commons.parser.util.EventHandler;

import java.util.ArrayList;
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



    public GCTimeSeriesModel(IView view){
        super(view, JFRConstants.GCHANDLER);
        eventMap = new TreeMap<Long, MemEvent>();

        gcTimeHandler= new GCTimeHandler(view, eventMap);
        gcTimeHandler.configureEventGCTime();

        gcMemoryHandler= new GCMemoryHandler(view, eventMap);
        gcMemoryHandler.configureGCMemory();

        recordingEventHandler = new RecordingEventHandler(view);
        startTime = recordingEventHandler.getRecordingEvent().getStartTime();


    }

    public ArrayList<Short> getPauseTimeSeries(){
        pauseTimeModel = new GCPauseTimeSeries(eventMap,startTime);
        return  pauseTimeModel.configureTimeSeries();
    }



    public ArrayList<Double> getHeapandPauseSeries(){
        ArrayList<Double> timeSeries;
        pauseTimeModel = new GCPauseTimeSeries(eventMap,startTime);
        timeSeries = pauseTimeModel.heapAndPauseTime();

        return timeSeries;
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
