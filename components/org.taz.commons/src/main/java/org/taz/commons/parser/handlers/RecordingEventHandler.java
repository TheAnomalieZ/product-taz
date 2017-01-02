package org.taz.commons.parser.handlers;

import com.jrockit.mc.flightrecorder.spi.IEvent;
import com.jrockit.mc.flightrecorder.spi.IView;
import org.taz.commons.constants.JFRConstants;
import org.taz.commons.parser.events.RecordingEvent;
import org.taz.commons.parser.util.EventHandler;

/**
 * Created by  Maninesan on 12/12/16.
 */
public class RecordingEventHandler extends EventHandler {
    RecordingEvent recordingEvent;

    public RecordingEventHandler(IView view) {
        super(view, JFRConstants.RECORDING);
        recordingEvent = new RecordingEvent();
        init();
    }

    public void init(){
        for (IEvent event : view) {
            if (EVENT_TYPE.equals(event.getEventType().getName())) {
                recordingEvent.setId(Long.parseLong(event.getValue(JFRConstants.ID).toString()));
                recordingEvent.setDuration(Long.parseLong(event.getValue(JFRConstants.DURATION).toString()));
                recordingEvent.setStartTime(Long.parseLong(event.getValue(JFRConstants.STARTTIME).toString()));
//                recordingEvent.setStartTime(event.getStartTimestamp());
                recordingEvent.setName(event.getValue(JFRConstants.RECORDINGNAME).toString());
            }
        }
    }

    public RecordingEvent getRecordingEvent() {
        return recordingEvent;
    }


}
