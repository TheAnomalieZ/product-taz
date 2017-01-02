package org.taz.commons.parser.events;

import org.taz.commons.constants.JFRConstants;
import org.taz.commons.parser.util.JFREvent;

/**
 * Created by K.Kokulan on 12/26/2016.
 */
public class GCReferenceProcessingEvent extends JFREvent {

    private String gcId;
    private String type;
    private String count;

    public GCReferenceProcessingEvent() {
        super(JFRConstants.GC_REFER_PROCESSING);
    }

    public String getGcId() {
        return gcId;
    }

    public void setGcId(String gcId) {
        this.gcId = gcId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
