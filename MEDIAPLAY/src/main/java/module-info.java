module com.example.mediaplay {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;


    opens com.example.mediaplay to javafx.fxml;
    exports com.example.mediaplay;
}