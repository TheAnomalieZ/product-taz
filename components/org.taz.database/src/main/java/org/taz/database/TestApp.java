package org.taz.database;

import org.taz.database.internal.CassandraConnection;

/**
 * Created by vithulan on 11/30/16.
 */
public class TestApp {
    public static void main(String[] args) {
        /*CassandraConnection cassandraConnection = new CassandraConnection();
        cassandraConnection.createKeySpace("Tempo");*/
        CassandraConnection cassandraConnection = new CassandraConnection();
        cassandraConnection.connect("localhost",9042);
    }
}
