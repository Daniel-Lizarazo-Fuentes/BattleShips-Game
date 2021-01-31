package game.server;


import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.net.InetAddress;

import game.players.*;
import game.Game;

import game.exceptions.*;
import game.server.ProtocolMessages;

public class Server implements Runnable {
    private ServerSocket serverSock;
    private ArrayList<String> playerNames = new ArrayList<>();
    private ArrayList<ClientHandler> clients;
    private ServerTUI TUI;
    private static final String SERVERNAME = "BIT BattleShip Server";

    private ArrayList<Player> ready;
    private Game game;


    private int playerCount;
    public boolean wait = false;
    public String result = null;

    private ArrayList<ClientHandler> waitingList = new ArrayList<>();
    private ArrayList<ClientHandler> readyList = new ArrayList<>();
    private ArrayList<Game> gameList = new ArrayList<>();

    private String address;
    private int port;

    public Server(String address, int port) {
        clients = new ArrayList<>();
        TUI = new ServerTUI();


        ready = new ArrayList<Player>();
        game = null;
        playerCount = 1;
        this.address = address;
        this.port = port;
    }

    public ArrayList<String> getPlayerNames() {
        return this.playerNames;
    }

    public void addPlayerName(String name) {
        this.playerNames.add(name);

    }

    public void initGame(ArrayList<Player> ready) {

    }

    public String getServerName() {
        return SERVERNAME;
    }

    public Game getGame() {
        return this.game;
    }

    public ArrayList<Game> getGameList() {
        return this.gameList;
    }


    @Override
    public synchronized void run() {
        boolean openNewSocket = true;
        while (openNewSocket) {
            setup();
            while (true) {
                connect();
              //  startGame();
            }
        }
        TUI.showMessage("See you later!");
    }

//    public void startGame() {
//        ArrayList<Game> players = new ArrayList<>();
//        players.addAll(gameList);
//        for (Game game : gameList) {
//            if (game.getGamePlayer(0) != null && game.getGamePlayer(1) != null) {
//                gameList.clear();
//                TUI.showMessage("New game created.");
//            }
//        }
//
//    }

    public void addToReadyList(ClientHandler ch) {
        if (waitingList.contains(ch)) {
            readyList.add(ch);
            waitingList.remove(ch);
        }
    }

    public void connect() {
        Socket sock;
        try {
            sock = serverSock.accept();
            TUI.showMessage("[Server] New player connected!");
            ClientHandler handler = new ClientHandler(sock, this);
            new Thread(handler).start();
            waitingList.add(handler);

        } catch (IOException e) {
        }
    }

    public void setup() {
        serverSock = null;
        while (serverSock == null) {
            try {
                TUI.showMessage("Attempting to open a socket at " + address + " on port " + port + "...");
                serverSock = new ServerSocket(port, 0, InetAddress.getByName(address));
                serverSock.setSoTimeout(100);
                TUI.showMessage("Server started at port " + port);
            } catch (IOException e) {
                TUI.showMessage("ERROR: could not create a socket on address " + address + " and port " + port + ".");
            }
        }
    }

    public void removeClient(ClientHandler client) {
        this.clients.remove(client);
    }

    public void getReady(Player player) {
        ready.add(player);
    }

    public boolean inList(Player player) {
        if (ready.contains(player)) {
            return true;
        }
        return false;
    }


}
