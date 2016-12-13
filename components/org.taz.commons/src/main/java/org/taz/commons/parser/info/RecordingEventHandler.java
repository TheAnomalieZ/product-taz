package org.taz.commons.parser.info;

import com.jrockit.mc.flightrecorder.spi.IEvent;
import com.jrockit.mc.flightrecorder.spi.IView;
import org.taz.commons.constants.JFRConstants;
import org.taz.commons.parser.memory.MemEvent;
import org.taz.commons.parser.util.EventHandler;

import java.util.Map;

/**
 * Created by  Maninesan on 12/12/16.
 */
public class RecordingEventHandler extends EventHandler {
    RecordingEvent recordingEvent;

    public RecordingEventHandler(IView view) {
        super(view, JFRConstants.RECORDINGHANDLER);
        recordingEvent = new RecordingEvent();
        init();
    }

    public void init(){
        for (IEvent event : view) {
            if (EVENT_TYPE.equals(event.getEventType().getName())) {
                long id = Long.parseLong(event.getValue(JFRConstants.ID).toString());
                recordingEvent.setDuration(Long.parseLong(event.getValue(JFRConstants.DURATION).toString()));
                recordingEvent.setStartTime(Long.parseLong(event.getValue(JFRConstants.STARTTIME).toString()));
//                recordingEvent.setStartTime(event.getStartTimestamp());
                recordingEvent.setName(event.getValue(JFRConstants.RECORDINGNAME).toString());

                System.out.println(recordingEvent.getDuration());
                System.out.println(recordingEvent.getName());

            }
        }
    }

    public RecordingEvent getRecordingEvent() {
        return recordingEvent;
    }


}
