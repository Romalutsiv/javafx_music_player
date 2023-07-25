package com.romych.player.musicplayer.model;

import java.time.LocalDate;
import java.util.List;

public class Playlist {
    private int id;
    private String title;
    private Song currentSong;
    private List<Song> songs;
    private LocalDate creatingDate;
    private boolean active;

    public Playlist() {
        this.creatingDate = LocalDate.now();
        this.active = false;
    }

    public Playlist(String title, Song currentSong, List<Song> songs, LocalDate creatingDate) {
        this.title = title;
        this.currentSong = currentSong;
        this.songs = songs;
        this.creatingDate = creatingDate;
        this.active = false;
    }

    public Playlist(int id, String title, Song currentSong, List<Song> songs, LocalDate creatingDate, boolean active) {
        this.id = id;
        this.title = title;
        this.currentSong = currentSong;
        this.songs = songs;
        this.creatingDate = creatingDate;
        this.active = active;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Song getCurrentSong() {
        return currentSong;
    }

    public void setCurrentSong(Song currentSong) {
        this.currentSong = currentSong;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    public LocalDate getCreatingDate() {
        return creatingDate;
    }

    public void setCreatingDate(LocalDate creatingDate) {
        this.creatingDate = creatingDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(int activeNumber) {
        switch (activeNumber){
            case 0: this.active = false;
                    break;
            case 1: this.active = true;
                    break;
        }
    }
    public int getActive(){
        return this.active ? 1: 0;
    }
}
