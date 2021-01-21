package game.players;
import game.ships.*;
import game.board.*;
public interface Player {
    /**
     * Returns the name of the player
     * @ensures result=the name of the player
     */
    public String getName();

    /**
     * Returnts the points of the player
     * @ensures result = the points of the player
     */
    public int getPoints();

    /**
     * Adds points based on what ship was sunk
     */
    public void addPoints (Ship s);
    /**
     * Fires at specified field
     * @requires field is valid field
     */
    public void fire();

}
