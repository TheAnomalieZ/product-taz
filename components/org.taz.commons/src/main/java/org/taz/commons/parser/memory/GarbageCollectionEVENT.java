package org.taz.commons.parser.memory;

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
}
