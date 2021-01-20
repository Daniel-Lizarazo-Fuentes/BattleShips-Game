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
        BOARDS[0] = new Board(true);
        BOARDS[1] = new Board(false);
        if (randomPlacement) {
            fillBoardRandom();
        } else {
            fillBoardManual();
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
        for (Ship sh : carriers) {
            boolean foundFittingField = false;
            // while a random field that fits hasn't been found
            while (!foundFittingField) {
                // find random field
                int randomField = (int) (Math.random() * ((board.getFields().size()) + 1));
                // TODO find better way of simplifying the and statements switch?
                if (board.getFields().get(randomField).getState() == boardPosition.positionState.EMPTY && board.getFields().get(randomField).getState() == boardPosition.positionState.EMPTY) {


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

    public void fillBoardManual() {

    }

}



