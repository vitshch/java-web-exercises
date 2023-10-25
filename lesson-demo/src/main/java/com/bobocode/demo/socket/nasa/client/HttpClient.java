package com.bobocode.demo.socket.nasa.client;

import com.bobocode.demo.socket.nasa.client.model.Method;
import com.bobocode.demo.socket.nasa.client.model.Request;
import com.bobocode.demo.socket.nasa.client.model.Response;
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
import java.net.URL;
import java.net.UnknownHostException;
import java.util.stream.Collectors;

public class HttpClient {

    public static void main(String[] args) {
        HttpClient httpClient = new HttpClient();
        httpClient.execute(
                Request.of(Method.GET, "https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?sol=15&api_key=JCXHdFXIZqaWGR5lWUQU1RHg34uGbWxCPfs65e8a"),
                JsonNode.class
        );
    }

    private ObjectMapper objectMapper;

    public HttpClient() {
        objectMapper = new ObjectMapper();
    }

    public <T> Response<T> execute(Request request, Class<T> responseClass) {
        SocketFactory socketFactory = SSLSocketFactory.getDefault();
        URL url = request.getUrl();

        try (Socket socket = socketFactory.createSocket(url.getHost(), url.getPort())) {
            var writer = new PrintWriter(socket.getOutputStream());
            var reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            writer.printf("%s %s HTTP/1.1\n", request.getMethod(), request.getUrl().getPath() + request.getUrl().getQuery());
            request.getHeaders()
                    .forEach((key, value) -> writer.printf("%s: %s", key, value));
            writer.println();
            writer.println(request.getPayload());

            writer.println();
            writer.flush();
//
//            var body = reader.lines()
//                    .dropWhile(StringUtils::hasText)
//                    .collect(Collectors.joining());
//            var body = reader.lines()
//                    .takeWhile(StringUtils::hasText)
//                    .map();
//
//            new Response<>(parseBody(body));
//            return parseBody(body);
            return null;
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private <T> T parseBody(String body, Class<T> modelClass) {
        try {
            var mapper = new ObjectMapper();
            return mapper.readValue(body, modelClass);
        } catch (JsonProcessingException exception) {
            throw new RuntimeException(exception);
        }
    }
}
