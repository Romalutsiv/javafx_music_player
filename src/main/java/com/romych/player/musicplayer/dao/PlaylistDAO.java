package com.romych.player.musicplayer.dao;

import com.romych.player.musicplayer.model.Playlist;

public interface PlaylistDAO extends AbstractDAO<Integer, Playlist> {
    Playlist findByTitle(String title);
}
