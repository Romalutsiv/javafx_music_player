package com.romych.player.musicplayer.service;

import com.romych.player.musicplayer.model.Playlist;
import com.romych.player.musicplayer.model.Song;

import java.util.List;

public interface PlaylistService {

    Playlist createPlaylist(List<Song> songs, String title , Song currentSong);
    void deletePlaylist(Playlist playlist);
    Playlist updatePlaylist(Playlist playlist);
    List<Playlist> getAll();
    Playlist getOne(String playlistTitle);
}
