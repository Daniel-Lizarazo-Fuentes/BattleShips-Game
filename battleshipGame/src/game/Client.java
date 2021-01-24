package game;

import java.net.*;
import java.io.*;

public class Client {
    public static void main(String args[]) throws Exception {
        Socket s = new Socket("localhost", 5000);
        PrintWriter pr = new PrintWriter(s.getOutputStream());
        pr.println("Connection is working");
        pr.flush();

        InputStreamReader in = new InputStreamReader(s.getInputStream());
        BufferedReader bf = new BufferedReader(in);

        String receivedMessage = bf.readLine();
        System.out.println("Server said: "+receivedMessage);

    }
}
