package org.taz.commons.parser.memory;

import com.jrockit.mc.flightrecorder.spi.IEvent;
import com.jrockit.mc.flightrecorder.spi.IView;
import org.taz.commons.constants.JFRConstants;
import org.taz.commons.parser.util.EventHandler;


import java.util.Map;

public class GCTimeHandler extends EventHandler {
    private final Map<Long,MemEvent> eventMap;

    public GCTimeHandler(IView view, Map<Long,MemEvent> eventMap){
        super(view, JFRConstants.GARBAGECOLLECTION);
        this.eventMap = eventMap;
    }

    public void configureEventGCTime() {
        for (IEvent event : view) {
            if(EVENT_TYPE.equals(event.getEventType().getName())){
                MemEvent memEvent = new MemEvent();
                memEvent.setStartTimestamp(event.getStartTimestamp());
                memEvent.setEndTimestamp(event.getEndTimestamp());

                memEvent.setGcId(Long.parseLong(event.getValue(JFRConstants.GCID).toString()));
                memEvent.setType(event.getValue(JFRConstants.GCTYPE).toString());
                memEvent.setCause(event.getValue(JFRConstants.GCREASON).toString());
                memEvent.setPauseTime(Long.parseLong(event.getValue(JFRConstants.GCPAUSETIME).toString()));

                if(!eventMap.containsKey(memEvent.getGcId())){
                    eventMap.put(memEvent.getGcId(),memEvent);
                }
                else {
                    System.out.println("gcID already exists!");
                }
            }
        }
    }
}
