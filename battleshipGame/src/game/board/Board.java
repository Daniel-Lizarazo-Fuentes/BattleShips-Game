package game.board;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Locale;

public class Board implements Serializable {
    private static final int ROWS = 10;
    private static final int COLUMNS = 15;

    private ArrayList<boardPosition> fields;
    private ArrayList<String> coordinates;


    public Board(boolean visible) {
        this.fields = new ArrayList<>();
        this.coordinates = new ArrayList<>();
        fillCoordinates();
       // System.out.println(coordinates.toString());
        fillFields(visible);
    }

    /**
     * @return
     */
    public int getRows() {
        return this.ROWS;
    }

    /**
     * @return
     */
    public int getColumns() {
        return this.COLUMNS;
    }


    /**
     * Fills the field of the game.board, all are empty but visibility depends on parameter
     *
     * @param visible indicates whether the whole game.board is visible or not
     */
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

    /**
     * @ensures result!=null
     */
    public ArrayList<boardPosition> getFields() {
        return this.fields;
    }

    /**
     * Find the index of a coordinate in fields
     *
     * @param s
     * @return
     */
    public int getFieldIndex(String s) {
        int result = -1;
        //System.out.println(getFields().size()); // for testing purposes
        for (int i = 0; i < getFields().size(); i++) {
            // System.out.println(s+"TEST: "+getFields().get(i).getCoordinate()); //for testing purposes
            if ((getFields().get(i).getCoordinate()).equals(s.toLowerCase(Locale.ROOT))) {
                result = i;
            }
        }
        //System.out.println(result); //for testing purposes
        return result;
    }

    /**
     * @ensures result!=null
     */
    public ArrayList<String> getCoordinates() {
        return this.coordinates;
    }

    /**
     * Method to set individual coordinate in coordinates
     *
     * @param a
     * @param i
     */
    public void setCoordinate(char a, int i) {
        getCoordinates().add(a + Integer.toString(i));
    }

    /**
     * method to fill coordinates Arraylist with the correct coordinates
     */
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

    /**
     * Method which overrides regular toString so that a game.board with the correct visibility is returned
     *
     * @ensures result!=null;
     */
    @Override
    public String toString() {
        int i = 0; // for column numbers which reset
        int j = 0; // for row numbers to print
        boolean[] columnFullyHidden = new boolean[COLUMNS];
        int o = 0; // for iterating through array
        // fill array with true as the game.board is empty at first
        for (boolean b : columnFullyHidden) {
            columnFullyHidden[o] = true;
            o++;
        }
        // string to append to the result as the column letters are decided on at the end
        String append = Integer.toString(j) + " |";

//for all boardPositions in fields we add them to the string
        for (int k = 0; k < getFields().size(); k++) {
            boardPosition p = getFields().get(k);
            // if the end of the columns is reached we go to a new row and add the row number
            if (i == COLUMNS) {
                j++;
                append += "\n" + Integer.toString(j) + " |";
                i = 0;

            }
            // check if hidden and based on that print the state or hidden
            if (p.getPositionHidden()) {
                append += "..... |";
            } else {
                switch (p.getState()) {
                    case SHIP:

                        append += "  " + p.getShipType() + "  |";

                        break;
                    case EMPTY:
                        append += " EMPTY|";
                        break;
                    case WRECK:
                        append += " WRECK|";
                        break;
                    default:
                }
                columnFullyHidden[i] = false;
            }
            i++;
        }
        String result = "";
// add letters for all columns and depending on if column is not hidden change width
        for (int z = 0; z < COLUMNS; z++) {
            char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();

            result += "      " + alphabet[z];


        }
        result += "\n" + append;
        // Enter board representation so the board can be constructed from this string as well, syntax (BoardRepresentation:coordinate;positionState;hidden;shipType;isHit,...) so the string can be split using : into boardreperesentation (just for the case it's printed), then using , into seperate boardpostions and within boardpositon using ; the aspects of it can be seperated
//        result += "\nBoardRepresentation:";
//        for (boardPosition bp : this.getFields()) {
//            result += bp.getCoordinate() + ";" + bp.getState() + ";" + bp.getPositionHidden() + ";" + bp.getShipType() + ";" + bp.getIsHit() + ",";
//        }
        return result;
    }


}

// used for testing if fillFields worked and if upon creating new game.board object the game.board would be empty
//public static void main(String[] args) {
//    Board boardVisble = new Board(true);
//    System.out.println(boardVisble.toString());
//    System.out.println(boardVisble.getFieldIndex("a1"));
//      Board boardHidden = new Board(false);
//
//     System.out.println(boardHidden.toString());
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
