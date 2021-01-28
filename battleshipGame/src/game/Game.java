package game;

import game.ships.*;
import game.board.*;
import game.players.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Game {


    //TODO network and threading

    private Board[] boards = new Board[2];
    private Player[] players = new Player[2];

    // for now single player game
    public Game(boolean randomPlacement) {
        // fill boards[] with 2 empty boards of which one is completely visible
        Board playerBoard = new Board(true);
        Board computerBoard = new Board(false);
        boards[0] = playerBoard;
        boards[1] = computerBoard;

        Player p0 = new humanPlayer("human", createShipArrays(), boards[0]);
        Player p1 = new randomComputerPlayer(createShipArrays(), boards[1]);
        players[0] = p0;
        players[1] = p1;

        if (randomPlacement) {
            fillBoardRandom(p0.getBoard(), p0);
        } else {
            fillBoardManual(p0.getBoard(), p0);
        }
        fillBoardRandom(computerBoard, p1);
        boards[0] = hideBoard(boards[0], false);
    }

    /**
     * @requires i==0||i==1
     * @ensures result!=null;
     */
    public Board getBoard(int i) {
        return this.boards[i];
    }


    /**
     * @requires i==0||i==1
     * @ensures result!=null;
     */
    public Player getPlayer(int i) {
        return this.players[i];
    }


    /**
     * @return
     */
    public ArrayList<ArrayList<? extends Ship>> createShipArrays() {
        // create the ship arrays for player but without positions yet
        ArrayList<String> positions = new ArrayList<>();
        ArrayList<ArrayList<? extends Ship>> shipLists = new ArrayList<>();

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

        shipLists.add(carriers);
        shipLists.add(battleships);
        shipLists.add(destroyers);
        shipLists.add(superPatrols);
        shipLists.add(patrolBoats);

        return shipLists;

    }

    /**
     * This method makes a "deep clone" of any object it is given.
     */
    public static Object deepClone(Object object) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            return ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Board hideBoard(Board board, boolean hideCopy) {
        if (hideCopy) {
            Board copy = (Board) deepClone(board);
            for (boardPosition position : copy.getFields()) {
                position.setPositionHidden(true);

            }
            return copy;
        } else {


            for (boardPosition position : board.getFields()) {
                position.setPositionHidden(true);

            }
            return board;
        }
    }

    public Board unHideBoard(Board board, boolean unHideCopy) {
        if (unHideCopy) {
            Board copy = (Board) deepClone(board);
            for (boardPosition position : copy.getFields()) {
                position.setPositionHidden(false);

            }
            return copy;
        } else {


            for (boardPosition position : board.getFields()) {
                position.setPositionHidden(false);

            }
            return board;
        }
    }

    /**
     * Fills board with all ships in a random fashion, ships only placed horizontally
     *
     * @param board board to fill, can be player of computer
     */
    public void fillBoardRandom(Board board, Player player) {


        // for each ship in arraylist of ? extends Ship (i.e. carriers, battleships etc.)
        String shipName;
        int i = 0;
        for (Ship sh : player.getShipArrayList().get(0)) {
            shipName = "CV";

            checkAndPlaceRandom(board, sh, shipName, player, i);
            i++;

        }
        i = 0;
        for (Ship sh : player.getShipArrayList().get(1)) {
            shipName = "BB";
            checkAndPlaceRandom(board, sh, shipName, player, i);
            i++;
        }
        i = 0;
        for (Ship sh : player.getShipArrayList().get(2)) {
            shipName = "DD";
            checkAndPlaceRandom(board, sh, shipName, player, i);
            i++;

        }
        i = 0;
        for (Ship sh : player.getShipArrayList().get(3)) {
            shipName = "SV";
            checkAndPlaceRandom(board, sh, shipName, player, i);
            i++;
        }
        i = 0;
        for (Ship sh : player.getShipArrayList().get(4)) {
            shipName = "PV";
            checkAndPlaceRandom(board, sh, shipName, player, i);
            i++;
        }

    }

    /**
     * Let's human player fill board manually
     *
     * @param board board to fill, only for human players
     * @requires board belongs to humanplayer
     */
    public void fillBoardManual(Board board, Player player) {
        Scanner sc = new Scanner(System.in);


        ArrayList<String> positions = new ArrayList<>();

        String shipName;
        int i = 0;
        for (Ship sh : player.getShipArrayList().get(0)) {
            shipName = "CV";
            checkAndPlaceManual(sc, board, sh, shipName, player, i);
            i++;
        }
        i = 0;
        for (Ship sh : player.getShipArrayList().get(1)) {
            shipName = "BB";
            checkAndPlaceManual(sc, board, sh, shipName, player, i);
            i++;
        }
        i = 0;
        for (Ship sh : player.getShipArrayList().get(2)) {
            shipName = "DD";
            checkAndPlaceManual(sc, board, sh, shipName, player, i);
            i++;
        }
        i = 0;
        for (Ship sh : player.getShipArrayList().get(3)) {
            shipName = "SV";
            checkAndPlaceManual(sc, board, sh, shipName, player, i);
            i++;
        }
        i = 0;
        for (Ship sh : player.getShipArrayList().get(4)) {
            shipName = "PV";
            checkAndPlaceManual(sc, board, sh, shipName, player, i);
            i++;
        }

    }

    /**
     * Checks if a the param randomField is a valid field for placing the ship sh, for manual board.
     *
     * @param sh          ship to place
     * @param board       board in question
     * @param randomField field provided to check
     * @requires board to be of a human player
     * @ensures result=true if field is valid || result = false if field is not valid
     */
    public boolean isValidField(Ship sh, Board board, String randomField) {
        try {
            randomField.charAt(2);
            Character.getNumericValue((randomField.charAt(2)));
            return false;
        } catch (IndexOutOfBoundsException e) {
// continue as normal, there should be no index 2

        }
        char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        for (int k = 0; k < sh.getSize(); k++) {
            int randomColumn = -1;
            for (int z = 0; z < board.getColumns(); z++) {
                if (alphabet[z] == (randomField.charAt(0))) {
                    randomColumn = z;
                }
            }
            int randomRow = Character.getNumericValue((randomField.charAt(1)));
            try {
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
            } catch (IndexOutOfBoundsException e) {
                return false;
            }

        }
        return true;
    }

    /**
     * Generates random field and checks if it's a valid field then also places it
     *
     * @param board
     * @param sh
     * @param shipName
     */
    public void checkAndPlaceRandom(Board board, Ship sh, String shipName, Player player, int shipIndexInArrayList) {
        ArrayList<String> positions = new ArrayList<>();
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
                        positions.add(randomCoordinate);

                    } catch (IndexOutOfBoundsException e) {
                        foundFittingField = false;
                    }

                }
                ArrayList<ArrayList<? extends Ship>> shipLists = player.getShipArrayList();
                switch (shipName) {
                    case "CV":

                        shipLists.get(0).get(shipIndexInArrayList).setPositions(positions);
                        player.setShipArrayList(shipLists);
                        break;
                    case "BB":

                        shipLists.get(1).get(shipIndexInArrayList).setPositions(positions);
                        player.setShipArrayList(shipLists);
                        break;
                    case "DD":
                        shipLists.get(2).get(shipIndexInArrayList).setPositions(positions);
                        player.setShipArrayList(shipLists);
                        break;
                    case "SV":
                        shipLists.get(3).get(shipIndexInArrayList).setPositions(positions);
                        player.setShipArrayList(shipLists);
                        break;
                    case "PV":
                        shipLists.get(4).get(shipIndexInArrayList).setPositions(positions);
                        player.setShipArrayList(shipLists);
                        break;

                    default:
                        System.out.println("Something went wrong in the switch trying to assign ships to the player");
                }
            }
        }

    }

    /**
     * Check if fields is valid and place it, only for manual board, uses isValidField
     *
     * @param sc
     * @param board
     * @param sh
     * @param shipName
     */
    public void checkAndPlaceManual(Scanner sc, Board board, Ship sh, String shipName, Player player, int shipIndexInArrayList) {
        ArrayList<String> positions = new ArrayList<>();
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
                    positions.add(randomCoordinate);
                }
                ArrayList<ArrayList<? extends Ship>> shipLists = player.getShipArrayList();
                switch (shipName) {
                    case "CV":
                        shipLists.get(0).get(shipIndexInArrayList).setPositions(positions);
                        player.setShipArrayList(shipLists);

                        break;
                    case "BB":

                        shipLists.get(1).get(shipIndexInArrayList).setPositions(positions);
                        player.setShipArrayList(shipLists);
                        break;
                    case "DD":
                        shipLists.get(2).get(shipIndexInArrayList).setPositions(positions);
                        player.setShipArrayList(shipLists);
                        break;
                    case "SV":
                        shipLists.get(3).get(shipIndexInArrayList).setPositions(positions);
                        player.setShipArrayList(shipLists);
                        break;
                    case "PV":
                        shipLists.get(4).get(shipIndexInArrayList).setPositions(positions);
                        player.setShipArrayList(shipLists);
                        break;

                    default:
                        System.out.println("Something went wrong in the switch trying to assign ships to the player");
                }

                System.out.println(board.toString());
                validField = true;
            } else {

                System.out.println("Enter valid field");
                randomField = sc.nextLine();
            }
        }
    }

    public boolean gameHasWinner(Player p0, Player p1) {
        if ((p0.getPoints() == (2 * 5 + 3 * 4 + 5 * 3 + 8 * 2 + 10 * 1 + 28)) || (p1.getPoints() == (2 * 5 + 3 * 4 + 5 * 3 + 8 * 2 + 10 * 1 + 28))) {
            return true;
        } else {
            return false;
        }
    }

