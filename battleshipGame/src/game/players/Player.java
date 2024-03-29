package game.players;

import game.ships.*;
import game.board.*;

import java.util.ArrayList;

/**
 * --------------------------------------------------------------------------------------------
 * This class is the interface for creating Players and it states the main methods which a
 * Player (be it computer or human) would use.
 * --------------------------------------------------------------------------------------------
 */
public interface Player {

    public boolean getTurn();


    public void setTurn(boolean turn);



    /**
     * Returns the name of the player
     *
     * @ensures result=the name of the player
     */
    public String getName();

    /**
     * Sets the board of a player
     *
     * @param board
     */
    public void setBoard(Board board);

    /**
     * Gets the board of a player
     *
     * @return
     */
    public Board getBoard();

    /**
     * Returns the points of the player
     *
     * @ensures result = the points of the player
     */
    public int getPoints();

    /**
     * Sets an arrayList with ships arraylists, used for the scores
     *
     * @param shipLists
     */
    public void setShipArrayList(ArrayList<ArrayList<? extends Ship>> shipLists);
    public ArrayList<ArrayList<? extends Ship>> createShipArrays();

    /**
     * Returns the arraylist whith ship arraylists, used for scores
     *
     * @ensures result is an arrayList of Arraylists with ships
     */
    public ArrayList<ArrayList<? extends Ship>> getShipArrayList();

    /**
     * Setter for points
     */
    public void setPoints(int i);





}
