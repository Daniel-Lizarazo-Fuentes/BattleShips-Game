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
                if (input.split(";")[0].equals(ProtocolMessages.SUCCESS)) {
                    showMessage(ProtocolMessages.SUCCESS);

                } else if (input.split(";")[0].equals(ProtocolMessages.FAIL)) {
                    showMessage(ProtocolMessages.FAIL + ProtocolMessages.CS + input.split(";")[1]);

                } else if (input.split(";")[0].equals(ProtocolMessages.BEGIN)) {

                    game.run(); //TODO probably not right

                } else if (input.split(";")[0].equals(ProtocolMessages.READY)) {
                    //TODO only for manual placement

                } else if (input.split(";")[0].equals(ProtocolMessages.TURN) && input.split(";")[1].equals(getName())) {
                    if (!game.getTurn().getName().equals(this.getName())) {
                        game.setTurn(playerNumber);
                    }

                    Scanner scnr = new Scanner(System.in);
                    showMessage("----------------------------------------------------");
                    showMessage("Input your salvo:");
                    userInput = scnr.nextLine();
                    handleMove(userInput);

                    //TODO scores

                } else if (input.split(";")[0].equals(ProtocolMessages.HIT)) {
                    //TODO

                } else if (input.split(";")[0].equals(ProtocolMessages.END)) {
                    //TODO

                } else if (input.split(";")[0].equals(ProtocolMessages.CHAT)) {
                    //TODO we won't have a chat

                } else if (input.split(";")[0].equals(ProtocolMessages.LIST)) {
                    //TODO we won't have a chat

                }


            }

        } catch (ExitProgram e) {

            return;
        }

    }


    public void handleMove(String input) throws game.exceptions.ExitProgram, game.exceptions.ServerUnavailableException {
        game.fire(input); //TODO check if mvc coherent
        c.sendMessage(input);
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
        //TODO
    }
}
