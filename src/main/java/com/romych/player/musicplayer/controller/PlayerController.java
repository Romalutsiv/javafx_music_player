package com.romych.player.musicplayer.controller;

import com.romych.player.musicplayer.factory.PlaylistCellFactory;
import com.romych.player.musicplayer.model.Playlist;
import com.romych.player.musicplayer.model.Song;
import com.romych.player.musicplayer.service.PlaylistService;
import com.romych.player.musicplayer.service.impl.PlaylistServiceImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PlayerController implements Initializable {
    @FXML
    private Label playlistLabel;
    @FXML
    private Label backButton;
    @FXML
    private Label playlistTitle;
    @FXML
    private AnchorPane playlistsPane;
    @FXML
    private ImageView playlistsBtn;
    @FXML
    private ListView<Playlist> listOfPlaylists;
    @FXML
    private ListView<Song> listOfSongs;
    @FXML
    private MenuItem openItem;

    private FileChooser fileChooser;
    private Media media;
    private MediaPlayer mediaPlayer;
    private PlaylistService playlistService;
    private List<Playlist> playlists;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        playlistService = new PlaylistServiceImpl();
        playlists = playlistService.getAll();
        ObservableList<Playlist> playlistObservableList = FXCollections.observableList(playlists);
        listOfPlaylists.getItems().addAll(playlists);
        listOfPlaylists.setCellFactory(new PlaylistCellFactory());
        listOfPlaylists.getSelectionModel().selectedItemProperty().addListener((observableValue, playlist, t1) -> {
            System.out.println(observableValue.getValue().getTitle());
            listOfPlaylists.setVisible(false);
            backButton.setVisible(true);
            playlistTitle.setVisible(true);
            playlistTitle.setText(observableValue.getValue().getTitle());
            listOfSongs.setVisible(true);
            listOfSongs.getItems().clear();
            listOfSongs.getItems().addAll(observableValue.getValue().getSongs());
        });
        backButton.setOnMouseClicked(mouseEvent -> {
            backButton.setVisible(false);
            playlistTitle.setVisible(false);
            listOfSongs.setVisible(false);
            listOfPlaylists.setVisible(true);
        });

        playlistsBtn.setOnMouseClicked(mouseEvent -> {
            if (playlistLabel.isVisible() && playlistsPane.isVisible()){
                playlistLabel.setVisible(false);
                playlistsPane.setVisible(false);
            } else {
                playlistLabel.setVisible(true);
                playlistsPane.setVisible(true);
            }
        });
        playlistsBtn.setOnMouseEntered(mouseEvent -> {
                playlistsBtn.getScene().setCursor(Cursor.HAND);
        });
        playlistsBtn.setOnMouseExited(mouseEvent -> {
            playlistsBtn.getScene().setCursor(Cursor.DEFAULT);
        });

//        list.setOnMouseClicked(mouseEvent -> {
//            System.out.println(list.getSelectionModel().getSelectedItem().getTitle());
////            list.getSelectionModel().getSelectedItem();
//        });

        openItem.setOnAction(actionEvent -> {
            fileChooser = new FileChooser();
            fileChooser.getExtensionFilters()
                    .addAll(new FileChooser.ExtensionFilter("Music Files", "*.mp3"),
                            new FileChooser.ExtensionFilter("Wav files", "*.wav"));
            List<File> selectedFiles = fileChooser.showOpenMultipleDialog(new Stage());
            if (selectedFiles.size() == 1) {
                openOneFile();
            } else if (selectedFiles.size() == 0){
                System.out.println("NO CHOOSE!");
            } else {
                opeMultiFiles(selectedFiles);
            }

        });
    }

    private void opeMultiFiles(List<File> selectedFiles) {
        Playlist playlist = new Playlist();
        playlist.setTitle("Casual playlist");
        List<Song> songs = new ArrayList<>();
        for (File sectedFile :
                selectedFiles) {
            songs.add(new Song(sectedFile.toURI().toString(), playlist));
        }
        playlist.setSongs(songs);
//        media = getMediaFromFileList(selectedFiles, 0);
//        mediaPlayer = new MediaPlayer(media);
//        playlists.add(0, playlist);
//        list.getItems().removeAll(playlists);
        listOfPlaylists.getItems().add(0, playlist);
        listOfPlaylists.getItems().forEach(System.out::println);
        System.out.println("More than one file!");


    }

    private void openOneFile() {
        System.out.println("One file choose!");
    }

    private Media getMediaFromFileList(List<File> files, int index){
        return new Media(files.get(index).toURI().toString());
    }


}
