package game;

import game.server.ClientHandler;
import game.server.ProtocolMessages;
import game.ships.*;
import game.board.*;
import game.players.*;

import java.util.ArrayList;

/**
 * --------------------------------------------------------------------------------------------
 * This class runs on the server and has the core game functionality. It does everything from
 * setting up a game to playing and ending a game.
 * --------------------------------------------------------------------------------------------
 */
public class Game implements Runnable {

    // ------------------variables-------------------//
    private Board[] GameBoards = new Board[2];
    private ArrayList<ClientHandler> clientHandlers;
    private Player[] GamePlayers = new Player[2];
    private String move;
    // private boolean timedOut = false; //For timer

    // -----------------constructor----------------//

    /**
     * The constructor doesn't actually fully construct the game. It does assign the clientHandlers
     * so the game can be constructed (the game actually is constructed in run()).
     *
     * @param clientHandlers an ArrayList of clientHandlers.
     *                       The clientHandlers in the arrayList clientHandlers are used to send messages, get names, getBoards among other things.
     */
    public Game(ArrayList<ClientHandler> clientHandlers) {
        this.clientHandlers = clientHandlers;


    }

    /**
     * Checks if enough players are connected for a multiplayer game.
     *
     * @return false if enough (2) players are connected, returns true if there are less than 2 connected
     */
    public boolean doesStillNeedPlayers() {
        return clientHandlers.size() < 2;
    }

    /**
     * Method to add a ClientHandler to the ArrayList of clientHandlers
     *
     * @param cl a ClientHandler
     */
    public void addClientHandler(ClientHandler cl) {
        clientHandlers.add(cl);
    }
    //----------------------------------- Timer -----------------------------------//
    //    private class timer implements Runnable {
    //
    //        private ClientHandler clientHand;
    //
    //        public timer(ClientHandler ch) {
    //            this.clientHand = ch;
    //        }
    //
    //        @Override
    //        public void run() {
    //            while (!timedOut) {
    //                try {
    //                    synchronized (this) {
    //                        wait(30000); //give 30s to make move
    //                        switchTurn();
    //                        timedOut = true;
    //                        clientHand.writeOut(ProtocolMessages.TURN + ProtocolMessages.CS + clientHand.getName());
    //                    }
    //                } catch (InterruptedException e) {
    //
    //                }
    //            }
    //        }
    //    }
    //----------------------------------- End of timer part -----------------------------------//

