package game.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import game.Game;
import game.server.ProtocolMessages;

public class ClientHandler implements Runnable {
    private BufferedReader in;
    private BufferedWriter out;
    private Socket sock;
    private Server srv;
    private String name;
    public boolean turn;
    private boolean ready;
    private String move;
    private Game game;

    public ClientHandler(Socket sock, Server srv) {
        try {
            in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
            this.sock = sock;
            this.srv = srv;
            turn = false;
            ready = false;
            move = null;
        } catch (IOException e) {
            shutdown();
        }
    }

    public String getName() {
        return this.name;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return this.game;
    }

    public Socket getSock() {
        return this.sock;
    }

    public BufferedWriter getOut() {
        return this.out;
    }

    public String[] getMove() {
        writeOut(ProtocolMessages.TURN);
        while (move.isEmpty()) {

            String[] input = this.move.split(";");
            this.move = null;
            if (input[0] == ProtocolMessages.SALVO && input.length >= 5 && input.length <= 9) {
                return input;
            }
        }
        return null;
    }

    public boolean isReady() {
        return this.ready;
    }


    @Override
    public void run() {
        String msg;
        try {
            msg = in.readLine();
            while (msg != null) {
                System.out.println("[" + name + "] " + msg);
                handleCommand(msg);
                msg = in.readLine();
            }
            shutdown();
        } catch (IOException e) {
            shutdown();
        }
    }

    private void handleCommand(String msg) throws IOException {
        //TODO
    }

    public void writeOut(String msg) {
        try {
            out.write(msg);
            out.newLine();
            out.flush();
        } catch (IOException e) {
            System.out.println("> [" + name + "] ERROR: could not send write a message out.");
        }
    }

    private void shutdown() {
        System.out.println("> [" + name + "] Shutting down.");
        try {
            in.close();
            out.close();
            sock.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        srv.removeClient(this);
    }

}
