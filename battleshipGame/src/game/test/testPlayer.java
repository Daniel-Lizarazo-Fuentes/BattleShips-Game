package game.test;

import static org.junit.jupiter.api.Assertions.*;

import game.board.Board;
import game.players.Player;
import game.players.humanPlayer;
import game.players.randomComputerPlayer;
import game.ships.Ship;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class testPlayer {

    private Board board;
    private Player playerH;
    private Player playerC;
    private ArrayList<ArrayList<? extends Ship>> shipLists;
    private boolean visible = true;



    @BeforeEach
    void setup(){
        board = new Board(visible);
        playerH = new humanPlayer("Human Player", board, shipLists);
        playerC = new randomComputerPlayer(board);

    }

    @Test
    void testGetName(){
        assertEquals(playerH.getName(), "Human Player");
        // The computer player's name could not be tested because he also has a randomly generated int
    }

    @Test
    void testGetTurn(){
        assertEquals(playerH.getTurn(), false);
        this.playerH.setTurn(true);
        assertEquals(playerH.getTurn(), true);

        assertEquals(playerC.getTurn(), false);
        this.playerC.setTurn(true);
        assertEquals(playerC.getTurn(), true);
    }

    @Test
    void testGetBoard(){
        assertEquals(playerH.getBoard(),board);

        assertEquals(playerC.getBoard(),board);
    }

    @Test
    void testGetPoints(){
        assertEquals(playerH.getPoints(),0);

        this.playerH.setPoints(1);
        assertEquals(playerH.getPoints(),1);

        this.playerH.setPoints(10);
        assertEquals(playerH.getPoints(),10);

        assertEquals(playerC.getPoints(),0);

        this.playerC.setPoints(5);
        assertEquals(playerC.getPoints(),5);

        this.playerC.setPoints(16);
        assertEquals(playerC.getPoints(),16);

    }

    @Test
    void testShipList(){
        assertEquals(playerH.getShipArrayList(),shipLists);

        //The computer players does not ask for a specific arraylist of ships
    }

}
