public class PatrolBoat extends Ship {
    String position0;

    public PatrolBoat(int size, int hitPoints, String name, String position0) {
        super(size, hitPoints, name);
        setPosition0(position0);

    }

    /**
     *
     * @param position0
     */
    public void setPosition0(String position0) {
        this.position0 = position0;
    }
}
