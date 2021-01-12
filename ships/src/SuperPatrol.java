
public class SuperPatrol extends Ship {
    String position0;
    String position1;

    public SuperPatrol(int size, int hitPoints, String name, String position0, String position1) {
        super(size, hitPoints, name);
        setPosition0(position0);
        setPosition1(position1);

    }

    /**
     *
     * @param position
     */
    public void setPosition0(String position) {
        this.position0 = position;
    }

    /**
     *
     * @param position
     */
    public void setPosition1(String position) {
        this.position1 = position;
    }
}