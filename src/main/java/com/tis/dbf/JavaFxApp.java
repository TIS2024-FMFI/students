package com.tis.dbf;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class JavaFxApp extends Application {

    private ApplicationContext springContext;

    @Override
    public void init() throws Exception {
        springContext = new AnnotationConfigApplicationContext(DbfApplication.class);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"));
        //fxmlLoader.setControllerFactory(springContext::getBean);
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 1920, 1080);
        primaryStage.setScene(scene);
        primaryStage.setTitle("JavaFX app");
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        ((AnnotationConfigApplicationContext) springContext).close();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
