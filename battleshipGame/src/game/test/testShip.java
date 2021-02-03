package game.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import game.ships.*;

import java.util.ArrayList;

class testShips {

    ArrayList<String> positions = new ArrayList<>();


    private Ship battleship = new Battleship("battleship",positions);
    private Ship carrier = new Carrier("carrier",positions );
    private Ship destroyer =  new Destroyer("destroyer",positions );
    private Ship patrolBoat =  new PatrolBoat("patrolBoat",positions );
    private Ship superPatrol =  new SuperPatrol("superPatrol",positions );


    @Test
    void testGetSize() {
        assertEquals(battleship.getSize(), 4);
        assertEquals(carrier.getSize(), 5);
        assertEquals(destroyer.getSize(), 3);
        assertEquals(patrolBoat.getSize(), 1);
        assertEquals(superPatrol.getSize(), 2);
    }

    @Test
    void testGetHitPoints() {
        assertEquals(battleship.getHitPoints(), 4);
        assertEquals(carrier.getHitPoints(), 5);
        assertEquals(destroyer.getHitPoints(), 3);
        assertEquals(patrolBoat.getHitPoints(), 1);
        assertEquals(superPatrol.getHitPoints(), 2);
    }

    @Test
    void testGetName() {
        assertEquals(battleship.getName(), "battleship");
        assertEquals(carrier.getName(), "carrier");
        assertEquals(destroyer.getName(), "destroyer");
        assertEquals(patrolBoat.getName(), "patrolBoat");
        assertEquals(superPatrol.getName(), "superPatrol");
    }

    @Test
    void testGetPositions() {
        assertEquals(battleship.getPositions(), positions);
        assertEquals(carrier.getPositions(), positions);
        assertEquals(destroyer.getPositions(), positions);
        assertEquals(patrolBoat.getPositions(), positions);
        assertEquals(superPatrol.getPositions(), positions);
    }
}