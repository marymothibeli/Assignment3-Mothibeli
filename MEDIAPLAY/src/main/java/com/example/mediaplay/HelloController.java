package com.example.mediaplay;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

public class HelloController implements Initializable{
    @FXML
    private MediaView mediaView;
    @FXML
    private File file;
    private Media media;
    private ArrayList<File>songs;
    private int songNumber;
    private int[] speeds={25,50,75,100,125,150,175,200};
    private String path;
    private MediaPlayer mediaPlayer;

    @FXML
    private ResourceBundle resources;
    @FXML
    private Slider progressBar;
    @FXML
    private Button FAST;
    @FXML
    private Button SPEED;
    @FXML
    private Button SLOW;

    @FXML
    private URL location;

    @FXML
    private MediaView MEDIAVIEW;
    @FXML
    private ProgressBar progress;
    @FXML
    private Pane PANE;
    @FXML
    private Button PAUSE,PLAY,STOP,RESET,upload;
   @FXML
    private ComboBox<String> SpeedBox;
    @FXML
    private ComboBox<Double>speedComboBox;
    @FXML
    private Slider VOLUMESLIDER;
    @FXML
    private Label currentTimeLabel;
    @FXML
    private ProgressBar Progree;
    @FXML
    private Slider playbackSlider;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
     }
    @FXML
    void PAUSE() {
        mediaPlayer.pause();

    }
    @FXML
    void PLAY() {
        mediaPlayer.play();
        mediaPlayer.setRate(1);

    }
    @FXML
    void RESET() {
        if (mediaPlayer.getStatus() != MediaPlayer.Status.READY) {
            mediaPlayer.seek(Duration.seconds(0.0));
        }
    }
    @FXML
    void STOP() {
        mediaPlayer.stop();

    }
    @FXML
    void changeSpeed(ActionEvent event) {
        ComboBox<Double>speedComboBox=new ComboBox<>();
        speedComboBox.getItems().addAll(0.5,0.75,1.0,1.25,1.5,1.75,2.0);
        speedComboBox.setValue(1.0);

        speedComboBox.setOnAction(event1 -> {
            double selectedSpeed=speedComboBox.getValue();
            mediaPlayer.setRate(selectedSpeed);
        });

        for (int i=0;i>speeds.length;i++){
            SpeedBox.getItems().add(speeds[i] +"%");
        }


    }
    @FXML
    void changeSpeede(ActionEvent event) {

        mediaPlayer.setRate(2);


    }

    @FXML
    void upload(ActionEvent event){
        FileChooser fileChooser = new FileChooser();
        File file1=fileChooser.showOpenDialog(null);
        path=file1.toURI().toString();
        if(path!=null) {
            Media media = new Media(path);
            mediaPlayer = new MediaPlayer(media);
            MEDIAVIEW.setMediaPlayer(mediaPlayer);

            DoubleProperty width = MEDIAVIEW.fitWidthProperty();
            DoubleProperty height = MEDIAVIEW.fitHeightProperty();
            width.bind(Bindings.selectDouble(MEDIAVIEW.sceneProperty(), "width"));
            height.bind(Bindings.selectDouble(MEDIAVIEW.sceneProperty(), "height"));

            Slider fontSizeSlider = new Slider();
            fontSizeSlider.setShowTickLabels(true);
            fontSizeSlider.setShowTickMarks(true);
            fontSizeSlider.setMajorTickUnit(4);
            fontSizeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
                progressBar.setStyle("-fx-font-size: " + newValue.intValue() + "px;");
            });

            mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>() {
                @Override
                public void changed(ObservableValue<? extends Duration> observable, Duration duration, Duration t1) {

                }

                public void changed(ObservableValue<? extends Duration> observableValue, Duration duration, Duration oldValue, Duration newValue, Duration t1) {
                    String formattedTime = formatDuration(newValue);
                    currentTimeLabel.setText(formattedTime);
                    Duration totalDuration = mediaPlayer.getTotalDuration();
                    double progress = newValue.toMillis() / totalDuration.toMillis();
                    playbackSlider.setValue(progress * 100.0);
                    progressBar.setValue(newValue.toSeconds());

                }

                private String formatDuration(Duration newValue) {
                    java.time.Duration duration = null;
                    int minutes = (int) duration.toMinutes();
                    int seconds = (int) duration.toSeconds() % 60;
                    return String.format("%d:%02d", minutes, seconds);
                }
            });
            progressBar.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    mediaPlayer.seek(Duration.seconds(progressBar.getValue()));
                }
            });
            progressBar.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    mediaPlayer.seek(Duration.seconds(progressBar.getValue()));
                }
            });
            mediaPlayer.setOnReady(new Runnable() {
                @Override
                public void run() {
                    Duration total = media.getDuration();
                    progressBar.setMax(total.toSeconds());
                }
            });

            VOLUMESLIDER.setValue(mediaPlayer.getVolume() * 100);
            VOLUMESLIDER.valueProperty().addListener(new InvalidationListener() {
                @Override
                public void invalidated(Observable observable) {
                    mediaPlayer.setVolume(VOLUMESLIDER.getValue() / 200);
                }
            });


        }

     }
    @FXML
    void FAST(ActionEvent event) {
        mediaPlayer.seek(mediaPlayer.getCurrentTime().add(Duration.seconds(15)));

    }
    @FXML
    void SLOW(ActionEvent event) {
        mediaPlayer.seek(mediaPlayer.getCurrentTime().add(Duration.seconds(-15)));

 }
    public void BeginTimer(){
    }
    public void CancelTimer(){

    }

}
