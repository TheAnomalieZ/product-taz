package org.taz.commons.parser.memory;

import com.jrockit.mc.flightrecorder.spi.IEvent;
import com.jrockit.mc.flightrecorder.spi.IView;
import org.taz.commons.constants.JFRConstants;
import org.taz.commons.parser.EventHandler;


import java.util.Map;

public class GCMemoryHandler extends EventHandler {
    private final Map<Long, MemEvent> eventMap;

    public GCMemoryHandler(IView view, Map<Long, MemEvent> eventMap) {
        super(view, JFRConstants.HEAPSUMMARY);
        this.eventMap = eventMap;
    }

    public void configureGCMemory() {
        for (IEvent event : view) {
            if (EVENT_TYPE.equals(event.getEventType().getName())) {
                if(event.getValue(JFRConstants.WHEN).toString().equals(JFRConstants.BEFOREGC)){
                    long gcID = Long.parseLong(event.getValue(JFRConstants.GCID).toString());
                    if (eventMap.containsKey(gcID)){
                        MemEvent memEvent = eventMap.get(gcID);
                        memEvent.setUsedHeap(Long.parseLong(event.getValue(JFRConstants.HEAPUSED).toString()));
                    }
                    else {
                        System.out.println("gcID doesn't exist!");
                    }
                }
            }
        }
    }
}
