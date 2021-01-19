public class position {
    public static enum positionState {
        EMPTY,
        SHIP,
        WRECK
    }

    private String coordinate;
    private positionState state;
    private boolean positionHidden;

    public position(String coordinate, positionState state, boolean hidden) {
        this.coordinate = coordinate;
        this.state = state;
        this.positionHidden = hidden;
    }

    public String getCoordinate() {
        return this.coordinate;
    }

    public positionState getState() {
        return this.state;
    }
    public boolean getPositionHidden(){
        return this.positionHidden;
    }
}
