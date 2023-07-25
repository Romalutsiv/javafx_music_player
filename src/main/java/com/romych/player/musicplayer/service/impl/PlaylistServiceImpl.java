package com.romych.player.musicplayer.service.impl;

import com.romych.player.musicplayer.dao.PlaylistDAO;
import com.romych.player.musicplayer.dao.impl.PlaylistDAOImpl;
import com.romych.player.musicplayer.model.Playlist;
import com.romych.player.musicplayer.model.Song;
import com.romych.player.musicplayer.service.PlaylistService;

import java.time.LocalDate;
import java.util.List;

public class PlaylistServiceImpl implements PlaylistService {

    private final PlaylistDAO playlistDAO = new PlaylistDAOImpl();


    public PlaylistServiceImpl()  {

    }
    @Override
    public Playlist createPlaylist(List<Song> songs, String title, Song currentSong) {
        if (songs.isEmpty() || songs == null) throw new RuntimeException("Errr.");
        Playlist playlist = new Playlist(title, currentSong, songs, LocalDate.now());
        if(!playlistDAO.create(playlist)) throw new RuntimeException("Not created");
        playlist.setId(playlistDAO.findByTitle(title).getId());
        return playlist;
    }

    @Override
    public void deletePlaylist(Playlist playlist) {
        if (!playlistDAO.delete(playlist.getId())) throw new RuntimeException("Not deleted!!!");
    }

    @Override
    public Playlist updatePlaylist(Playlist playlist) {
        return playlistDAO.update(playlist);
    }

    @Override
    public List<Playlist> getAll() {
        List<Playlist> playlists = playlistDAO.findAll();
//        if (playlists.size() == 0) throw new RuntimeException("Empty!");
        return playlists;
    }

    @Override
    public Playlist getOne(String playlistTitle) {
        if (playlistTitle.isEmpty() || playlistTitle.isBlank()) throw new RuntimeException("Errrrr");
        return playlistDAO.findByTitle(playlistTitle);
    }
}
