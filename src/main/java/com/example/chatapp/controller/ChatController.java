package com.example.chatapp.controller;

import com.example.chatapp.service.FirebaseService;
import com.example.chatapp.MainApp;
import com.example.chatapp.model.Message;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.*;

public class ChatController {
    private MainApp mainApp;
    private String username;
    private Timer timer;


    @FXML
    private ListView<String> chatListView;

    @FXML
    private TextField messageField;

    private final ObservableList<String> messagesObservable = FXCollections.observableArrayList();

    @FXML
    private void handleSend() {
        String message = messageField.getText().trim();
        if (!message.isEmpty()) {
            FirebaseService.sendMessage(username, message);
            messageField.clear();
        }
    }

    private void startChatUpdater() {
        timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> updateChat());
            }
        }, 0, 2000);
    }

    private void updateChat() {
        String messages = FirebaseService.getMessages();
        if (messages != null) {
            parseAndDisplayMessages(messages);
        }
    }

    private void parseAndDisplayMessages(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, Message>>() {}.getType();
        Map<String, Message> messagesMap = gson.fromJson(json, type);

        if (messagesMap != null) {
            List<Message> messageList = new ArrayList<>(messagesMap.values());
            messageList.sort(Comparator.comparingLong(m -> m.timestamp));

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            List<String> formattedMessages = new ArrayList<>();

            for (Message msg : messageList) {
                String dateFormatted = sdf.format(new Date(msg.timestamp));
                formattedMessages.add("[" + dateFormatted + "] " + msg.user + ": " + msg.message);
            }

            messagesObservable.setAll(formattedMessages); // Actualizamos la lista observable
        }
    }

    @FXML
    public void initialize() {
        chatListView.setItems(messagesObservable);

        chatListView.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> param) {
                return new ListCell<String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item);

                            // Establecer colores alternados
                            if (getIndex() % 2 != 0) {
                                setStyle("-fx-background-color: #2c2f33; -fx-text-fill: white;");
                            } else {
                                setStyle("-fx-background-color: #3e444b; -fx-text-fill: white;");
                            }
                        } else {
                            setText(null);
                            setStyle("");  // Limpiar el estilo si el item es nulo
                        }
                    }
                };
            }
        });
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public void setUsername(String username) {
        this.username = username;
        startChatUpdater();
    }
}
