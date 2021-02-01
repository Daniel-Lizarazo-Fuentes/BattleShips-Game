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
import game.players.humanPlayer;
import game.server.ProtocolMessages;

public class ClientHandler implements Runnable {
    private Board board;
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

//    public String getMove() {
//
//
////TODO edit so plyaer that has turn and scores are sent
//        String[] input = this.move.split(";");
//        this.move = null;
//        if (input[0].equals(ProtocolMessages.MOVE)) {
//            return input[1];
//        }
//
//        return null;
//    }


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
                    String boardString = msgSplit[1];
                    toBoard(boardString);
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

    public Board toBoard(String boardString) {
        Board result = new Board(false);
        String lines[] = boardString.split("\\r?\\n");
        String[] boardRepresentation = lines[12].split(":");
        String[] boardPositions = boardRepresentation[1].split(",");
        for (String boardPositionString : boardPositions) {
            String[] boardPositionArray = boardPositionString.split(";");

            boardPosition bp = new boardPosition(boardPositionArray[0], boardPosition.positionState.valueOf(boardPositionArray[1]), Boolean.parseBoolean(boardPositionArray[2]));
            bp.setShipType(boardPositionArray[3]);
            bp.setIsHit(Boolean.parseBoolean(boardPositionArray[4]));
            result.getFields().set(result.getFieldIndex(boardPositionArray[0]), bp);

        }
        return result;
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
