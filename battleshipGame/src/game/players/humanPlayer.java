package game.players;

import game.ships.*;
import game.board.*;

import java.util.ArrayList;

public class humanPlayer implements Player {
    private String name;
    private int points;
    private Board board;
    private ArrayList<ArrayList<? extends Ship>> shipLists;


    /**
     * @param name of the player
     * @ensures the Player starts with 0 points
     */
    public humanPlayer(String name, ArrayList<ArrayList<? extends Ship>> shipLists, Board board) {
        this.name = name;
        this.points = 0;
        this.shipLists = shipLists;
        this.board = board;
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
     * Adds points based on what ship was sunk
     */
    @Override
    public void updatePoints() {
    }

    /**
     * Fires at specified field
     *
     * @requires field is valid field
     */
    @Override
    public void fire() {
    }
}
