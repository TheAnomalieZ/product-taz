package org.taz.core.clustering;

import de.lmu.ifi.dbs.elki.algorithm.clustering.optics.AbstractOPTICS;
import de.lmu.ifi.dbs.elki.algorithm.outlier.OPTICSOF;
import de.lmu.ifi.dbs.elki.data.DoubleVector;
import de.lmu.ifi.dbs.elki.database.Database;
import de.lmu.ifi.dbs.elki.database.ids.DBIDIter;
import de.lmu.ifi.dbs.elki.database.ids.DBIDUtil;
import de.lmu.ifi.dbs.elki.database.relation.DoubleRelation;
import de.lmu.ifi.dbs.elki.result.outlier.OutlierResult;
import de.lmu.ifi.dbs.elki.utilities.ClassGenericsUtil;
import de.lmu.ifi.dbs.elki.utilities.optionhandling.parameterization.ListParameterization;
import org.taz.core.clustering.util.DatabaseHandler;

/**
 * Created by vithulan on 11/25/16.
 */
public class Clustering extends DatabaseHandler {
    //Clusterer clusterer = new OPTICS(5.0,3);
    public void cluster(){
        Database db = makeSimpleDatabase(FILE_PATH,550);
        /*ListParameterization params = new ListParameterization();
        params.addParameter(AbstractOPTICS.Parameterizer.MINPTS_ID, 22);

        // setup Algorithm
        OPTICSOF<DoubleVector> opticsof = ClassGenericsUtil.parameterizeOrAbort(OPTICSOF.class, params);
        OutlierResult result = opticsof.run(db);*/

      //  Database db = makeSimp
        //clusterer.cluster()
        //OPTICSHeap opticsHeap = new OPTICSHeap();

        ListParameterization params = new ListParameterization();
        params.addParameter(AbstractOPTICS.Parameterizer.MINPTS_ID, 5);

        OPTICSOF<DoubleVector> opticsof = ClassGenericsUtil.parameterizeOrAbort(OPTICSOF.class, params);

        // run OPTICSOF on database
        OutlierResult result = opticsof.run(db);

        DoubleRelation scores = result.getScores();
        for (DBIDIter iter = scores.iterDBIDs(); iter.valid(); iter.advance()) {
            System.out.println(DBIDUtil.toString(iter) + " " + scores.doubleValue(iter));
        }
    }
}
