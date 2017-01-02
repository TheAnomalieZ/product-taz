package org.taz.commons.parser.events;

import org.taz.commons.constants.JFRConstants;
import org.taz.commons.parser.util.JFREvent;

/**
 * Created by K.Kokulan on 12/26/2016.
 */
public class PermGenSummaryEvent extends JFREvent {
    private String gcId;
    private String when;
    private String permSpaceStart;
    private String permSpaceCommittedEnd;
    private String permSpaceCommittedSize;
    private String permSpaceReservedEnd;
    private String permSpaceReservedSize;
    private String objectSpaceStart;
    private String objectSpaceEnd;
    private String objectSpaceUsed;
    private String objectSpaceSize;

    public PermGenSummaryEvent() {
        super(JFRConstants.PERM_GEN_SUMMARY);
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

    public String getPermSpaceStart() {
        return permSpaceStart;
    }

    public void setPermSpaceStart(String permSpaceStart) {
        this.permSpaceStart = permSpaceStart;
    }

    public String getPermSpaceCommittedEnd() {
        return permSpaceCommittedEnd;
    }

    public void setPermSpaceCommittedEnd(String permSpaceCommittedEnd) {
        this.permSpaceCommittedEnd = permSpaceCommittedEnd;
    }

    public String getPermSpaceCommittedSize() {
        return permSpaceCommittedSize;
    }

    public void setPermSpaceCommittedSize(String permSpaceCommittedSize) {
        this.permSpaceCommittedSize = permSpaceCommittedSize;
    }

    public String getPermSpaceReservedEnd() {
        return permSpaceReservedEnd;
    }

    public void setPermSpaceReservedEnd(String permSpaceReservedEnd) {
        this.permSpaceReservedEnd = permSpaceReservedEnd;
    }

    public String getPermSpaceReservedSize() {
        return permSpaceReservedSize;
    }

    public void setPermSpaceReservedSize(String permSpaceReservedSize) {
        this.permSpaceReservedSize = permSpaceReservedSize;
    }

    public String getObjectSpaceStart() {
        return objectSpaceStart;
    }

    public void setObjectSpaceStart(String objectSpaceStart) {
        this.objectSpaceStart = objectSpaceStart;
    }

    public String getObjectSpaceEnd() {
        return objectSpaceEnd;
    }

    public void setObjectSpaceEnd(String objectSpaceEnd) {
        this.objectSpaceEnd = objectSpaceEnd;
    }

    public String getObjectSpaceUsed() {
        return objectSpaceUsed;
    }

    public void setObjectSpaceUsed(String objectSpaceUsed) {
        this.objectSpaceUsed = objectSpaceUsed;
    }

    public String getObjectSpaceSize() {
        return objectSpaceSize;
    }

    public void setObjectSpaceSize(String objectSpaceSize) {
        this.objectSpaceSize = objectSpaceSize;
    }
}
