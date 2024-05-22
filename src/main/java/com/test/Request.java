package com.test;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * This class sends out the user requests and receives server response.
 */
public class Request {
    private final CloseableHttpClient httpClient = HttpClients.createDefault();
    private JSONObject jsonObject = new JSONObject();
    private final UserInput userInput = new UserInput();
    private final String url = "http://localhost:8080/";

    /**
     * Calls a request specified by the user.
     */
    public void SendChoice () throws IOException, URISyntaxException {
        System.out.println("What would you like to do?");
        System.out.println("read / add / modify / delete / reset / exit");
        jsonObject = userInput.userJson();
        switch (userInput.getUserChoice()) {
            case "read" -> sendGet();
            case "add" -> sendPost();
            case "modify" -> sendPutModify();
            case "reset" -> sendPutReset();
            case "delete" -> sendDelete();
        }
        httpClient.close();
    }

    /**
     * This method is used to receive and print out the server response
     * by all requests.
     */

    private void responseMessage (CloseableHttpResponse response) throws IOException {
        HttpEntity entity = response.getEntity();
        String responseMessage = EntityUtils.toString(entity);
        System.out.println(responseMessage);
    }

    private void sendGet () throws IOException {
        HttpGet request = new HttpGet(url);

        CloseableHttpResponse response = httpClient.execute(request);
        responseMessage(response);
    }

    private void sendPost () throws IOException {
        HttpPost request = new HttpPost(url);
        HttpEntity messageEntity = new StringEntity(String.valueOf(jsonObject), ContentType.APPLICATION_JSON);
        request.setEntity(messageEntity);

        CloseableHttpResponse response = httpClient.execute(request);
        responseMessage(response);
    }

    private void sendPutModify () throws IOException, URISyntaxException {
        URIBuilder builder = new URIBuilder(url);
        builder.setParameter("modify", userInput.getOldWeapon());
        HttpPut request = new HttpPut(builder.build());
        HttpEntity messageEntity = new StringEntity(String.valueOf(jsonObject), ContentType.APPLICATION_JSON);
        request.setEntity(messageEntity);

        CloseableHttpResponse response = httpClient.execute(request);
        responseMessage(response);
    }

    private void sendPutReset () throws IOException, URISyntaxException {
        URIBuilder builder = new URIBuilder(url);
        builder.setParameter("reset", "");
        HttpPut request = new HttpPut(builder.build());

        CloseableHttpResponse response = httpClient.execute(request);
        responseMessage(response);
    }

    private void sendDelete () throws IOException, URISyntaxException {
        URIBuilder builder = new URIBuilder(url);
        builder.setParameter("delete", userInput.getOldWeapon());
        HttpDelete request = new HttpDelete(builder.build());

        CloseableHttpResponse response = httpClient.execute(request);
        responseMessage(response);
    }
}
