package game;

import game.ships.*;
import game.board.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;


public class Game {

    //TODO score things
    //TODO players
    //TODO network and threading
    private Board[] boards = new Board[2];

    // for now single player game
    public Game(boolean randomPlacement) {
        // fill boards[] with 2 empty boards of which one is completely visible
        Board playerBoard = new Board(true);
        Board computerBoard = new Board(false);
        boards[0] = playerBoard;
        boards[1] = computerBoard;
        if (randomPlacement) {
            fillBoardRandom(playerBoard);
        } else {
            fillBoardManual(playerBoard);
        }
        fillBoardRandom(computerBoard);

    }

    public Board getPlayerBoard() {
        return this.boards[0];
    }

    public Board getComputerBoard() {
        return this.boards[1];
    }

    // TODO add to player
    public void createShipArrays() {
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
            checkAndPlaceRandom(board, sh, "CV");

        }
        for (Ship sh : battleships) {
            checkAndPlaceRandom(board, sh, "BB");
        }
        for (Ship sh : destroyers) {
            checkAndPlaceRandom(board, sh, "DD");

        }
        for (Ship sh : superPatrols) {
            checkAndPlaceRandom(board, sh, "SV");
        }
        for (Ship sh : patrolBoats) {
            checkAndPlaceRandom(board, sh, "PV");
        }

    }

    public boolean isValidField(Ship sh, Board board, String randomField) {
        char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        for (int k = 0; k < sh.getSize(); k++) {
            int randomColumn = -1;
            for (int z = 0; z < board.getColumns(); z++) {
                if (alphabet[z] == (randomField.charAt(0))) {
                    randomColumn = z;
                }
            }
            int randomRow = Character.getNumericValue((randomField.charAt(1)));
            String randomInput = alphabet[randomColumn + k] + Integer.toString(randomRow);
            if (randomColumn + k < 15) {
                //if index doesn't exist then it will just be set to false
                try {
                    if (board.getFields().get(board.getFieldIndex(randomInput)).getState() != boardPosition.positionState.EMPTY) {
                        return false;
                    }
                } catch (IndexOutOfBoundsException e) {
                    return false;
                }

            } else {
                return false;
            }
        }
        return true;
    }

    public void checkAndPlaceRandom(Board board, Ship sh, String shipName) {
        boolean foundFittingField = false;
        char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        // while a random field that fits hasn't been found
        while (!foundFittingField) {
            // generate random field
            int randomColumn = (int) (Math.random() * (board.getColumns() + 1));
            int randomRow = (int) (Math.random() * (board.getRows() + 1));
//temporarily set to true until proven otherwise, avoids load of and statements for checking if everything is true
            foundFittingField = true;
            for (int k = 0; k < sh.getSize(); k++) {

                String randomCoordinate = alphabet[randomColumn + k] + Integer.toString(randomRow);
                if (randomColumn + k < 15) {
                    //if index doesn't exist then it will just be set to false
                    try {
                        if (board.getFields().get(board.getFieldIndex(randomCoordinate)).getState() != boardPosition.positionState.EMPTY) {
                            foundFittingField = false;
                        }
                    } catch (IndexOutOfBoundsException e) {
                        foundFittingField = false;
                    }

                } else {
                    foundFittingField = false;
                }
            }
//                System.out.println(foundFittingField); // for testing purposes
            if (foundFittingField) {
                for (int j = 0; j < sh.getSize(); j++) {
                    String randomCoordinate = alphabet[randomColumn + j] + Integer.toString(randomRow);
                    try {
                        board.getFields().get(board.getFieldIndex(randomCoordinate)).setState(boardPosition.positionState.SHIP);
                        board.getFields().get(board.getFieldIndex(randomCoordinate)).setShipType(shipName);
                    } catch (IndexOutOfBoundsException e) {
                        foundFittingField = false;
                    }

                }
            }
        }

    }

    public void checkAndPlaceManual(Scanner sc, Board board, Ship sh, String shipName) {
        System.out.println("Enter a field");
        String randomField = sc.nextLine();
        boolean validField = false;
        while (board.getFieldIndex(randomField) == -1 || !validField) {
            if (isValidField(sh, board, randomField)) {
                for (int j = 0; j < sh.getSize(); j++) {
                    char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
                    int randomColumn = -1;
                    for (int z = 0; z < board.getColumns(); z++) {
                        if (alphabet[z] == (randomField.charAt(0))) {
                            randomColumn = z;
                        }
                    }
                    int randomRow = Character.getNumericValue((randomField.charAt(1)));
                    String randomCoordinate = alphabet[randomColumn + j] + Integer.toString(randomRow);
                    board.getFields().get(board.getFieldIndex(randomCoordinate)).setState(boardPosition.positionState.SHIP);
                    board.getFields().get(board.getFieldIndex(randomCoordinate)).setShipType(shipName);
                }
                System.out.println(board.toString());
                validField = true;
            } else {

                System.out.println("Enter valid field");
                randomField = sc.nextLine();
            }
        }
    }

    public void fillBoardManual(Board board) {
        Scanner sc = new Scanner(System.in);

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

        String shipName;
        for (Ship sh : carriers) {
            shipName = "CV";
            checkAndPlaceManual(sc, board, sh, shipName);

        }
        for (Ship sh : battleships) {
            shipName = "BB";
            checkAndPlaceManual(sc, board, sh, shipName);
        }
        for (Ship sh : destroyers) {
            shipName = "DD";
            checkAndPlaceManual(sc, board, sh, shipName);
        }
        for (Ship sh : superPatrols) {
            shipName = "SV";
            checkAndPlaceManual(sc, board, sh, shipName);
        }
        for (Ship sh : patrolBoats) {
            shipName = "PV";
            checkAndPlaceManual(sc, board, sh, shipName);
        }

    }


    public static void main(String[] args) {
        Board board = new Board(true);
        board.toString();
        Game game = new Game(true);
        System.out.println(game.getPlayerBoard().toString());
        System.out.println(game.getComputerBoard().toString());
    }
}



