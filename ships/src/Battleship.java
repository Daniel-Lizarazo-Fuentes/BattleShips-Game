import java.util.ArrayList;

public class Battleship extends Ship {
    private static final int SIZE=4;

    public Battleship(String name, ArrayList<String> positions) {
        super(SIZE, SIZE, name, positions);
    }


}
