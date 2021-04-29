import java.util.Random;
import java.util.Scanner;


public class Battleship {

    static final String ANSI_RESET = "\u001B[0m";
    static final String ANSI_BLACK = "\u001B[30m";
    static final String ANSI_RED = "\u001B[31m";
    static final String ANSI_GREEN = "\u001B[32m";
    static final String ANSI_YELLOW = "\u001B[33m";
    static final String ANSI_BLUE = "\u001B[34m";
    static final String ANSI_PURPLE = "\u001B[35m";
    static final String ANSI_CYAN = "\u001B[36m";
    static final String ANSI_WHITE = "\u001B[37m";

    Scanner keys = new Scanner(System.in);
    Random random = new Random();

    String hits = "";

    boolean showShips;

    Board board;


    public static void main(String[] args) {
        Battleship battleship = new Battleship(10);
        //System.out.println("\n" + battleship.hits);

        battleship.attack();
    }




    Battleship(int dimension){
        board = new Board(dimension, ' ', false);
        defineChars();
        setShipsRandomly();

      
    }

    private void defineChars(){
        board.setDictionary('h', ANSI_RED + "X" + ANSI_RESET);
        board.setDictionary(' ', ANSI_GREEN + "O" + ANSI_RESET);
    }

    void attack(){
        int shipsSunk = 0;
        boolean previousGuess = false;
        board.printBoard();
        while(shipsSunk < 10){
            if(previousGuess){
                System.out.println(ANSI_RED + "\t   You sunk my Battleship!" + ANSI_RESET);
            }
            System.out.print("\n\t     Tile to attack: ");
            if(previousGuess = dropBomb(keys.nextLine())){
                shipsSunk++;
            }
            board.printBoard();
        }
        System.out.println(ANSI_GREEN + "\t\t   You won!" + ANSI_RESET);
    }

    private boolean dropBomb(String pos){
        board.changeVisibility(pos, true);
        return board.getTile(pos) == 'h' && isShipSunk(pos);
    }

    private void tileSweep(){
        boolean tileChanged = true;
        String currentPos;
        while(tileChanged){
            tileChanged = false;
            for(int i = 0; i < board.dimension * board.dimension; i++){
                currentPos = board.iterateTiles(i);
                if(board.isPosVisible(currentPos) && board.getTile(currentPos) == 'h' && (!board.isPosVisible(board.getPosNorth(currentPos, 1)) || !board.isPosVisible(board.getPosSouth(currentPos, 1)) || !board.isPosVisible(board.getPosEast(currentPos, 1)) || !board.isPosVisible(board.getPosWest(currentPos, 1)) || !board.isPosVisible(board.getPosNW(currentPos)) || !board.isPosVisible(board.getPosNE(currentPos)) || !board.isPosVisible(board.getPosSW(currentPos)) || !board.isPosVisible(board.getPosSE(currentPos)))){
                    board.changeVisibility(board.getPosNorth(currentPos, 1), true);
                    board.changeVisibility(board.getPosSE(currentPos), true);
                    board.changeVisibility(board.getPosSW(currentPos), true);
                    board.changeVisibility(board.getPosNE(currentPos), true);
                    board.changeVisibility(board.getPosNW(currentPos), true);
                    board.changeVisibility(board.getPosWest(currentPos, 1), true);
                    board.changeVisibility(board.getPosEast(currentPos, 1), true);
                    board.changeVisibility(board.getPosSouth(currentPos, 1), true);
                    tileChanged = true;
                }
            }
        }
    }

