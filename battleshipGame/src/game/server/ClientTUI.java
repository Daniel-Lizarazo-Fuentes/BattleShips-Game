package game.server;

import java.util.ArrayList;
import java.util.Scanner;
import game.board.Board;
import game.board.boardPosition;
import game.exceptions.ExitProgram;
import game.ships.*;


/**
 * --------------------------------------------------------------------------------------------
 *
 * --------------------------------------------------------------------------------------------
 */
public class ClientTUI {
    private Client c;
    public boolean turn;
    private int points;
    private String name;
    private Board enemyBoard = new Board(false);

    public ClientTUI(Client c) {
        this.c = c;
        turn = false;
    }

    public void start() throws game.exceptions.ServerUnavailableException {
        String userInput;
        // System.out.println("Reached start method in CLientTUI: ");
        try {
            while (true) {
                // System.out.println("Before readlinefromserver: ");
                String input = c.readLineFromServer();
                //  System.out.println("After readline and input: " + input);
                if (input.split(";")[0].equals(ProtocolMessages.SUCCESS)) {
                    showMessage(ProtocolMessages.SUCCESS);


                } else if (input.split(";")[0].equals(ProtocolMessages.FAIL)) {
                    showMessage(ProtocolMessages.FAIL + ProtocolMessages.CS + input.split(";")[1]);

                } else if (input.split(";")[0].equals(ProtocolMessages.BEGIN)) {
                    Board board = new Board(false);
                    fillBoardRandom(board, createShipArrays());
                    this.c.sendMessage(ProtocolMessages.DEPLOY + ProtocolMessages.CS + toGrid(board));
                    showMessage("Starting game with the following players: " + input.split(";")[1]);
                    // showMessage("Your board"+board.toString());

                } else if (input.split(";")[0].equals(ProtocolMessages.READY)) {
                    showMessage("Ready to play!");

                } else if (input.split(";")[0].equals(ProtocolMessages.TURN) && input.split(";")[1].equals(getName())) {

                    showMessage(this.enemyBoard.toString());
                    showMessage("Your points: " + this.points);

                    //show scores
                    //   Integer[] scoreArray = stringToIntArray(input.split(";")[2]);
                    //  showMessage("p1 score: "+scoreArray[0]+"p2 score: "+scoreArray[1]);
                    //    if (this.name.contains("Random Computer Player-")) {


                    //  } else {
                    Scanner scnr = new Scanner(System.in);
                    showMessage("----------------------------------------------------");
                    showMessage("Input your salvo:");
                    userInput = scnr.nextLine();
                    handleMove(userInput);

                    String[] messageFromServer = c.readLineFromServer().split(ProtocolMessages.CS);
                    if (messageFromServer[0].equals(ProtocolMessages.HIT)) {

                        if (messageFromServer[1].equals("-1")) {

                        } else {
                            boardPosition bp = enemyBoard.getFields().get(enemyBoard.getFieldIndex(userInput));
                            bp.setPositionHidden(false);
                            if (!messageFromServer[1].equals("0")) {
                                bp.setState(boardPosition.positionState.WRECK);
                                bp.setIsHit(true);
                            }
                        }
                        if (messageFromServer[1].equals("0")) {
                            showMessage("Miss");
                        } else if (messageFromServer[1].equals("1")) {
                            showMessage("Hit");
                            points++;
                        } else if (messageFromServer[1].equals("2")) {
                            showMessage("Hit and Sunk");
                            points += 2;
                        } else if (messageFromServer[1].equals("-1")) {
                            showMessage("Invalid coordinate");
                        }

                    } else {
                        showMessage("Invalid message");
                    }
                    // }


                } else if (input.split(";")[0].equals(ProtocolMessages.HIT)) {


                    if (input.split(";")[1].equals("0")) {
                        showMessage("Miss");
                    } else if (input.split(";")[1].equals("1")) {
                        showMessage("Hit");

                    } else if (input.split(";")[1].equals("2")) {
                        showMessage("Hit and Sunk");

                    } else if (input.split(";")[1].equals("-1")) {
                        showMessage("Invalid coordinate");
                    }


                } else if (input.split(";")[0].equals(ProtocolMessages.END)) {

                    if (input.split(";")[1].equals("-1")) {
                    } else {
                        showMessage(input.split(";")[1]);

                    }

                } else if (input.split(";")[0].equals(ProtocolMessages.CHAT)) {
                    // we won't have a chat

                } else if (input.split(";")[0].equals(ProtocolMessages.LIST)) {
                    // we won't have a chat

                } else {
                    System.out.println("Something went wrong the following was received: " + input);
                }


            }

        } catch (ExitProgram e) {

            return;
        }

    }
//------------------- Old implementation  ---------------------//
//    public Integer[] stringToIntArray(String input) {
//        Integer[] intArray = new Integer[2];
//        String[] intValues = input.split(",");
//        intArray[0] = Integer.parseInt(intValues[0]);
//        intArray[1] = Integer.parseInt(intValues[1]);
//        return intArray;
//    }


