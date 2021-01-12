public class Destroyer extends Ship {
    String position0;
    String position1;
    String position2;

    public Destroyer(int size, int hitPoints, String name, String position0, String position1,
                     String position2) {
        super(size, hitPoints, name);
        setPosition0(position0);
        setPosition1(position1);
        setPosition2(position2);
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


}