    private boolean isShipSunk(String pos){
        int i = 0;
        int leftCounter = 0;
        int rightCounter = 0;
        int aboveCounter = 0;
        int belowCounter = 0;
        if(areTilesAround(pos, ' ', ' ', ' ' ,' ')){
            tileSweep();
            return true;    //all tiles around are non-ships
        } else if(areTilesAround(pos, ' ', 'h', ' ' ,'h')){
            //above and below are non-ships, left and right are ship tiles - a combo of 2 other if statements below
            while(board.getTile(board.getPosEast(pos, leftCounter)) != ' '){
                if(board.getTile(board.getPosEast(pos, leftCounter)) == 'h' && !board.isPosVisible(board.getPosEast(pos, leftCounter))){
                    return false;
                }
                leftCounter++;
            }
            while(board.getTile(board.getPosEast(pos, rightCounter)) != ' '){
                if(board.getTile(board.getPosEast(pos, rightCounter)) == 'h' && !board.isPosVisible(board.getPosEast(pos, rightCounter))){
                    return false;
                }
                rightCounter++;
            }
            tileSweep();
            return true;
        } else if(areTilesAround(pos, ' ', ' ', ' ' ,'h')){
            while(board.getTile(board.getPosWest(pos, i)) != ' '){
                if(board.getTile(board.getPosWest(pos, i)) == 'h' && !board.isPosVisible(board.getPosWest(pos, i))){
                    return false;
                }
                i++;
            }
            tileSweep();
            return true;   
        } else if(areTilesAround(pos, ' ', 'h', ' ' ,' ')){
            while(board.getTile(board.getPosEast(pos, i)) != ' '){
                if(board.getTile(board.getPosEast(pos, i)) == 'h' && !board.isPosVisible(board.getPosEast(pos, i))){
                    return false;
                }
                i++;
            }
            tileSweep();
            return true; 
        } else if(areTilesAround(pos, 'h', ' ', 'h' ,' ')){
            while(board.getTile(board.getPosSouth(pos, belowCounter)) != ' '){
                if(board.getTile(board.getPosSouth(pos, belowCounter)) == 'h' && !board.isPosVisible(board.getPosSouth(pos, belowCounter))){
                    return false;
                }
                belowCounter++;
            }
            while(board.getTile(board.getPosNorth(pos, aboveCounter)) != ' '){
                if(board.getTile(board.getPosNorth(pos, aboveCounter)) == 'h' && !board.isPosVisible(board.getPosNorth(pos, aboveCounter))){
                    return false;
                }
                aboveCounter++;
            }
            tileSweep();
            return true; 
        } else if(areTilesAround(pos, ' ', ' ', 'h' ,' ')){
            while(board.getTile(board.getPosSouth(pos, i)) != ' '){
                if(board.getTile(board.getPosSouth(pos, i)) == 'h' && !board.isPosVisible(board.getPosSouth(pos, i))){
                    return false;
                }
                i++;
            }
            tileSweep();
            return true;   
        } else if(areTilesAround(pos, 'h', ' ', ' ' ,' ')){
            while(board.getTile(board.getPosNorth(pos, i)) != ' '){
                if(board.getTile(board.getPosNorth(pos, i)) == 'h' && !board.isPosVisible(board.getPosNorth(pos, i))){
                    return false;
                }
                i++;
            }
            tileSweep();
            return true;  
        } else {
            throw new IllegalArgumentException("Something went wrong idk");
        }
    }

    void setShips(){
        board.printBoard();
        for(int numOfShips = 1; numOfShips <= 4; numOfShips++){
            System.out.print("Position of Ship " + numOfShips + " (1x1): ");
            placeShip(1, true, keys.nextLine());
            board.printBoard();
        }
        for(int numOfShips = 5; numOfShips <= 7; numOfShips++){
            System.out.print("Position of Ship " + numOfShips + " (2x1): ");
            placeShip(2, true, keys.nextLine());
            board.printBoard();
        }
        for(int numOfShips = 8; numOfShips <= 9; numOfShips++){
            System.out.print("Position of Ship " + numOfShips + " (3x1): ");
            placeShip(3, true, keys.nextLine());
            board.printBoard();
        }
        System.out.print("Position of Ship 10 (4x1): ");
        placeShip(4, false, keys.nextLine());
        board.printBoard();
    } 

    void setShipsRandomly(){
        for(int numOfShips = 1; numOfShips <= 4; numOfShips++){
            if(!placeShip(1, random.nextBoolean(), board.getRandomTilePos())){
                numOfShips--;
            }
        }
        for(int numOfShips = 5; numOfShips <= 7; numOfShips++){
            if(!placeShip(2, random.nextBoolean(), board.getRandomTilePos())){
                numOfShips--;
            }
        }
        for(int numOfShips = 8; numOfShips <= 9; numOfShips++){
            if(!placeShip(3, random.nextBoolean(), board.getRandomTilePos())){
                numOfShips--;
            }
        }
        while(!placeShip(4, random.nextBoolean(), board.getRandomTilePos())){
            //loop the argument
        }
    } 



