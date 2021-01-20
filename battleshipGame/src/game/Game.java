package game;

import game.ships.*;
import game.board.*;

import java.util.ArrayList;
import java.util.HashSet;


public class Game {

    //TODO score things
    //TODO players
    //TODO network and threading
    private final Board[] BOARDS = new Board[2];

    // for now single player game
    public Game(boolean randomPlacement) {
        // fill boards[] with 2 empty boards of which one is completely visible
        Board playerBoard = new Board(true);
        Board computerBoard = new Board(false);
        BOARDS[0] = playerBoard;
        BOARDS[1] = computerBoard;
        if (randomPlacement) {
            fillBoardRandom(playerBoard);
            fillBoardRandom(computerBoard);
        } else {
            fillBoardManual(playerBoard);
            fillBoardRandom(computerBoard);
        }

    }

    // adjust to only be for one specific board
    public void fillBoardRandom(Board board) {
// create the ship arrays for player but without positions yet
        ArrayList<String> positions = new ArrayList<>();

        ArrayList<Carrier> carriers = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            carriers.add(new Carrier("carrier" + i, positions));
        }
        ArrayList<Battleship> battleships = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            battleships.add(new Battleship("battleship" + i, positions));
        }
        ArrayList<Destroyer> destroyers = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            destroyers.add(new Destroyer("destroyer" + i, positions));
        }
        ArrayList<SuperPatrol> superPatrols = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            superPatrols.add(new SuperPatrol("SuperPatrol" + i, positions));
        }
        ArrayList<PatrolBoat> patrolBoats = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            patrolBoats.add(new PatrolBoat("PatrolBoat" + i, positions));
        }
// for each ship find a valid position
        char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        for (Ship sh : carriers) {
            boolean foundFittingField = false;
            // while a random field that fits hasn't been found
            while (!foundFittingField) {
                // generate random field
                int randomColumn = (int) (Math.random() * (board.getColumns() + 1));
                int randomRow = (int) (Math.random() * (board.getRows() + 1));
//temporarily set to true until proven otherwise, avoids load of and statements for checking if everything is true
                foundFittingField = true;
                for (int k = 0; k < sh.getSize(); k++) {
                    String randomCoordinate = Integer.toString(randomColumn + k) + Integer.toString(randomRow);
                    if (board.getFields().get(board.getFieldIndex(randomCoordinate)).getState() != boardPosition.positionState.EMPTY) {
                        foundFittingField = false;
                    }

                }
                if(foundFittingField){
                    for (int j = 0; j < sh.getSize(); j++) {
                        String randomCoordinate = Integer.toString(randomColumn + j) + Integer.toString(randomRow);
                        board.getFields().get(board.getFieldIndex(randomCoordinate)).setState(boardPosition.positionState.SHIP);

                    }
                }
            }

        }
        for (Ship sh : battleships) {

        }
        for (Ship sh : destroyers) {

        }
        for (Ship sh : superPatrols) {

        }
        for (Ship sh : patrolBoats) {

        }

    }

    public void fillBoardManual(Board board) {

    }

}



