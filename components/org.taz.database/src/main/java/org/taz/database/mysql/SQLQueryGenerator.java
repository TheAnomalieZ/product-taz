package org.taz.database.mysql;

import java.util.ArrayList;

/**
 * Created by vithulan on 1/18/17.
 */
public class SQLQueryGenerator {
    private String database;
    private final String varchar = "varchar(255)";
    private final String space = " ";
    public SQLQueryGenerator (String database){
        this.database = database;
    }

    public String getCreateTableQuery(String tableName, ArrayList<String> columns){
        String query = "CREATE TABLE "+trimmer(tableName)+" (" ;
        String subQuery = "";
        int count = 1;
        for(String column : columns){
            subQuery = subQuery+space+""+trimmer(column)+""+space+varchar;
            if(count<columns.size()){
                subQuery = subQuery+",";
            }
            count++;
        }
        query = query + subQuery + ");";
        return query;
    }

    public String getInsertionQuery(String tableName, ArrayList<String> columns, ArrayList<String> values){
        String query = "INSERT INTO "+trimmer(tableName)+" (";
        String subQuery = "";
        int count = 1;
        for (String column : columns){
            subQuery = subQuery+space+trimmer(column)+space;
            if(count<columns.size()){
                subQuery=subQuery+",";
            }
            count++;
        }
        query = query + subQuery + ")";
        String valuesQuery = "VALUES (";
        int val = 1;
        for (String value : values){
            valuesQuery = valuesQuery+space+"'"+value+"'"+space;
            if(val<columns.size()){
                valuesQuery=valuesQuery+",";
            }
            val++;
        }
        query = query + valuesQuery + ");";
        return query;
    }

    private String trimmer(String query){
        query = query.replaceAll("\\s","");
        query = query.replace("(","_");
        query = query.replace(")","_");
        query = query.replaceAll("\\.", "");
        query = query.replaceAll("when", "_when");
        query = query.replaceAll("value", "_value");
        query = query.replaceAll("key", "_key");
        query = query.replaceAll(":", "_");
        return query;
    }
}
