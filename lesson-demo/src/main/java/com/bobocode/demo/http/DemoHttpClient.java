package com.bobocode.demo.http;

import org.springframework.web.client.RestTemplate;

public class DemoHttpClient {

    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();
        var user = new User("Vitalii", "Shcherban");
        String response = restTemplate.postForObject("http://93.175.203.215:8080/users/signin", user, String.class);

        System.out.println(response);
        User response2 = restTemplate.getForObject("http://93.175.203.215:8080/users/current", User.class);

        System.out.println(response2);
    }

    record User(String firstName, String lastName) {
    }

}
