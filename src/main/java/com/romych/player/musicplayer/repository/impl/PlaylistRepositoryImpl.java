package com.romych.player.musicplayer.repository.impl;

import com.romych.player.musicplayer.model.Playlist;
import com.romych.player.musicplayer.model.Song;
import com.romych.player.musicplayer.repository.PlaylistRepository;
import com.romych.player.musicplayer.repository.SongRepository;
import com.romych.player.musicplayer.storage.SQLiteStorage;

import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;

public class PlaylistRepositoryImpl implements PlaylistRepository {
    private final SQLiteStorage storage;
    private final Connection con;
    private final SongRepository songRepository;


    public PlaylistRepositoryImpl() throws SQLException, IOException, ClassNotFoundException {
        storage = new SQLiteStorage();
        con = storage.getCon();

        songRepository = new SongRepositoryImpl();
    }

    @Override
    public boolean save(Playlist playlist) throws TransformerException {



        return false;
    }

    @Override
    public List<Playlist> findAll() {
        String query = "SELECT * FROM Playlist";

        return null;
    }

    @Override
    public Playlist findOne(String title) throws SQLException {
        Playlist playlist = new Playlist();
        String query = "SELECT * FROM Playlist WHERE title = " + title;
        PreparedStatement stmt = con.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()){
            Song curentSong = new Song();
            int playlistId = rs.getInt("id");
            String playlistTitle = rs.getString("title");
            LocalDate playlistDate = LocalDate.parse(rs.getString("creating_date"));
            playlist.setId(playlistId);
            playlist.setCreatingDate(playlistDate);
            playlist.setTitle(playlistTitle);
            playlist.setCurrentSong(curentSong);
            List<Song> playlisSongs = songRepository.findSongsFromPlaylist(playlist);
            playlist.setSongs(playlisSongs);
        }
        return playlist;
    }

    @Override
    public void delete(Playlist playlist) throws SQLException {
        String query = "DELETE FROM Song WHERE id = " + playlist.getId();
        Statement stmt = con.createStatement();
        stmt.executeUpdate(query);

    }

    @Override
    public Playlist findOneById(int playlistId) throws SQLException {
        Playlist playlist = new Playlist();
        String query = "SELECT * FROM Playlist WHERE id = " + playlistId;
        PreparedStatement stmt = con.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()){
            Song curentSong = new Song();
            String playlistTitle = rs.getString("title");
            LocalDate playlistDate = LocalDate.parse(rs.getString("creating_date"));
            playlist.setId(playlistId);
            playlist.setCreatingDate(playlistDate);
            playlist.setTitle(playlistTitle);
            playlist.setCurrentSong(curentSong);
            List<Song> playlisSongs = songRepository.findSongsFromPlaylist(playlist);
            playlist.setSongs(playlisSongs);
        }
        return playlist;
    }
}
