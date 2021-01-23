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
     * Fires at specified field on enemy board, includes functionallity for added turn upon hitting an enemy vessel
     *
     * @requires @param board to be enemy board
     */
    @Override
    public void fire(Player attacker, Player defender) {
        Scanner scanner = new Scanner(System.in);
        boolean hasTurn = true;
        Board board = defender.getBoard();
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
                        //check if there was a ship at the given position
                        if (hitPosition.getState() == boardPosition.positionState.SHIP) {

                            //find the ship and change it's hitPoints
                            for (ArrayList<? extends Ship> shipList : defender.getShipArrayList()) {
                                for (Ship sh : shipList) {
                                    for (String position : sh.getPositions()) {
                                        if (position.equals(input)) {
                                            sh.setHitPoints(sh.getHitPoints() - 1);
                                        }
                                    }

                                }
                            }
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
                updatePoints(attacker,defender);

            }
        }


    }
}
