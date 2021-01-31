package game.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

import game.Game;
import game.players.humanPlayer;
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
//TODO edit so plyaer that has turn and scores are sent
            String[] input = this.move.split(";");
            this.move = null;
            if (input[0] == ProtocolMessages.MOVE && input.length >= 5 && input.length <= 9) {
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
        if (msg != null && !msg.isEmpty()) {
            String[] msgSplit = msg.split(ProtocolMessages.CS);
            switch (msgSplit[0]) {

                case ProtocolMessages.JOIN:
                    if (msgSplit[1] != null) {
                        if (!srv.getPlayerNames().contains(msgSplit[1])) {
                            srv.addPlayerName(msgSplit[1]);
                            this.name = msgSplit[1];
                            for (Game game : srv.getGameList()) {
                                if (game.getGamePlayer(1) == null) {
                                    game.addPlayer(new humanPlayer(msgSplit[1], game.getBoard()[1]));
                                }
                            }
                            writeOut(ProtocolMessages.SUCCESS);
                            ready = false;
                        } else {
                            writeOut(ProtocolMessages.FAIL + ProtocolMessages.CS + "Name already taken");
                        }
                    }
                    break;

                case ProtocolMessages.PLAY:
                    try {
                        int numberOfPlayers = Integer.parseInt(msgSplit[1]);
                        if (numberOfPlayers == 1) {

                            ArrayList<ClientHandler> singlePlayerList = new ArrayList<>();
                            singlePlayerList.add(this);
                            Game singlePlayerGame = new Game(singlePlayerList);
                            new Thread(singlePlayerGame).start();
                            srv.getGameList().add(singlePlayerGame);

                        } else if (numberOfPlayers == 2) {

                            ArrayList<ClientHandler> multiPlayerList = new ArrayList<>();
                            multiPlayerList.add(this);
                            Game multiPlayerGame = new Game(multiPlayerList);
                            srv.getGameList().add(multiPlayerGame);

                        } else {
                            writeOut("Enter amount (1 for Singleplayer or 2 for Multiplayer)");
                        }
                    } catch (NumberFormatException e) {
                        writeOut("Enter valid integer (1 or 2)");
                    }
                    break;

                case ProtocolMessages.MOVE:
                    this.game.setMove(msgSplit[1]);
                    break;
                case ProtocolMessages.DEPLOY:
                    //TODO prob no implementation
                    break;
                case ProtocolMessages.RADAR:
                    //TODO prob no implementation
                    break;
                case ProtocolMessages.GET:
                    //TODO prob no implementation
                    break;
            }
        }
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
