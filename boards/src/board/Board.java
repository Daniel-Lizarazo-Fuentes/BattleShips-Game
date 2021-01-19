package board;

import java.util.ArrayList;

public class Board {
    private static final int ROWS = 10;
    private static final int COLUMNS = 15;

    private ArrayList<boardPosition> fields;
    private ArrayList<String> coordinates;


    public Board() {
        this.fields = new ArrayList<>();
        this.coordinates = new ArrayList<>();
        fillCoordinates();
        fillFields();
    }

    public void fillFields() {
        for (String s : getCoordinates()) {
            // boardPosition bp = new boardPosition(s, boardPosition.positionState.EMPTY, false); // only used for testing if toString works correctly
            boardPosition bp = new boardPosition(s, boardPosition.positionState.EMPTY, true);
            getFields().add(bp);
        }
    }

    public ArrayList<boardPosition> getFields() {
        return this.fields;
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

    @Override
    public String toString() {
        String result = "|";
        int i = 0;
        for (int k = 0; k < getFields().size(); k++) {
            boardPosition p = getFields().get(k);
            if (i == COLUMNS) {
                result += "\n|";
                i = 0;
            }
            if (p.getPositionHidden()) {
                result += p.getCoordinate() + "....|";
            } else {
                switch (p.getState()) {
                    case SHIP:
                        result += p.getCoordinate() + " SHIP|";
                        break;
                    case EMPTY:
                        result += p.getCoordinate() + " EMPTY|";
                        break;
                    case WRECK:
                        result += p.getCoordinate() + " WRECK|";
                        break;
                    default:
                }
            }
            i++;
        }

        return result;
    }


}

// used for testing if fillFields worked and if upon creating new board object the board would be empty
//public static void main(String[] args) {
//    Board board = new Board();
//    System.out.println(board.toString());
//}

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
