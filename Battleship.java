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

    //define char variables
    static final String HIT = ANSI_RED + "X" + ANSI_RESET;
    static final String MISS = ANSI_GREEN + "O" + ANSI_RESET;
    static final String BLANK = " ";





    boolean showShips;

    Board board;


    public static void main(String[] args) {
        Battleship battleship = new Battleship(10);
        // System.out.println("\n" + battleship.hits);
        battleship.attack();
    }




    Battleship(int dimension){
        board = new Board(dimension, MISS, BLANK, false);
        setShipsRandomly();
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
        return board.getTile(pos) == HIT && isShipSunk(pos);
    }


    private boolean isShipSunk(String pos){
        int i = 0;
        int leftCounter = 0;
        int rightCounter = 0;
        int aboveCounter = 0;
        int belowCounter = 0;
        if(areTilesAround(pos, MISS, MISS, MISS ,MISS)){
            board.uncoverTilesAroundAll(HIT);
            return true;    //all tiles around are non-ships
        } else if(areTilesAround(pos, MISS, HIT, MISS ,HIT)){
            //above and below are non-ships, left and right are ship tiles - a combo of 2 other if statements below
            while(board.getTile(board.getPosEast(pos, leftCounter)) != MISS){
                if(board.getTile(board.getPosEast(pos, leftCounter)) == HIT && !board.isPosVisible(board.getPosEast(pos, leftCounter))){
                    return false;
                }
                leftCounter++;
            }
            while(board.getTile(board.getPosEast(pos, rightCounter)) != MISS){
                if(board.getTile(board.getPosEast(pos, rightCounter)) == HIT && !board.isPosVisible(board.getPosEast(pos, rightCounter))){
                    return false;
                }
                rightCounter++;
            }
            board.uncoverTilesAroundAll(HIT);
            return true;
        } else if(areTilesAround(pos, MISS, MISS, MISS ,HIT)){
            while(board.getTile(board.getPosWest(pos, i)) != MISS){
                if(board.getTile(board.getPosWest(pos, i)) == HIT && !board.isPosVisible(board.getPosWest(pos, i))){
                    return false;
                }
                i++;
            }
            board.uncoverTilesAroundAll(HIT);
            return true;   
        } else if(areTilesAround(pos, MISS, HIT, MISS ,MISS)){
            while(board.getTile(board.getPosEast(pos, i)) != MISS){
                if(board.getTile(board.getPosEast(pos, i)) == HIT && !board.isPosVisible(board.getPosEast(pos, i))){
                    return false;
                }
                i++;
            }
            board.uncoverTilesAroundAll(HIT);
            return true; 
        } else if(areTilesAround(pos, HIT, MISS, HIT ,MISS)){
            while(board.getTile(board.getPosSouth(pos, belowCounter)) != MISS){
                if(board.getTile(board.getPosSouth(pos, belowCounter)) == HIT && !board.isPosVisible(board.getPosSouth(pos, belowCounter))){
                    return false;
                }
                belowCounter++;
            }
            while(board.getTile(board.getPosNorth(pos, aboveCounter)) != MISS){
                if(board.getTile(board.getPosNorth(pos, aboveCounter)) == HIT && !board.isPosVisible(board.getPosNorth(pos, aboveCounter))){
                    return false;
                }
                aboveCounter++;
            }
            board.uncoverTilesAroundAll(HIT);
            return true; 
        } else if(areTilesAround(pos, MISS, MISS, HIT ,MISS)){
            while(board.getTile(board.getPosSouth(pos, i)) != MISS){
                if(board.getTile(board.getPosSouth(pos, i)) == HIT && !board.isPosVisible(board.getPosSouth(pos, i))){
                    return false;
                }
                i++;
            }
            board.uncoverTilesAroundAll(HIT);
            return true;   
        } else if(areTilesAround(pos, HIT, MISS, MISS ,MISS)){
            while(board.getTile(board.getPosNorth(pos, i)) != MISS){
                if(board.getTile(board.getPosNorth(pos, i)) == HIT && !board.isPosVisible(board.getPosNorth(pos, i))){
                    return false;
                }
                i++;
            }
            board.uncoverTilesAroundAll(HIT);
            return true;  
        } else {
            throw new IllegalArgumentException("Something went wrong idk");
        }
    }

    // void setShips(){
    //     board.printBoard();
    //     for(int numOfShips = 1; numOfShips <= 4; numOfShips++){
    //         System.out.print("Position of Ship " + numOfShips + " (1x1): ");
    //         placeShip(1, true, keys.nextLine());
    //         board.printBoard();
    //     }
    //     for(int numOfShips = 5; numOfShips <= 7; numOfShips++){
    //         System.out.print("Position of Ship " + numOfShips + " (2x1): ");
    //         placeShip(2, true, keys.nextLine());
    //         board.printBoard();
    //     }
    //     for(int numOfShips = 8; numOfShips <= 9; numOfShips++){
    //         System.out.print("Position of Ship " + numOfShips + " (3x1): ");
    //         placeShip(3, true, keys.nextLine());
    //         board.printBoard();
    //     }
    //     System.out.print("Position of Ship 10 (4x1): ");
    //     placeShip(4, false, keys.nextLine());
    //     board.printBoard();
    // } 

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
                if(board.getTile(currentPos) == HIT || board.getTile(board.getPosNorth(currentPos, 1)) == HIT || board.getTile(board.getPosSouth(currentPos, 1)) == HIT){
                    canBePlaced = false;   //checks if a ship is already in that position or above or below
                    // System.out.println("Ship cannot be placed here.");                    
                    return false;
                }
                if(i == 0 && (board.getTile(board.getPosWest(currentPos, 1)) == HIT || board.getTile(board.getPosNW(currentPos)) == HIT || board.getTile(board.getPosSW(currentPos)) == HIT)){ //checks if there is a ship to the left
                    canBePlaced = false;
                    // System.out.println("Ship cannot be placed here.");                    
                    return false;
                }
                if(i == length - 1 && (board.getTile(board.getPosEast(currentPos, 1)) == HIT || board.getTile(board.getPosNE(currentPos)) == HIT || board.getTile(board.getPosSE(currentPos)) == HIT)){ //checks if there is a ship to the right
                    canBePlaced = false;
                    // System.out.println("Ship cannot be placed here.");                   
                    return false;
                }
            }
        } else if(isVertical){
            for(int i = 0; i < length; i++){
                currentPos = board.getPosSouth(pos, i);
                if(board.getTile(currentPos) == HIT || board.getTile(board.getPosWest(currentPos, 1)) == HIT || board.getTile(board.getPosEast(currentPos, 1)) == HIT){  //checks if ship is already in that pos or to either side
                    canBePlaced = false;
                    // System.out.println("Ship cannot be placed here.");                   
                    return false;
                }
                if(i == 0 && (board.getTile(board.getPosNorth(currentPos, 1)) == HIT || board.getTile(board.getPosNE(currentPos)) == HIT || board.getTile(board.getPosNW(currentPos)) == HIT)){    //checks if there is a ship above
                    canBePlaced = false;
                    // System.out.println("Ship cannot be placed here.");                    
                    return false;
                }
                if(i == length - 1 && (board.getTile(board.getPosSouth(currentPos, 1)) == HIT || board.getTile(board.getPosSE(currentPos)) == HIT || board.getTile(board.getPosSW(currentPos)) == HIT)){    //checks if there is a ship below
                    canBePlaced = false;
                    // System.out.println("Ship cannot be placed here.");                    
                    return false;
                }
            }
        }

        //Places ship tiles
        if(isVertical && canBePlaced){
            for(int i = 0; i < length; i++){
                board.setTile(board.getPosSouth(pos, i), HIT);
                hits += board.getPosSouth(pos, i) + " ";
            }
        } else if(!isVertical && canBePlaced){
            for(int i = 0; i < length; i++){
                board.setTile(board.getPosEast(pos, i), HIT);
                hits += board.getPosEast(pos, i) + " ";
            }
        }
        return true;

    }

    private boolean areTilesAround(String pos, String north, String east, String south, String west){
        return board.getTile(board.getPosNorth(pos, 1)) == north  && board.getTile(board.getPosSouth(pos, 1)) == south && board.getTile(board.getPosWest(pos, 1)) == west && board.getTile(board.getPosEast(pos, 1)) == east;
    }
}