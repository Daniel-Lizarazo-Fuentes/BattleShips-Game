public class Carrier extends Ship {
    String position0;
    String position1;
    String position2;
    String position3;
    String position4;

    public Carrier(int size, int hitPoints, String name, String position0,
                   String position1,
                   String position2,
                   String position3,
                   String position4) {
        super(size, hitPoints, name);
        setPosition0(position0);
        setPosition1(position1);
        setPosition2(position2);
        setPosition3(position3);
        setPosition4(position4);

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

    /**
     *
     * @param position
     */
    public void setPosition2(String position) {
        this.position2 = position;
    }

    /**
     *
     * @param position
     */
    public void setPosition3(String position) {
        this.position3 = position;
    }

    /**
     *
     * @param position
     */
    public void setPosition4(String position) {
        this.position4 = position;
    }
}
