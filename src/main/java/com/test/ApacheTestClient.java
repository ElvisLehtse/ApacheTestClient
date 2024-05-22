package com.test;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * This class initializes the Request class and handles
 * exceptions thrown from the Request class.
 * While loop cannot be exited here.
 * User can still exit the program via exit command
 * in the UserInput class.
 */
public class ApacheTestClient
{
    @SuppressWarnings("InfiniteLoopStatement")
    public static void main( String[] args ) {
        while (true) {
            Request request = new Request();
            try {
                request.SendChoice();
            } catch (IOException | URISyntaxException e) {
                System.out.println(STR."\{e.getMessage()} Could not send request");
            }
        }
    }
}
