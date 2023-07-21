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

public class PlaylistDAOImpl implements PlaylistDAO {
    private final String CREATE_PLAYLIST_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS \"Playlist\" (\n" +
            "\t\"title\"\tTEXT NOT NULL,\n" +
            "\t\"current_song\"\tTEXT NOT NULL,\n" +
            "\t\"creating_date\"\tTEXT,\n" +
            "\t\"id\"\tINTEGER PRIMARY KEY AUTOINCREMENT\n" +
            ");";
    private final String FIND_ALL_QUERY = "SELECT * FROM Playlist";
    private final String FIND_BY_ID_QUERY = "SELECT * FROM Playlist WHERE id =?";
    private final String FIND_BY_TITLE_QUERY = "SELECT * FROM Playlist WHERE title =?";
    private final String DELETE_PLAYLIST_QUERY = "DELETE FROM Playlist WHERE id =?";
    private final String SAVE_PLAYLIST_QUEERY = "INSERT INTO Playlist" +
            "  (title, current_song, creating_date) VALUES " +
            " (?, ?, ?);";

    private final String UPDATE_PLAYLIST_QUERY = "UPDATE warehouses SET name = ? , "
            + "capacity = ? "
            + "WHERE id = ?";

    private final SongDAO songDAO = new SongDAOImpl();

    public PlaylistDAOImpl() {
        try(Connection connection = ConnectorSQLite.getConnection();
            Statement statement = connection.createStatement()){
            statement.executeUpdate(CREATE_PLAYLIST_TABLE_QUERY);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    @Override
    public List<Playlist> findAll() {
        List<Playlist> playlists = new ArrayList<>();
        try(Connection connection = ConnectorSQLite.getConnection();
            Statement statement = connection.createStatement()){
            ResultSet rs = statement.executeQuery(FIND_ALL_QUERY);
            while (rs.next()){
                Playlist playlist = new Playlist();
                int id = rs.getInt("id");
                playlist.setId(id);
                String title = rs.getString("title");
                playlist.setTitle(title);
                String currentSongPath = rs.getString("current_song");
                Song currentSong = songDAO.findByPathAndPlaylistId(currentSongPath, playlist);
                playlist.setCurrentSong(currentSong);
                List<Song> playlistSongs = songDAO.findSongsFromPlaylist(playlist);
                playlist.setSongs(playlistSongs);
                LocalDate date = LocalDate.parse(rs.getString(""));
                playlist.setCreatingDate(date);
                playlists.add(playlist);
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return playlists;
    }

    @Override
    public Playlist findById(Integer id) {
        Playlist playlist = null;
        try(Connection connection = ConnectorSQLite.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_QUERY)){
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                playlist = new Playlist();
                int playlistId = rs.getInt("id");
                playlist.setId(playlistId);
                String title = rs.getString("title");
                String currentSongPath = rs.getString("current_song");
                Song currentSong = songDAO.findByPathAndPlaylistId(currentSongPath, playlist);
                List<Song> playlistSongs = songDAO.findSongsFromPlaylist(playlist);
                LocalDate date = LocalDate.parse(rs.getString(""));
                playlist.setTitle(title);
                playlist.setCurrentSong(currentSong);
                playlist.setCreatingDate(date);
                playlist.setSongs(playlistSongs);
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return playlist;
    }

    @Override
    public Playlist findByTitle(String title) {
        Playlist playlist = null;
        try(Connection connection = ConnectorSQLite.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_BY_TITLE_QUERY)){
            statement.setString(1, title);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                playlist = new Playlist();
                int playlistId = rs.getInt("id");
                playlist.setId(playlistId);
                String playlistTitle = rs.getString("title");
                String currentSongPath = rs.getString("current_song");
                Song currentSong = songDAO.findByPathAndPlaylistId(currentSongPath, playlist);
                List<Song> playlistSongs = songDAO.findSongsFromPlaylist(playlist);
                LocalDate date = LocalDate.parse(rs.getString(""));
                playlist.setTitle(title);
                playlist.setCurrentSong(currentSong);
                playlist.setCreatingDate(date);
                playlist.setSongs(playlistSongs);
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return playlist;
    }

    @Override
    public boolean delete(Integer id) {
        songDAO.delete(id);
        try(Connection connection = ConnectorSQLite.getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE_PLAYLIST_QUERY)){

            statement.setInt(1,id);
            statement.executeUpdate();
            return true;
        } catch (SQLException e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean create(Playlist entity) {
        Playlist playlistFromDb = findByTitle(entity.getTitle());
        if (playlistFromDb != null){
            return false;
        }
        try(Connection connection = ConnectorSQLite.getConnection();
            PreparedStatement statement = connection.prepareStatement(SAVE_PLAYLIST_QUEERY)){
            statement.setString(1, entity.getTitle());
            statement.setString(2, entity.getCurrentSong().getPath());
            statement.setString(3, entity.getCreatingDate().toString());
            statement.executeUpdate();
            Playlist playlist = findByTitle(entity.getTitle());
            for (Song song :
                    entity.getSongs()) {
                song.setPlaylist(playlist);
                songDAO.create(song);
            }
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public Playlist update(Playlist entity) {
        Playlist playlistFromBD = findById(entity.getId());
        if (playlistFromBD == null) {
            throw new RuntimeException("NOT FOUND");
        }
        try(Connection connection = ConnectorSQLite.getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE_PLAYLIST_QUERY)) {
            statement.setString(1, entity.getTitle());
            statement.setString(2, entity.getCurrentSong().getPath());
            songDAO.delete(entity.getId());
            statement.executeUpdate();
            for (Song song :
                    entity.getSongs()) {
                song.setPlaylist(entity);
                songDAO.create(song);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return entity;
    }
}
