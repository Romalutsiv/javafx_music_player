package com.romych.player.musicplayer.factory;

import com.romych.player.musicplayer.model.Playlist;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class PlaylistCellFactory implements Callback<ListView<Playlist>, ListCell<Playlist>> {
    @Override
    public ListCell<Playlist> call(ListView<Playlist> playlistListView) {
        return new ListCell<>(){
            @Override
            protected void updateItem(Playlist playlist, boolean empty) {
                super.updateItem(playlist, empty);
                if (empty || playlist == null){
                } else {
                    setText(playlist.getTitle());
                }
            }
        };
    }
}
