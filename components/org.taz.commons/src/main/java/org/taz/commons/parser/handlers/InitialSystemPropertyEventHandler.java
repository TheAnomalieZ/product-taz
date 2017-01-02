package org.taz.commons.parser.handlers;

import com.jrockit.mc.flightrecorder.spi.IEvent;
import com.jrockit.mc.flightrecorder.spi.IView;
import oracle.jrockit.jfr.JFR;
import org.taz.commons.constants.JFRConstants;
import org.taz.commons.parser.events.HeapSummaryEvent;
import org.taz.commons.parser.events.InitialSystemPropertyEvent;
import org.taz.commons.parser.util.EventHandler;

import java.util.ArrayList;

/**
 * Created by K.Kokulan on 12/26/2016.
 */
public class InitialSystemPropertyEventHandler extends EventHandler {
    private ArrayList<InitialSystemPropertyEvent> initialSystemPropertyEventArrayList;

    public InitialSystemPropertyEventHandler(IView view) {
        super(view, JFRConstants.INIT_SYSTEM_PROPERTY);
        initialSystemPropertyEventArrayList = new ArrayList<>();
    }

    public ArrayList<InitialSystemPropertyEvent> getEventSeries() {
        for (IEvent event : view) {
            if(EVENT_TYPE.equals(event.getEventType().getName())){
                InitialSystemPropertyEvent initialSystemPropertyEvent = new InitialSystemPropertyEvent();

                initialSystemPropertyEvent.setStartTimestamp(event.getStartTimestamp());
                initialSystemPropertyEvent.setEndTimestamp(event.getEndTimestamp());

                initialSystemPropertyEvent.setKey(event.getValue(JFRConstants.KEY).toString());
                initialSystemPropertyEvent.setValue(event.getValue(JFRConstants.VALUE).toString());
                initialSystemPropertyEventArrayList.add(initialSystemPropertyEvent);
            }
        }
        return initialSystemPropertyEventArrayList;
    }
}
