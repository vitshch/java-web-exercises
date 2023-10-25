package com.bobocode.demo.socket;
//ONLINE TRAINING 11 | TASK 1
//        👉  Post you name via Java Socket
//
//        Create an app (or use DemoApp)
//        Connect to a remote server via TCP socket
//        Host: 93.175.203.215
//        Post: 18080
//        Send your name as a text line
//        E.g. Ihor Zaporozhtsev
//        Post screenshots of your code in the Thread 👇


import lombok.SneakyThrows;

import java.io.*;
import java.net.Socket;

public class ClientSocketDemo {

    @SneakyThrows
    public static void main(String[] args) {
        try (Socket socket = new Socket("93.175.203.215", 18080)) {
            var writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));

            writer.write("Vitalii Shcherban");
            writer.close();
        }
    }

}
