/*
SHIP CLASS
int array for cells
no of hits

-----
set array cells
check hits(int guess)


GAME CLASS
user input
 */

/*
             for ship class
DECLARE an int array to hold location place , call it   shipLocation
DECLARE int to store no. of hits and assign it to 0 , call it  numOfHits
-----
DECLARE setShipLocation() setter method that takes randomly genreted 3 cells array
DECLARE checkhit() method that compare user input to the ship array location cells and return miss, hit, kill
-----
METHOD void setShipLocation( int[] loc)
             shipLocation = loc;

METHOD String checkHit( int guess)
       GET the user guess as an int parameter
       COMPARE user guess with every element of array
            IF guess matches
               INCREMENT numOfHits by 1
               RETURN hit
            ELSE
                RETURN miss
            IF numOfHits = 3 or array lenght
               RETUEN kill
               BREAK


 */

//   TEST CODE
//package ch5.src;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.*;

//class GameHelp {
//    public int getUserInput(String prompt) {
//        System.out.print(prompt + ": ");
//        Scanner scanner = new Scanner(System.in);
//        return scanner.nextInt();
//    }
//}

public class MyFinalGame {


        private GameHelp helper = new GameHelp();
        private ArrayList<Ship> ships= new ArrayList<Ship>();
        private int numOfGuesses = 0;

        private void setUpGame (){
            Ship one = new Ship();
            Ship two = new Ship();
            Ship three = new Ship();

            one.setName("ONE");
            two.setName("TWO");
            three.setName("THREE");

            ships.add(one);
            ships.add(two);
            ships.add(three);

            System.out.println("this r INSTRUCTIONS");

            for (Ship ship : ships) {
                ArrayList<String> newLocation = helper.placeStartup(3);
                ship.setShipLocation(newLocation);
            }

        }

        private void startPlay(){
            while(!ships.isEmpty()){
                String userguess = helper.getUserInput("kha ho skta h yrr ");
                checkGuess(userguess);
            }
            gameEnd();
        }

        private void checkGuess(String g){
            numOfGuesses++;
            String result = "miss";
            for (Ship i : ships){
                result = i.checkHit(g);
                if(result.equals("hit")){
                    break;
                }
                if(result.equals("kill")){
                    ships.remove(i);
                    break;
                }
            }
            System.out.println(result);
        }
        private void gameEnd(){
            System.out.println("u killed all ships, & nw u r papi ");
            if (numOfGuesses<= 20 ){
                System.out.println("waah, bhot jldi bhai");
            }else{
                System.out.println("chii bhai, game khelna bhi nhi aata");
            }
            System.out.println("u took " + numOfGuesses + " guesses..");
        }

    public static void main (String[] args) {
        MyFinalGame hh = new MyFinalGame();
        hh.setUpGame();
        hh.startPlay();
    }
        /*
        GameHelp helper = new GameHelp();

        Ship theShip = new Ship();

        int randomNo = (int) (Math.random() * 5);
        int[] location = {randomNo, randomNo +1, randomNo +2};

        theShip.setShipLocation(location);

        int numOfGuesses = 0;
        boolean isAlive = true;

        while(isAlive) {
            int userguess =  helper.getUserInput("enter a number");//get input from user by command panel
            numOfGuesses++;
            String result = theShip.checkHit(userguess);
            if (result.equals("kill")) {
                System.out.println("u killed the ship");
                System.out.println("u take " + numOfGuesses + " chances to shink the ship :)");
                isAlive = false;

            } else {
                System.out.println(result);

            }
        }

         */


}

class Ship {
    private String name;
    private ArrayList<String> shipLocation;

    public void setName( String n){
        name = n;
    }
    public void setShipLocation(ArrayList<String> locs){
        shipLocation = locs;
    }

    public String checkHit(String guess){
        String result = "miss";
        int index = shipLocation.indexOf(guess);
        if (index>= 0){
            result = "hit";
            shipLocation.remove(index);
        }
        if (shipLocation.isEmpty()){
            result ="kill";
            System.out.println("u sunk " + name );
        }
        return result;
    }

    /*
    private int[] ShipLocation;
    private int numOfHits = 0;

    void setShipLocation( int[] loc){
        ShipLocation = loc;
    }

    public String checkHit(int guess) {
        String result1 = "miss";
        for (int cell : ShipLocation) {
            if (guess == cell) {
                numOfHits++;
                result1 = "hit";
                break;
            }

        }
        if (numOfHits == ShipLocation.length) {
            result1 = "kill";
        }
        return result1;
    }

     */
}

