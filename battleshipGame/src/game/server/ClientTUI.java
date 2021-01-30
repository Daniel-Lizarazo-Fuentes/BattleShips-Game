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

                if (input.split(";")[0].equals(ProtocolMessages.TURN) && input.split(";")[1].equals(getName())) {
                    Scanner scnr = new Scanner(System.in);
                    showMessage("----------------------------------------------------");
                    showMessage("Input your salvo:");
                    userInput = scnr.nextLine();
                    handleMove(userInput);
                } else if (input.split(";")[0].equals(ProtocolMessages.NOT_VALID)) {
                    showMessage("Move not valid. try again!");
                } else if (input.split(";")[0].equals(ProtocolMessages.MOVE)) {
                    String[] inputSplit = input.split(";");
                    String move = inputSplit[0];
                    for (int i = 2; i < inputSplit.length; i++) {
                        move += ";" + inputSplit[i];
                    }
                    game.clientMoveCheck(move.split(";"));
                    showMessage("----------------------------------------------------");
                    showMessage("Salvo shot by player " + inputSplit[1] + ":");

                    showMessage(game.getBoard(0).toString() + "\n");
                    showMessage(game.getBoard(1).toString() + "\n"); //TODO fix so that boards aren't visible

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


    public void handleMove(String input) throws game.exceptions.ExitProgram, game.exceptions.ServerUnavailableException {

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

    public int getNumberOfPlayers() {
        return 2;
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
