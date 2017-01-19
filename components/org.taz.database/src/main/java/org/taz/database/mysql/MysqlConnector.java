package org.taz.database.mysql;



import java.sql.*;

/**
 * Created by vithulan on 1/17/17.
 */
public class MysqlConnector {
    private String url;
    private String databaseName;
    private String user = "root";
    private String password = "root";
    private Connection connection = null;
    public MysqlConnector(String host, String port, String databaseName){
        url="jdbc:mysql://"+host+":"+port+"/"+databaseName;
        this.databaseName = databaseName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void createConnection(){
        System.out.println("Connecting database...");
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("Database connected!");
    }

    public void executeQuery(String query){
        Statement stmt= null;
        //ResultSet rs = null;
        try {
            stmt = connection.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();

        }

    }

    public boolean closeConnection(){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
