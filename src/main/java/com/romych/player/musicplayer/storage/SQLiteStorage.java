package com.romych.player.musicplayer.storage;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.Map;

public class SQLiteStorage {
    private String FILE_NAME = "player_db.db";
    private final String JDBC_URL = "jdbc:sqlite:" + FILE_NAME;
    final String JDBC_DRIVER = "org.sqlite.JDBC";
    private final File DB;
    private Connection con;

    public SQLiteStorage() throws SQLException, ClassNotFoundException, IOException {
        DB = new File(FILE_NAME);
        if (!DB.exists()){
            DB.createNewFile();
        }
        try{
            Class.forName(JDBC_DRIVER);
            con = DriverManager.getConnection(JDBC_URL);
            Statement statement = con.createStatement();
            createPlaylistTable(statement);
            createSongTable(statement);
            statement.close();
            con.close();
        } catch (Exception e){
            System.out.printf(e.getMessage());
        }
    }

    private void createPlaylistTable(Statement stmt) throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS \"Playlist\" (\n" +
                "\t\"title\"\tTEXT NOT NULL,\n" +
                "\t\"creating_date\"\tTEXT,\n" +
                "\t\"id\"\tINTEGER NOT NULL,\n" +
                "\tPRIMARY KEY(\"id\")\n" +
                ");";
        stmt.executeUpdate(query);
    }

    private void createSongTable(Statement stmt) throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS \"Song\" (\n" +
                "\t\"path\"\tTEXT NOT NULL,\n" +
                "\t\"id\"\tINTEGER NOT NULL,\n" +
                "\t\"playlist_id\"\tINTEGER NOT NULL, \n" +
                "\tPRIMARY KEY(\"id\")\n" +
                "\tFOREIGN KEY (\"playlist_id\") REFERENCES Playlist (\"id\")" +
                ");";
        stmt.executeUpdate(query);
    }

    public Connection getCon() {
        return con;
    }
}
