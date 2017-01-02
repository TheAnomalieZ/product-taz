package org.taz.commons.parser.util;

import com.jrockit.mc.flightrecorder.spi.IView;
import org.taz.commons.parser.handlers.CPULoadHandler;
import org.taz.commons.parser.memory.GCTimeSeriesModel;

public class EventHandlerFactory {

    public CPULoadHandler getCPULoadHandler(IView view){
        return new CPULoadHandler(view);
    }

    public GCTimeSeriesModel getGCHandler(IView view){
        return new GCTimeSeriesModel(view);
    }

}
