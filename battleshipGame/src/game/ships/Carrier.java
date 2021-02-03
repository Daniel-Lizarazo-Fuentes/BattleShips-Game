package game.ships;

import java.util.ArrayList;

/**
 * Largest ship, has a size of 5 which doesn't change
 */
public class Carrier extends Ship {
    private static final int SIZE=5;

    public Carrier(String name, ArrayList<String> positions) {
        super(SIZE, SIZE, name, positions);


    }


}
