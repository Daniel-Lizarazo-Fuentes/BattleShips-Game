package game.players;

import game.ships.*;
import game.board.*;

import java.util.ArrayList;

/**
 * --------------------------------------------------------------------------------------------
 * This class is the blueprint for a randomComputerPlayer, meaning an ai which fires on valid
 * fields randomly. It also implements the Player interface. It has similar characteristics
 * to the humanPlayer.
 * The main characteristics are that this player implementation has a name, a board and an
 * ArrayList containing multiple ArrayLists which in turn have ships in them.
 * So this is one single ArrayList for all ArrayLists which contain all different ships.
 * --------------------------------------------------------------------------------------------
 */
public class randomComputerPlayer implements Player {
    private String name;
    private int points;
    private Board board;
    private ArrayList<ArrayList<? extends Ship>> shipLists;
    private boolean hasTurn = false;


    public randomComputerPlayer(Board board) {
        this.points = 0;
        this.shipLists = createShipArrays();
        this.board = board;
        this.name = "Random Computer Player-" + ((int) (Math.random() * (1000000) + 1));

    }
//    /**
//     * @param name of the player
//     * @ensures the Player starts with 0 points
//     */
//    public randomComputerPlayer(String name, Board board,ArrayList<ArrayList<? extends Ship>> shipLists) {
//        this.name = name;
//        this.points = 0;
//        this.shipLists = shipLists;
//        this.board = board;
//    }

    public boolean getTurn() {
        return this.hasTurn;
    }

    public void setTurn(boolean turn) {
        this.hasTurn = turn;
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

    public String fire(Board defenderBoard) {
        char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        Board board = defenderBoard;
        while (true) {
            int randomColumn = (int) (Math.random() * (board.getColumns() + 1));
            int randomRow = (int) (Math.random() * (board.getRows() + 1));
            String input = alphabet[randomColumn] + Integer.toString(randomRow);
            // check if existing position
            if (board.getFieldIndex(input) != -1) {
                // check if position was already hit
                if (!board.getFields().get(board.getFieldIndex(input)).getIsHit()) {
                   System.out.println("On fire: "+input);
                    return input;
                }
            }
        }
    }

}
