package game.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

import game.Game;
import game.board.Board;
import game.board.boardPosition;
import game.ships.*;

public class ClientHandler implements Runnable {
    private Board board;
    private ArrayList<ArrayList<? extends Ship>> shipLists;

    private BufferedReader in;
    private BufferedWriter out;
    private Socket sock;
    private Server srv;
    private String name;
    public boolean turn;


    private Game game;

    public ClientHandler(Socket sock, Server srv) {
        try {
            in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
            this.sock = sock;
            this.srv = srv;
            turn = false;


        } catch (IOException e) {
            shutdown();
        }
    }

    public Board getBoard() {
        return this.board;
    }


    public ArrayList<ArrayList<? extends Ship>> getShipLists() {
        return this.shipLists;
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
//        System.out.println("handlecommand called");
        if (msg != null && !msg.isEmpty()) {
            String[] msgSplit = msg.split(ProtocolMessages.CS);
            switch (msgSplit[0]) {

                case ProtocolMessages.JOIN:
                    //   System.out.println("after join read");
                    if (msgSplit[1] != null) {
                        if (!srv.getPlayerNames().contains(msgSplit[1])) {
                            srv.addPlayerName(msgSplit[1]);
                            this.name = msgSplit[1];
                            boolean hasRunGame = false;
                            for (Game game : srv.getGameList()) {
                                if (game.doesStillNeedPlayers()) {

                                    game.addClientHandler(this);
                                    writeOut(ProtocolMessages.SUCCESS);
                                    hasRunGame = true;
                                    new Thread(game).start();

                                }
                            }
                            if (!hasRunGame) {
                                writeOut(ProtocolMessages.SUCCESS);
                            }

//                            ready = true;
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
                            new Thread(singlePlayerGame).start(); //TODO
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
                    String boardString = msgSplit[1];
                    this.board = gridToBoard(boardString);
                    this.shipLists = gridToShips(boardString);
                    writeOut(ProtocolMessages.READY);

                    synchronized (this) {
                        this.notifyAll();
                    }

                    //   toBoard(boardString);
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

    public ArrayList<ArrayList<? extends Ship>> gridToShips(String grid) {
        ArrayList<String> positions = new ArrayList<>();
        ArrayList<ArrayList<? extends Ship>> result = new ArrayList<>();

        ArrayList<Carrier> carriers = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            carriers.add(new Carrier("c" + i, positions));
        }
        ArrayList<Battleship> battleships = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            battleships.add(new Battleship("b" + i, positions));
        }
        ArrayList<Destroyer> destroyers = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            destroyers.add(new Destroyer("d" + i, positions));
        }
        ArrayList<SuperPatrol> superPatrols = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            superPatrols.add(new SuperPatrol("s" + i, positions));
        }
        ArrayList<PatrolBoat> patrolBoats = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            patrolBoats.add(new PatrolBoat("p" + i, positions));
        }

        result.add(carriers);
        result.add(battleships);
        result.add(destroyers);
        result.add(superPatrols);
        result.add(patrolBoats);


        String[] rows = grid.split(":");

        int rowIndex = 0;
        int collumIndex = 0;
        char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();

        for (String row : rows) {
            String[] fields = row.split(",");
            for (String field : fields) {
                if (!field.equals("")) {
                    String coordinate = alphabet[collumIndex] + Integer.toString(rowIndex);
                    switch (field.charAt(0)) {
                        case 'c':
                            if (field.length() == 3) {
                                //get carrierList, then carrier with two characters (number), the get is positions and add the coordinate
                                result.get(0).get((Character.getNumericValue(field.charAt(1) + field.charAt(2)))).getPositions().add(coordinate);
                            } else if (field.length() == 2) {
                                //get carrierList, then carrier with character (number), the get is positions and add the coordinate
                                result.get(0).get(Character.getNumericValue(field.charAt(1))).getPositions().add(coordinate);
                            } else {
                                writeOut("Something went wrong when getting the ships from the grid");
                            }
                            break;
                        case 'b':
                            if (field.length() == 3) {
                                //get carrierList, then carrier with two characters (number), the get is positions and add the coordinate
                                result.get(1).get((Character.getNumericValue(field.charAt(1) + field.charAt(2)))).getPositions().add(coordinate);
                            } else if (field.length() == 2) {
                                //get carrierList, then carrier with character (number), the get is positions and add the coordinate
                                result.get(1).get(Character.getNumericValue(field.charAt(1))).getPositions().add(coordinate);
                            } else {
                                writeOut("Something went wrong when getting the ships from the grid");
                            }
                            break;
                        case 'd':
                            if (field.length() == 3) {
                                //get carrierList, then carrier with two characters (number), the get is positions and add the coordinate
                                result.get(2).get((Character.getNumericValue(field.charAt(1) + field.charAt(2)))).getPositions().add(coordinate);
                            } else if (field.length() == 2) {
                                //get carrierList, then carrier with character (number), the get is positions and add the coordinate
                                result.get(2).get(Character.getNumericValue(field.charAt(1))).getPositions().add(coordinate);
                            } else {
                                writeOut("Something went wrong when getting the ships from the grid");
                            }
                            break;
                        case 's':
                            if (field.length() == 3) {
                                //get carrierList, then carrier with two characters (number), the get is positions and add the coordinate
                                result.get(3).get((Character.getNumericValue(field.charAt(1) + field.charAt(2)))).getPositions().add(coordinate);
                            } else if (field.length() == 2) {
                                //get carrierList, then carrier with character (number), the get is positions and add the coordinate
                                result.get(3).get(Character.getNumericValue(field.charAt(1))).getPositions().add(coordinate);
                            } else {
                                writeOut("Something went wrong when getting the ships from the grid");
                            }
                            break;
                        case 'p':
                            if (field.length() == 3) {
                                //get carrierList, then carrier with two characters (number), the get is positions and add the coordinate
                                result.get(4).get((Character.getNumericValue(field.charAt(1) + field.charAt(2)))).getPositions().add(coordinate);
                            } else if (field.length() == 2) {
                                //get carrierList, then carrier with character (number), the get is positions and add the coordinate
                                result.get(4).get(Character.getNumericValue(field.charAt(1))).getPositions().add(coordinate);
                            } else {
                                writeOut("Something went wrong when getting the ships from the grid");
                            }
                            break;

                    }

                    collumIndex++;
                }
            }
            collumIndex = 0;
            rowIndex++;
        }
        return result;
    }


