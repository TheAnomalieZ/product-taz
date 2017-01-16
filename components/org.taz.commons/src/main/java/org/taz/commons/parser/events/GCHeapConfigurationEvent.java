package org.taz.commons.parser.events;

import org.taz.commons.constants.JFRConstants;
import org.taz.commons.parser.util.JFREvent;

/**
 * Created by Maninesan on 1/16/17.
 */
public class GCHeapConfigurationEvent  extends JFREvent {

    private String minSize;
    private String maxSize;
    private String initialSize;

    public GCHeapConfigurationEvent(){
        super(JFRConstants.GCHEAPCONFIGURATION);
    }

    public String getMinSize() {
        return minSize;
    }

    public void setMinSize(String minSize) {
        this.minSize = minSize;
    }

    public String getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(String maxSize) {
        this.maxSize = maxSize;
    }

    public String getInitialSize() {
        return initialSize;
    }

    public void setInitialSize(String initialSize) {
        this.initialSize = initialSize;
    }



}