    private boolean placeShip(int length, boolean isVertical, String pos){  //TODO add back a var for show output when placing tiles by hand
        //checks for correct length
        if(length < 1){
            length = 1;
        } else if (length > 4){
            length = 4;
        }

        //checks for position in relation to board
        switch(length){
            case 2:{
                if(isVertical && board.getRowIndex(pos) >= 9){
                    // System.out.println("Ship cannot be placed here.");
                    return false;
                } else if (!isVertical && board.getColIndex(pos) >= 9){
                    // System.out.println("Ship cannot be placed here.");
                    return false;
                }
                break;
            }
            case 3:{
                if(isVertical && board.getRowIndex(pos) >= 8){
                    // System.out.println("Ship cannot be placed here.");                   
                    return false;
                } else if (!isVertical && board.getColIndex(pos) >= 8){
                    // System.out.println("Ship cannot be placed here.");                   
                    return false;
                }
                break;
            }
            case 4:{
                if(isVertical && board.getRowIndex(pos) >= 7){
                    // System.out.println("Ship cannot be placed here.");                   
                    return false;
                } else if (!isVertical && board.getColIndex(pos) >= 7){
                    // System.out.println("Ship cannot be placed here.");                    
                    return false;
                }
                break;
            }
        }

        String currentPos;
        boolean canBePlaced = true;
        //checks tiles to see if it can be placed in relation to other ships
        if(!isVertical){
            for(int i = 0; i < length; i++){
                currentPos = board.getPosEast(pos, i);
                if(board.getTile(currentPos) == 'h' || board.getTile(board.getPosNorth(currentPos, 1)) == 'h' || board.getTile(board.getPosSouth(currentPos, 1)) == 'h'){
                    canBePlaced = false;   //checks if a ship is already in that position or above or below
                    // System.out.println("Ship cannot be placed here.");                    
                    return false;
                }
                if(i == 0 && (board.getTile(board.getPosWest(currentPos, 1)) == 'h' || board.getTile(board.getPosNW(currentPos)) == 'h' || board.getTile(board.getPosSW(currentPos)) == 'h')){ //checks if there is a ship to the left
                    canBePlaced = false;
                    // System.out.println("Ship cannot be placed here.");                    
                    return false;
                }
                if(i == length - 1 && (board.getTile(board.getPosEast(currentPos, 1)) == 'h' || board.getTile(board.getPosNE(currentPos)) == 'h' || board.getTile(board.getPosSE(currentPos)) == 'h')){ //checks if there is a ship to the right
                    canBePlaced = false;
                    // System.out.println("Ship cannot be placed here.");                   
                    return false;
                }
            }
        } else if(isVertical){
            for(int i = 0; i < length; i++){
                currentPos = board.getPosSouth(pos, i);
                if(board.getTile(currentPos) == 'h' || board.getTile(board.getPosWest(currentPos, 1)) == 'h' || board.getTile(board.getPosEast(currentPos, 1)) == 'h'){  //checks if ship is already in that pos or to either side
                    canBePlaced = false;
                    // System.out.println("Ship cannot be placed here.");                   
                    return false;
                }
                if(i == 0 && (board.getTile(board.getPosNorth(currentPos, 1)) == 'h' || board.getTile(board.getPosNE(currentPos)) == 'h' || board.getTile(board.getPosNW(currentPos)) == 'h')){    //checks if there is a ship above
                    canBePlaced = false;
                    // System.out.println("Ship cannot be placed here.");                    
                    return false;
                }
                if(i == length - 1 && (board.getTile(board.getPosSouth(currentPos, 1)) == 'h' || board.getTile(board.getPosSE(currentPos)) == 'h' || board.getTile(board.getPosSW(currentPos)) == 'h')){    //checks if there is a ship below
                    canBePlaced = false;
                    // System.out.println("Ship cannot be placed here.");                    
                    return false;
                }
            }
        }

        //Places ship tiles
        if(isVertical && canBePlaced){
            for(int i = 0; i < length; i++){
                board.setTile(board.getPosSouth(pos, i), 'h');
                hits += board.getPosSouth(pos, i) + " ";
            }
        } else if(!isVertical && canBePlaced){
            for(int i = 0; i < length; i++){
                board.setTile(board.getPosEast(pos, i), 'h');
                hits += board.getPosEast(pos, i) + " ";
            }
        }
        return true;
    }

    private boolean areTilesAround(String pos, char north, char east, char south, char west){
        return board.getTile(board.getPosNorth(pos, 1)) == north  && board.getTile(board.getPosSouth(pos, 1)) == south && board.getTile(board.getPosWest(pos, 1)) == west && board.getTile(board.getPosEast(pos, 1)) == east;
    }
}