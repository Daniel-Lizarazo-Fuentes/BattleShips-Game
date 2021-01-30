package game.server;

import java.util.InputMismatchException;
import java.util.Scanner;

import game.Game;
import game.exceptions.ExitProgram;

public class ClientTUI {
    private Client c;
    public boolean turn;
    private Game game;
    private int numOfPlayers;
    private int playerNumber;
    private String name;

    public ClientTUI(Client c) {
        this.c = c;
        turn = false;
    }

    public void start() throws game.exceptions.ServerUnavailableException {
        String userInput;
        try {
            while (true) {
                String input = c.readLineFromServer();
                if (input.equals(ProtocolMessages.TURN)) {
                    Scanner scnr = new Scanner(System.in);
                    showMessage("----------------------------------------------------");
                    showMessage("Input your salvo:");
                    userInput = scnr.nextLine();
                    handleSalvo(userInput);
                } else if (input.split(";")[0].equals(ProtocolMessages.NOT_VALID)) {
                    showMessage("Move not valid. try again!");
                } else if (input.split(";")[0].equals(ProtocolMessages.NUM_OF_PLAYERS)) {
                    showMessage("----------------------------------------------------");
                    String[] inputSplit = input.split(";");
                    if (inputSplit.length == 3) {
                        this.numOfPlayers = Integer.parseInt(inputSplit[1]);
                        this.playerNumber = Integer.parseInt(inputSplit[2]);
                        switch (playerNumber) {
                            case 1:
                                showMessage(
                                        "You, Player 1, are the color " + Marble.ANSI_BLUE + "BLUE" + Marble.ANSI_RESET);
                                break;
                            case 2:
                                showMessage("You, Player 2, are the color " + Marble.ANSI_RED + "RED" + Marble.ANSI_RESET);
                                break;
                            case 3:
                                showMessage(
                                        "You, Player 3, are the color " + Marble.ANSI_GREEN + "GREEN" + Marble.ANSI_RESET);
                                break;
                            case 4:
                                showMessage("You, Player 4, are the color " + Marble.ANSI_YELLOW + "YELLOW"
                                        + Marble.ANSI_RESET);
                                break;
                        }
                        showMessage("Type your moves as follows:\n" + "- One marble move: [row1],[col1];[dir]\n"
                                + "- Two marble move: [row1],[col1] [row2],[col2];[dir]\n"
                                + "- Three marble move: [row1],[col1] [row2],[col2] [row3],[col3];[dir]\n"
                                + "the rows and colums start from 0.\n"
                                + "The direction is one of the following: nw, sw, ne, se, w and e.\n"
                                + "When making a multimarble move, make sure the first marble you mention is the last marble to move!");
                        Player p1 = new Player(colors.BLUE, new MarbleSet(colors.BLUE, numOfPlayers), "1");
                        p1.getMarbleSet().setPlayer(p1);
                        Player p2 = new Player(colors.RED, new MarbleSet(colors.RED, numOfPlayers), "2");
                        p2.getMarbleSet().setPlayer(p2);
                        switch (numOfPlayers) {
                            case 1:
                            case 2:
                                game = new Game(p1, p2);
                                break;
                            case 3:
                                Player p3 = new Player(colors.GREEN, new MarbleSet(colors.GREEN, numOfPlayers), "3");
                                p3.getMarbleSet().setPlayer(p3);
                                game = new Game(p1, p2, p3);
                                break;
                            case 4:
                                p3 = new Player(colors.GREEN, new MarbleSet(colors.GREEN, numOfPlayers), "3");
                                p3.getMarbleSet().setPlayer(p3);
                                Player p4 = new Player(colors.YELLOW, new MarbleSet(colors.YELLOW, numOfPlayers), "4");
                                p4.getMarbleSet().setPlayer(p4);
                                game = new Game(p1, p2, p3, p4);
                                break;
                        }
                        showMessage(game.getBoard().toString());

                    }
                    input = null;
                } else if (input.split(";")[0].equals(ProtocolMessages.MOVE)) {
                    String[] inputSplit = input.split(";");
                    String move = inputSplit[0];
                    for (int i = 2; i < inputSplit.length; i++) {
                        move += ";" + inputSplit[i];
                    }
                    game.clientMoveCheck(move.split(";"));
                    showMessage("----------------------------------------------------");
                    showMessage("Move made by player " + inputSplit[1] + ":");
                    if (game.getBoard().outsideCheck() != null) {
                        game.getBoard().remove(game.getBoard().outsideCheck());
                        game.getTurn().addPoint();
                        showMessage("> Player " + inputSplit[1] + " has made a point!");
                        String scoreboard = "Scoreboard:\n" + game.getBoard().getPlayers().get(0).toString() + "\n"
                                + game.getBoard().getPlayers().get(1).toString();
                        if (game.getBoard().getPlayers().size() >= 3) {
                            scoreboard += "\n" + game.getBoard().getPlayers().get(2).toString();
                        }
                        if (game.getBoard().getPlayers().size() == 4) {
                            scoreboard += "\n" + game.getBoard().getPlayers().get(3).toString();
                        }
                        showMessage(scoreboard + "\n");
                    }
                    showMessage(game.getBoard().toString() + "\n");
                    game.nextTurn();
                } else if (input.split(";")[0].equals(ProtocolMessages.WINNER)) {
                    String[] inputSplit = input.split(";");
                    if (inputSplit.length == 2) {
                        if (inputSplit[1].equals("0")) {
                            showMessage("It's a draw!");
                        } else {
                            showMessage("Player " + inputSplit[1] + " has won the game! Congratulations!");
                        }
                    }
                    return;
                } else if (input.split(";")[0].equals(ProtocolMessages.UNEXPECTED_MESSAGE)) {
                    showMessage("unexpected message");
                }
            }

        } catch (ExitProgram e) {

            return;
        }

    }



    public void handleSalvo(String input) throws game.exceptions.ExitProgram, game.exceptions.ServerUnavailableException {
    }

    public void showMessage(String msg) {
        System.out.println(msg);
    }

    public String getNameQuestion() {
        this.showMessage("What is your name?");
        Scanner scnr = new Scanner(System.in);
        String name = scnr.nextLine();
        this.name = name;
        return name;
    }

    public String getName() {
        return this.name;
    }

    public boolean getBoolean(String question) {
        return false;
    }

    public String getReady() {
        Scanner scnr = new Scanner(System.in);
        return scnr.nextLine();
    }

    public String getmove() {
    }
}
