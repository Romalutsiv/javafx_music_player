package com.romych.player.musicplayer.repository;

import com.romych.player.musicplayer.model.Playlist;
import com.romych.player.musicplayer.model.Song;

import java.sql.SQLException;
import java.util.List;

public interface SongRepository {
    boolean save(String path, int playlistId) throws SQLException;
    void delete(Song song) throws SQLException;
    List<Song> findSongsFromPlaylist(Playlist playlist) throws SQLException;
    Song findByPathAndPlaylistId(String path, int playlistId) throws SQLException;



}
