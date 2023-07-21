module com.romych.player.musicplayer {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires java.xml;
    requires java.sql;


    opens com.romych.player.musicplayer to javafx.fxml;
    exports com.romych.player.musicplayer;
    exports com.romych.player.musicplayer.controller;
    opens com.romych.player.musicplayer.controller to javafx.fxml;
}