package com.example.chatapp.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class FirebaseService {

    // URL de la base de datos
    private static final String FIREBASE_DB_URL = "https://chatapp-cfcdc-default-rtdb.europe-west1.firebasedatabase.app";

    // Guardar el mensaje en la base de datos
    public static void sendMessage(String user, String message) {
        try {
            URL url = new URL(FIREBASE_DB_URL + "/messages.json");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setDoOutput(true);

            // Obtener el tiempo actual en milisegundos
            long currentTimeMillis = System.currentTimeMillis();

            // Crear el JSON con los datos user, message y el tiempo en milis
            String jsonInputString = "{\"user\":\"" + user + "\", \"message\":\"" + message + "\", \"timestamp\":" + currentTimeMillis + "}";

            // enviar el JSON
            try (OutputStream os = con.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // Leer la respuesta de firebase
            try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder response = new StringBuilder();
                String responseLine;

                while ((responseLine = br.readLine()) != null) {
                    response.append((responseLine.trim()));
                }

                System.out.println("Mensaje enviado: " + response.toString());
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Obtener los mensajes almacenados en firebase
    public static String getMessages() {
        try {
            URL url = new URL(FIREBASE_DB_URL + "/messages.json");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            // leer los mensajes de la base de datos
            try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))){
                StringBuilder response = new StringBuilder();
                String responseLine;

                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                System.out.println(response.toString());
                return response.toString();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
