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
import org.taz.core.clustering.util.Properties;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by vithulan on 11/25/16.
 */
public class Clustering extends DatabaseHandler {
    private int minPoint;
    private int totalPoints;
    private String file_path;
    private String file_name;
    //Clusterer clusterer = new OPTICS(5.0,3);
    public Clustering(int totalPoints, String file_name,int minPoint){
        this.minPoint = minPoint;
        this.totalPoints = totalPoints;
        this.file_path = Properties.ATTRIBUTE_TABLE_FILEPATH+file_name;
        this.file_name = file_name;

    }
    public Clustering (int totalPoints, String file_name){
        this.file_path = Properties.ATTRIBUTE_TABLE_FILEPATH+file_name;
        this.totalPoints = totalPoints;
        this.minPoint = (totalPoints/100)*51;
        this.file_name = file_name;
        System.out.println("total - "+totalPoints);
        System.out.println("filepath - "+file_path);
        System.out.println("minpoints - "+minPoint);
    }
    public void generateAnomalyScore(){
        String dirName = file_name.replaceAll(".csv","");
        new File(Properties.ANOMALY_SCORES_FILEPATH+dirName).mkdir();
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            try {
                PrintWriter outfile = new PrintWriter(new File(Properties.ANOMALY_SCORES_FILEPATH+dirName+"/"+"anomaly_score_"+timestamp+".csv"));
                Database db = makeSimpleDatabase(file_path,totalPoints);
                ListParameterization params = new ListParameterization();
                params.addParameter(AbstractOPTICS.Parameterizer.MINPTS_ID, minPoint);
                System.out.println("Min point - "+minPoint);
                OPTICSOF<DoubleVector> opticsof = ClassGenericsUtil.parameterizeOrAbort(OPTICSOF.class, params);
                // run OPTICSOF on database
                OutlierResult result = opticsof.run(db);
                DoubleRelation scores = result.getScores();
                for (DBIDIter iter = scores.iterDBIDs(); iter.valid(); iter.advance()) {
                    //System.out.println(DBIDUtil.toString(iter) + "," + scores.doubleValue(iter));
                    outfile.append(Double.toString(scores.doubleValue(iter))+"\n");
                    //DBIDUtil.toString(iter) + " " +
                }
                outfile.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
    }

    public void runMinPointTest(int startMinPoint, int iterations, int gap){
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        int minPoint = startMinPoint;
        String dirName = file_name.replaceAll(".csv","");
        System.out.println(dirName);
        new File(Properties.MINPOINT_TEST_FILEPATH+dirName).mkdir();
        for (int i=0;i<iterations;i++) {
            minPoint = startMinPoint + i*gap;
            try {
                PrintWriter outfile = outfile = new PrintWriter(new File(Properties.MINPOINT_TEST_FILEPATH+dirName+"/"+"minPointTest"+"_"+(minPoint)+"_"+timestamp+".csv"));
                Database db = makeSimpleDatabase(file_path,totalPoints);
                ListParameterization params = new ListParameterization();
                params.addParameter(AbstractOPTICS.Parameterizer.MINPTS_ID, minPoint);
                System.out.println("Min point - "+minPoint);
                OPTICSOF<DoubleVector> opticsof = ClassGenericsUtil.parameterizeOrAbort(OPTICSOF.class, params);
                // run OPTICSOF on database
                OutlierResult result = opticsof.run(db);
                DoubleRelation scores = result.getScores();
                for (DBIDIter iter = scores.iterDBIDs(); iter.valid(); iter.advance()) {
                    //System.out.println(DBIDUtil.toString(iter) + "," + scores.doubleValue(iter));
                    outfile.append(Double.toString(scores.doubleValue(iter))+"\n");
                    //DBIDUtil.toString(iter) + " " +
                }
                outfile.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
