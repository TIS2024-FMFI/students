package com.example.login;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.shape.Arc;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.layout.AnchorPane;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.io.IOException;

public class DownloadController {

    @FXML
    private Label statusLabel;

    @FXML
    private Arc spinner; // The spinner arc (loading circle)

    @FXML
    private Button nextButton; // The button that will appear after download

    @FXML
    private AnchorPane anchorPane; // Injected AnchorPane reference

    private Timeline timeline; // For continuous spinning

    public void initialize() {
        // Position the spinner arc at the center of the AnchorPane
        anchorPane.widthProperty().addListener((obs, oldWidth, newWidth) ->
                spinner.setLayoutX((newWidth.doubleValue() - spinner.getRadiusX() * 2) / 2)
        );
        anchorPane.heightProperty().addListener((obs, oldHeight, newHeight) ->
                spinner.setLayoutY((newHeight.doubleValue() - spinner.getRadiusY() * 2) / 2)
        );

        // Initially, hide the proceed button
        nextButton.setVisible(false);
    }

    public void updateStatus(String message) {
        statusLabel.setText(message);
    }

    public void startDownload() {
        // Make sure the spinner is visible before starting the download
        spinner.setVisible(true);
        statusLabel.setText("Downloading...");

        // Start the continuous spinning when the download begins
        timeline = new Timeline(
                new KeyFrame(Duration.seconds(0.01), e -> spinner.setRotate(spinner.getRotate() + 5)) // Rotate by 5 degrees every frame
        );
        timeline.setCycleCount(Timeline.INDEFINITE); // Spin indefinitely
        timeline.play();

        Task<Void> downloadTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                // Simulate downloading by sleeping for 2 seconds
                Thread.sleep(2000);

                Platform.runLater(() -> {
                    // Stop the spinning and update the status when download is complete
                    timeline.stop(); // Stop the spinning animation
                    spinner.setVisible(false); // Hide the spinner after download
                    updateStatus("Download complete!"); // Update the status label

                    // Set the button's position to the same as the spinner's center
                    nextButton.setLayoutX((anchorPane.getWidth() - nextButton.getWidth()) / 2);
                    nextButton.setLayoutY((anchorPane.getHeight() - nextButton.getHeight()) / 2);

                    // Show the button after the download is complete
                    nextButton.setVisible(true); // Make the button visible
                });

                return null;
            }
        };

        Thread downloadThread = new Thread(downloadTask);
        downloadThread.setDaemon(true);
        downloadThread.start();
    }

    @FXML
    private void handleProceed() {
        try {
            // Load the new scene (Main Scene)
            Scene newScene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/MainScene2.fxml")));

            // Get the current stage and set the new scene
            Stage stage = (Stage) nextButton.getScene().getWindow();
            stage.setScene(newScene);

            // Optionally, update the window title or other settings
            stage.setTitle("Main Scene");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
