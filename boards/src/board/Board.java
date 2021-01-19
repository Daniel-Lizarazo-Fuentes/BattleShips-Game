package board;

import java.util.ArrayList;

public class Board {
    private static final int ROWS = 10;
    private static final int COLUMNS = 15;

    private ArrayList<boardPosition> fields;
    private ArrayList<String> coordinates;


    public Board(boolean visible) {
        this.fields = new ArrayList<>();
        this.coordinates = new ArrayList<>();
        fillCoordinates();
        fillFields(visible);
    }

    public void fillFields(boolean visible) {
        for (String s : getCoordinates()) {
            boardPosition bp;
            if (visible) {
                bp = new boardPosition(s, boardPosition.positionState.EMPTY, false);
            } else {
                bp = new boardPosition(s, boardPosition.positionState.EMPTY, true);
            }
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
        int i = 0; // for column numbers which reset
        int j = 0; // for row numbers to print
        boolean[] rowEmpty = new boolean[COLUMNS];
        int o = 0; // for iterating through array
        for (boolean b : rowEmpty) {
            rowEmpty[o] = true;
            o++;
        }
        String append = Integer.toString(j) + " |";


        for (int k = 0; k < getFields().size(); k++) {
            boardPosition p = getFields().get(k);
            if (i == COLUMNS) {
                j++;
                append += "\n" + Integer.toString(j) + " |";
                i = 0;

            }
            if (p.getPositionHidden()) {
                append += ".... |";
            } else {
                switch (p.getState()) {
                    case SHIP:
                        append += " SHIP |";

                        break;
                    case EMPTY:
                        append += " EMPTY|";
                        break;
                    case WRECK:
                        append += " WRECK|";
                        break;
                    default:
                }
                rowEmpty[i] = false;
            }
            i++;
        }
        String result = "";

        for (int z = 0; z < COLUMNS; z++) {
            char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
            if (rowEmpty[z]) {
                result += "     " + alphabet[z];
            } else {
                result += "      " + alphabet[z];
            }

        }
        result += "\n" + append;
        return result;
    }


}

// used for testing if fillFields worked and if upon creating new board object the board would be empty
//public static void main(String[] args) {
//    Board boardVisble = new Board(true);
//    Board boardHidden = new Board(false);
//    System.out.println(boardVisble.toString());
//    System.out.println(boardHidden.toString());
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
