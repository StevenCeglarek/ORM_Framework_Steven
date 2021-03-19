package com.steven.util;

import java.io.Closeable;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory implements Closeable {

    public static final int CONNECTIONS = 1;
    private final Connection[] connectionPool = new Connection[CONNECTIONS];

    private static ConnectionFactory instance;

    private ConnectionFactory(){
        for (int i = 0; i < CONNECTIONS; i++) {
//            System.out.println("adding connection number "+i+" to the connection pool");
            connectionPool[i] = createConnection();
        }
    }

    public static ConnectionFactory getInstance() {
        if (instance == null) {
            instance = new ConnectionFactory();
        }
        return instance;
    }

    private Connection createConnection() {
        Properties props = new Properties();
        try {
            props.load(new FileReader(new File("cegframework.cfg.xml")));
//            System.out.println("Creating a new Connection");
            return DriverManager.getConnection(
                    props.getProperty("cegframework.connection.url"),
                    props.getProperty("cegframework.connection.username"),
                    props.getProperty("cegframework.connection.password"));
        } catch (IOException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Connection[] getConnectionPool() {
        return connectionPool;
    }

    public void close(){
        for(Connection con: connectionPool){
            try {
                con.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
