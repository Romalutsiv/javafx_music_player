package com.romych.player.musicplayer.controller;

import javafx.application.Platform;
import javafx.collections.MapChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tooltip;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.media.Media;

import javafx.scene.media.MediaPlayer;


import java.io.File;
import java.util.*;

public class HelloController {

    @FXML
    private Button playBtn;

    @FXML
    private Button pauseBtn;
    @FXML
    private Label songEnd;
    @FXML
    private Label songTitle;
    @FXML
    private Label artistName;
    @FXML
    private Label songCurrent;
    @FXML
    private ProgressBar songProgressBar;


    private FileChooser fileChooser;

    private Media media;

    private MediaPlayer mediaPlayer;

    private MediaView mediaView;

    private Timer timer;

    private TimerTask task;
    private boolean running;

    private Tooltip progressBarTooltip;





    @FXML
    protected void handleDragOver(DragEvent e){
        if (e.getDragboard().hasFiles()) {
            e.acceptTransferModes(TransferMode.ANY);
        }

    }
    @FXML
    protected void dropFile(DragEvent e){
        List<File> files = e.getDragboard().getFiles();
        openFile(files.get(0));
        play();

    }
    @FXML
    protected void play() {
        pauseBtn.setVisible(true);
        playBtn.setVisible(false);
        mediaPlayer.play();
        beginTimer();
    }

    @FXML
    protected void pause() {
        pauseBtn.setVisible(false);
        playBtn.setVisible(true);
        mediaPlayer.pause();
    }
    @FXML
    protected void openFileFromMenu(){
        if (running) {
            cancelTimer();
            artistName.setText("");
            songTitle.setText("");
        }
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters()
                .addAll(new FileChooser.ExtensionFilter("Music Files", "*.mp3"));
        File selected = fileChooser.showOpenDialog(new Stage());
        openFile(selected);
        play();
    }

    public void openFile(File file){
        songTitle.setText(file.getName());
        media = new Media(file.toURI().toString());
        media.getMetadata().addListener((MapChangeListener.Change<? extends String, ? extends Object> c) -> {
            if (c.wasAdded()) {
                if ("duration".equals(c.getKey())) {
                    String s = c.getValueAdded().toString().replaceAll("[^\\d.]", "");
                    double length = Double.parseDouble(s);
                    songEnd.setText(getTimeString(length));

                } else if ("title".equals(c.getKey())) {
                    songTitle.setText(c.getValueAdded().toString());
                }else if ("artist".equals(c.getKey())) {
                    artistName.setText(c.getValueAdded().toString());
                }
            }
        });
        mediaPlayer = new MediaPlayer(media);
        mediaView = new MediaView(mediaPlayer);
    }

    public void beginTimer(){
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                running = true;
                double current = mediaPlayer.getCurrentTime().toMillis();
                double end = media.getDuration().toMillis();
                songProgressBar.setProgress(current/end);
                Platform.runLater(() -> {
                    songCurrent.setText(getTimeString(current));
                });
                if (current/end == 1){
                    cancelTimer();
                }
            }
        };
        timer.scheduleAtFixedRate(task, 0 , 1000);
    }
    public void cancelTimer() {
        running = false;
        timer.cancel();
    }

    public  String getTimeString(double millis) {
        millis /= 1000;
        String s = formatTime(millis % 60);
        millis /= 60;
        String m = formatTime(millis % 60);
        millis /= 60;
        String h = formatTime(millis % 24);
        return h + ":" + m + ":" + s;
    }

    public String formatTime(double time) {
        int t = (int)time;
        if (t > 9) { return String.valueOf(t); }
        return "0" + t;
    }
}