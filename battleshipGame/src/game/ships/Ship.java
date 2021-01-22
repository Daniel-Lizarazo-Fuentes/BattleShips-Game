package game.ships;

import java.util.ArrayList;
//abstract parent class ship
public abstract class Ship {
    private int size;
    private int hitPoints;
    private String name;
    private ArrayList<String> positions = new ArrayList<String>();

    public Ship(int size, int hitPoints, String name, ArrayList<String> positions) {
        this.size = size;
        this.hitPoints = hitPoints;
        this.name = name;
        this.positions=positions;
    }

    /**
     * @requires  1 <= size <=5;
     * @param size
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * @ensures size >=1;
     * @return
     */
    public int getSize() {
        return this.size;
    }
    /**
     * @requires  0 <= hitPoints <=5;
     * @param hitPoints
     */
    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    /**
     * @ensures  0 <= hitPoints <=5;
     * @return
     */
    public int getHitPoints() {
        return this.hitPoints;
    }
    /**
     * @requires name!=null;
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @ensures result!=null;
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
         if(getHitPoints() == 0){
             return true;
         } else {
             return false;
         }
     }

    /**
     * for changing an existing position
     * @requires positions.size()-1>=i;
     *
     * @param pos string position to replace an existing position
     * @param i
     */
     public void setPosition(String pos,int i){
         this.positions.set(i,pos);
     }

    /**
     * for adding a new position
     * @param pos string position to add to the list
     */
     public void addPosition(String pos){
         this.positions.add(pos);
     }

    /**
     * for getting the Arraylist of positions
     * @ensures result!=null;
     * @return
     */
     public ArrayList<String> getPositions(){
         return this.positions;
     }
    /**
     * for setting the Arraylist of positions
     * @ensures result!=null;
     * @return
     */
    public void setPositions(ArrayList<String> positions){
        this.positions=positions;
    }

}
