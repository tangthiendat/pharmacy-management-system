package com.ttdat.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class App extends Application {
    private double x = 0;
    private double y = 0;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        root.setOnMousePressed((MouseEvent event) -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });

        root.setOnMouseDragged((MouseEvent event) -> {
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        });
        //Add icons to the app
        Image icon16 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/icon16.png")));
        Image icon32 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/icon32.png")));
        Image icon64 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/icon64.png")));
        Image icon128 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/icon128.png")));
        stage.getIcons().addAll(icon16, icon32, icon64, icon128);
        stage.setTitle("Pharmacy Management System");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}