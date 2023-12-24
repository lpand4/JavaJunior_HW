package org.example;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientManager implements Runnable {
    private final Socket socket;

    public final static ArrayList<ClientManager> clients = new ArrayList<>();
    private BufferedWriter bw;
    private BufferedReader br;
    private String name;

    public ClientManager(Socket socket) {
        this.socket = socket;
        try {
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            name = br.readLine();
            clients.add(this);
            System.out.println(name + " подключился к чату!");
            broadcastMessage("Server: " + name + " подключился к чату!");
        } catch (IOException e) {
            closeEverything(socket, br, bw);
        }
    }


    @Override
    public void run() {
        String msgFromClient;
        try {
            while (socket.isConnected()) {
                msgFromClient = br.readLine();
                broadcastMessage(msgFromClient);
            }

        } catch (IOException e) {
            closeEverything(socket, br, bw);
        }

    }

    private void closeEverything(Socket socket, BufferedReader br, BufferedWriter bw) {

        removeClient();
        try {
            if (br != null)
                br.close();
            if (bw != null)
                bw.close();
            if (socket != null)
                socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void removeClient() {
        clients.remove(this);
        System.out.println(name + " покинул чат!");
        broadcastMessage("Server: " + name + " покинул чат!");
    }

    private void broadcastMessage(String msg) {
        for (ClientManager c : clients) {
            try {
                if (!c.name.equals(name)) {
                    c.bw.write(msg);
                    c.bw.newLine();
                    c.bw.flush();
                }
            } catch (IOException e) {
                closeEverything(socket, br, bw);
                break;
            }
        }


    }
}
