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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * Created by vithulan on 11/25/16.
 */
public class Clustering extends DatabaseHandler {
    //Clusterer clusterer = new OPTICS(5.0,3);
    public void cluster(){

        /*ListParameterization params = new ListParameterization();
        params.addParameter(AbstractOPTICS.Parameterizer.MINPTS_ID, 22);

        // setup Algorithm
        OPTICSOF<DoubleVector> opticsof = ClassGenericsUtil.parameterizeOrAbort(OPTICSOF.class, params);
        OutlierResult result = opticsof.run(db);*/

      //  Database db = makeSimp
        //clusterer.cluster()
        //OPTICSHeap opticsHeap = new OPTICSHeap();
        int minPoint = 400;
        for (int i=1;i<=10;i++) {
            PrintWriter outfile = null;
            try {
                outfile = new PrintWriter(new File("heap_duration_old_gap_results"+"_"+(minPoint+i*10)+"_v2"+".csv"));

            Database db = makeSimpleDatabase(FILE_PATH, 8090);
            ListParameterization params = new ListParameterization();
            params.addParameter(AbstractOPTICS.Parameterizer.MINPTS_ID, minPoint+i*10);
                System.out.println("Min point - "+minPoint+i*10);
            OPTICSOF<DoubleVector> opticsof = ClassGenericsUtil.parameterizeOrAbort(OPTICSOF.class, params);
            // run OPTICSOF on database
            OutlierResult result = opticsof.run(db);
            DoubleRelation scores = result.getScores();
            for (DBIDIter iter = scores.iterDBIDs(); iter.valid(); iter.advance()) {
                //System.out.println(DBIDUtil.toString(iter) + "," + scores.doubleValue(iter));
                outfile.append(Double.toString(scores.doubleValue(iter))+"\n");
                //DBIDUtil.toString(iter) + " " +
            }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            finally {
                outfile.close();
            }
        }
    }
}
