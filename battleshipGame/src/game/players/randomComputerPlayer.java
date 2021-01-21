package game.players;

import game.ships.*;
import game.board.*;

import java.util.ArrayList;

public class randomComputerPlayer implements Player {

    private final String name = "Random Computer Player";
    private int points;
    private ArrayList<ArrayList<Ship>> shipLists = new ArrayList<>();

    /**
     * @ensures the Player starts with 0 points
     */
    public randomComputerPlayer() {
        this.points = 0;
        this.shipLists = shipLists;
    }

    /**
     * Sets an arrayList with ships arraylists, used for the scores
     *
     * @param shipLists
     */
    public void setShipArrayList(ArrayList<ArrayList<Ship>> shipLists) {
        this.shipLists = shipLists;
    }

    /**
     * Returns the arraylist whith ship arraylists, used for scores
     *
     * @ensures result is an arrayList of Arraylists with ships
     */
    public ArrayList<ArrayList<Ship>> getShipArrayList() {
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
