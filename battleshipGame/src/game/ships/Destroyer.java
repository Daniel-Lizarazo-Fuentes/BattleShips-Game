package game.ships;

import java.util.ArrayList;

public class Destroyer extends Ship {
    private static final int SIZE = 3;

    public Destroyer(String name, ArrayList<String> positions) {
        super(SIZE, SIZE, name, positions);
    }


}
