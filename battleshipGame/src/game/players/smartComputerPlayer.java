package game.players;

import game.ships.*;
import game.board.*;

import java.util.ArrayList;

public class smartComputerPlayer implements Player {
    private final String name = "Smart Computer Player";
    private int points;
    private Board board;
    private boolean hasTurn= false;
    private ArrayList<ArrayList<? extends Ship>> shipLists = new ArrayList<>();

    /**
     * @ensure the Player starts with 0 points
     */
    public smartComputerPlayer(ArrayList<ArrayList<? extends Ship>> shipLists, Board board) {
        this.points = 0;
        this.shipLists = shipLists;
        this.board=board;
    }
    public boolean getTurn(){
        return this.hasTurn;
    }
    public void setTurn(boolean turn){
        this.hasTurn=turn;
    }


    /**
     * Sets the board of a player
     * @param board
     */
    public void setBoard(Board board){
        this.board=board;
    }

    /**
     * Gets the board of a player
     * @return
     */
    public Board getBoard(){
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
    public void setPoints(int i){
        this.points=i;
    }

    /**
     * Adds points based on what ship was sunk
     */
    @Override
    public void updatePoints(Player attacker, Player defender) {
        int points = 0;
        for (ArrayList<? extends Ship> shipList : defender.getShipArrayList()) {
            for (Ship sh : shipList) {
                points += sh.getSize() - sh.getHitPoints();
                if (sh.getHitPoints() == 0) {
                    points++;
                }

            }
        }
        attacker.setPoints(points);

    }

    /**
     * Fires at specified field
     *
     * @requires field is valid field
     */
    @Override
    public void fire(Player attacker, Player defender) {
    }
}
