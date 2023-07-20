package com.romych.player.musicplayer.repository.impl;

import com.romych.player.musicplayer.model.Playlist;
import com.romych.player.musicplayer.model.Song;
import com.romych.player.musicplayer.repository.PlaylistRepository;
import com.romych.player.musicplayer.repository.SongRepository;
import com.romych.player.musicplayer.storage.SQLiteStorage;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SongRepositoryImpl implements SongRepository {

    private final SQLiteStorage storage;
    private final Connection con;

    private final PlaylistRepository playlistRepository;

    public SongRepositoryImpl() throws SQLException, IOException, ClassNotFoundException {
        storage = new SQLiteStorage();
        con = storage.getCon();
        playlistRepository = new PlaylistRepositoryImpl();
    }

    @Override
    public boolean save(String path, int playlistId) throws SQLException {
        String query = "INSERT INTO Song" +
                "  (title, playlist_id) VALUES " +
                " (?, ?);";
        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setString(1, path);
        stmt.setInt(2, playlistId);
        return true;
    }

    @Override
    public void delete(Song song) throws SQLException {
        String query = "DELETE FROM Song WHERE id = " + song.getId();
        Statement stmt = con.createStatement();
        stmt.executeUpdate(query);
    }

    @Override
    public List<Song> findSongsFromPlaylist(Playlist playlist) throws SQLException {
        List<Song> result = new ArrayList<>();
        String query = "SELECT * FROM Song WHERE playlist_id =?";
        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setInt(1, playlist.getId());
        ResultSet rs = stmt.executeQuery();
        while (rs.next()){
            int songId = rs.getInt("id");
            String songPath = rs.getString("title");
            Song song = new Song();
            song.setId(songId);
            song.setPath(songPath);
            song.setPlaylist(playlist);
            result.add(song);
        }
        return result;
    }
    @Override
    public Song findByPathAndPlaylistId(String path, int playlistId) throws SQLException {
        String query = "SELECT * FROM Song WHERE path = " + path + "AND playlist_id = " + playlistId;
        Song resultSong = new Song();
        PreparedStatement stmt = con.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();
        while(rs.next()){
            int songId = rs.getInt("id");
            String songPath = rs.getString("path");
            resultSong.setId(songId);
            resultSong.setPath(songPath);
            resultSong.setPlaylist(playlistRepository.findOneById(playlistId));
        }
        return resultSong;
    }
}
