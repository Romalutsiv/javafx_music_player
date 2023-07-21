package com.romych.player.musicplayer.dao;

import com.romych.player.musicplayer.model.Playlist;
import com.romych.player.musicplayer.model.Song;

import java.util.List;

public interface SongDAO extends AbstractDAO<Integer, Song> {
    Song findByPathAndPlaylistId(String path, Playlist playlist);
    List<Song> findSongsFromPlaylist(Playlist playlist);
    boolean deleteByPath(String path);
}
