package game.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.InputMismatchException;
import java.util.Scanner;

import game.exceptions.ExitProgram;
import game.exceptions.ServerUnavailableException;
import game.server.ProtocolMessages;

public class Client {
    private Socket clientSock;
    private BufferedReader in;
    private BufferedWriter out;
    private ClientTUI TUI;

    public Client() {
        TUI = new ClientTUI(this);
    }


    public void start() {
        try {
            createConnection();
            while (true) {
                String name = TUI.getNameQuestion();
                handleJoin(name);
                this.playersReady();
                TUI.start();
                TUI.showMessage("Would you like to play another game? (yes/no)");

                Scanner scnr = new Scanner(System.in);
                if (!scnr.nextLine().equals("yes")) {
                    closeConnection();
                    return;
                }
            }
        } catch (ExitProgram e) {
            System.out.println(e.getMessage());
            return;
        } catch (ServerUnavailableException e) {
            System.out.println("Server unavailable at the moment");
            return;
        }
    }

    public void createConnection() throws ExitProgram {
        clearConnection();
        while (clientSock == null) {

            Scanner scnr = new Scanner(System.in);
            TUI.showMessage("What is the address of the Server?");
            String host = scnr.nextLine();
            TUI.showMessage("What is the port of the server?");
            int port = 0;

            try {
                port = scnr.nextInt();
                InetAddress addr = InetAddress.getByName(host);
                System.out.println("Attempting to connect to " + addr + ":" + port + "...");
                clientSock = new Socket(addr, port);
                in = new BufferedReader(new InputStreamReader(clientSock.getInputStream()));
                out = new BufferedWriter(new OutputStreamWriter(clientSock.getOutputStream()));
                System.out.println("Connected!\n----------------------------------------------------");
            } catch (IOException | InputMismatchException e) {
                System.out.println("ERROR: could not create a socket on " + host + " and port " + port);
                System.out.println("Do you want to try again? (yes/no)");
                scnr.nextLine();
                if (!scnr.nextLine().equals("yes")) {
                    throw new ExitProgram("Exiting...");
                }
            }
        }
    }

    public void clearConnection() {
        clientSock = null;
        in = null;
        out = null;
    }

    public synchronized void sendMessage(String msg) throws ServerUnavailableException {
        if (out != null) {
            try {
                out.write(msg);
                out.newLine();
                out.flush();
            } catch (IOException e) {
                System.out.println(e.getMessage());
                throw new ServerUnavailableException("Could not write to server.");
            }
        } else {
            throw new ServerUnavailableException("Could not write to server.");
        }
    }

    public String readLineFromServer() throws ServerUnavailableException {
        if (in != null) {
            try {
                // Read and return answer from Server
                String answer = in.readLine();
                if (answer == null) {
                    throw new ServerUnavailableException("Could not read from server.");
                }
                return answer;
            } catch (IOException e) {
                throw new ServerUnavailableException("Could not read from server.");
            }
        } else {
            throw new ServerUnavailableException("Could not read from server.");
        }
    }

    public void closeConnection() {
        TUI.showMessage("Closing the connection...");
        try {
            in.close();
            out.close();
            clientSock.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleJoin(String playername) throws ServerUnavailableException {
        sendMessage(ProtocolMessages.JOIN + ProtocolMessages.CS + playername + ProtocolMessages.CS + "false" + ProtocolMessages.CS + "false");
        String result = readLineFromServer();
        if (result.equals(ProtocolMessages.SUCCESS)) {
            TUI.showMessage("Game preference saved!\n");

        }
        else if(result.split(";")[0].equals(ProtocolMessages.FAIL)){
            TUI.showMessage(result.split(";")[1]);
        }else {
            throw new ServerUnavailableException("Invalid input");
        }
    }


    public void playersReady() throws ServerUnavailableException {
        Scanner scnr = new Scanner(System.in);
        while (true) {
            TUI.showMessage("Are you ready? (type 'yes' when ready)");
            if (scnr.nextLine().equals("yes")) {
                sendMessage(ProtocolMessages.READY);
                TUI.showMessage("Great! Now we have to wait on the others!");
                return;
            }
        }
    }


    public static void main(String args[]) {
        (new Client()).start();

    }
}
