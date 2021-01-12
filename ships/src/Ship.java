//abstract parent class ship
public abstract class Ship {
    private int size;
    private int hitPoints;
    private String name;

    public Ship(int size, int hitPoints, String name) {
        this.size = size;
        this.hitPoints = hitPoints;
        this.name = name;
    }

    /**
     *
     * @param size
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     *
     * @return
     */
    public int getSize() {
        return this.size;
    }
    /**
     *
     * @param hitPoints
     */
    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    /**
     *
     * @return
     */
    public int getHitPoints() {
        return this.hitPoints;
    }
    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     */
    public String getName() {
        return this.name;
    }

    /**
     *
     * @return
     */
     public boolean sunk(){
         if(getHitPoints() <= 0){
             return true;
         } else {
             return false;
         }
     }
}
