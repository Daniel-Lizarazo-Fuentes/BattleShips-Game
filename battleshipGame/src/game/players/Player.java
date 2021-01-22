package game.players;
import game.ships.*;
import game.board.*;

import java.util.ArrayList;

public interface Player {
    /**
     * Returns the name of the player
     * @ensures result=the name of the player
     */
    public String getName();

    /**
     * Sets the board of a player
     * @param board
     */
    public void setBoard(Board board);

    /**
     * Gets the board of a player
     * @return
     */
    public Board getBoard();

    /**
     * Returns the points of the player
     * @ensures result = the points of the player
     */
    public int getPoints();

    /**
     * Sets an arrayList with ships arraylists, used for the scores
     *
     * @param shipLists
     */
    public void setShipArrayList(ArrayList<ArrayList<? extends Ship>> shipLists);

    /**
     * Returns the arraylist whith ship arraylists, used for scores
     *
     * @ensures result is an arrayList of Arraylists with ships
     */
    public ArrayList<ArrayList<? extends Ship>> getShipArrayList();

    /**
     * Adds points based on what ship was sunk
     */
    public void updatePoints ();
    /**
     * Fires at specified field
     * @requires field is valid field
     */
    public void fire();

}
