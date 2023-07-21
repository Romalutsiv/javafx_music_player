package com.romych.player.musicplayer.dao.impl;

import com.romych.player.musicplayer.dao.PlaylistDAO;
import com.romych.player.musicplayer.dao.SongDAO;
import com.romych.player.musicplayer.model.Playlist;
import com.romych.player.musicplayer.model.Song;
import com.romych.player.musicplayer.storage.ConnectorSQLite;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SongDAOImpl implements SongDAO {

    private final String CREATE_SONG_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS \"Songs\" (\n" +
            "\t\"path\"\tTEXT NOT NULL,\n" +
            "\t\"id\"\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "\t\"playlist_id\"\tINTEGER NOT NULL, \n" +
            "\tFOREIGN KEY (\"playlist_id\") REFERENCES Playlist (\"id\")" +
            ");";
    private final String FIND_FROM_PLAYLIST_QUERY = "SELECT * FROM Songs WHERE playlist_id =?";
    private final String FIND_BY_PATH_AND_PLAYLIST_QUERY = "SELECT * FROM Songs WHERE path =? AND playlist_id =?";
    private final String DELETE_PLAYLIST_SONGS_QUERY = "DELETE FROM Songs WHERE playlist_id =?";
    private final String DELETE_SONG_BY_PATH_QUERY = "DELETE FROM Songs WHERE path =?";
    private final String SAVE_SONG_QUERY = "INSERT INTO Songs" +
            "  (path, playlist_id) VALUES " +
            " (?, ?);";
    
//    private final PlaylistDAO playlistDAO = new PlaylistDAOImpl();

    public SongDAOImpl() {
        try(Connection connection = ConnectorSQLite.getConnection();
            Statement statement = connection.createStatement()){
            statement.executeUpdate(CREATE_SONG_TABLE_QUERY);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
//        playlistDAO = new PlaylistDAOImpl();
    }

    @Override
    public List<Song> findAll() {
        return null;
    }

    @Override
    public Song findById(Integer id) {
        return null;
    }


    @Override
    public boolean delete(Integer id) {
        try(Connection connection = ConnectorSQLite.getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE_PLAYLIST_SONGS_QUERY)){
            statement.setInt(1,id);
            statement.executeUpdate();
            return true;
        } catch (SQLException e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean create(Song entity) {
        Song songFromDB = findByPathAndPlaylistId(entity.getPath(), entity.getPlaylist());
        if (songFromDB != null){
            return false;
        }
        try(Connection connection = ConnectorSQLite.getConnection();
            PreparedStatement statement = connection.prepareStatement(SAVE_SONG_QUERY)){
            statement.setString(1, entity.getPath());
            statement.setInt(2, entity.getPlaylist().getId());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public Song update(Song entity) {
        return null;
    }

    @Override
    public Song findByPathAndPlaylistId(String path, Playlist playlist) {
        Song song = null;
        try(Connection connection = ConnectorSQLite.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_BY_PATH_AND_PLAYLIST_QUERY)){
            statement.setString(1, path);
            statement.setInt(2, playlist.getId());
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                int songId = rs.getInt("id");
                String songPath = rs.getString("path");
//                Playlist playlist = playlistDAO.findById(playlistId);
                song = new Song(songId, songPath, playlist);
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return song;
    }

    @Override
    public List<Song> findSongsFromPlaylist(Playlist playlist) {
        List<Song> songs = new ArrayList<>();
        try(Connection connection = ConnectorSQLite.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_FROM_PLAYLIST_QUERY)){
            statement.setInt(1,playlist.getId());
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                int songId = rs.getInt("id");
                String songPath = rs.getString("path");
                songs.add(new Song(songId, songPath, playlist));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return songs;
    }

    @Override
    public boolean deleteByPath(String path) {
        try(Connection connection = ConnectorSQLite.getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE_SONG_BY_PATH_QUERY)){
            statement.setString(1, path);
            statement.executeQuery();;
            return true;
        } catch (SQLException e){
            System.out.println(e.getMessage());
            return false;
        }
    }
}
