package game.server;

import java.util.InputMismatchException;
import java.util.Scanner;

import game.Game;

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


    }
    public String getSuggestion() {

    }
    public void handleMove(String input) throws game.exceptions.ExitProgram, game.exceptions.ServerUnavailableException {}
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
    public String getmove() {}
}
