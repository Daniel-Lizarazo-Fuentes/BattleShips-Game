package game.ships;

import java.util.ArrayList;

/**
 * --------------------------------------------------------------------------------------------
 * Largest ship, has a size of 5 which doesn't change. It's a child class of ship (explaining the extends).
 * The size constant is used to give size and hitPoints (which at creation of a ship are equal to size) to
 * the super so it can create a ship.
 * --------------------------------------------------------------------------------------------
 */
public class Carrier extends Ship {
    private static final int SIZE=5;

    public Carrier(String name, ArrayList<String> positions) {
        super(SIZE, SIZE, name, positions);


    }


}
