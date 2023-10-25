package com.bobocode.demo.socket;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import java.io.*;
import java.net.Socket;

/**
 * ONLINE TRAINING 11 | TASK 3
 * 👉  Post your training stats using Java Sockets
 * <p>
 * Create a JSON object with your training stats according to the following format:
 * {
 * "firstName": "Mariana",
 * "lastName": "Vorotniak",
 * "team": "Blyznytsia",
 * "trainingDaysPerWeek": 3,
 * "minutesPerTrainingDay": 90
 * }
 * <p>
 * Send an HTTP POST request with the given JSON to the following endpoint http://93.175.204.215:8080/training/stats using Java Sockets
 * Open this link http://93.175.204.215:8080/training/stats in the browser to make sure your data are there
 * Post screenshots of your code in the Thread 👇
 */


public class HttpSocketDemo {

    @SneakyThrows
    public static void main(String[] args) {
        var person = new Person("Vitalii", "Shcherban", "Blyznytsia", 4, 60);
        ObjectMapper objectMapper = new ObjectMapper();
        postPerson(objectMapper.writeValueAsString(person));
    }

    @SneakyThrows
    private static void postPerson(String body) {
        http://93.175.203.215:8080/training/stats
        try (var socket = new Socket("93.175.203.215", 8080)) {
            PrintWriter writer = new PrintWriter(socket.getOutputStream());
            writer.println("POST /training/stats HTTP/1.1");
            writer.println("Host: 93.175.203.215");
            writer.println("Connection: close");
            writer.println("Content-Type: application/json");
            writer.println("Content-Length: " + body.length());
            writer.println();
            writer.println(body);

            writer.flush();

//            BufferedReader reader = new BufferedReader(socket.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            reader.lines().forEach(System.out::println);
        }
    }

    private record Person(
            String firstName,
            String lastName,
            String team,
            int trainingDaysPerWeek,
            int minutesPerTrainingDay
    ) {
    }

    /**
      - Network communication is layering
      - Socket and DatagramSocket is a basic Java classes for low level communication
      - Socket for TCP communication, DatagramSocket for UDP connection
      - HTTP is an application layer, and you should keep the structure of protocol
      - It's valuable to read specification as a true source
     */
}
