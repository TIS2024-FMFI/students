package com.tis.dbf;

import com.controllers.controllers.LoginController;
import com.tis.dbf.service.DataService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class JavaFxApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        DataService dataService = DataService.getInstance();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
        Parent root = loader.load();

        LoginController loginController = loader.getController();
        loginController.setDataService(dataService);
        loginController.setStage(primaryStage);

        primaryStage.setScene(new Scene(root, 1366, 768));
        primaryStage.setTitle("Login");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
