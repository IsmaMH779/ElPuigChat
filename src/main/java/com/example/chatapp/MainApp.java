package com.example.chatapp;

import com.example.chatapp.controller.ChatController;
import com.example.chatapp.controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        showLoginScreen();
    }

    // Cargar la pantalla del login
    public void showLoginScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/login.fxml"));
            Scene scene = new Scene(loader.load());
            scene.getStylesheets().add(String.valueOf(getClass().getResource("css/login.css")));

            // Crear el controlador y pasar la referencia del MainApp
            LoginController controller = loader.getController();
            controller.setMainApp(this);

            primaryStage.setScene(scene);
            primaryStage.setTitle("Login - El Puig Chat");
            primaryStage.setResizable(false);
            primaryStage.setMaximized(false);
            primaryStage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void showChatSreen(String username) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/chat.fxml"));
            Scene scene = new Scene(loader.load());
            scene.getStylesheets().add(String.valueOf(getClass().getResource("css/chat.css")));

            // Crear el controlador y pasar la referencia del MainApp
            ChatController controller = loader.getController();
            controller.setMainApp(this);
            controller.setUsername(username);

            primaryStage.setScene(scene);
            primaryStage.setTitle("El Puig Chat - " + username);
            primaryStage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}