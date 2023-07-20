package com.romych.player.musicplayer.repository;

import com.romych.player.musicplayer.model.Playlist;
import org.w3c.dom.NodeList;

import javax.xml.transform.TransformerException;
import java.sql.SQLException;
import java.util.List;

public interface PlaylistRepository {
    boolean save(Playlist playlist) throws TransformerException;
    List<Playlist> findAll();
    Playlist findOne(String title) throws SQLException;
    void delete(Playlist playlist) throws SQLException;
    Playlist findOneById(int playlistId) throws SQLException;



}
