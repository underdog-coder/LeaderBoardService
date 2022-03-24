package com.example.demo.Core;

import java.sql.*;

/*
This is a postgres helper class , this will provide methods for easily queryin the postgres DB where we are storing all the
game score details, This class helps in reducing the  repetitive code  and make code somewhat cleaner
* */


public class PostgresHelper {

    private  static String url = "jdbc:postgresql://localhost/";
    private  final static String user = "rohitkvrm";
    private final static String password = "root";
    private String query ;
    private String dbName;

    public PostgresHelper(String dbName) {
        this.dbName = dbName;
    }

    public Connection getConnection(){  // this will return a connection object to a particular DB which can be used to query the DB
        Connection dbConnection = null;
        try {

            dbConnection = DriverManager.getConnection(url + dbName, user, password);
            System.out.println("You are now connected to the server");

        }catch(SQLException e){
            System.err.println(e.getMessage());
        }
        return dbConnection;
    }

    public void write(String query){  // this is a generic  write or update query , which just needs the query and will
                                    // write the data to the db
        Connection dbConnection = null;
        PreparedStatement stmt = null;

        try{
            dbConnection = getConnection();
            stmt = dbConnection.prepareStatement(query);
            Integer updatedRows =  stmt.executeUpdate();
            System.out.println("Updated rows Count  :" + updatedRows); // It will give the number of rows updated

        }catch (Exception ex){
            System.out.println("Error while writing data to the Database " + ex.getMessage());
        }
        finally {
            try {
                dbConnection.close();
            } catch (SQLException e) {
                System.out.println("Error while closing connection to the Database " + e.getMessage());
            }
        }
    }

    public ResultSet read(String query){   // this is generic method to read from the postgres DB, this requires only the query and will
                            // return the result set , which can be further consumed by some process;
        Connection dbConnection = null;
        PreparedStatement stmt =  null;
        ResultSet resultSet = null;
        try{

            dbConnection = getConnection();
            stmt = dbConnection.prepareStatement(query);
            resultSet =  stmt.executeQuery();
            System.out.println("Returned row Count  :" + resultSet.getRow());
        }
        catch (Exception ex){
            System.out.println("Error while Reading data from the Database " + ex.getMessage());
        }
        finally {
            try {
                dbConnection.close();
            } catch (SQLException e) {
                System.out.println("Error while closing connection to the Database " + e.getMessage());
            }
        }
        return  resultSet;
    }


}
