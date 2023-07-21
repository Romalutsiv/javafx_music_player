package com.romych.player.musicplayer.model;

public class Song {
    private int id;
    private String path;
    private Playlist playlist;

    public Song() {
    }

    public Song(String path) {
        this.path = path;
    }

    public Song(String path, Playlist playlist) {
        this.path = path;
        this.playlist = playlist;
    }

    public Song(int id, String path, Playlist playlist) {
        this.id = id;
        this.path = path;
        this.playlist = playlist;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Playlist getPlaylist() {
        return playlist;
    }

    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
    }
}
