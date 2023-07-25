package com.romych.player.musicplayer;

import com.romych.player.musicplayer.dao.PlaylistDAO;
import com.romych.player.musicplayer.dao.impl.PlaylistDAOImpl;
import com.romych.player.musicplayer.model.Playlist;
import com.romych.player.musicplayer.model.Song;
import com.romych.player.musicplayer.service.PlaylistService;
import com.romych.player.musicplayer.service.impl.PlaylistServiceImpl;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class Test {
    public static void main(String[] args) throws ParserConfigurationException, IOException, TransformerException, SQLException, ClassNotFoundException {
        Playlist playlist = new Playlist();
        Song song1 = new Song("first song");
        Song song2 = new Song("second song");
        Song song3 = new Song("third song");
        Song song4 = new Song("third song");
        playlist.setTitle("Second playlist");
        playlist.setCurrentSong(song1);
        List<Song> songs = List.of(song1, song2, song3, song4);
        playlist.setSongs(songs);
        PlaylistService playlistService = new PlaylistServiceImpl();
//        System.out.println(playlistService.getOne("New Playlist"));
        playlistService.createPlaylist(List.of(song1, song2, song3, song4), playlist.getTitle(), song3);
        System.out.println("Size is: " + playlistService.getAll().size());
//        System.out.println(playlistService.getAll().size());


    }
}
