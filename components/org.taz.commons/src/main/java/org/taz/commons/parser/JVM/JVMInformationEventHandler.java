package org.taz.commons.parser.JVM;

import com.jrockit.mc.flightrecorder.spi.IEvent;
import com.jrockit.mc.flightrecorder.spi.IView;
import org.taz.commons.constants.JFRConstants;
import org.taz.commons.parser.util.EventHandler;

import java.util.ArrayList;

/**
 * Created by K.Kokulan on 12/20/2016.
 */
public class JVMInformationEventHandler extends EventHandler {
    private JVMInformationEvent jvmInformationEvent;
    public JVMInformationEventHandler(IView view){
        super(view, JFRConstants.JVM_INFORMATION);
    }

    public JVMInformationEvent getEvent() {
        for (IEvent event : view) {
            if(EVENT_TYPE.equals(event.getEventType().getName())){
                jvmInformationEvent = new JVMInformationEvent();

                jvmInformationEvent.setStartTimestamp(event.getStartTimestamp());
                jvmInformationEvent.setEndTimestamp(event.getEndTimestamp());

                jvmInformationEvent.setJvmVersion(event.getValue(JFRConstants.JVM_VERSION).toString());
                jvmInformationEvent.setJvmStartTime(event.getValue(JFRConstants.JVM_START_TIME).toString());
            }
        }
        return jvmInformationEvent;
    }
}
