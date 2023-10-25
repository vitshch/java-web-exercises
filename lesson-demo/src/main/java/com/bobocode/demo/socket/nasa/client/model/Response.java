package com.bobocode.demo.socket.nasa.client.model;

import java.util.HashMap;
import java.util.Map;

public class Response<T> {

    private final Map<String, String> headers = new HashMap<>();

    private final T body;

    public Response(T body) {
        this.body = body;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public T getBody() {
        return body;
    }
}
