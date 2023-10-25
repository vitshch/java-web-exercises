package com.bobocode.demo.socket;

import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MoodSocketDemo {

    public static final String HOSTNAME = "ffb6-2a02-2378-1066-9ebd-89e-f6af-a2b2-7303.ngrok-free.app";

    @SneakyThrows
    public static void main(String[] args) {
        try (Socket socket = new Socket(HOSTNAME, 80)) {
            var writer = new PrintWriter(socket.getOutputStream());
            writer.println("GET /servlet_demo_app_war_exploded/hello HTTP/1.1");

            writer.println("Host: " + HOSTNAME);
            writer.println("X-Mood: Sleepy");
            writer.println("Connection: close");
            writer.println();
            writer.flush();

            var reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            reader.lines().forEach(System.out::println);
        }
    }

}
