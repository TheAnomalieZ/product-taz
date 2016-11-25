package org.taz.commons.parser;

import com.jrockit.mc.flightrecorder.spi.IView;

public abstract class EventHandler {
    protected IView view;
    protected String EVENT_TYPE;

    public EventHandler(IView view, String eventType){
        this.view = view;
        this.EVENT_TYPE = eventType;
    }

    public IView getView() {
        return view;
    }

    public String getEVENT_TYPE() {
        return EVENT_TYPE;
    }


    @Override
    public String toString(){
        return "Event Handler Type="+this.EVENT_TYPE+" Handler" ;
    }
}
