package com.romych.player.musicplayer;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Application extends javafx.application.Application {
    double x, y = 0;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("player_main_view.fxml"));
        Parent root = fxmlLoader.load();
        root.setOnMousePressed(event -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {
           stage.setX(event.getScreenX() - x);
           stage.setY(event.getScreenY() - y);
        });

        Scene scene = new Scene(root, 600, 600);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
//        stage.setFullScreen(true);
    }

    public static void main(String[] args) {
        launch();
    }
}