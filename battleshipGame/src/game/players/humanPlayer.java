package game.players;

import game.ships.*;
import game.board.*;

import java.util.ArrayList;

public class humanPlayer implements Player {
    private String name;
    private int points;

    private ArrayList<Carrier> carriers = new ArrayList<>();
    private ArrayList<Battleship> battleships = new ArrayList<>();
    private ArrayList<Destroyer> destroyers = new ArrayList<>();
    private ArrayList<SuperPatrol> superPatrols = new ArrayList<>();
    private ArrayList<PatrolBoat> patrolBoats = new ArrayList<>();


    /**
     * @param name of the player
     * @ensures the Player starts with 0 points
     */
    public humanPlayer(String name) {
        this.name = name;
        this.points = 0;
    }

       /**
     * Returns the name of the player
     * @ensures result=the name of the player
     */
       @Override
    public String getName(){return this.name;}

    /**
     * Returns the points of the player
     * @ensures result = the points of the player
     */
    @Override
    public int getPoints(){return this.points;}

    /**
     * Adds points based on what ship was sunk
     */
    @Override
    public void updatePoints (){}
    /**
     * Fires at specified field
     * @requires field is valid field
     */
    @Override
    public void fire(){}
}
