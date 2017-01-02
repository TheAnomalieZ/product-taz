package org.taz.commons.parser.handlers;

import com.jrockit.mc.flightrecorder.spi.IEvent;
import com.jrockit.mc.flightrecorder.spi.IView;
import org.taz.commons.constants.JFRConstants;
import org.taz.commons.parser.events.JVMInformationEvent;
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
                jvmInformationEvent.setJvmName(event.getValue(JFRConstants.JVM_NAME).toString());
                jvmInformationEvent.setJvmArguments(event.getValue(JFRConstants.JVM_ARGUMENTS).toString());
                jvmInformationEvent.setJavaArguments(event.getValue(JFRConstants.JAVA_ARGUMENTS).toString());
                jvmInformationEvent.setJvmFlags(event.getValue(JFRConstants.JVM_FLAGS).toString());
            }
        }
        return jvmInformationEvent;
    }

    public ArrayList<JVMInformationEvent> getEventList() {
        ArrayList<JVMInformationEvent> jvmInformationEventArrayList = new ArrayList<>();

        for (IEvent event : view) {
            if(EVENT_TYPE.equals(event.getEventType().getName())){
                jvmInformationEvent = new JVMInformationEvent();

                jvmInformationEvent.setStartTimestamp(event.getStartTimestamp());
                jvmInformationEvent.setEndTimestamp(event.getEndTimestamp());

                jvmInformationEvent.setJvmVersion(event.getValue(JFRConstants.JVM_VERSION).toString());
                jvmInformationEvent.setJvmStartTime(event.getValue(JFRConstants.JVM_START_TIME).toString());
                jvmInformationEvent.setJvmStartTime(event.getValue(JFRConstants.JVM_NAME).toString());
                jvmInformationEvent.setJvmStartTime(event.getValue(JFRConstants.JVM_ARGUMENTS).toString());
                jvmInformationEvent.setJvmStartTime(event.getValue(JFRConstants.JAVA_ARGUMENTS).toString());
                jvmInformationEvent.setJvmStartTime(event.getValue(JFRConstants.JVM_FLAGS).toString());

                jvmInformationEventArrayList.add(jvmInformationEvent);
            }
        }
        return jvmInformationEventArrayList;
    }
}
