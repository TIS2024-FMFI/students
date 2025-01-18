package com.controllers.controllers;

import com.tis.dbf.service.DataService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.StackPane;
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

    public StackPane stackPane;
    @FXML
    private Label statusLabel;

    @FXML
    private Arc spinner; // The spinner arc (loading circle)

    @FXML
    private Button nextButton; // The button that will appear after download

    @FXML
    private Button retryButton; // The button that will appear if download fails

    @FXML
    private AnchorPane anchorPane; // Injected AnchorPane reference

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button startDownloadButton;

    private Timeline timeline; // For continuous spinning

    private DataService dataService;

    public void setDataService(DataService dataService) {
        this.dataService = dataService;
    }

    public void initialize() {
        // Set initial visibility and positions
        nextButton.setVisible(false);
        retryButton.setVisible(false);

        // Update positions when the window is resized
        anchorPane.widthProperty().addListener((obs, oldWidth, newWidth) -> updatePositions());
        anchorPane.heightProperty().addListener((obs, oldHeight, newHeight) -> updatePositions());

        updatePositions();
    }

    private void updatePositions() {
        double centerX = anchorPane.getWidth() / 2;
        double centerY = anchorPane.getHeight() / 2;

        // Center the spinner
        spinner.setLayoutX(centerX);
        spinner.setLayoutY(centerY);

        // Center the status label above the spinner


        // Center the next button below the spinner
        nextButton.setLayoutX(centerX - nextButton.getWidth() / 2);
        nextButton.setLayoutY(centerY + spinner.getRadiusY() * 2 + 60);

        // Center the retry button below the next button (adjust as necessary)
        retryButton.setLayoutX(centerX - retryButton.getWidth() / 2);
        retryButton.setLayoutY(centerY + spinner.getRadiusY() * 2 + 120);
    }

    public void updateStatus(String message) {
        statusLabel.setText(message);
        statusLabel.setTranslateY(-80);
    }

    public void startDownload() {
        //System.out.println(dataService.getUsername());
        spinner.setVisible(true);
        updateStatus("Sťahujem XML");
        updatePositions(); // Ensure proper positioning

        // Start spinner animation
        timeline = new Timeline(
                new KeyFrame(Duration.seconds(0.01), e -> spinner.setRotate(spinner.getRotate() + 5))
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        Task<Void> downloadTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    dataService.startDownload();
                    Platform.runLater(() -> {
                        timeline.stop();
                        spinner.setVisible(false);
                        updateStatus("Sťahovanie dokončené");
                        updatePositions();
                        nextButton.setVisible(true); // Show "Proceed" button
                    });
                } catch (Exception e) {
                    System.out.println(e);
                    // If download fails
                    Platform.runLater(() -> {
                        timeline.stop();
                        spinner.setVisible(false);
                        updateStatus("Sťahovanie zlyhalo");
                        updatePositions();
                        retryButton.setVisible(true); // Show "Retry" button
                    });
                }
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
            // Load MainScene.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainScene.fxml"));
            Parent root = loader.load();

            // Get the controller for MainScene
            MainSceneController mainSceneController = loader.getController();

            // Pass the DataService instance to MainSceneController
            mainSceneController.setDataService(dataService);

            // Set the scene and show the stage
            Scene newScene = new Scene(root);
            Stage stage = (Stage) nextButton.getScene().getWindow();
            stage.setScene(newScene);
            stage.setTitle("Main Scene");
            mainSceneController.loadAllStudies();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleRetry() {
        // Hide retry button and restart the download
        retryButton.setVisible(false);
        startDownload(); // Restart the download process
    }

    public void handlePasswordAndStartDownload() {
        String password = passwordField.getText();
        if (password.isEmpty()) {
            updateStatus("Password cannot be empty!");
        } else {
            passwordField.setVisible(false);
            startDownloadButton.setVisible(false);
            dataService.setServerPassword(password);
            updateStatus("Starting download...");
            startDownload();
        }
    }
}
