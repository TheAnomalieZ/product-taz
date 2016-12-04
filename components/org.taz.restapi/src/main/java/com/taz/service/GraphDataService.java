package com.taz.service;

import com.taz.graph_models.CpuUsage;
import com.taz.graph_models.GcData;
import com.taz.graph_models.HeapUsage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Created by K.Kokulan on 12/3/2016.
 */

@Service
public class GraphDataService {


    public HeapUsage getHeapUsageData() {
        HeapUsage heapUsage = new HeapUsage();
        heapUsage.setMax(60);
        heapUsage.setAvg(30);
        heapUsage.setData(60);

        String sample= "[[1398709800000,780,136],[1398450600000,812,134],[1399401000000,784,154],[1399228200000,786,135],[1399573800000,802,131],"+
        "[1399487400000,773,166],[1399314600000,787,146],[1399919400000,1496,309],[1399833000000,767,138],[1399746600000,797,141],"+
        "[1399660200000,796,146],[1398623400000,779,143],[1399055400000,794,140],[1398969000000,791,140],[1398882600000,825,107],"+
        "[1399141800000,786,136],[1398537000000,773,143],[1398796200000,783,154],[1400005800000,1754,284]]";
        heapUsage.setTenpData(sample);
        return heapUsage;
    }

    public CpuUsage getCpuUsageData(){
        CpuUsage cpuUsage = new CpuUsage();
        cpuUsage.setMax(28);
        cpuUsage.setAvg(20);
        cpuUsage.setData(28);

        String sample= "[[1398709800000,10,15, 20],[1398450600000,8,13,22],[1399401000000,13,14,18],[1399228200000,16,20,18],[1399573800000,9,15,19],"+
                "[1399487400000,13,22,18],[1399314600000,8,6,12],[1399919400000,22,10,16],[1399833000000,21,13,11],[1399746600000,16,22,17]]";

        cpuUsage.setTempCpuUsage(sample);

        return cpuUsage;
    }

    public GcData getGcData(){
        GcData gcData = new GcData();
        gcData.setMax(90);
        gcData.setAvg(80);
        gcData.setData(90);

        return gcData;
    }
}
