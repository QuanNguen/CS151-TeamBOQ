module com.example.redditclone {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;


    opens com.example.redditclone to javafx.fxml;
    exports com.example.redditclone;
}