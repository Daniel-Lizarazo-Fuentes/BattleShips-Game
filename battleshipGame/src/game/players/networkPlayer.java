package game.players;

import game.board.Board;
import game.ships.Ship;

import java.util.ArrayList;

public class networkPlayer extends humanPlayer{

    /**
     * @param name      of the player
     * @param shipLists
     * @param board
     * @ensures the Player starts with 0 points
     */
    public networkPlayer(String name, ArrayList<ArrayList<? extends Ship>> shipLists, Board board) {
        super(name, shipLists, board);
    }
}
