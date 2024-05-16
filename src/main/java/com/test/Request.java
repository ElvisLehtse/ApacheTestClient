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
    // Map to store user input
    private List<String> userInput = new ArrayList<>();

    /**
     * Sends a user-defined choice to the server.
     *
     * @return True if the client should continue running, false if it should terminate.
     */
    void SendChoice () {
        System.out.println("What would you like to do?");
        System.out.println("read / add / modify / delete / reset / exit");
        UserInput userInput = new UserInput();
        this.userInput = userInput.userChoice();
        try {
            switch (this.userInput.getFirst()) {
                case "read" -> sendGet(httpClient);
                case "add", "modify", "delete", "reset" -> sendPost(httpClient);
                case "exit" -> close();
            }
        } catch (Exception e) {
            System.out.println(STR."\{e.getMessage()} Error closing http client");
        }
    }

    public void sendGet (CloseableHttpClient httpClient) {

        String url = "http://localhost:8080/GET";
        HttpGet request = new HttpGet(url);

        try {
            CloseableHttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();
            String responseMessage = EntityUtils.toString(entity);
            System.out.println(responseMessage);

        } catch (Exception e) {
            System.out.println(STR."\{e.getMessage()} Could not send GET request to \{url}");
        }
    }

    public void sendPost (CloseableHttpClient httpClient) {

        String url = "http://localhost:8080/POST";
        HttpPost post = new HttpPost(url);

        // POST message body
        StringBuilder input = new StringBuilder();
        for (String line : userInput) {
            input.append(line).append("\n");
        }
        String userLine = input.toString();
        HttpEntity messageEntity = new ByteArrayEntity(userLine.getBytes());
        post.setEntity(messageEntity);

        try {
            CloseableHttpResponse response = httpClient.execute(post);
            // Reply body from server
            HttpEntity responseEntity = response.getEntity();
            String responseMessage = EntityUtils.toString(responseEntity);
            System.out.println(responseMessage);

        } catch (Exception e){
            System.out.println(STR."\{e.getMessage()} Could not send POST request to \{url}");
        }
    }
    private void close() throws IOException {
        httpClient.close();
    }
}