class GameHelp {
    private static final String ALPHABET = "abcdefg";
    private static final int GRID_LENGTH = 7;
    private static final int GRID_SIZE = 49;
    private static final int MAX_ATTEMPTS = 200;

    static final int HORIZONTAL_INCREMENT = 1;          // A better way to represent these two
    static final int VERTICAL_INCREMENT = GRID_LENGTH;  // things is an enum (see Appendix B)

    private final int[] grid = new int[GRID_SIZE];
    private final Random random = new Random();

    private int startupCount = 0;

    public String getUserInput(String prompt) {
        System.out.print(prompt + ": ");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine().toLowerCase();
    } //end getUserInput

    public ArrayList<String> placeStartup(int startupSize) {
        // holds index to grid (0 - 48)
        int[] startupCoords = new int[startupSize];         // current candidate co-ordinates
        int attempts = 0;                                   // current attempts counter
        boolean success = false;                            // flag = found a good location?

        startupCount++;                                     // nth Startup to place
        int increment = getIncrement();                     // alternate vert & horiz alignment

        while (!success & attempts++ < MAX_ATTEMPTS) {      // main search loop
            int location = random.nextInt(GRID_SIZE);         // get random starting point

            for (int i = 0; i < startupCoords.length; i++) {  // create array of proposed coords
                startupCoords[i] = location;                    // put current location in array
                location += increment;                          // calculate the next location
            }
            System.out.println("Trying: " + Arrays.toString(startupCoords));

            if (startupFits(startupCoords, increment)) {      // startup fits on the grid?
                success = coordsAvailable(startupCoords);       // ...and locations aren't taken?
            }                                                 // end loop
        }                                                   // end while

        savePositionToGrid(startupCoords);                  // coords passed checks, save
        ArrayList<String> alphaCells = convertCoordsToAlphaFormat(startupCoords);
        System.out.println("Placed at: "+ alphaCells);
        return alphaCells;
    } //end placeStartup

    boolean startupFits(int[] startupCoords, int increment) {
        int finalLocation = startupCoords[startupCoords.length - 1];
        if (increment == HORIZONTAL_INCREMENT) {
            // check end is on same row as start
            return calcRowFromIndex(startupCoords[0]) == calcRowFromIndex(finalLocation);
        } else {
            return finalLocation < GRID_SIZE;                 // check end isn't off the bottom
        }
    } //end startupFits

    boolean coordsAvailable(int[] startupCoords) {
        for (int coord : startupCoords) {                   // check all potential positions
            if (grid[coord] != 0) {                           // this position already taken
                System.out.println("position: " + coord + " already taken.");
                return false;                                   // NO success
            }
        }
        return true;                                        // there were no clashes, yay!
    } //end coordsAvailable

    void savePositionToGrid(int[] startupCoords) {
        for (int index : startupCoords) {
            grid[index] = 1;                                  // mark grid position as 'used'
        }
    } //end savePositionToGrid

    private ArrayList<String> convertCoordsToAlphaFormat(int[] startupCoords) {
        ArrayList<String> alphaCells = new ArrayList<String>();
        for (int index : startupCoords) {                   // for each grid coordinate
            String alphaCoords = getAlphaCoordsFromIndex(index); // turn it into an "a0" style
            alphaCells.add(alphaCoords);                      // add to a list
        }
        return alphaCells;                                  // return the "a0"-style coords
    } // end convertCoordsToAlphaFormat

    String getAlphaCoordsFromIndex(int index) {
        int row = calcRowFromIndex(index);                  // get row value
        int column = index % GRID_LENGTH;                   // get numeric column value

        String letter = ALPHABET.substring(column, column + 1); // convert to letter
        return letter + row;
    } // end getAlphaCoordsFromIndex

    private int calcRowFromIndex(int index) {
        return index / GRID_LENGTH;
    } // end calcRowFromIndex

    private int getIncrement() {
        if (startupCount % 2 == 0) {                        // if EVEN Startup
            return HORIZONTAL_INCREMENT;                      // place horizontally
        } else {                                            // else ODD
            return VERTICAL_INCREMENT;                        // place vertically
        }
    } //end getIncrement
} //end class
