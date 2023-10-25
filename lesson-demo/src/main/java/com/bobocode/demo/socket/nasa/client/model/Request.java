package com.bobocode.demo.socket.nasa.client.model;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Request {

    private final Method method;

    private final URL url;

    private final Map<String, String> headers = new HashMap<>();

    private final String payload;

    private Request(Method method, URL url, String payload) {
        this.method = method;
        this.url = url;
        this.payload = payload;
    }

    public static Request of(Method method, String url) {
        try {
            return new Request(method, new URL(url), null);
        } catch (MalformedURLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public Method getMethod() {
        return method;
    }

    public URL getUrl() {
        return url;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getPayload() {
        return payload;
    }

    public void addHeader(String key, String value) {
        this.headers.put(key, value);
    }
}
