package org.example;

import java.awt.*;
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
                if (isRequestAllClients(msgFromClient)) {
                    sendListOnlineClients();
                } else if (isMessageForPersonal(msgFromClient)) {
                    sendPersonalMessage(msgFromClient);
                } else {
                    broadcastMessage(msgFromClient);
                }
            }

        } catch (IOException e) {
            closeEverything(socket, br, bw);
        }

    }

    private void sendListOnlineClients() {
        StringBuilder sb = new StringBuilder();
        int counter = 1;
        for (ClientManager c : clients) {
            if (!c.equals(this)) {
                sb.append(counter++).append(". ").append(c.name).append("\n");
            }
        }
        for (ClientManager c : clients) {
            try {
                if (c.equals(this)) {
                    c.bw.write(sb.toString());
                    c.bw.newLine();
                    c.bw.flush();
                }
            } catch (IOException e) {
                closeEverything(socket, br, bw);
                break;
            }
        }
    }

    private boolean isRequestAllClients(String msg) {
        String[] message = msg.split(": ");
        return message[1].startsWith("*all");
    }

    private boolean isMessageForPersonal(String msg) {
        String[] message = msg.split(": ");
        return message[1].charAt(0) == '@';
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

    private void sendPersonalMessage(String msg) {
        String[] msgAndName = parseMessage(msg);
        boolean isExistPerson = false;
        try {
            for (ClientManager c : clients) {
                if (c.name.equals(msgAndName[0])) {
                    isExistPerson = true;
                    c.bw.write(msgAndName[1]);
                    c.bw.newLine();
                    c.bw.flush();
                }
            }
            if (!isExistPerson){
                for (ClientManager c:clients){
                    if (c.equals(this)){
                        c.bw.write("Данного пользователя не существует!");
                        c.bw.newLine();
                        c.bw.flush();
                    }
                }
            }
        } catch (IOException e) {
            closeEverything(socket, br, bw);
        }
    }

    private String[] parseMessage(String msg) {
        StringBuilder sb = new StringBuilder();
        String[] result = new String[2];
        String[] temp = msg.split(" ");
        result[0] = temp[1].substring(1);
        for (int i = 0; i < temp.length; i++) {
            if (i != 1) {
                sb.append(temp[i]);
            } else {
                sb.append(" ");
            }
        }
        result[1] = sb.toString();
        return result;
    }

}
