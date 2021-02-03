package game.test;

import static org.junit.jupiter.api.Assertions.*;

import game.board.boardPosition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class testBoardPosition {

    private boardPosition boardPosition;

    @BeforeEach
    void setup(){
        boardPosition = new boardPosition("a5", game.board.boardPosition.positionState.EMPTY, true);
    }

    @Test
    void testGetIsHit(){
        assertEquals(boardPosition.getIsHit(),false);

        boardPosition.setIsHit(true);
        assertEquals(boardPosition.getIsHit(),true);
    }

    @Test
    void testGetShipType(){
        assertEquals(boardPosition.getShipType(),"EMPTY");

        boardPosition.setShipType("Carrier");
        assertEquals(boardPosition.getShipType(),"Carrier");
    }

    @Test
    void testGetCoordinate(){
        assertEquals(boardPosition.getCoordinate(),"a5");
    }

    @Test
    void testGetState(){
        assertEquals(boardPosition.getState(), game.board.boardPosition.positionState.EMPTY);

        boardPosition.setState(game.board.boardPosition.positionState.WRECK);
        assertEquals(boardPosition.getState(), game.board.boardPosition.positionState.WRECK);

        boardPosition.setState(game.board.boardPosition.positionState.SHIP);
        assertEquals(boardPosition.getState(), game.board.boardPosition.positionState.SHIP);

    }

    @Test
    void testPositionHidden(){
        assertEquals(boardPosition.getPositionHidden(),true);

        boardPosition.setPositionHidden(false);
        assertEquals(boardPosition.getPositionHidden(),false);
    }


}
