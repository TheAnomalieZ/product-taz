package org.taz.commons.parser.handlers;

import com.jrockit.mc.flightrecorder.spi.IEvent;
import com.jrockit.mc.flightrecorder.spi.IView;
import org.taz.commons.constants.JFRConstants;
import org.taz.commons.parser.events.GCHeapConfigurationEvent;
import org.taz.commons.parser.util.EventHandler;

import java.util.ArrayList;

/**
 * Created by Maninesan on 1/16/17.
 */
public class GCHeapConfigurationEventHandler extends EventHandler {
    GCHeapConfigurationEvent gcHeapConfig;
    public GCHeapConfigurationEventHandler(IView view){
        super(view, JFRConstants.GCHEAPCONFIGURATION);
        gcHeapConfig = new GCHeapConfigurationEvent();
        init();
    }

    public GCHeapConfigurationEvent getGcHeapConfig() {
        return gcHeapConfig;
    }

    private void init(){
        for (IEvent event : view) {
            if (EVENT_TYPE.equals(event.getEventType().getName())) {
                gcHeapConfig.setInitialSize(event.getValue(JFRConstants.INITIALSIZE).toString());
                gcHeapConfig.setMaxSize(event.getValue(JFRConstants.MAXSIZE).toString());
                gcHeapConfig.setMinSize(event.getValue(JFRConstants.MINSIZE).toString());
            }
        }
    }

}
