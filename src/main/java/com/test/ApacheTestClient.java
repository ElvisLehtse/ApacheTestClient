package com.test;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import java.io.IOException;

public class ApacheTestClient
{
    private final CloseableHttpClient httpClient = HttpClients.createDefault();
    public static void main( String[] args ) throws IOException {

        ApacheTestClient obj = new ApacheTestClient();
        RequestCreater requestCreater = new RequestCreater();

        requestCreater.sendGet(obj.httpClient);
        requestCreater.sendPost(obj.httpClient);
        obj.close();
    }
    private void close() throws IOException {
        httpClient.close();
    }
}
