package org.taz.commons.parser.events;

import com.jrockit.mc.flightrecorder.spi.IView;
import org.taz.commons.constants.JFRConstants;
import org.taz.commons.parser.util.JFREvent;


/**
 * Created by  Maninesan on 12/12/16.
 */
public class RecordingEvent extends JFREvent {

    private long id;
    private long startTime;
    private long endTime;
    private long duration;
    private String name;

    public RecordingEvent(){
        super(JFRConstants.RECORDING);
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