//    public class MyRunnable implements Runnable {
//
//        public void run() {
//
//        }
//    }

    //        Thread thread1 = new Thread(new MyRunnable());
//        thread1.start();

    public void playSinglePlayerGame(Player p0, Player p1) {


        // print out player board
        System.out.println("Your full board\n" + unHideBoard(getBoard(0), true).toString());
        //print out computer board
        System.out.println("Opponent board\n" + getBoard(1).toString());
        while (!gameHasWinner(p0, p1)) {
            p0.fire(p0, p1);

            p1.fire(p1, p0);
// print out player boards (one with full visibility)
            System.out.println("Your full board\n" + unHideBoard(getBoard(0), true).toString());

            System.out.println("What your opponent sees of your board\n" + getBoard(0).toString());
            //print out computer board
            System.out.println("Opponents board\n" + getBoard(1).toString());
            System.out.println("The current scores are:\n" + p0.getName() + ": " + p0.getPoints() + "\n" + p1.getName() + ": " + p1.getPoints());
        }
        if (p0.getPoints() == (2 * 5 + 3 * 4 + 5 * 3 + 8 * 2 + 10 * 1 + 28)) {
            System.out.println(p0.getName() + " has won!");
        } else {
            System.out.println(p1.getName() + " has won!");
        }


    }

    public void playMultiPlayerGame(Player p0, Player p1) {







    }


    // for testing purposes
    public static void main(String[] args) {
        Board board = new Board(true);
        board.toString();
        Game game = new Game(true);
        game.playSinglePlayerGame(game.getPlayer(0), game.getPlayer(1));




//        // print out player board
//        System.out.println(game.getBoard(0).toString());
//        //print out computer board
//        System.out.println(game.getBoard(1).toString());
//
//        // print out positions off first cv in cv-list of humanPlayer, for testing purposes
//        System.out.println(game.getPlayer(0).getShipArrayList().get(0).get(0).getPositions().get(0));
//        System.out.println(game.getPlayer(0).getShipArrayList().get(0).get(1).getPositions().get(0));

    }

}



