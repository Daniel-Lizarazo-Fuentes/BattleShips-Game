
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public abstract class abstractBoard {
    private static final int ROWS = 10;
    private static final int COLUMNS = 15;

    private ArrayList<position> fields = new ArrayList<>();
    private ArrayList<String> coordinates = new ArrayList<>();


    public abstractBoard() {

    }


    public ArrayList<String> getCoordinates() {
        return this.coordinates;
    }

    public void setCoordinate(char a, int i) {
        getCoordinates().add(a + Integer.toString(i));
    }

    public void fillCoordinates() {
        char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        // for each row we add all the letters with the row number
        // (note that we also could've chosen to do each collum and add the row numbers as that would result in exactly the same)
        for (int k = 0; k < ROWS; k++) {
            for (int i = 0; i < COLUMNS; i++) {

                setCoordinate(alphabet[i], k);

            }

        }
    }


    abstract String boardToString();

}

// used for testing if fillCoordinates worked
//    public static void main(String[] args) {
//        ArrayList<String> coordinates = new ArrayList<>();
//
//
//        char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
//        // for each row we add all the letters with the row number
//        // (note that we also could've chosen to do each collum and add the row numbers as that would result in exactly the same)
//
//        for (int k = 0; k < 10; k++) {
//            for (int i = 0; i < 15; i++) {
//
//                coordinates.add(alphabet[i]+Integer.toString(k));
//
//            }
//
//        }
//        int f=0;
//        while (f<coordinates.size()) {
//            System.out.println(coordinates.get(f));
//            f++;
//        }
//    }
