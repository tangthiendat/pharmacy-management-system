package com.ttdat.application.controllers;

import com.ttdat.application.utilities.DBUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {


    @FXML
    private Button loginBtn;


//    private AnchorPane mainForm;

    @FXML
    private PasswordField password;

    @FXML
    private TextField username;


    private double x = 0;
    private double y = 0;


    public void loginAdmin() throws SQLException, IOException {
        String sqlSelect = "SELECT * FROM admin WHERE username = ? AND password = ?";
        try (
                Connection connection = DBUtils.openConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sqlSelect)
        ) {
            preparedStatement.setString(1, username.getText());
            preparedStatement.setString(2, password.getText());
            ResultSet resultSet = preparedStatement.executeQuery();

            Alert alert;
            if (username.getText().isBlank() || password.getText().isBlank()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {
                if (resultSet.next()) {
                    //Hide your login form
                    loginBtn.getScene().getWindow().hide();
                    //Link to Dashboard
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/dashboard.fxml"));
                    Parent root = fxmlLoader.load();
                    Scene scene = new Scene(root);
                    Stage stage = new Stage();

                    root.setOnMousePressed((MouseEvent event) -> {
                        x = event.getSceneX();
                        y = event.getSceneY();
                    });

                    root.setOnMouseDragged((MouseEvent event) -> {
                        stage.setX(event.getScreenX() - x);
                        stage.setY(event.getScreenY() - y);
                    });

                    stage.setScene(scene);
                    stage.show();
                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("The username or password is incorrect");
                    alert.showAndWait();
                }
            }
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        username.setText("admin");
        password.setText("admin");
    }
}
