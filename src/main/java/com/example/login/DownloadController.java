package com.example.login;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.application.Platform;
import javafx.concurrent.Task;

public class DownloadController {

    @FXML
    private Label statusLabel;

    @FXML
    private ProgressIndicator progressIndicator;

    public void updateStatus(String message) {
        statusLabel.setText(message);
    }

    public void startDownload() {
        Task<Void> downloadTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Thread.sleep(2000);

                Platform.runLater(() -> {
                    statusLabel.setText("Download complete!");
                    progressIndicator.setProgress(1.0);
                });

                return null;
            }
        };

        Thread downloadThread = new Thread(downloadTask);
        downloadThread.setDaemon(true);
        downloadThread.start();
    }
}
