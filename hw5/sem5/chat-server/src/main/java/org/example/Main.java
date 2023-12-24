package org.example;

import java.io.IOException;
import java.net.ServerSocket;

public class Main {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(1414);
            Server server = new Server(serverSocket);
            server.runServer();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Start Server");
    }
}