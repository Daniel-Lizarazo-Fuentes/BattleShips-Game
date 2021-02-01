package game.server;

import java.util.InputMismatchException;
import java.util.Scanner;

import game.Game;
import game.board.Board;
import game.exceptions.ExitProgram;

public class ClientTUI {
    private Client c;
    public boolean turn;

    private int numOfPlayers;
    private int playerNumber;
    private String name;

    public ClientTUI(Client c) {
        this.c = c;
        turn = false;
    }

    public void start() throws game.exceptions.ServerUnavailableException {
        String userInput;
        System.out.println("Reached start method in CLientTUI: "); //TODO
        try {
            while (true) {
                System.out.println("Before readlinefromserver: "); //TODO
                String input = c.readLineFromServer();
                System.out.println("After readline and input: " + input); //TODO
                if (input.split(";")[0].equals(ProtocolMessages.SUCCESS)) {
                    showMessage(ProtocolMessages.SUCCESS);


                } else if (input.split(";")[0].equals(ProtocolMessages.FAIL)) {
                    showMessage(ProtocolMessages.FAIL + ProtocolMessages.CS + input.split(";")[1]);

                } else if (input.split(";")[0].equals(ProtocolMessages.BEGIN)) {
                    showMessage("Starting game with the following players: " + input.split(";")[1]);

                } else if (input.split(";")[0].equals(ProtocolMessages.READY)) {
                    //TODO only for manual placement

                } else if (input.split(";")[0].equals(ProtocolMessages.TURN) && input.split(";")[1].equals(getName())) {


                    //show scores
                    showMessage(input.split(";")[2]);


                    Scanner scnr = new Scanner(System.in);
                    showMessage("----------------------------------------------------");
                    showMessage("Input your salvo:");
                    userInput = scnr.nextLine();
                    handleMove(userInput);


                } else if (input.split(";")[0].equals(ProtocolMessages.HIT)) {
                    if (input.split(";")[1].equals("0")) {
                        showMessage("Miss");
                    } else if (input.split(";")[1].equals("1")) {
                        showMessage("Hit");
                    } else if (input.split(";")[1].equals("2")) {
                        showMessage("Hit and Sunk");
                    } else if (input.split(";")[1].equals("-1")) {
                        showMessage("For invalid coordinate");
                    }


                } else if (input.split(";")[0].equals(ProtocolMessages.END)) {

                    if (input.split(";")[1].equals("-1")) {
                    } else {
                        showMessage(input.split(";")[1]);

                    }

                } else if (input.split(";")[0].equals(ProtocolMessages.CHAT)) {
                    //TODO we won't have a chat

                } else if (input.split(";")[0].equals(ProtocolMessages.LIST)) {
                    //TODO we won't have a chat

                } else {
                    System.out.println("Something went wrong the following was received: " + input);
                }


            }

        } catch (ExitProgram e) {

            return;
        }

    }


    public void handleMove(String input) throws game.exceptions.ExitProgram, game.exceptions.ServerUnavailableException {
        c.sendMessage(ProtocolMessages.MOVE + ProtocolMessages.CS + input);
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


}
