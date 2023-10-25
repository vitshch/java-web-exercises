package com.bobocode.demo.socket;

import lombok.SneakyThrows;

import java.net.*;

/*
ONLINE TRAINING 11 | TASK 2
👉  Post you name via UDP Socket

Create an app (or use DemoApp)
Create to a UDP socket
Send your name  (e.g. Ihor Zaporozhtsev) to the following UDP socket
Host: 93.175.203.215
Post: 18080
Post screenshots of your code in the Thread 👇
 */

public class ClientSocketUdpDemo {

    @SneakyThrows
    public static void main(String[] args) {
        try(var datagramSocket = new DatagramSocket(18080)) {
            var name = "Vitalii Shcherban".getBytes();
            var address = InetAddress.getByAddress("93.175.203.215".getBytes());
            DatagramPacket datagramPacket = new DatagramPacket(
                    name, name.length, address , 18080
            );

            datagramSocket.send(datagramPacket);
        }
    }

}
