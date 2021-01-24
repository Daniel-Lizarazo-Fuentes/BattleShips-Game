package game;

import java.net.*;
import java.io.*;

public class Server {
    public static void main(String args[]) throws Exception {
        ServerSocket ss = new ServerSocket(5000);
        Socket s = ss.accept();
        System.out.println("client connected");

        InputStreamReader in = new InputStreamReader(s.getInputStream());
        BufferedReader bf = new BufferedReader(in);

        String receivedMessage = bf.readLine();
        System.out.println("Client said: "+receivedMessage);

        PrintWriter pr = new PrintWriter(s.getOutputStream());
        pr.println("yes");
        pr.flush();
    }
}