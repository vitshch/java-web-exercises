package com.bobocode.demo.socket.session;

import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Optional;

public class SessionSocketDemo {

    private static final String HOSTNAME = "ffb6-2a02-2378-1066-9ebd-89e-f6af-a2b2-7303.ngrok-free.app";

    @SneakyThrows
    public static void main(String[] args) {
        try (Socket socket = new Socket(HOSTNAME, 80)) {
            var writer = new PrintWriter(socket.getOutputStream());
            var reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            System.out.println("REQUEST 1");
            writer.println("GET /servlet_demo_app_war_exploded/hello?name=Vitalii HTTP/1.1");
            writer.println("Host: " + HOSTNAME);
            writer.println("Connection: close");
            writer.println("Accept: text/html");
            writer.println();
            writer.flush();

            reader.lines().forEach(System.out::println);

            String cookie = reader.lines()
                    .filter(line -> line.startsWith("Set-Cookie"))
                    .findFirst().orElseThrow();

            System.out.println("REQUEST 2");
            writer.println("GET /servlet_demo_app_war_exploded/hello HTTP/1.1");
            writer.println("Host: " + HOSTNAME);
            writer.println("Connection: close");
            writer.println("Cookie: " + cookie.substring(0, cookie.indexOf(";")));
            writer.println();
            writer.flush();

            reader.lines().forEach(System.out::println);
        }
    }

}
