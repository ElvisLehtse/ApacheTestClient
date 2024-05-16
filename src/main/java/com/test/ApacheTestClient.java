package com.test;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpMessage;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.FileEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

public class ApacheTestClient
{
    private final CloseableHttpClient httpClient = HttpClients.createDefault();

    private final String uri = "http://localhost:8080";
    public static void main( String[] args ) throws IOException {

        ApacheTestClient obj = new ApacheTestClient();
        obj.sendGet();
        obj.sendPost();
        obj.close();

    }
    private void close() throws IOException {
        httpClient.close();
    }
    private void sendGet(){

        HttpGet request = new HttpGet("http://localhost:8080/GET");

        try {
            CloseableHttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();
            String responseMessage = EntityUtils.toString(entity);
            System.out.println(responseMessage);


        } catch (Exception e) {
            System.out.println(e.getMessage() + "ERROR");
        }

    }

    private void sendPost() {

        HttpPost post = new HttpPost("http://localhost:8080/POST");
        String test = "test";
        HttpEntity entity = new ByteArrayEntity(test.getBytes());
        post.setEntity(entity);

        try {
            CloseableHttpResponse response = httpClient.execute(post);

            HttpEntity reply = response.getEntity();
            String responseMessage = EntityUtils.toString(reply);
            System.out.println(responseMessage);

        } catch (Exception e){
            System.out.println(e.getMessage() + "ERROR");
        }

    }
}
