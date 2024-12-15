package com.example.login;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;
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
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/DownloadScene.fxml"));
                Scene downloadScene = new Scene(loader.load(), 1366, 768);

                DownloadController controller = loader.getController();
                controller.startDownload();

                Stage stage = (Stage) loginButton.getScene().getWindow();
                stage.setScene(downloadScene);
                stage.show();
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
}
