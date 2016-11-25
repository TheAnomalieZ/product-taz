package org.taz.commons.parser.cpu;

import com.jrockit.mc.flightrecorder.spi.IEvent;
import com.jrockit.mc.flightrecorder.spi.IView;
import org.taz.commons.constants.JFRConstants;
import org.taz.commons.parser.EventHandler;

import java.util.ArrayList;

public class CPULoadHandler extends EventHandler {
    public ArrayList<CPULoadEvent> eventList;
    public CPULoadHandler(IView view){
        super(view, JFRConstants.CPULOAD);
        eventList = new ArrayList<CPULoadEvent>();
    }

    public ArrayList<CPULoadEvent> getEventSeries() {
        for (IEvent event : view) {
            if(EVENT_TYPE.equals(event.getEventType().getName())){
                CPULoadEvent cpuLoadEvent = new CPULoadEvent();

                cpuLoadEvent.setStartTimestamp(event.getStartTimestamp());
                cpuLoadEvent.setEndTimestamp(event.getEndTimestamp());

                cpuLoadEvent.setJvmUser(event.getValue(JFRConstants.JVMUSER).toString());
                cpuLoadEvent.setJvmSystem(event.getValue(JFRConstants.JVMSYSTEM).toString());
                cpuLoadEvent.setMachineTotal(event.getValue(JFRConstants.MACHINETOTAL).toString());

                eventList.add(cpuLoadEvent);
            }
        }
        return eventList;
    }

}
