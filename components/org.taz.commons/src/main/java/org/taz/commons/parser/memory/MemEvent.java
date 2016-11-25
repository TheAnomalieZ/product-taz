package org.taz.commons.parser.memory;

import org.taz.commons.constants.JFRConstants;
import org.taz.commons.parser.JFREvent;

public class MemEvent extends JFREvent {

    private long gcId;
    private long usedHeap;

    public MemEvent(){
        super(JFRConstants.GARBAGECOLLECTION);
    }

    public long getGcId() {
        return gcId;
    }

    public long getUsedHeap() {
        return usedHeap;
    }

    public void setGcId(long gcId) {
        this.gcId = gcId;
    }

    public void setUsedHeap(long usedHeap) {
        this.usedHeap = usedHeap;
    }
}
