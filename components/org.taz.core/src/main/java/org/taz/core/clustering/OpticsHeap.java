package org.taz.core.clustering;

import de.lmu.ifi.dbs.elki.algorithm.clustering.optics.OPTICSHeap;
import de.lmu.ifi.dbs.elki.algorithm.clustering.optics.OPTICSXi;
import de.lmu.ifi.dbs.elki.data.*;
import de.lmu.ifi.dbs.elki.database.Database;
import de.lmu.ifi.dbs.elki.utilities.ClassGenericsUtil;
import de.lmu.ifi.dbs.elki.utilities.optionhandling.parameterization.ListParameterization;
import org.taz.core.clustering.util.DatabaseHandler;

import java.util.List;

/**
 * Created by vithulan on 1/13/17.
 */
public class OpticsHeap extends DatabaseHandler {
    private String filepath;
    public OpticsHeap(String filepath){
        this.filepath=filepath;
    }
    public void run(){
        Database db = makeSimpleDatabase(filepath,60);
        ListParameterization params = new ListParameterization();
        params.addParameter(OPTICSHeap.Parameterizer.MINPTS_ID, 5);
        params.addParameter(OPTICSXi.Parameterizer.XI_ID, 0.038);
        params.addParameter(OPTICSXi.Parameterizer.XIALG_ID, OPTICSHeap.class);
        OPTICSXi opticsxi = ClassGenericsUtil.parameterizeOrAbort(OPTICSXi.class, params);

        de.lmu.ifi.dbs.elki.data.Clustering<?> clustering = opticsxi.run(db);
        List<? extends Cluster<?>> clusters = clustering.getAllClusters();

        for(Cluster cluster : clusters){
            System.out.println("Cluster name : "+cluster.getName());
            System.out.println(cluster.size());
        }

    }
}