    public String toGrid(Board board) {
        String result = "";
        // collum counter
        int i = 0;

        for (boardPosition bp : board.getFields()) {

            if (bp.getShipType().charAt(0) != 'E') {
                result += bp.getShipType();
            }

            if (i >= 14) {
                i = 0;

                result += ":";
            } else {
                i++;
                result += ",";
            }
        }
        return result;

    }

    /**
     * Fills board with all ships in a random fashion, ships only placed horizontally
     *
     * @param board board to fill, can be player or computer
     */
    // both multi and single player
    public void fillBoardRandom(Board board, ArrayList<ArrayList<? extends Ship>> shipLists) {


        // for each ship in arraylist of ? extends Ship (i.e. carriers, battleships etc.)
        String shipName;
        int i = 0;
        for (Ship sh : shipLists.get(0)) {
            shipName = "c" + i;

            checkAndPlaceRandom(board, sh, shipName, shipLists, i);
            i++;

        }
        i = 0;
        for (Ship sh : shipLists.get(1)) {
            shipName = "b" + i;
            checkAndPlaceRandom(board, sh, shipName, shipLists, i);
            i++;
        }
        i = 0;
        for (Ship sh : shipLists.get(2)) {
            shipName = "d" + i;
            checkAndPlaceRandom(board, sh, shipName, shipLists, i);
            i++;

        }
        i = 0;
        for (Ship sh : shipLists.get(3)) {
            shipName = "s" + i;
            checkAndPlaceRandom(board, sh, shipName, shipLists, i);
            i++;
        }
        i = 0;
        for (Ship sh : shipLists.get(4)) {
            shipName = "p" + i;
            checkAndPlaceRandom(board, sh, shipName, shipLists, i);
            i++;
        }

    }

    /**
     * Generates random field and checks if it's a valid field then also places it
     *
     * @param board
     * @param sh
     * @param shipName
     */
    public void checkAndPlaceRandom(Board board, Ship sh, String shipName, ArrayList<ArrayList<? extends Ship>> shipLists, int shipIndexInArrayList) {
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

                switch (shipName.charAt(0)) {
                    case 'c':
                        shipLists.get(0).get(shipIndexInArrayList).setPositions(positions);
                        break;
                    case 'b':
                        shipLists.get(1).get(shipIndexInArrayList).setPositions(positions);
                        break;
                    case 'd':
                        shipLists.get(2).get(shipIndexInArrayList).setPositions(positions);
                        break;
                    case 's':
                        shipLists.get(3).get(shipIndexInArrayList).setPositions(positions);
                        break;
                    case 'p':
                        shipLists.get(4).get(shipIndexInArrayList).setPositions(positions);
                        break;

                    default:
                        System.out.println("Something went wrong in the switch trying to assign ships to the player");
                }
            }
        }

    }


    public void handleMove(String input) throws game.exceptions.ExitProgram, game.exceptions.ServerUnavailableException {
        c.sendMessage(ProtocolMessages.MOVE + ProtocolMessages.CS + input);

    }

    public void showMessage(String msg) {
        System.out.println(msg);
    }

    public String getNameQuestion() {
        this.showMessage("What is your name?");
        Scanner scnr = new Scanner(System.in);
        String name = scnr.nextLine();
        this.name = name;
        return name;
    }

    public String getName() {
        return this.name;
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

    //------------------- Old implementation  ---------------------//
//    public int getNumberOfPlayers() {
//        return 2;
//    }
//
//    public boolean getBoolean(String question) {
//        return false;
//    }
//
//    public String getReady() {
//        Scanner scnr = new Scanner(System.in);
//        return scnr.nextLine();
//    }


}
