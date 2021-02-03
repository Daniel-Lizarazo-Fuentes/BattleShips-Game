package game.exceptions;

public class ServerUnavailableException extends Exception {

    private static final long serialVersionUID = 42L;

    public ServerUnavailableException(String msg) {
        super(msg);
    }
}
