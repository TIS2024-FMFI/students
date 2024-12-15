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
        // Umiestnenie pri zmene veľkosti AnchorPane
        anchorPane.widthProperty().addListener((obs, oldWidth, newWidth) -> updatePositions());
        anchorPane.heightProperty().addListener((obs, oldHeight, newHeight) -> updatePositions());

        // Skryť tlačidlo na začiatku
        nextButton.setVisible(false);

        // Počiatočná aktualizácia pozícií
        updatePositions();
    }

    private void updatePositions() {
        // Výpočet stredu AnchorPane
        double centerX = anchorPane.getWidth() / 2;
        double centerY = anchorPane.getHeight() / 2;

        // Nastavenie pozície spinnera
        spinner.setLayoutX(centerX);
        spinner.setLayoutY(centerY);

        // Nastavenie pozície statusLabel (text nad spinnerom)
        Platform.runLater(() -> {
            statusLabel.setLayoutX(centerX - statusLabel.getWidth() / 2); // Vycentrovať text horizontálne
            statusLabel.setLayoutY(centerY - statusLabel.getHeight() - spinner.getRadiusY() - 20);
        });

        // Nastavenie pozície NextButton (button pod spinnerom)
        nextButton.setLayoutX(centerX - nextButton.getWidth() / 2); // Vycentrovať tlačidlo horizontálne
        nextButton.setLayoutY(centerY + spinner.getRadiusY() * 2 + 60); // 60px pod spinnerom
    }

    public void updateStatus(String message) {
        statusLabel.setText(message);
    }

    public void startDownload() {
        // Zobraziť spinner pred začiatkom sťahovania
        spinner.setVisible(true);
        updateStatus("Sťahujem XML");
        updatePositions(); // Zaisti správne umiestnenie

        // Spustiť animáciu spinnera
        timeline = new Timeline(
                new KeyFrame(Duration.seconds(0.01), e -> spinner.setRotate(spinner.getRotate() + 5)) // Rotácia o 5 stupňov
        );
        timeline.setCycleCount(Timeline.INDEFINITE); // Nekonečná rotácia
        timeline.play();

        Task<Void> downloadTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                // Simulácia sťahovania (čakanie 5 sekúnd)
                Thread.sleep(5000);

                Platform.runLater(() -> {
                    // Zastaviť spinner a zobraziť stav
                    timeline.stop();
                    spinner.setVisible(false);
                    updateStatus("Sťahovanie dokončené");
                    updatePositions();  // Po aktualizácii stavu, vycentruj pozíciu textu

                    // Zobraziť tlačidlo
                    nextButton.setVisible(true);
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
            // Načítať novú scénu (Main Scene)
            Scene newScene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/MainScene2.fxml")), 1366, 768);

            // Nastaviť novú scénu na aktuálne okno
            Stage stage = (Stage) nextButton.getScene().getWindow();
            stage.setScene(newScene);

            // Nastaviť titulok okna
            stage.setTitle("Main Scene");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
