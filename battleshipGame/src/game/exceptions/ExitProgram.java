package game.exceptions;

public class ExitProgram extends Exception {

    private static final long serialVersionUID = -7278082936441803850L;

    public ExitProgram(String msg) {
        super(msg);
    }
}