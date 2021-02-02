package game.players;

import game.ships.*;
import game.board.*;

import java.util.ArrayList;
import java.util.Scanner;

public class humanPlayer implements Player {
    private String name;
    private int points;
    private Board board;
    private ArrayList<ArrayList<? extends Ship>> shipLists;
    private boolean hasTurn=false;


    /**
     * @param name of the player
     * @ensures the Player starts with 0 points
     */
    public humanPlayer(String name, Board board,ArrayList<ArrayList<? extends Ship>> shipLists) {
        this.name = name;
        this.points = 0;
        this.shipLists = shipLists;
        this.board = board;
    }

    public boolean getTurn(){
        return this.hasTurn;
    }
    public void setTurn(boolean turn){
         this.hasTurn=turn;
    }

    public ArrayList<ArrayList<? extends Ship>> createShipArrays() {
        // create the ship arrays for player but without positions yet
        ArrayList<String> positions = new ArrayList<>();
        ArrayList<ArrayList<? extends Ship>> shipLists = new ArrayList<>();

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

        shipLists.add(carriers);
        shipLists.add(battleships);
        shipLists.add(destroyers);
        shipLists.add(superPatrols);
        shipLists.add(patrolBoats);

        return shipLists;

    }
    /**
     * Sets the board of a player
     *
     * @param board
     */
    public void setBoard(Board board) {
        this.board = board;
    }

    /**
     * Gets the board of a player
     *
     * @return
     */
    public Board getBoard() {
        return this.board;
    }

    /**
     * Sets an arrayList with ships arraylists, used for the scores
     *
     * @param shipLists
     */
    public void setShipArrayList(ArrayList<ArrayList<? extends Ship>> shipLists) {
        this.shipLists = shipLists;
    }


    /**
     * Returns the arraylist whith ship arraylists, used for scores
     *
     * @ensures result is an arrayList of Arraylists with ships
     */
    public ArrayList<ArrayList<? extends Ship>> getShipArrayList() {
        return this.shipLists;
    }

    /**
     * Returns the name of the player
     *
     * @ensures result=the name of the player
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Returns the points of the player
     *
     * @ensures result = the points of the player
     */
    @Override
    public int getPoints() {
        return this.points;
    }

    /**
     * Setter for points
     */
    public void setPoints(int i) {
        this.points = i;
    }




}
