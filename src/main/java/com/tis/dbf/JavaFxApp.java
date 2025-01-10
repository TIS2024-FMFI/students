package com.tis.dbf;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class JavaFxApp extends Application {

    @Override
    public void init() throws Exception {
        System.out.println("Aplikácia sa inicializuje...");
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 1366, 768);
        primaryStage.setScene(scene);
        primaryStage.setTitle("JavaFX app");
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        System.out.println("Aplikácia sa zatvára...");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
