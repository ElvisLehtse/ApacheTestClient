package com.test;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

public class RequestCreater {

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
        String userInput = "test";
        HttpEntity messageEntity = new ByteArrayEntity(userInput.getBytes());
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
}
