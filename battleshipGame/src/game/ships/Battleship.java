package game.ships;

import java.util.ArrayList;
/**
 * --------------------------------------------------------------------------------------------
 * The second largest ship, has a size of 4 which doesn't change. It's a child class of ship (explaining the extends).
 * The size constant is used to give size and hitPoints (which at creation of a ship are equal to size) to
 * the super so it can create a ship.
 * --------------------------------------------------------------------------------------------
 */
public class Battleship extends Ship {
    private static final int SIZE = 4;

    public Battleship(String name, ArrayList<String> positions) {
        super(SIZE, SIZE, name, positions);
    }


}
