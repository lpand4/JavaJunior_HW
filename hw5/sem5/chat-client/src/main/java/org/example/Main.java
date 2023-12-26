package org.example;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Введите Ваше имя: ");
            String name = scanner.nextLine();
            Socket socket = new Socket("localhost", 1414);

            Client client = new Client(socket, name);

            InetAddress inetAddress = socket.getInetAddress();
            System.out.println("InetAdress: " + inetAddress);
            String remoteIp = inetAddress.getHostAddress();
            System.out.println("Remote IP: " + remoteIp);
            System.out.println("LocalPort: " + socket.getLocalPort());
            System.out.println("Чтобы увидеть пользователей онлайн напишите *all");
            System.out.println("Чтобы отправить сообщение определенному пользователю, напишите @Ник");


            client.listenForMessage();
            client.sendMessage();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}