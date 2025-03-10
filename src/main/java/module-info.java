module com.example.chatapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;


    opens com.example.chatapp to javafx.fxml;
    exports com.example.chatapp;
    exports com.example.chatapp.controller;
    opens com.example.chatapp.controller to javafx.fxml;
    exports com.example.chatapp.service;
    opens com.example.chatapp.service to javafx.fxml;
    exports com.example.chatapp.model;
    opens com.example.chatapp.model to javafx.fxml;
}