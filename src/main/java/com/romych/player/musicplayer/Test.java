package com.romych.player.musicplayer;

import com.romych.player.musicplayer.storage.SQLiteStorage;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.sql.SQLException;

public class Test {
    public static void main(String[] args) throws ParserConfigurationException, IOException, TransformerException, SQLException, ClassNotFoundException {
        SQLiteStorage storage = new SQLiteStorage();
    }
}
