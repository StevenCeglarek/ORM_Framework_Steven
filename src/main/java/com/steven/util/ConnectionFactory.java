package com.steven.util;

import java.io.Closeable;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConnectionFactory implements Closeable {

    public static final int CONNECTIONS = 1;
    private final Connection[] connectionPool = new Connection[CONNECTIONS];

    private static ConnectionFactory instance;

    final int MAX_THREADS = 4;
    ExecutorService threadActivator = Executors.newFixedThreadPool(MAX_THREADS);

    public ExecutorService getThreadActivator() {
        return threadActivator;
    }

    private ConnectionFactory(){
        for (int i = 0; i < CONNECTIONS; i++) {
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
            props.load(new FileReader(new File("src/main/resources/db.properties")));
            return DriverManager.getConnection(
                    props.getProperty("jdbc.connection.profile.dev.url"),
                    props.getProperty("jdbc.connection.profile.dev.username"),
                    props.getProperty("jdbc.connection.profile.dev.password"));
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
