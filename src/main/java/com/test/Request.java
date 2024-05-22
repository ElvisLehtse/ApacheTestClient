package com.test;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import java.io.IOException;
import java.net.URISyntaxException;


public class Request {
    private final CloseableHttpClient httpClient = HttpClients.createDefault();
    private JSONObject jsonObject = new JSONObject();
    private final UserInput userInput = new UserInput();
    private String modifyOrReset;

    /**
     * Sends a user-defined choice to the server.
     */
    void SendChoice () throws IOException, URISyntaxException {
        System.out.println("What would you like to do?");
        System.out.println("read / add / modify / delete / reset / exit");
        jsonObject = userInput.userJson();
        switch (userInput.getUserChoice()) {
            case "read" -> sendGet();
            case "add" -> sendPost();
            case "modify" -> {
                modifyOrReset = "modify";
                sendPut();
            }
            case "reset" -> {
                modifyOrReset = "reset";
                sendPut();
            }
            case "delete" -> sendDelete();
        }
        httpClient.close();
    }

    public void responseMessage (CloseableHttpResponse response) throws IOException {
        HttpEntity entity = response.getEntity();
        String responseMessage = EntityUtils.toString(entity);
        System.out.println(responseMessage);
    }

    public void sendGet () throws IOException {
        String url = "http://localhost:8080/GET";
        HttpGet request = new HttpGet(url);
        CloseableHttpResponse response = httpClient.execute(request);
        responseMessage(response);

    }

    public void sendPost () throws IOException {
        String url = "http://localhost:8080/POST";
        HttpPost request = new HttpPost(url);
        HttpEntity messageEntity = new StringEntity
                (String.valueOf(jsonObject), ContentType.APPLICATION_JSON);
        request.setEntity(messageEntity);

        CloseableHttpResponse response = httpClient.execute(request);
        responseMessage(response);
    }

    public void sendPut () throws IOException, URISyntaxException {
        URIBuilder builder = new URIBuilder("http://localhost:8080/PUT");
        try {
            if (modifyOrReset.equals("modify")) {
                builder.setParameter("modify", userInput.getOldWeapon());
            } else if (modifyOrReset.equals("reset")) {
                builder.setParameter("reset", "all");
            }
            HttpPost request = new HttpPost(builder.build());

            if (!modifyOrReset.equals("reset")) {
                HttpEntity messageEntity = new StringEntity
                        (String.valueOf(jsonObject), ContentType.APPLICATION_JSON);
                request.setEntity(messageEntity);
            }
            CloseableHttpResponse response = httpClient.execute(request);
            responseMessage(response);

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

    }

    public void sendDelete () throws IOException, URISyntaxException {
        URIBuilder builder = new URIBuilder("http://localhost:8080/DELETE");
        builder.setParameter("delete", userInput.getOldWeapon());
        HttpPost request = new HttpPost(builder.build());

        CloseableHttpResponse response = httpClient.execute(request);
        responseMessage(response);
    }
}