    /**
     * The run methods makes the Game class implement Runnable. In this method the game is constructed (see the switch) and the game is played
     */
    @Override
    public void run() {
        //System.out.println("RUNNING"); For testing

        String nameList = "";
        for (ClientHandler ch : clientHandlers) {
            nameList = nameList + "," + ch.getName();

        }

        nameList = nameList + ProtocolMessages.CS + false;
        nameList = nameList.substring(1);
        // Send begin message to clients
        for (ClientHandler ch : clientHandlers) {
            ch.writeOut(ProtocolMessages.BEGIN + ProtocolMessages.CS + nameList);
        }

        switch (clientHandlers.size()) {
            //if only one clientHandler is provided it will be a singlePlayer game
            case 1:
                //======================================= SinglePlayer ============================================//
                try {
                    synchronized (clientHandlers.get(0)) {
                        clientHandlers.get(0).wait();
                    }
                    System.out.println("Ready to play");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                GameBoards[0] = clientHandlers.get(0).getBoard();


                GameBoards[1] = new Board(false);
                fillBoardRandom(GameBoards[1], createShipArrays());

                GamePlayers[0] = new humanPlayer(clientHandlers.get(0).getName(), GameBoards[0], clientHandlers.get(0).getShipLists());
                GamePlayers[1] = new randomComputerPlayer(GameBoards[1]);

                GamePlayers[0].setTurn(true);

                break;
            //=========================== End of SinglePlayer implementation =================================//
            //if two clientHandlers are provided it will be a MultiPlayer game
            case 2:
                //======================================= Multiplayer ============================================//

                // Wait for the first clientHandler
                try {
                    synchronized (clientHandlers.get(0)) {
                        clientHandlers.get(0).wait();
                    }
                    System.out.println("Ready to play");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                GameBoards[0] = clientHandlers.get(0).getBoard();

                // Wait for the second clientHandler
                try {
                    synchronized (clientHandlers.get(1)) {
                        clientHandlers.get(1).wait();
                    }
                    System.out.println("Ready to play");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                GameBoards[1] = clientHandlers.get(1).getBoard();

                //Create game players
                GamePlayers[0] = new humanPlayer(clientHandlers.get(0).getName(), GameBoards[0], clientHandlers.get(0).getShipLists());
                GamePlayers[1] = new humanPlayer(clientHandlers.get(1).getName(), GameBoards[1], clientHandlers.get(1).getShipLists());

                //First connected always get first turn
                GamePlayers[0].setTurn(true);

                // fillBoardRandom(GameBoards[0], GamePlayers[0]); // old implementation
                //  fillBoardRandom(GameBoards[1], GamePlayers[1]); // old implementation


//                System.out.println( GamePlayers[0].getName()+" board\n"+GameBoards[0].toString()); // for testing if board were correctly generated
//                System.out.println(GamePlayers[1].getName()+" board\n"+GameBoards[1].toString()); // for testing
                break;
            //=========================== End of MultiPlayer implementation =======================================//
        }


        // assign game to clientHandlers
        for (ClientHandler ch : clientHandlers) {
            ch.setGame(this);
        }

        //=========================== Playing the game ===============================//
        while (!hasWinner() && !connectionLossCheck()) {
            if (GamePlayers[1] instanceof randomComputerPlayer && GamePlayers[1].getTurn()) {
                //======================================= SinglePlayer ============================================//
                this.move = ((randomComputerPlayer) GamePlayers[1]).fire(GamePlayers[0].getBoard());
                //=========================== End of SinglePlayer implementation =================================//
            } else {
                //======================================= Multiplayer =============================================//
                ClientHandler ch = getCH();

                if (ch != null) {
                    // send turn message to indicate turn
                    ch.writeOut(ProtocolMessages.TURN + ProtocolMessages.CS + ch.getName()); // add the following to send scores as well, protocol doesn't send scores in the examples and TA said we should not as well: + ProtocolMessages.CS + pointsToArray(updatePoints(GamePlayers[0], GamePlayers[1]))

                    //-------------- Timer ----------------//
                    //  Thread thread = new Thread(new timer(ch));
                    //      thread.start();
                    //-------------- Timer end ----------------//
                    try {
                        synchronized (this) {
                            this.wait();
                        }
                        System.out.println("The move was equal to:" + this.move);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //-------------- Timer ----------------//
                    //               if (timedOut) {
                    //                   ch.writeOut(ProtocolMessages.TURN + ProtocolMessages.CS + ch.getName());
                    //               } else {
                    //-------------- Timer End ----------------//
                    System.out.println(this.move);

                    //   }
                }
                //=========================== End of MultiPlayer implementation =======================================//
            }

            //=========================== For both SinglePlayer and Multiplayer =======================================//
            // if provided move isn't null
            if (move != null) {
                // if it's player 0's turn
                if (GamePlayers[0].getTurn()) {
                    // check if move provided was correct
                    if (moveCheck(GamePlayers[1], move)) {
                        //make move
                        int fireResult = fire(move);
                        // send result message (HIT),fireResult =: -1 if invalid, 0 if empty, 1 if hit, 2 if hit and sunk
                        sendAll(ProtocolMessages.HIT + ProtocolMessages.CS + fireResult);
                        //-------------- Timer ----------------//
                        //      synchronized (thread) {
                        //          timedOut = false;
                        //         thread.notify();
                        //      }
                        //-------------- Timer End ----------------//
                        if (fireResult == 0) {
                            // if a ship wasn't hit and move provided was valid give turn to other player
                            switchTurn();
                        }
                    } else {
                        sendAll(ProtocolMessages.HIT + ProtocolMessages.CS + "-1");
                    }
                }
                // if it's player 1's turn
                else {
                    // check if move provided was correct
                    if (moveCheck(GamePlayers[0], move)) {
                        int fireResult = fire(move);
                        // send result message (HIT),fireResult =: -1 if invalid, 0 if empty, 1 if hit, 2 if hit and sunk
                        sendAll(ProtocolMessages.HIT + ProtocolMessages.CS + fireResult);

                        if (fireResult == 0) {
                            // if a ship wasn't hit and move provided was valid give turn to other player
                            switchTurn();
                        }
                    } else {
                        sendAll(ProtocolMessages.HIT + ProtocolMessages.CS + "-1");
                    }
                }
            }
        }
    }

    /**
     * Fills board with all ships in a random fashion, ships are only placed horizontally
     * -----------------------------------------------------------------------------------
     * <p>
     * Summarized:
     * for each ship in all Arraylists containing ships in the Arraylist shipLists create
     * a shipName (consisting of shipType + the shipNumber) and place that ship on the
     * board.
     * <p>
     * -----------------------------------------------------------------------------------
     *
     * @param board     board to fill
     * @param shipLists arrayList containing ArrayLists which contain ships
     */
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
     * Generates random field and checks if it's a fitting field
     * (meaning existing && empty && needed fields to the right for shiplacements also empty)
     * then also places them (set positionState on board and add positions to ships).
     * ----------------------------------------------------------------------------------------
     * <p>
     * Summarized:
     * <p>
     * <p>
     * ----------------------------------------------------------------------------------------
     *
     * @param board
     * @param sh
     * @param shipName
     */
    public void checkAndPlaceRandom(Board board, Ship sh, String shipName, ArrayList<ArrayList<? extends
            Ship>> shipLists, int shipIndexInArrayList) {

        ArrayList<String> positions = new ArrayList<>();
        boolean foundFittingField = false;
        char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        // while a random field that fits hasn't been found
        while (!foundFittingField) {
            // generate random field parameters
            int randomColumn = (int) (Math.random() * (board.getColumns() + 1));
            int randomRow = (int) (Math.random() * (board.getRows() + 1));
            // set to true temporarily until proven otherwise, avoids load of && statements for checking if everything is true
            foundFittingField = true;
            for (int k = 0; k < sh.getSize(); k++) {
                // create coordinate from randomly generated field parameters
                String randomCoordinate = alphabet[randomColumn + k] + Integer.toString(randomRow);
                // check if collum to the right (for the entire size) exists, if not it's not a fitting field
                if (randomColumn + k < 15) {
                    //if index doesn't exist it will throw an out of bounds exception, in that case we just set it to false
                    try {
                        // check if that particular board position is empty
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
            // System.out.println(foundFittingField); // for testing purposes

            // check if fitting field was generated
            if (foundFittingField) {
                //set the boardPositions variables to correspond with a ship instead of empty waters
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
                // check what type a ship is and then add the positions to the corresponding ships
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
                        System.out.println("Something went wrong in the switch trying to assign ships to the player"); //more for debugging but still useful, will print on server as the game is ran there
                }
            }
        }

    }

    /**
     * Creates an ArrayList containing ArrayLists which each contain different type of ships.
     * The ships will actually not have any positions assigned (that happens in checkAndPlaceRandom).
     *
     * @return ArrayList containing ArrayLists which each contain different type of ships
     */
    public ArrayList<ArrayList<? extends Ship>> createShipArrays() {
        // create the ship arrays for player but without positions yet
        ArrayList<String> positions = new ArrayList<>();
        ArrayList<ArrayList<? extends Ship>> shipLists = new ArrayList<>();

        // --------create ArrayLists with different kinds of ships (ships have no positions, just an empty Position ArrayList)---------//
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

        // add arrayLists containing ships to shipLists
        shipLists.add(carriers);
        shipLists.add(battleships);
        shipLists.add(destroyers);
        shipLists.add(superPatrols);
        shipLists.add(patrolBoats);

        return shipLists;

    }

    //-------------------- Previous (non functional) implementation of keeping up with points ---------------------------//
//    /**
//     * Adds points based on what ship was sunk
//     */
//    public Integer[] updatePoints(Player p0, Player p1) {
//        Integer[] playerPoints = new Integer[2];
//        int points = 0;
//        for (ArrayList<? extends Ship> shipList : p0.getShipArrayList()) {
//            System.out.println("Points at each shiparraylist p0 "+points);
//            for (Ship sh : shipList) {
//                points += sh.getSize() - sh.getHitPoints();
//                if (sh.getHitPoints() == 0) {
//                    points++;
//                }
//
//            }
//        }
//        p1.setPoints(points);
//
//        points = 0;
//        for (ArrayList<? extends Ship> shipList : p1.getShipArrayList()) {
//            System.out.println("Points at each shiparraylist p1 "+points);
//            for (Ship sh : shipList) {
//                points += sh.getSize() - sh.getHitPoints();
//                if (sh.getHitPoints() == 0) {
//                    points++;
//                }
//
//            }
//        }
//        p0.setPoints(points);
//
//        playerPoints[0] = p0.getPoints();
//        playerPoints[1] = p1.getPoints();
//
//        return playerPoints;
//
//    }
//
//    public String pointsToArray(Integer[] intArray){
//       return intArray[0]+","+intArray[1];
//    }


    /**
     * Getter for clientHandler which turn it is.
     *
     * @return clientHandler which turn it is.
     */
    public ClientHandler getCH() {
        for (ClientHandler ch : clientHandlers) {
            if (getTurn().getName().equals(ch.getName())) {
                return ch;
            }
        }
        return null;
    }


    /**
     * @param msg
     */
    public void sendAll(String msg) {
        for (ClientHandler ch : clientHandlers) {
            ch.writeOut(msg);
        }
    }
//-------------------------- Previous implementation where clienthandlers numbers were needed -------------------------------//
    //    public int getNumber(ClientHandler ch) {
    //        if (clientHandlers.size() == 1) {
    //            return 1;
    //        } else if (ch == null) {
    //            return 2;
    //        }
    //        for (int i = 0; i < clientHandlers.size(); i++) {
    //            if (clientHandlers.get(i).getName().equals(ch.getName())) {
    //                return (i + 1);
    //            }
    //        }
    //        return 0;
    //    }
    //
    //    public int getNumber(String name) {
    //        for (int i = 0; i < clientHandlers.size(); i++) {
    //            if (clientHandlers.get(i).getName().equals(name)) {
    //                return (i + 1);
    //            }
    //        }
    //        return 0;
    //    }

    /**
     * Check if connection is still up
     *
     * @return true if connection is up, false if connection isn't up
     */
    public boolean connectionLossCheck() {
        for (ClientHandler ch : clientHandlers) {
            if (ch.getSock().isInputShutdown() && ch.getSock().isOutputShutdown()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Getter for GameBoards
     *
     * @return array of Boards
     */
    public Board[] getBoard() {
        return this.GameBoards;
    }

    /**
     * Get the player which currently has the turn
     *
     * @return Player for which getTurn is true
     */
    public Player getTurn() {
        if (GamePlayers[0].getTurn()) {
            return GamePlayers[0];
        } else {

            return GamePlayers[1];
        }

    }

    /**
     * Switch the turn of the players
     */
    public void switchTurn() {
        if (GamePlayers[0].getTurn()) {
            GamePlayers[0].setTurn(false);
            GamePlayers[1].setTurn(true);
        } else {
            GamePlayers[1].setTurn(false);
            GamePlayers[0].setTurn(true);
        }
    }

    /**
     * Method to check if the game has a winner.
     *
     * @return true if game has a winner, false if not
     */
    public boolean hasWinner() {
        if ((GamePlayers[0].getPoints() == (2 * 5 + 3 * 4 + 5 * 3 + 8 * 2 + 10 * 1 + 28)) || (GamePlayers[1].getPoints() == (2 * 5 + 3 * 4 + 5 * 3 + 8 * 2 + 10 * 1 + 28))) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * ---------------------------------------------------------------------------------------------------
     * Method to actually fire, check for valid input is done beforehand.
     * (changes boardPositions on Board, and returns 0 for a miss, 1 for a hit, 2 for a hit and sunk)
     * ---------------------------------------------------------------------------------------------------
     *
     * @param input move, so the coordinate on which is fired
     * @return 0 for a miss, 1 for a hit, 2 for a hit and sunk
     */
    public int fire(String input) {
        int ret = 0;
        Player defender;
        //set the player
        if (GamePlayers[0].getTurn()) {
            defender = GamePlayers[1];
        } else {
            defender = GamePlayers[0];
        }


        boardPosition hitPosition = defender.getBoard().getFields().get(defender.getBoard().getFieldIndex(input));
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
                            if (sh.getHitPoints() == 0) {
                                ret = 2;
                            } else {
                                ret = 1;
                            }
                        }
                    }
                }
            }
            hitPosition.setState(boardPosition.positionState.WRECK);


        }

        return ret;
    }

    /**
     * Check if a move is valid
     * @param defender defending player of which we retrieve the board
     * @param input coordiante to be fired on
     * @return false if field doesn't exist or field was already fired on
     */
    public boolean moveCheck(Player defender, String input) {

        char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();

        int randomColumn = -1;
        for (int z = 0; z < defender.getBoard().getColumns(); z++) {
            if (alphabet[z] == (input.charAt(0))) {
                randomColumn = z;
            }
        }
        try {
            int randomRow = Character.getNumericValue((input.charAt(1)));
            try {

                if (randomColumn < 15 && randomRow < 10) {

                } else {
                    return false;
                }
            } catch (IndexOutOfBoundsException e) {
                return false;
            }

            Board defenderBoard = defender.getBoard();
            ArrayList<boardPosition> defenderFields = defenderBoard.getFields();
            int index = defenderBoard.getFieldIndex(input);
            if (index == -1) {
                return false;
            } else {
                return !defenderFields.get(index).getIsHit();
            }
        } catch (IndexOutOfBoundsException e) {
            return false;
        }

    }

    /**
     * Setter for the move, also notifies all threads that a move has been set
     * @param move
     */
    synchronized public void setMove(String move) {
        this.move = move;
        this.notifyAll();
    }


    //=================== Pre mvc singleplayer ========================

//
//    private Board[] boards = new Board[2];
//    private Player[] players = new Player[2];
//
//    // for now single player game
//    public Game(boolean randomPlacement) {
//        // fill boards[] with 2 empty boards of which one is completely visible
//        Board playerBoard = new Board(true);
//        Board computerBoard = new Board(false);
//        boards[0] = playerBoard;
//        boards[1] = computerBoard;
//
//        Player p0 = new humanPlayer("human", createShipArrays(), boards[0]);
//        Player p1 = new randomComputerPlayer(createShipArrays(), boards[1]);
//        players[0] = p0;
//        players[1] = p1;
//
//        if (randomPlacement) {
//            fillBoardRandom(p0.getBoard(), p0);
//        } else {
//            fillBoardManual(p0.getBoard(), p0);
//        }
//        fillBoardRandom(computerBoard, p1);
//        boards[0] = hideBoard(boards[0], false);
//    }
//
//    /**
//     * @requires i==0||i==1
//     * @ensures result!=null;
//     */
//    public Board getBoard(int i) {
//        return this.boards[i];
//    }
//
//
//    /**
//     * @requires i==0||i==1
//     * @ensures result!=null;
//     */
//    public Player getPlayer(int i) {
//        return this.players[i];
//    }
//
//

//
//    /**
//     * This method makes a "deep clone" of any object it is given.
//     */
//    // both multi and single player
//    public static Object deepClone(Object object) {
//        try {
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            ObjectOutputStream oos = new ObjectOutputStream(baos);
//            oos.writeObject(object);
//            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
//            ObjectInputStream ois = new ObjectInputStream(bais);
//            return ois.readObject();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    // both multi and single player
//    public Board hideBoard(Board board, boolean hideCopy) {
//        if (hideCopy) {
//            Board copy = (Board) deepClone(board);
//            for (boardPosition position : copy.getFields()) {
//                position.setPositionHidden(true);
//
//            }
//            return copy;
//        } else {
//
//
//            for (boardPosition position : board.getFields()) {
//                position.setPositionHidden(true);
//
//            }
//            return board;
//        }
//    }
//
//    // both multi and single player
//    public Board unHideBoard(Board board, boolean unHideCopy) {
//        if (unHideCopy) {
//            Board copy = (Board) deepClone(board);
//            for (boardPosition position : copy.getFields()) {
//                position.setPositionHidden(false);
//
//            }
//            return copy;
//        } else {
//
//
//            for (boardPosition position : board.getFields()) {
//                position.setPositionHidden(false);
//
//            }
//            return board;
//        }
//    }
//

//
//    /**
//     * Let's human player fill board manually
//     *
//     * @param board board to fill, only for human players
//     * @requires board belongs to humanplayer
//     */
//    public void fillBoardManual(Board board, Player player) {
//        Scanner sc = new Scanner(System.in);
//
//
//        ArrayList<String> positions = new ArrayList<>();
//
//        String shipName;
//        int i = 0;
//        for (Ship sh : player.getShipArrayList().get(0)) {
//            shipName = "CV";
//            checkAndPlaceManual(sc, board, sh, shipName, player, i);
//            i++;
//        }
//        i = 0;
//        for (Ship sh : player.getShipArrayList().get(1)) {
//            shipName = "BB";
//            checkAndPlaceManual(sc, board, sh, shipName, player, i);
//            i++;
//        }
//        i = 0;
//        for (Ship sh : player.getShipArrayList().get(2)) {
//            shipName = "DD";
//            checkAndPlaceManual(sc, board, sh, shipName, player, i);
//            i++;
//        }
//        i = 0;
//        for (Ship sh : player.getShipArrayList().get(3)) {
//            shipName = "SV";
//            checkAndPlaceManual(sc, board, sh, shipName, player, i);
//            i++;
//        }
//        i = 0;
//        for (Ship sh : player.getShipArrayList().get(4)) {
//            shipName = "PV";
//            checkAndPlaceManual(sc, board, sh, shipName, player, i);
//            i++;
//        }
//
//    }
//
//    /**
//     * Checks if a the param randomField is a valid field for placing the ship sh, for manual board.
//     *
//     * @param sh          ship to place
//     * @param board       board in question
//     * @param randomField field provided to check
//     * @requires board to be of a human player
//     * @ensures result=true if field is valid || result = false if field is not valid
//     */
//    public boolean isValidField(Ship sh, Board board, String randomField) {
//        try {
//            randomField.charAt(2);
//            Character.getNumericValue((randomField.charAt(2)));
//            return false;
//        } catch (IndexOutOfBoundsException e) {
//// continue as normal, there should be no index 2
//
//        }
//        char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
//        for (int k = 0; k < sh.getSize(); k++) {
//            int randomColumn = -1;
//            for (int z = 0; z < board.getColumns(); z++) {
//                if (alphabet[z] == (randomField.charAt(0))) {
//                    randomColumn = z;
//                }
//            }
//            int randomRow = Character.getNumericValue((randomField.charAt(1)));
//            try {
//                String randomInput = alphabet[randomColumn + k] + Integer.toString(randomRow);
//                if (randomColumn + k < 15) {
//                    //if index doesn't exist then it will just be set to false
//                    try {
//                        if (board.getFields().get(board.getFieldIndex(randomInput)).getState() != boardPosition.positionState.EMPTY) {
//                            return false;
//                        }
//                    } catch (IndexOutOfBoundsException e) {
//                        return false;
//                    }
//
//                } else {
//                    return false;
//                }
//            } catch (IndexOutOfBoundsException e) {
//                return false;
//            }
//
//        }
//        return true;
//    }
//

//
//    /**
//     * Check if fields is valid and place it, only for manual board, uses isValidField
//     *
//     * @param sc
//     * @param board
//     * @param sh
//     * @param shipName
//     */
//    public void checkAndPlaceManual(Scanner sc, Board board, Ship sh, String shipName, Player player, int shipIndexInArrayList) {
//        ArrayList<String> positions = new ArrayList<>();
//        System.out.println("Enter a field");
//        String randomField = sc.nextLine();
//        boolean validField = false;
//        while (board.getFieldIndex(randomField) == -1 || !validField) {
//            if (isValidField(sh, board, randomField)) {
//                for (int j = 0; j < sh.getSize(); j++) {
//                    char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
//                    int randomColumn = -1;
//                    for (int z = 0; z < board.getColumns(); z++) {
//                        if (alphabet[z] == (randomField.charAt(0))) {
//                            randomColumn = z;
//                        }
//                    }
//                    int randomRow = Character.getNumericValue((randomField.charAt(1)));
//                    String randomCoordinate = alphabet[randomColumn + j] + Integer.toString(randomRow);
//                    board.getFields().get(board.getFieldIndex(randomCoordinate)).setState(boardPosition.positionState.SHIP);
//                    board.getFields().get(board.getFieldIndex(randomCoordinate)).setShipType(shipName);
//                    positions.add(randomCoordinate);
//                }
//                ArrayList<ArrayList<? extends Ship>> shipLists = player.getShipArrayList();
//                switch (shipName) {
//                    case "CV":
//                        shipLists.get(0).get(shipIndexInArrayList).setPositions(positions);
//                        player.setShipArrayList(shipLists);
//
//                        break;
//                    case "BB":
//
//                        shipLists.get(1).get(shipIndexInArrayList).setPositions(positions);
//                        player.setShipArrayList(shipLists);
//                        break;
//                    case "DD":
//                        shipLists.get(2).get(shipIndexInArrayList).setPositions(positions);
//                        player.setShipArrayList(shipLists);
//                        break;
//                    case "SV":
//                        shipLists.get(3).get(shipIndexInArrayList).setPositions(positions);
//                        player.setShipArrayList(shipLists);
//                        break;
//                    case "PV":
//                        shipLists.get(4).get(shipIndexInArrayList).setPositions(positions);
//                        player.setShipArrayList(shipLists);
//                        break;
//
//                    default:
//                        System.out.println("Something went wrong in the switch trying to assign ships to the player");
//                }
//
//                System.out.println(board.toString());
//                validField = true;
//            } else {
//
//                System.out.println("Enter valid field");
//                randomField = sc.nextLine();
//            }
//        }
//    }
//
//    public boolean gameHasWinner(Player p0, Player p1) {
//        if ((p0.getPoints() == (2 * 5 + 3 * 4 + 5 * 3 + 8 * 2 + 10 * 1 + 28)) || (p1.getPoints() == (2 * 5 + 3 * 4 + 5 * 3 + 8 * 2 + 10 * 1 + 28))) {
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//    public void playSinglePlayerGame(Player p0, Player p1) {
//
//
//        // print out player board
//        System.out.println("Your full board\n" + unHideBoard(getBoard(0), true).toString());
//        //print out computer board
//        System.out.println("Opponent board\n" + getBoard(1).toString());
//        while (!gameHasWinner(p0, p1)) {
//            p0.fire(p0, p1);
//
//            p1.fire(p1, p0);
//// print out player boards (one with full visibility)
//            System.out.println("Your full board\n" + unHideBoard(getBoard(0), true).toString());
//
//            System.out.println("What your opponent sees of your board\n" + getBoard(0).toString());
//            //print out computer board
//            System.out.println("Opponents board\n" + getBoard(1).toString());
//            System.out.println("The current scores are:\n" + p0.getName() + ": " + p0.getPoints() + "\n" + p1.getName() + ": " + p1.getPoints());
//        }
//        if (p0.getPoints() == (2 * 5 + 3 * 4 + 5 * 3 + 8 * 2 + 10 * 1 + 28)) {
//            System.out.println(p0.getName() + " has won!");
//        } else {
//            System.out.println(p1.getName() + " has won!");
//        }
//
//
//    }
//
//
//    // for testing purposes
//    public static void main(String[] args) {
//        Board board = new Board(true);
//        board.toString();
//        Game game = new Game(true);
//        game.playSinglePlayerGame(game.getPlayer(0), game.getPlayer(1));


//        // print out player board
//        System.out.println(game.getBoard(0).toString());
//        //print out computer board
//        System.out.println(game.getBoard(1).toString());
//
//        // print out positions off first cv in cv-list of humanPlayer, for testing purposes
//        System.out.println(game.getPlayer(0).getShipArrayList().get(0).get(0).getPositions().get(0));
//        System.out.println(game.getPlayer(0).getShipArrayList().get(0).get(1).getPositions().get(0));

}





