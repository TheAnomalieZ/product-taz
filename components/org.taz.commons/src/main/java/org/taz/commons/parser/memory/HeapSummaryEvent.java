package org.taz.commons.parser.memory;

import org.taz.commons.constants.JFRConstants;
import org.taz.commons.parser.util.JFREvent;

/**
 * Created by K.Kokulan on 12/20/2016.
 */
public class HeapSummaryEvent extends JFREvent {

    private int gcId;
    private String when;
    private String heapSpaceStart;
    private String heapSpaceCommittedEnd;
    private String heapSpaceCommittedSize;
    private String heapSpaceReservedEnd;
    private String heapSpaceReservedSize;
    private String heapUsed;

    public HeapSummaryEvent() {
        super(JFRConstants.HEAPSUMMARY);
    }

    public int getGcId() {
        return gcId;
    }

    public void setGcId(int gcId) {
        this.gcId = gcId;
    }

    public String getWhen() {
        return when;
    }

    public void setWhen(String when) {
        this.when = when;
    }

    public String getHeapSpaceStart() {
        return heapSpaceStart;
    }

    public void setHeapSpaceStart(String heapSpaceStart) {
        this.heapSpaceStart = heapSpaceStart;
    }

    public String getHeapSpaceCommittedEnd() {
        return heapSpaceCommittedEnd;
    }

    public void setHeapSpaceCommittedEnd(String heapSpaceCommittedEnd) {
        this.heapSpaceCommittedEnd = heapSpaceCommittedEnd;
    }

    public String getHeapSpaceCommittedSize() {
        return heapSpaceCommittedSize;
    }

    public void setHeapSpaceCommittedSize(String heapSpaceCommittedSize) {
        this.heapSpaceCommittedSize = heapSpaceCommittedSize;
    }

    public String getHeapSpaceReservedEnd() {
        return heapSpaceReservedEnd;
    }

    public void setHeapSpaceReservedEnd(String heapSpaceReservedEnd) {
        this.heapSpaceReservedEnd = heapSpaceReservedEnd;
    }

    public String getHeapSpaceReservedSize() {
        return heapSpaceReservedSize;
    }

    public void setHeapSpaceReservedSize(String heapSpaceReservedSize) {
        this.heapSpaceReservedSize = heapSpaceReservedSize;
    }

    public String getHeapUsed() {
        return heapUsed;
    }

    public void setHeapUsed(String heapUsed) {
        this.heapUsed = heapUsed;
    }
}
