package org.taz.core.clustering;

import org.apache.commons.math3.stat.descriptive.rank.Percentile;
import org.taz.core.clustering.util.CSVGenerator;
import org.taz.core.clustering.util.Parameter;
import org.taz.core.clustering.util.ParserAPI;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by vithulan on 1/6/17.
 */
public class TestApp {
    public static void main(String[] args) {
        //String file_path = "/home/vithulan/JFRs/OpticsOF/data.csv";
       // String file_path = "/home/vithulan/JFRs/CSVs/heapused_metaspace_dataspace.csv";
       // String FILE_PATH = "/home/vithulan/JFRs/JFR_Collection/Testing/App1/anomaly_final.jfr";
        String FILE_PATH = "/home/vithulan/JFRs/JFR_Collection/Testing/App1/anomaly.jfr";

        /*OpticsOF opticsOF  = new OpticsOF();
        opticsOF.cluster();*/

        ClusteringHandler clusteringHandler = new ClusteringHandler(FILE_PATH);
        clusteringHandler.setPercentile(90.0);
        TreeMap<Integer, Parameter> anomalyMap = clusteringHandler.getAnomalyPoints();
        for(Map.Entry<Integer,Parameter> entry : anomalyMap.entrySet()){
            System.out.println(entry.getValue().getGcID()+"  "+entry.getValue().getAnomalyScore());
        }
        /*ParserAPI parserAPI = new ParserAPI(FILE_PATH);
        TreeMap<Long, Parameter> parameterTreeMap = parserAPI.generateAttributeTable();
        CSVGenerator csvGenerator = new CSVGenerator(parameterTreeMap);
        String file = csvGenerator.generateCSV();
        int totalPoints = csvGenerator.getTotalPoints();
        OpticsOF opticsOF = new OpticsOF(totalPoints,file);
        opticsOF.setParameterTreeMap(parameterTreeMap);
        ArrayList<Double> anomalyScores =  opticsOF.generateAnomalyScore();
        Percentile percentile = new Percentile();
        //anomalyScore.getPercentile(95);
        double[] anomalyScoreArray = new double[anomalyScores.size()];
        //anomalyScoreArray = anomalyScores.toArray(anomalyScoreArray);
        for(int i=0;i<anomalyScores.size();i++){
            anomalyScoreArray[i] = anomalyScores.get(i);
        }
        percentile.setData(anomalyScoreArray);
        double percentileVal = percentile.evaluate(75.00);
        System.out.println(percentileVal);

        parameterTreeMap = opticsOF.getParameterTreeMap();

        int i = 0;
        int count = 0;
        for (Map.Entry<Long,Parameter> entry: parameterTreeMap.entrySet()){
            if(count>0) {
                System.out.println(entry.getValue().getAnomalyScore() + " == " + anomalyScores.get(i));
                i++;
            }
            count++;
        }*/
        //opticsOF.runMinPointTest(400,11,10);

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
