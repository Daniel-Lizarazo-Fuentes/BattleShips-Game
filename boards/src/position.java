public class position {
    public static enum positionState {
        EMPTY,
        SHIP,
        WRECK
    }
    private String coordinate;
    private positionState state;
    private boolean positionHidden;

    public position(positionState state, boolean hidden){
        this.state=state;
        this.positionHidden=hidden;
    }

}
