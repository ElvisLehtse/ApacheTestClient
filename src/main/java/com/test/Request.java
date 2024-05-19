package com.test;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Request {
    private final CloseableHttpClient httpClient = HttpClients.createDefault();
    // List to store user input
    private List<String> userInput = new ArrayList<>();

    /**
     * Sends a user-defined choice to the server.
     */
    void SendChoice () throws IOException {
        System.out.println("What would you like to do?");
        System.out.println("read / add / modify / delete / reset / exit");
        UserInput userInput = new UserInput();
        this.userInput = userInput.userChoice();
        switch (this.userInput.getFirst()) {
            case "read" -> sendGet(httpClient);
            case "add", "modify", "delete", "reset" -> sendPost(httpClient);
        }
        httpClient.close();
    }

    public void sendGet (CloseableHttpClient httpClient) throws IOException {
        String url = "http://localhost:8080/GET";
        HttpGet getReq = new HttpGet(url);

        CloseableHttpResponse response = httpClient.execute(getReq);
        HttpEntity entity = response.getEntity();
        String responseMessage = EntityUtils.toString(entity);
        System.out.println(responseMessage);
    }

    public void sendPost (CloseableHttpClient httpClient) throws IOException {
        String url = "http://localhost:8080/POST";
        HttpPost postReq = new HttpPost(url);

        // POST message body
        StringBuilder input = new StringBuilder();
        for (String line : userInput) {
            input.append(line);
            input.append("\n");
        }
        String userLine = input.toString();
        HttpEntity messageEntity = new ByteArrayEntity(userLine.getBytes());
        postReq.setEntity(messageEntity);

        CloseableHttpResponse response = httpClient.execute(postReq);
        // Reply body from server
        HttpEntity responseEntity = response.getEntity();
        String responseMessage = EntityUtils.toString(responseEntity);
        System.out.println(responseMessage);

    }
}
