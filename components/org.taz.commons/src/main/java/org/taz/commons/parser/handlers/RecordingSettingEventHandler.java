package org.taz.commons.parser.handlers;

import com.jrockit.mc.flightrecorder.spi.IEvent;
import com.jrockit.mc.flightrecorder.spi.IView;
import org.taz.commons.constants.JFRConstants;
import org.taz.commons.parser.events.RecordingSettingEvent;
import org.taz.commons.parser.util.EventHandler;

import java.util.ArrayList;

/**
 * Created by K.Kokulan on 12/26/2016.
 */
public class RecordingSettingEventHandler extends EventHandler{
    private ArrayList<RecordingSettingEvent> eventList;

    public RecordingSettingEventHandler(IView view) {
        super(view, JFRConstants.RECORDING_SETTING);
        eventList = new ArrayList<>();
    }

    public ArrayList<RecordingSettingEvent> getEventsList() {
        for (IEvent event : view) {
            if(EVENT_TYPE.equals(event.getEventType().getName())){
                RecordingSettingEvent recordingSettingEvent = new RecordingSettingEvent();

                recordingSettingEvent.setId(event.getValue(JFRConstants.ID).toString());
                recordingSettingEvent.setName(event.getValue(JFRConstants.NAME).toString());
                recordingSettingEvent.setEnabled(event.getValue(JFRConstants.ENABLED).toString());
                recordingSettingEvent.setPath(event.getValue(JFRConstants.PATH).toString());
                recordingSettingEvent.setPeriod(event.getValue(JFRConstants.PERIOD).toString());
                recordingSettingEvent.setStacktrace(event.getValue(JFRConstants.STACKTRACE).toString());
                recordingSettingEvent.setThreshold(event.getValue(JFRConstants.THRESHOLD).toString());

                eventList.add(recordingSettingEvent);
            }
        }
        return eventList;
    }

}
