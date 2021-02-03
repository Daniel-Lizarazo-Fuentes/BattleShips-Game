package game.board;

import java.io.Serializable;

/**
 * --------------------------------------------------------------------------------------------
 * This class is the blueprint for a field on the board, it is characterized by it's coordinate
 * and stores all relevant information to a field on the board.
 * --------------------------------------------------------------------------------------------
 * Information stored is:
 * - The state (meaning if the field is empty, has a ship or has a wreck)
 * - The shipType describes which particular ship is connected to this field
 * - The positionHidden stores if a field is visible, currently if a position is hit it's set to
 *   visible, but if more time would have been given a radar could have been implemented which
 *   would change this as well while not chaning isHit
 * - The isHit boolean describes if a field was fired on or not.
 * --------------------------------------------------------------------------------------------
 */
public class boardPosition implements Serializable {
    public enum positionState {
        EMPTY,
        SHIP,
        WRECK
    }
    
    private String coordinate;
    private positionState state;
    private String shipType;
    private boolean positionHidden;
    private boolean isHit;

    public boardPosition(String coordinate, positionState state, boolean hidden) {
        this.coordinate = coordinate;
        this.state = state;
        this.positionHidden = hidden;
        this.shipType = "EMPTY";
        this.isHit = false;
    }

    public void setShipType(String shipType) {
        this.shipType = shipType;
    }


    public boolean getIsHit() {
        return isHit;
    }

    public void setIsHit(boolean hit) {
        isHit = hit;
    }

    public String getShipType() {
        return this.shipType;
    }

    /**
     * @ensures result !=null;
     */
    public String getCoordinate() {
        return this.coordinate;
    }

    // no setter needed for coordinate as it will not change after initialization

    /**
     * @ensures result!=null;
     */
    public positionState getState() {
        return this.state;
    }

    /**
     * @param state the state of the coordinate (i.e. empty field, field with ship, field with wreck)
     * @requires state is a valid positionState
     */
    public void setState(positionState state) {
        this.state = state;
    }

    /**
     *
     */
    public boolean getPositionHidden() {
        return this.positionHidden;
    }

    /**
     * @param positionHidden if the position is visible to the player
     * @requires positionHidden == true && positionHidden==false;
     */
    public void setPositionHidden(boolean positionHidden) {
        this.positionHidden = positionHidden;
    }
}
