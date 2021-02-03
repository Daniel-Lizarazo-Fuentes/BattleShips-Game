package game.ships;

import java.util.ArrayList;
/**
 * --------------------------------------------------------------------------------------------
 * Second smallest ship, has a size of 2 which doesn't change. It's a child class of ship (explaining the extends).
 * The size constant is used to give size and hitPoints (which at creation of a ship are equal to size) to
 * the super so it can create a ship.
 * --------------------------------------------------------------------------------------------
 */
public class SuperPatrol extends Ship {
    private static final int SIZE=2;

    public SuperPatrol(String name, ArrayList<String> positions) {
        super(SIZE, SIZE, name, positions);


    }


}