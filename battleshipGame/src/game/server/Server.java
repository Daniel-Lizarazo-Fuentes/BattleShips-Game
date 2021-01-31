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
    private Player p1;
    private Player p2;
    private int playerCount;
    private ArrayList<Player> ready;
    private Game game;
    public boolean wait = false;
    public String result = null;

    private ArrayList<ClientHandler> waitingList = new ArrayList<>();
    private ArrayList<ClientHandler> readyList = new ArrayList<>();
    private ArrayList<Game> gameList = new ArrayList<>();
    private int gameSize = 0;
    private String address;
    private int port;

    public Server(String address, int port) {
        clients = new ArrayList<>();
        TUI = new ServerTUI();
        p1 = null;
        p2 = null;

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

    public void initGame(ArrayList<Player> ready2) {
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

    public Player getP1() {
        return this.p1;
    }

    public void setP1(String name) {
        this.p1 = new humanPlayer(name);//TODO edit stuff so human player doesn't need nested arraylists
    }

    public Player getP2() {
        return this.p2;
    }

    public void setP2(String name) {
        this.p2 = new humanPlayer(name);//TODO edit stuff so human player doesn't need nested arraylists
    }


    @Override
    public synchronized void run() {
//TODO
    }

    public void createGame() {
        ArrayList<ClientHandler> players = new ArrayList<ClientHandler>();
        players.addAll(gameList);
        (new Thread(new Game(players))).start(); //TODO make additional constructor in game for multiplayer
        gameSize = 0;
        gameList.clear();
        TUI.showMessage("New game created.");
    }

    public void addToReadyList(ClientHandler ch) {
        if (waitingList.contains(ch)) {
            readyList.add(ch);
            waitingList.remove(ch);
        }
    }

    public void sendAll(String[] move, ClientHandler ch) {
        String part1 = ProtocolMessages.MOVE + ProtocolMessages.CS;
        int number = 0;
        for (Player p : ready) {
            number++;
            if (ch.getName().equals(p.getName())) {
                part1 += number;
                break;
            }
        }
        for (int i = 1; i < move.length; i++) {
            part1 += ";" + move[i];
        }
        for (ClientHandler c : clients) {
            c.writeOut(part1);
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

    public void printMessage(String msg) {
        System.out.println(msg);
    }

    public int getGameSize() {
        return this.playerCount;
    }

    synchronized public void setGameSize(int gs) {
        this.gameSize = gs;
        notify();
    }
}
