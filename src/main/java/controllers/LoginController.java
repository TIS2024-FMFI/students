package controllers;

import others.service.DataService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;

    private DataService dataService;
    private Stage stage;

    public void setDataService(DataService dataService) {
        this.dataService = dataService;
    }

    @FXML
    public void initialize() {
        // Clear focus from all components
        Platform.runLater(() -> {
            // Clear focus by setting focus to the root of the scene
            usernameField.getScene().getRoot().requestFocus();
        });
    }

    // Handle the login button click event
    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (authenticateUser(username, password)) {
            try {
                dataService.setUsername(username);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/DownloadScene.fxml"));
                Parent root = loader.load();

                // Pass DataService to DownloadController
                DownloadController downloadController = loader.getController();
                downloadController.setDataService(dataService);

                // Set the new scene on the same Stage
                stage.setScene(new Scene(root, 1366, 768));
                stage.setTitle("Download Scene");
                //downloadController.startDownload();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Login Failed/Nesprávne prihlásenie", "Zle meno alebo heslo");
        }
    }

    // Show an alert with the provided title and message
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean authenticateUser(String username, String password) {
        try {
            String hashedPassword = hashPassword(password);
            InputStream is = getClass().getClassLoader().getResourceAsStream("users.txt");
            if (is == null) {
                return false;
            }
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(":");
                    if (parts.length == 2 && parts[0].equals(username) && parts[1].equals(hashedPassword)) {
                        return true;
                    }
                }
            }
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            hexString.append(Integer.toHexString(0xff & b));
        }
        return hexString.toString();
    }

    public void setStage(Stage primaryStage) {
        this.stage = primaryStage;
    }
}
