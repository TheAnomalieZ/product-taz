package org.taz.commons.parser.cpu;


import org.taz.commons.constants.JFRConstants;
import org.taz.commons.parser.util.JFREvent;

public class CPULoadEvent extends JFREvent {

    private String jvmUser;
    private String jvmSystem;
    private String machineTotal;

    public CPULoadEvent(){
        super(JFRConstants.CPULOAD);
    }

    public String getJvmUser() {
        return jvmUser;
    }

    public void setJvmUser(String jvmUser) {
        this.jvmUser = jvmUser;
    }

    public String getJvmSystem() {
        return jvmSystem;
    }

    public void setJvmSystem(String jvmSystem) {
        this.jvmSystem = jvmSystem;
    }

    public String getMachineTotal() {
        return machineTotal;
    }

    public void setMachineTotal(String machineTotal) {
        this.machineTotal = machineTotal;
    }


}
