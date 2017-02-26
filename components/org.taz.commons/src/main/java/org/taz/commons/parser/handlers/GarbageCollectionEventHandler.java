package org.taz.commons.parser.handlers;

import com.jrockit.mc.flightrecorder.spi.IEvent;
import com.jrockit.mc.flightrecorder.spi.IView;
import org.taz.commons.constants.JFRConstants;
import org.taz.commons.parser.events.GarbageCollectionEvent;
import org.taz.commons.parser.util.EventHandler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by K.Kokulan on 12/20/2016.
 */
public class GarbageCollectionEventHandler extends EventHandler{
    public ArrayList<GarbageCollectionEvent> eventList;
    public GarbageCollectionEventHandler(IView view){
        super(view, JFRConstants.GARBAGECOLLECTION);
        eventList = new ArrayList<>();
    }

    public ArrayList<GarbageCollectionEvent> getEventSeries() {
        for (IEvent event : view) {
            if(EVENT_TYPE.equals(event.getEventType().getName())){
                GarbageCollectionEvent garbageCollectionEvent = new GarbageCollectionEvent();

                garbageCollectionEvent.setStartTimestamp(event.getStartTimestamp());
                garbageCollectionEvent.setEndTimestamp(event.getEndTimestamp());

                garbageCollectionEvent.setGcId(event.getValue(JFRConstants.GCID).toString());
                garbageCollectionEvent.setName(event.getValue(JFRConstants.GCNAME).toString());
                garbageCollectionEvent.setCause(event.getValue(JFRConstants.GCREASON).toString());
                garbageCollectionEvent.setSumOfPauses(event.getValue(JFRConstants.GCPAUSETIME).toString());
                garbageCollectionEvent.setLongestPause(event.getValue(JFRConstants.GC_LONGEST_PAUSE).toString());

                eventList.add(garbageCollectionEvent);
            }
        }
        Collections.sort(eventList, (o1, o2) -> (Integer.parseInt(o1.getGcId())) - Integer.parseInt(o2.getGcId()));

        return eventList;
    }
}
