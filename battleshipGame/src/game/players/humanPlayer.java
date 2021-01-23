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
     * Fires at specified field on enemy board, includes functionallity for added turn upon hitting an enemy vessel
     *
     * @requires @param board to be enemy board
     */
    @Override
    public void fire(Board board) {
        Scanner scanner = new Scanner(System.in);
        boolean hasTurn = true;
        while (hasTurn) {
            boolean validField = false;
            while (!validField) {
                System.out.println("Enter field to fire on");
                String input = scanner.nextLine();
                // check if existing position
                if (board.getFieldIndex(input) != -1) {

                    // check if position was already hit
                    if (!board.getFields().get(board.getFieldIndex(input)).getIsHit()) {
                        boardPosition hitPosition = board.getFields().get(board.getFieldIndex(input));
                        hitPosition.setIsHit(true);
                        hitPosition.setPositionHidden(false);
                        if (hitPosition.getState() == boardPosition.positionState.SHIP) {
                            updatePoints();
                            hitPosition.setState(boardPosition.positionState.WRECK);
                        } else {
                            hasTurn = false;
                        }
                    } else {
                        System.out.println("position already hit!");
                    }
                } else {
                    System.out.println("Enter a valid field!");
                }
            }
        }


    }
}
