package com.example.chatapp.controller;

import com.example.chatapp.MainApp;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.util.Objects;


public class LoginController {
    private MainApp mainApp;

    @FXML
    private ImageView imageLogo;

    @FXML
    private TextField usernameField;

    @FXML
    private void handleLogin() {
        String username = usernameField.getText().trim();

        if (username.isEmpty()) {
            showAlert("Debes ingresar un nombre para poder chatear.");
        } else {
            if (username.matches("[a-zA-Z]*")) {
                mainApp.showChatSreen(username);
            }
            else {
                showAlert("No se permiten espacios ni caracteres especiales en el nombre.");
            }
        }
    }

    @FXML
    private void initialize() {
        // Cargar la imagen en el ImageView
        URL resource = getClass().getResource("/com/example/chatapp/images/elpuig_logo.jpg");
        if (resource != null) {
            imageLogo.setImage(new Image(resource.toExternalForm()));
            imageLogo.setFitWidth(200);
            imageLogo.setFitHeight(150);
            imageLogo.setPreserveRatio(true);
        }

    }

    private void showAlert(String alertMessage) {
        Stage alertStage = new Stage();
        alertStage.initModality(Modality.APPLICATION_MODAL);
        alertStage.initStyle(StageStyle.UNDECORATED);
        alertStage.setTitle("Advertencia");

        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));
        vbox.getStyleClass().add("vbox"); // Agregar clase CSS

        Label label = new Label(alertMessage);
        label.getStyleClass().add("label"); // Agregar clase CSS

        Button closeButton = new Button("Cerrar");
        closeButton.getStyleClass().add("button"); // Agregar clase CSS
        closeButton.setOnAction(e -> alertStage.close());

        vbox.getChildren().addAll(label, closeButton);

        Scene scene = new Scene(vbox, 480, 150);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/chatapp/css/alert.css")).toExternalForm());
        alertStage.setScene(scene);
        alertStage.showAndWait();
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

}
