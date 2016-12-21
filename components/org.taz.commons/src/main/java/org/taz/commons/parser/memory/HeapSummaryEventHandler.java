package org.taz.commons.parser.memory;

import com.jrockit.mc.flightrecorder.spi.IEvent;
import com.jrockit.mc.flightrecorder.spi.IView;
import org.taz.commons.constants.JFRConstants;
import org.taz.commons.parser.util.EventHandler;

import java.util.ArrayList;

/**
 * Created by K.Kokulan on 12/20/2016.
 */
public class HeapSummaryEventHandler extends EventHandler {
    public ArrayList<HeapSummaryEvent> eventList;

    public HeapSummaryEventHandler(IView view){
        super(view, JFRConstants.HEAPSUMMARY);
        eventList = new ArrayList<>();
    }

    public ArrayList<HeapSummaryEvent> getEventSeries() {
        for (IEvent event : view) {
            if(EVENT_TYPE.equals(event.getEventType().getName())){
                HeapSummaryEvent heapSummaryEvent = new HeapSummaryEvent();

                heapSummaryEvent.setStartTimestamp(event.getStartTimestamp());
                heapSummaryEvent.setEndTimestamp(event.getEndTimestamp());

                heapSummaryEvent.setHeapUsed(event.getValue(JFRConstants.HEAPUSED).toString());
                heapSummaryEvent.setHeapSpaceCommittedSize(event.getValue(JFRConstants.HEAP_SPACE_COMMITTED_SIZE).toString());
                eventList.add(heapSummaryEvent);
            }
        }
        return eventList;
    }
}
