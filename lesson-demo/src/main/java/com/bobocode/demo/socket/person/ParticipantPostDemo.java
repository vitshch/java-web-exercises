package com.bobocode.demo.socket.person;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ParticipantPostDemo {

    @SneakyThrows
    public static void main(String[] args) {
        try (Socket socket = new Socket("db5c-2a02-2378-1066-9ebd-89e-f6af-a2b2-7303.ngrok-free.app", 80)) {
            var writer = new PrintWriter(socket.getOutputStream());
            var reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            var participant = new Participant("Vitalii", "Shcherban");

            String body = new ObjectMapper().writeValueAsString(participant);

            writer.println("POST /here HTTP/1.1");
            writer.println("Host: db5c-2a02-2378-1066-9ebd-89e-f6af-a2b2-7303.ngrok-free.app");
            writer.println("Connection: close");
            writer.println("Content-Type: application/json");
            writer.println("Content-Length: " + body.length());
            writer.println();
            writer.println(body);
            writer.flush();

            reader.lines()
                    .forEach(System.out::println);
        }
    }

    record Participant(String firstName, String lastName) {
    }
}
