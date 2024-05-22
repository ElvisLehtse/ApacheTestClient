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

    /**
     * Sends a user-defined choice to the server.
     */
    void SendChoice () throws IOException, URISyntaxException {
        System.out.println("What would you like to do?");
        System.out.println("read / add / modify / delete / reset / exit");
        jsonObject = userInput.userJson();
        switch (userInput.getUserChoice()) {
            case "read" -> sendGet(httpClient);
            case "add" -> sendPost(httpClient);
            case "modify", "reset" -> {
                sendPut(httpClient);
            }
            case "delete" -> sendDelete(httpClient);
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
        HttpEntity messageEntity = new StringEntity
                (String.valueOf(jsonObject), ContentType.APPLICATION_JSON);
        postReq.setEntity(messageEntity);
        CloseableHttpResponse response = httpClient.execute(postReq);

        // Reply body from server
        HttpEntity responseEntity = response.getEntity();
        String responseMessage = EntityUtils.toString(responseEntity);
        System.out.println(responseMessage);
    }

    public void sendPut (CloseableHttpClient httpClient) throws IOException, URISyntaxException {
        URIBuilder builder = new URIBuilder("http://localhost:8080/PUT");
        builder.setParameter("modify", userInput.getOldWeapon());
        HttpPost putReq = new HttpPost(builder.build());

        // PUT message body
        HttpEntity messageEntity = new StringEntity
                (String.valueOf(jsonObject), ContentType.APPLICATION_JSON);
        putReq.setEntity(messageEntity);
        CloseableHttpResponse response = httpClient.execute(putReq);

        // Reply body from server
        HttpEntity responseEntity = response.getEntity();
        String responseMessage = EntityUtils.toString(responseEntity);
        System.out.println(responseMessage);

    }

    public void sendDelete (CloseableHttpClient httpClient) throws IOException, URISyntaxException {
        URIBuilder builder = new URIBuilder("http://localhost:8080/DELETE");
        builder.setParameter("delete", userInput.getOldWeapon());
        HttpPost deleteReq = new HttpPost(builder.build());

        CloseableHttpResponse response = httpClient.execute(deleteReq);

        // Reply body from server
        HttpEntity responseEntity = response.getEntity();
        String responseMessage = EntityUtils.toString(responseEntity);
        System.out.println(responseMessage);
    }
}
