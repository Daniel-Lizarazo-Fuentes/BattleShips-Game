package game.players;
import game.ships.*;
import game.board.*;

public class randomComputerPlayer implements Player {

        private final String name = "Random Computer Player";
        private int points;

        /**
         * @ensures the Player starts with 0 points
         */
        public randomComputerPlayer () {
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