    public Board gridToBoard(String grid) {
        Board result = new Board(false);
        String[] rows = grid.split(":");

        //System.out.println(result.getFields().size());

        int rowIndex = 0;
        int collumIndex = 0;
        char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();

        for (String row : rows) {
            String[] fields = row.split(",");
            for (String field : fields) {
                if (!field.equals("")) {
                    String coordinate = alphabet[collumIndex] + Integer.toString(rowIndex);
                    result.getFields().get(result.getFieldIndex(coordinate)).setShipType(field);
                    result.getFields().get(result.getFieldIndex(coordinate)).setState(boardPosition.positionState.SHIP);
                }
                collumIndex++;
            }
            collumIndex = 0;
            rowIndex++;
        }
        return result;
    }

//    public Board toBoard(String boardString) {
//        Board result = new Board(false);
//        String lines[] = boardString.split("\\r?\\n");
//        String[] boardRepresentation = lines[12].split(":");
//        String[] boardPositions = boardRepresentation[1].split(",");
//        for (String boardPositionString : boardPositions) {
//            String[] boardPositionArray = boardPositionString.split(";");
//            boardPosition bp = new boardPosition(boardPositionArray[0], boardPosition.positionState.valueOf(boardPositionArray[1]), Boolean.parseBoolean(boardPositionArray[2]));
//            bp.setShipType(boardPositionArray[3]);
//            bp.setIsHit(Boolean.parseBoolean(boardPositionArray[4]));
//            result.getFields().set(result.getFieldIndex(boardPositionArray[0]), bp);
//
//        }
//        return result;
//    }

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
