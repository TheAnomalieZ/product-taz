package org.taz.commons.parser.JVM;

import org.taz.commons.constants.JFRConstants;
import org.taz.commons.parser.util.JFREvent;

/**
 * Created by K.Kokulan on 12/20/2016.
 */
public class JVMInformationEvent extends JFREvent {
    private String jvmName;
    private String jvmVersion;
    private String jvmArguments;
    private String jvmFlags;
    private String javaArguments;
    private String jvmStartTime;

    public JVMInformationEvent() {
        super(JFRConstants.JVM_INFORMATION);
    }

    public String getJvmName() {
        return jvmName;
    }

    public void setJvmName(String jvmName) {
        this.jvmName = jvmName;
    }

    public String getJvmVersion() {
        return jvmVersion;
    }

    public void setJvmVersion(String jvmVersion) {
        this.jvmVersion = jvmVersion;
    }

    public String getJvmArguments() {
        return jvmArguments;
    }

    public void setJvmArguments(String jvmArguments) {
        this.jvmArguments = jvmArguments;
    }

    public String getJvmFlags() {
        return jvmFlags;
    }

    public void setJvmFlags(String jvmFlags) {
        this.jvmFlags = jvmFlags;
    }

    public String getJavaArguments() {
        return javaArguments;
    }

    public void setJavaArguments(String javaArguments) {
        this.javaArguments = javaArguments;
    }

    public String getJvmStartTime() {
        return jvmStartTime;
    }

    public void setJvmStartTime(String jvmStartTime) {
        this.jvmStartTime = jvmStartTime;
    }
}
