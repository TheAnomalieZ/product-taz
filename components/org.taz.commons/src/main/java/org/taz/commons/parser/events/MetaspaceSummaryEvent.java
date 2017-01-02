package org.taz.commons.parser.events;

import org.taz.commons.constants.JFRConstants;
import org.taz.commons.parser.util.JFREvent;

/**
 * Created by K.Kokulan on 12/27/2016.
 */
public class MetaspaceSummaryEvent extends JFREvent {

    private String gcId;
    private String when;
    private String gcThreshold;
    private String metaspaceCommitted;
    private String metaspaceUsed;
    private String metaspaceReserved;
    private String dataSpaceCommitted;
    private String dataSpaceUsed;
    private String dataSpaceReserved;
    private String classSpaceCommitted;
    private String classSpaceUsed;
    private String classSpaceReserved;

    public MetaspaceSummaryEvent() {
        super(JFRConstants.METASPACE_SUMMARY);
    }

    public String getGcId() {
        return gcId;
    }

    public void setGcId(String gcId) {
        this.gcId = gcId;
    }

    public String getWhen() {
        return when;
    }

    public void setWhen(String when) {
        this.when = when;
    }

    public String getGcThreshold() {
        return gcThreshold;
    }

    public void setGcThreshold(String gcThreshold) {
        this.gcThreshold = gcThreshold;
    }

    public String getMetaspaceCommitted() {
        return metaspaceCommitted;
    }

    public void setMetaspaceCommitted(String metaspaceCommitted) {
        this.metaspaceCommitted = metaspaceCommitted;
    }

    public String getMetaspaceUsed() {
        return metaspaceUsed;
    }

    public void setMetaspaceUsed(String metaspaceUsed) {
        this.metaspaceUsed = metaspaceUsed;
    }

    public String getMetaspaceReserved() {
        return metaspaceReserved;
    }

    public void setMetaspaceReserved(String metaspaceReserved) {
        this.metaspaceReserved = metaspaceReserved;
    }

    public String getDataSpaceCommitted() {
        return dataSpaceCommitted;
    }

    public void setDataSpaceCommitted(String dataSpaceCommitted) {
        this.dataSpaceCommitted = dataSpaceCommitted;
    }

    public String getDataSpaceUsed() {
        return dataSpaceUsed;
    }

    public void setDataSpaceUsed(String dataSpaceUsed) {
        this.dataSpaceUsed = dataSpaceUsed;
    }

    public String getDataSpaceReserved() {
        return dataSpaceReserved;
    }

    public void setDataSpaceReserved(String dataSpaceReserved) {
        this.dataSpaceReserved = dataSpaceReserved;
    }

    public String getClassSpaceCommitted() {
        return classSpaceCommitted;
    }

    public void setClassSpaceCommitted(String classSpaceCommitted) {
        this.classSpaceCommitted = classSpaceCommitted;
    }

    public String getClassSpaceUsed() {
        return classSpaceUsed;
    }

    public void setClassSpaceUsed(String classSpaceUsed) {
        this.classSpaceUsed = classSpaceUsed;
    }

    public String getClassSpaceReserved() {
        return classSpaceReserved;
    }

    public void setClassSpaceReserved(String classSpaceReserved) {
        this.classSpaceReserved = classSpaceReserved;
    }
}
