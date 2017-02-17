package org.taz.core.clustering;

import de.lmu.ifi.dbs.elki.algorithm.clustering.optics.AbstractOPTICS;
import de.lmu.ifi.dbs.elki.algorithm.outlier.OPTICSOF;
import de.lmu.ifi.dbs.elki.data.DoubleVector;
import de.lmu.ifi.dbs.elki.database.Database;
import de.lmu.ifi.dbs.elki.database.StaticArrayDatabase;
import de.lmu.ifi.dbs.elki.database.ids.DBIDIter;
import de.lmu.ifi.dbs.elki.database.ids.DBIDUtil;
import de.lmu.ifi.dbs.elki.database.relation.DoubleRelation;
import de.lmu.ifi.dbs.elki.datasource.ArrayAdapterDatabaseConnection;
import de.lmu.ifi.dbs.elki.datasource.DatabaseConnection;
import de.lmu.ifi.dbs.elki.logging.LoggingConfiguration;
import de.lmu.ifi.dbs.elki.result.outlier.OutlierResult;
import de.lmu.ifi.dbs.elki.utilities.ClassGenericsUtil;
import de.lmu.ifi.dbs.elki.utilities.optionhandling.parameterization.ListParameterization;
import org.taz.core.clustering.util.ParserAPI;

/**
 * Created by vithulan on 1/6/17.
 */
public class TestApp {
    public static void main(String[] args) {
        //String file_path = "/home/vithulan/JFRs/Clustering/data.csv";
        String file_path = "/home/vithulan/JFRs/CSVs/heapused_metaspace_dataspace.csv";

        Clustering clustering  = new Clustering();
        clustering.cluster();

       // OpticsHeap opticsHeap = new OpticsHeap(file_path);
       // opticsHeap.run();

        /*ParserAPI parserAPI = new ParserAPI();
        parserAPI.getAttributes();
*/

        //LoggingConfiguration.setStatistics();

        // Generate a random data set.
        // Note: ELKI has a nice data generator class, use that instead.
        /*double[][] data = new double[1000][2];
        for(int i = 0; i < data.length; i++) {
            for(int j = 0; j < data[i].length; j++) {
                data[i][j] = Math.random();
            }
        }

        // Adapter to load data from an existing array.
        DatabaseConnection dbc = new ArrayAdapterDatabaseConnection(data);
        // Create a database (which may contain multiple relations!)
        Database db = new StaticArrayDatabase(dbc, null);
        // Load the data into the database (do NOT forget to initialize...)
        db.initialize();

        ListParameterization params = new ListParameterization();
        params.addParameter(AbstractOPTICS.Parameterizer.MINPTS_ID, 50);

        // setup Algorithm
        OPTICSOF<DoubleVector> opticsof = ClassGenericsUtil.parameterizeOrAbort(OPTICSOF.class, params);

        // run OPTICSOF on database
        OutlierResult result = opticsof.run(db);

        DoubleRelation scores = result.getScores();
        for (DBIDIter iter = scores.iterDBIDs(); iter.valid(); iter.advance()) {
            System.out.println(DBIDUtil.toString(iter) + " " + scores.doubleValue(iter));
        }*/
    }
}
