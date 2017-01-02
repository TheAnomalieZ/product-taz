package org.taz.commons.parser.events;

import org.taz.commons.constants.JFRConstants;
import org.taz.commons.parser.util.JFREvent;

/**
 * Created by K.Kokulan on 12/20/2016.
 */
public class GarbageCollectionEvent extends JFREvent {
    private String gcId;
    private String name;
    private String cause;
    private String sumOfPauses;
    private String longestPause;
    private String startTimeString;
    private String endTimeString;
    private String durationString;

    public GarbageCollectionEvent(){
        super(JFRConstants.GARBAGECOLLECTION);
    }

    public String getGcId() {
        return gcId;
    }

    public void setGcId(String gcId) {
        this.gcId = gcId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public String getSumOfPauses() {
        return sumOfPauses;
    }

    public void setSumOfPauses(String sumOfPauses) {
        this.sumOfPauses = sumOfPauses;
    }

    public String getLongestPause() {
        return longestPause;
    }

    public void setLongestPause(String longestPause) {
        this.longestPause = longestPause;
    }

    public String getStartTimeString() {
        return startTimeString;
    }

    public void setStartTimeString(String startTimeString) {
        this.startTimeString = startTimeString;
    }

    public String getEndTimeString() {
        return endTimeString;
    }

    public void setEndTimeString(String endTimeString) {
        this.endTimeString = endTimeString;
    }

    public String getDurationString() {
        return durationString;
    }

    public void setDurationString(String durationString) {
        this.durationString = durationString;
    }
}
