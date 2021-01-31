package game.server;

public class ProtocolMessages {

    //General
    public static final String CS = ";";

    public static final String CHAT = "C";
    public static final String LIST = "L";

    //Client
    public static final String JOIN = "J";
    public static final String PLAY = "P";
    public static final String MOVE = "M";

    public static final String DEPLOY = "D";
    public static final String GET = "G";
    public static final String RADAR = "W";




    public static final String NUM_OF_PLAYERS = "g";
    public static final String FIRST_PLAYER = "f";
    public static final String PLAYER_AMOUNT = "p";


    public static final String NOT_VALID = "n";
    public static final String VALID = "v";

    public static final String WINNER = "w";
    public static final String DISCONNECT_WINNER = "d";

    public static final String UNEXPECTED_MESSAGE = "u";
    public static final String PLAYER_ONE = "1";
    public static final String PLAYER_TWO = "2";




    //Server
    public static final String FAIL = "F";
    public static final String SUCCESS = "S";
    public static final String CREATED = "C";
    public static final String BEGIN = "B";
    public static final String READY = "R";
    public static final String TURN = "T";
    public static final String HIT = "H";
    public static final String END = "E";




}
