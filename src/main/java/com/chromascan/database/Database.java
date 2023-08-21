package com.chromascan.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    static final String DB_URL = "jdbc:mysql://localhost/chromascandb";
    static final String USER = "myuser";
    static final String PASS = "7fnqBpx224C!";

    public void setupDatabase(){
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
            
            String sql = "CREATE TABLE IF NOT EXISTS Image " +
                    "(filename VARCHAR(255) not NULL, " +
                    " first VARCHAR(255), " + 
                    " last VARCHAR(255), " + 
                    " age INTEGER, " + 
                    " PRIMARY KEY ( filename ))"; 

            stmt.executeUpdate(sql);
            System.out.println("Created table in given database...");   	  
        } catch(SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }
    
}
