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
import org.taz.core.clustering.util.Parameter;
import org.taz.core.clustering.util.Properties;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TreeMap;

/**
 * Created by vithulan on 11/25/16.
 */
public class OpticsOF extends DatabaseHandler {
    private int minPoint;
    private int totalPoints;
    private String file_path;
    private String file_name;
    private TreeMap<Long, Parameter> parameterTreeMap = null;

    public OpticsOF(int totalPoints, String file_name, int minPoint){
        this.minPoint = minPoint;
        this.totalPoints = totalPoints;
        this.file_path = Properties.ATTRIBUTE_TABLE_FILEPATH+file_name;
        this.file_name = file_name;

    }
    public OpticsOF(int totalPoints, String file_name){
        this.file_path = Properties.ATTRIBUTE_TABLE_FILEPATH+file_name;
        this.totalPoints = totalPoints;
        this.minPoint = (totalPoints/100)*55; //Set min point to 55% of total points
        this.file_name = file_name;
        System.out.println("total - "+totalPoints);
        System.out.println("filepath - "+file_path);
        System.out.println("minpoints - "+minPoint);
    }

    /**
     * Generate anomaly scorelist for dataset
     * @return list array of scores
     */
    public ArrayList<Double> generateAnomalyScore(){
        ArrayList<Double> anomalyScore = new ArrayList<>();
        String dirName = file_name.replaceAll(".csv","");
        new File(Properties.ANOMALY_SCORES_FILEPATH+dirName).mkdir();
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            try {
                PrintWriter outfile = new PrintWriter(new File(Properties.ANOMALY_SCORES_FILEPATH+dirName+"/"+"anomaly_score_"+timestamp+".csv"));
                Database db = makeSimpleDatabase(file_path,totalPoints);
                ListParameterization params = new ListParameterization();
                params.addParameter(AbstractOPTICS.Parameterizer.MINPTS_ID, minPoint);
                OPTICSOF<DoubleVector> opticsof = ClassGenericsUtil.parameterizeOrAbort(OPTICSOF.class, params);
                // run OPTICSOF on database
                OutlierResult result = opticsof.run(db);
                DoubleRelation scores = result.getScores();
                for (DBIDIter iter = scores.iterDBIDs(); iter.valid(); iter.advance()) {
                    anomalyScore.add(scores.doubleValue(iter));
                    outfile.append(Double.toString(scores.doubleValue(iter))+"\n");
                   /* long key = parameterTreeMap.firstKey()+Long.parseLong(DBIDUtil.toString(iter));
                    Parameter parameter = parameterTreeMap.get(key);
                    parameter.setAnomalyScore(scores.doubleValue(iter));
                    parameterTreeMap.put(key,parameter);*/
                }
                outfile.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        return anomalyScore;
    }

    /**
     * This method is for research purpose. Generate multiple score values for multiple min point values.
     * @param startMinPoint start minpoint value
     * @param iterations number of iterations to run
     * @param gap gap between increment of minpoint
     */
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

    public void setParameterTreeMap(TreeMap<Long, Parameter> parameterTreeMap) {
        this.parameterTreeMap = parameterTreeMap;
    }

    public TreeMap<Long, Parameter> getParameterTreeMap() {
        return parameterTreeMap;
    }

    public ArrayList<Double> generateAnomalyScoreForTest(){
        ArrayList<Double> anomalyScore = new ArrayList<>();
        String dirName = file_name.replaceAll(".csv","");
        //new File(Properties.ANOMALY_SCORES_FILEPATH+dirName).mkdir();
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        try {
            PrintWriter outfile = new PrintWriter(new File(Properties.ANOMALY_SCORES_FILEPATH+dirName+".csv"));
            Database db = makeSimpleDatabase(file_path,totalPoints);
            ListParameterization params = new ListParameterization();
            params.addParameter(AbstractOPTICS.Parameterizer.MINPTS_ID, minPoint);
            OPTICSOF<DoubleVector> opticsof = ClassGenericsUtil.parameterizeOrAbort(OPTICSOF.class, params);
            // run OPTICSOF on database
            OutlierResult result = opticsof.run(db);
            DoubleRelation scores = result.getScores();
            for (DBIDIter iter = scores.iterDBIDs(); iter.valid(); iter.advance()) {
                anomalyScore.add(scores.doubleValue(iter));
                outfile.append(Double.toString(scores.doubleValue(iter))+"\n");
                   /* long key = parameterTreeMap.firstKey()+Long.parseLong(DBIDUtil.toString(iter));
                    Parameter parameter = parameterTreeMap.get(key);
                    parameter.setAnomalyScore(scores.doubleValue(iter));
                    parameterTreeMap.put(key,parameter);*/
            }
            outfile.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return anomalyScore;
    }
}
