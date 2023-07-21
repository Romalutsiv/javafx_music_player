package com.romych.player.musicplayer.storage;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ConnectorSQLite {
    private static final ResourceBundle RESOURCE = ResourceBundle.getBundle("database");
    private static final String URL = RESOURCE.getString("db.url");
    private static final String DB_NAME = RESOURCE.getString("db.name");
    private static final String DRIVER = RESOURCE.getString("db.driver");
    public static Connection getConnection() throws SQLException {
        createDB();
        return DriverManager.getConnection(URL + DB_NAME);
    }

    public static void createDB(){
        File DB = new File(DB_NAME);
        if (!DB.exists()){
            try {
                DB.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


}
