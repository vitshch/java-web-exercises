package com.bobocode.demo.socket.nasa;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.StringUtils;

import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class MarsPictureSocketDemo {

//    **HOME TRAINING 11
//            👉  Find the largest  Mars picture using Java Sockets**
//
//            * **Do** the warmup exercise https://github.com/bobocode-projects/java-web-exercises/tree/main/1-0-networking-and-http/1-0-0-hello-network-socket
//            * **Find** the largest Mars picture calling this endpoint https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?sol=15&api_key=YOUR_API_KEY **using Java Sockets instead of Http client libraries** ❗️
//            * Make sure you specify your `api_key`
//            * Make sure you're opening a secure socket (`SSLSocket` not just a simple `Socket`)
//            * Make sure you're connecting to the valid port (the default port for HTTP is `80`, but you need HTTPS instead)
//            * Make sure you're performing all required remote calls using Java Sockets not Http client libraries
//            * **Post** screenshots of your code and the largest picture URL in the Thread 👇

    private static final String NASA_PICTURES_URL = "https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?sol=15" +
            "&api_key=JCXHdFXIZqaWGR5lWUQU1RHg34uGbWxCPfs65e8a";

    public static void main(String[] args) {
        SocketFactory socketFactory = SSLSocketFactory.getDefault();

        try (Socket socket = socketFactory.createSocket("api.nasa.gov", 443)) {
            List<String> photos = getPhotos(socket);

        } catch (UnknownHostException e) {
            throw new RuntimeException("Host is not known", e);
        } catch (IOException e) {
            throw new RuntimeException("Could establish socket connection", e);
        }
    }

    private static List<String> getPhotos(Socket socket) throws IOException {
        var writer = new PrintWriter(socket.getOutputStream());
        writer.println("GET /mars-photos/api/v1/rovers/curiosity/photos?sol=15&api_key=JCXHdFXIZqaWGR5lWUQU1RHg34uGbWxCPfs65e8a HTTP/1.1");
        writer.println("Host: api.nasa.gov");
        writer.println("Connection: close");
        writer.println();
        writer.flush();

        var reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        var body = reader.lines()
                .dropWhile(StringUtils::hasText)
                .collect(Collectors.joining());
        return parseBody(body);
    }

    private static List<String> parseBody(String body) {
        try {
            var mapper = new ObjectMapper();
            var jsonNode = mapper.readValue(body, JsonNode.class);
            var photos = jsonNode.get("photos");
            return StreamSupport.stream(photos.spliterator(), false)
                    .map(node -> node.get("img_src"))
                    .map(JsonNode::asText)
                    .collect(Collectors.toList());
        } catch (JsonProcessingException exception) {
            throw new RuntimeException(exception);
        }
    }

//    private static Picture findLargestPicture(List<String> pictures) {
//
//    }

}
