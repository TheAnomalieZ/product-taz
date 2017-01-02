package org.taz.commons.parser.events;

import org.taz.commons.constants.JFRConstants;
import org.taz.commons.parser.util.JFREvent;

/**
 * Created by K.Kokulan on 12/26/2016.
 */
public class RecordingSettingEvent extends JFREvent {

    private String period;
    private String threshold;
    private String stacktrace;
    private String enabled;
    private String path;
    private String name;
    private String id;

    public RecordingSettingEvent(){
        super(JFRConstants.RECORDING_SETTING);
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getThreshold() {
        return threshold;
    }

    public void setThreshold(String threshold) {
        this.threshold = threshold;
    }

    public String getStacktrace() {
        return stacktrace;
    }

    public void setStacktrace(String stacktrace) {
        this.stacktrace = stacktrace;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
