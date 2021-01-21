package game.players;

import game.ships.*;
import game.board.*;

public class smartComputerPlayer implements Player {
    private final String name = "Smart Player";
    private int points;

    /**
     * @ensure the Player starts with 0 points
     */
    public smartComputerPlayer() {
        points = 0;
    }

    /**
     * Getter for the name of the player
     *
     * @return the name of the player
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Getter for the points of the player
     *
     * @return the points of the player
     */
    @Override
    public int getPoints() {
        return points;
    }

    /**
     * Adds points of a type of ship to the points of the player
     *
     * @param s ship which was destroyed
     */
    @Override
    public void addPoints(Ship s) {

    }

    /**
     * Lets the player shot to his intended place
     *
     * @return whether his shot was possible and completed successful
     */
    @Override
    public void fire() {

    }
}
