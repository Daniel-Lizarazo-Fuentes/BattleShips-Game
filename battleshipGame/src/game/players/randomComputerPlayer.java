package game.players;
import game.ships.*;
import game.board.*;

public class randomComputerPlayer implements Player {

        private final String name = "Random Player";
        private int points;

        /**
         * @ensures the Player starts with 0 points
         */
        public randomComputerPlayer () {
            this.points = 0;
        }

        /**
         * Getter for the name of the player
         * @returns the name of the player
         */
        @Override
        public String getName() {
            return name;
        }

        /**
         * Getter for the points of the player
         * @returns the points of the player
         */
        @Override
        public int getPoints() {
            return points;
        }

        /**
         * Adds points of a type of ship to the points of the player
         * @param sh ship which was destroyed
         */
        @Override
        public void addPoints(Ship sh) {

        }

        /**
         * Lets the player shot to his intended place
         * @returns whether his shot was possible and completed successful
         */
        @Override
        public void fire() {

        }

}
