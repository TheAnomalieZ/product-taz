package org.taz.database;

import org.taz.database.internal.CassandraConnection;
import org.taz.database.mysql.JFRMysqlHandler;
import org.taz.database.mysql.MysqlConnector;
import org.taz.database.mysql.SQLQueryGenerator;

import java.util.ArrayList;

/**
 * Created by vithulan on 11/30/16.
 */
public class TestApp {
    public static void main(String[] args) {
        final String FILE_PATH = "/home/vithulan/JFRs/JFR_Collection/JFR/CEP/cep-2016_12_17_06_16_26.jfr";
        /*CassandraConnection cassandraConnection = new CassandraConnection();
        cassandraConnection.createKeySpace("Tempo");*/
       /* CassandraConnection cassandraConnection = new CassandraConnection();
        cassandraConnection.connect("localhost",9042);*/

       /* ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("Hi");
        arrayList.add("Bye");
        arrayList.add("dodod");

        ArrayList<String> values = new ArrayList<String>();
        values.add("HiValue");
        values.add("ByeValue");
        values.add("dododValue");
        SQLQueryGenerator sqlQueryGenerator = new SQLQueryGenerator("testDB");
        String query1 = sqlQueryGenerator.getCreateTableQuery("TestTable2",arrayList);
        String query2 = sqlQueryGenerator.getInsertionQuery("TestTable2",arrayList,values);
        System.out.println(query1);*/
        /*MysqlConnector mysqlConnector = new MysqlConnector("localhost","3306","JFR_Collection");
        mysqlConnector.createConnection();*/

        JFRMysqlHandler jfrMysqlHandler = new JFRMysqlHandler(FILE_PATH,"localhost","3306","JFR_Sample");
        jfrMysqlHandler.generateDatabase();

        /*MysqlConnector mysqlConnector = new MysqlConnector("localhost","3306","JFR_Sample");
        mysqlConnector.createConnection();
        mysqlConnector.executeQuery(query1);*/
    }
}
