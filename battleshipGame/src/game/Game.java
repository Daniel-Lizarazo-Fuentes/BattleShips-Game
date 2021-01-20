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
                            board.getFields().get(board.getFieldIndex(randomCoordinate)).setShipType("CV"); // CV is based on hull type, meaning generic aircarft carrier
                        } catch (IndexOutOfBoundsException e) {
                            foundFittingField = false;
                        }

                    }
                }
            }

        }
        for (Ship sh : battleships) {
            boolean foundFittingField = false;
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
                            board.getFields().get(board.getFieldIndex(randomCoordinate)).setShipType("BB"); // BB is based on hull type, meaning heavy gun-armed vessel
                        } catch (IndexOutOfBoundsException e) {
                            foundFittingField = false;
                        }

                    }
                }
            }
        }
        for (Ship sh : destroyers) {
            boolean foundFittingField = false;
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
                            board.getFields().get(board.getFieldIndex(randomCoordinate)).setShipType("DD"); // DD is based on hull type, meaning destroyer
                        } catch (IndexOutOfBoundsException e) {
                            foundFittingField = false;
                        }

                    }
                }
            }

        }
        for (Ship sh : superPatrols) {
            boolean foundFittingField = false;
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
                            board.getFields().get(board.getFieldIndex(randomCoordinate)).setShipType("SV"); // SV is based on hull type (made up definition), meaning super patrol vessel
                        } catch (IndexOutOfBoundsException e) {
                            foundFittingField = false;
                        }

                    }
                }
            }
        }
        for (Ship sh : patrolBoats) {
            boolean foundFittingField = false;
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
                            board.getFields().get(board.getFieldIndex(randomCoordinate)).setShipType("PV"); // PV is based on hull type, meaning patrol vessel
                        } catch (IndexOutOfBoundsException e) {
                            foundFittingField = false;
                        }

                    }
                }
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

        char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        for (Ship sh : carriers) {
            System.out.println("Enter valid field");
            String randomfield = sc.nextLine();
            boolean validField = true;

            for (int k = 0; k < sh.getSize(); k++) {
                char randomColumn = randomfield.charAt(0);
                int randomRow = Character.digit(randomfield.charAt(1), 10);
                String randomInput = alphabet[randomColumn + k] + Integer.toString(randomRow);
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
            while (board.getFieldIndex(randomfield) == -1 && !validField) {


                System.out.println("Enter valid field");
                randomfield = sc.nextLine();
            }

            for (int j = 0; j < sh.getSize(); j++) {


                board.getFields().get(board.getFieldIndex(randomfield)).setState(boardPosition.positionState.SHIP);
                board.getFields().get(board.getFieldIndex(randomfield)).setShipType("CV"); // CV is based on hull type, meaning generic aircarft carrier


            }
            System.out.println(board.toString());
        }
        for (Ship sh : battleships) {
            System.out.println("Enter valid field");
            String randomfield = sc.nextLine();

            while (board.getFieldIndex(randomfield) == -1) {


                System.out.println("Enter valid field");
                randomfield = sc.nextLine();
            }

            for (int j = 0; j < sh.getSize(); j++) {


                board.getFields().get(board.getFieldIndex(randomfield)).setState(boardPosition.positionState.SHIP);
                board.getFields().get(board.getFieldIndex(randomfield)).setShipType("BB"); // BB is based on hull type, meaning heavy gun-armed vessel


            }
            System.out.println(board.toString());
        }
        for (Ship sh : destroyers) {
            System.out.println("Enter valid field");
            String randomfield = sc.nextLine();

            while (board.getFieldIndex(randomfield) == -1) {


                System.out.println("Enter valid field");
                randomfield = sc.nextLine();
            }

            for (int j = 0; j < sh.getSize(); j++) {


                board.getFields().get(board.getFieldIndex(randomfield)).setState(boardPosition.positionState.SHIP);
                board.getFields().get(board.getFieldIndex(randomfield)).setShipType("DD"); // DD is based on hull type, meaning destroyer


            }
            System.out.println(board.toString());
        }
        for (Ship sh : superPatrols) {
            System.out.println("Enter valid field");
            String randomfield = sc.nextLine();

            while (board.getFieldIndex(randomfield) == -1) {


                System.out.println("Enter valid field");
                randomfield = sc.nextLine();
            }

            for (int j = 0; j < sh.getSize(); j++) {


                board.getFields().get(board.getFieldIndex(randomfield)).setState(boardPosition.positionState.SHIP);
                board.getFields().get(board.getFieldIndex(randomfield)).setShipType("SV"); // SV is based on hull type (made up definition), meaning super patrol vessel


            }
            System.out.println(board.toString());
        }
        for (Ship sh : patrolBoats) {
            System.out.println("Enter valid field");
            String randomfield = sc.nextLine();

            while (board.getFieldIndex(randomfield) == -1) {


                System.out.println("Enter valid field");
                randomfield = sc.nextLine();
            }

            for (int j = 0; j < sh.getSize(); j++) {


                board.getFields().get(board.getFieldIndex(randomfield)).setState(boardPosition.positionState.SHIP);
                board.getFields().get(board.getFieldIndex(randomfield)).setShipType("PV"); // PV is based on hull type, meaning patrol vessel


            }
            System.out.println(board.toString());
        }

    }


    public static void main(String[] args) {
        Board board = new Board(true);
        board.toString();
        Game game = new Game(false);
        System.out.println(game.getPlayerBoard().toString());
        System.out.println(game.getComputerBoard().toString());
    }
}



