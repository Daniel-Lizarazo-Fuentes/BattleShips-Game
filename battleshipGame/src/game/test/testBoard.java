package game.test;

import static org.junit.jupiter.api.Assertions.*;

import game.board.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class testBoard {

    private Board board;


    @BeforeEach
    void setup(){
        board = new Board(true);
    }

    @Test
    void testRowColumn(){
        assertEquals(board.getColumns(),15);
        assertEquals(board.getRows(),10);
    }

    @Test
    void testIndex(){
        assertEquals(board.getFieldIndex("a1"), 15);
    }

   // Fields and Coordinates were tested using a main method
}
