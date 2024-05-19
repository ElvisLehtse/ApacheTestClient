package com.test;

import java.io.IOException;

public class ApacheTestClient
{
    @SuppressWarnings("InfiniteLoopStatement")
    public static void main( String[] args ) {
        while (true) {
            Request obj = new Request();
            try {
                obj.SendChoice();
            } catch (IOException e) {
                System.out.println(STR."\{e.getMessage()} Could not send request");
            }
        }
    }
}
