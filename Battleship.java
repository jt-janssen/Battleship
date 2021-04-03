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

    char[][] board;

    public static void main(String[] args) {
        Battleship board= new Battleship();
        board.setShips();
        board.attack();

    }


    Battleship(){
        //creates a 12x12 matrix
        board = new char[12][12];
        for(int i = 0; i < 12; i++){
            for(int j = 0; j < 12; j++){
                board[i][j] = '.';
            }
        }
    }

    private void dropBomb(String pos){
        if(getTile(pos) == 's'){
            setTile(pos, 'h');
            System.out.println(isShipSunk(pos));
        } else if (getTile(pos) == '.'){
            setTile(pos, 'm');
        }
    }

    private boolean isShipSunk(String pos){
        int i = 0;
        int leftCounter = 0;
        int rightCounter = 0;
        int aboveCounter = 0;
        int belowCounter = 0;
        if(areTilesAround(pos, "not", "not", "not", "not")){
            return true;    //all tiles around are non-ships
        } else if(areTilesAround(pos, "not", "ship", "not", "ship")){
            //above and below are non-ships, left and right are ship tiles - a combo of 2 other if statements below
            while(getTileRight(pos, leftCounter) != 'm' && getTileRight(pos, leftCounter) != '.'){
                if(getTileRight(pos, leftCounter) == 's'){
                    return false;
                }
                leftCounter++;
            }
            while(getTileRight(pos, rightCounter) != 'm' && getTileRight(pos, rightCounter) != '.'){
                if(getTileRight(pos, rightCounter) == 's'){
                    return false;
                }
                rightCounter++;
            }
            return true;
        } else if(areTilesAround(pos, "not", "not", "not", "ship")){
            while(getTileLeft(pos, i) != 'm' && getTileLeft(pos, i) != '.'){
                if(getTileLeft(pos, i) == 's'){
                    return false;
                }
                i++;
            }
            return true;   
        } else if(areTilesAround(pos, "not", "ship", "not", "not")){
            while(getTileRight(pos, i) != 'm' && getTileRight(pos, i) != '.'){
                if(getTileRight(pos, i) == 's'){
                    return false;
                }
                i++;
            }
            return true; 
        } else if(areTilesAround(pos, "ship", "not", "ship", "not")){
            while(getTileBelow(pos, belowCounter) != 'm' && getTileBelow(pos, belowCounter) != '.'){
                if(getTileBelow(pos, belowCounter) == 's'){
                    return false;
                }
                belowCounter++;
            }
            below = true; 
            while(getTileAbove(pos, aboveCounter) != 'm' && getTileAbove(pos, aboveCounter) != '.'){
                if(getTileAbove(pos, aboveCounter) == 's'){
                    return false;
                }
                aboveCounter++;
            }
            return true; 
        } else if(areTilesAround(pos, "not", "not", "ship", "not")){
            while(getTileBelow(pos, i) != 'm' && getTileBelow(pos, i) != '.'){
                if(getTileBelow(pos, i) == 's'){
                    return false;
                }
                i++;
            }
            return true;   
        } else if(areTilesAround(pos, "ship", "not", "not", "not")){
            while(getTileAbove(pos, i) != 'm' && getTileAbove(pos, i) != '.'){
                if(getTileAbove(pos, i) == 's'){
                    return false;
                }
                i++;
            }
            return true;    
        } else {
            throw new IllegalArgumentException("Something went wrong idk");
        }
    }

    void attack(){
        boolean shipsAlive = true;
        while(shipsAlive){
            System.out.print("Tile to attack: ");
            dropBomb(keys.nextLine());
            printBoard();
        }
    }

    void setShips(){
        printBoard();
        // for(int numOfShips = 1; numOfShips <= 4; numOfShips++){
        //     System.out.print("Position of Ship " + numOfShips + " (1x1): ");
        //     placeShip(1, true, keys.nextLine());
        //     printBoard();
        // }
        // for(int numOfShips = 5; numOfShips <= 8; numOfShips++){
        //     System.out.print("Position of Ship " + numOfShips + " (2x1): ");
        //     placeShip(2, true, keys.nextLine());
        //     printBoard();
        // }
        // for(int numOfShips = 8; numOfShips <= 9; numOfShips++){
        //     System.out.print("Position of Ship " + numOfShips + " (3x1): ");
        //     placeShip(3, true, keys.nextLine());
        //     printBoard();
        // }
        System.out.print("Position of Ship 10 (4x1): ");
        placeShip(4, false, keys.nextLine());
        printBoard();
    } 

    private void placeShip(int length, boolean isVertical, String pos){
        //checks for correct length
        if(length < 1){
            length = 1;
        } else if (length > 4){
            length = 4;
        }

        //checks for position in relation to board
        switch(length){
            case 2:{
                if(isVertical && getRow(pos) >= 9){
                    throw new IllegalArgumentException("ship cannot be placed here");
                } else if (!isVertical && getCol(pos) >= 9){
                    throw new IllegalArgumentException("ship cannot be placed here");
                }
                break;
            }
            case 3:{
                if(isVertical && getRow(pos) >= 8){
                    throw new IllegalArgumentException("ship cannot be placed here");
                } else if (!isVertical && getCol(pos) >= 8){
                    throw new IllegalArgumentException("ship cannot be placed here");
                }
                break;
            }
            case 4:{
                if(isVertical && getRow(pos) >= 7){
                    throw new IllegalArgumentException("ship cannot be placed here");
                } else if (!isVertical && getCol(pos) >= 7){
                    throw new IllegalArgumentException("ship cannot be placed here");
                }
                break;
            }
        }

        String currentPos;
        boolean canBePlaced = true;
        //checks tiles to see if it can be placed in relation to other ships
        if(!isVertical){
            for(int i = 0; i < length; i++){
                currentPos = getPosRight(pos, i);
                if(getTile(currentPos) == 's' || getTileAbove(currentPos, 1) == 's' || getTileBelow(currentPos, 1) == 's'){
                    canBePlaced = false;   //checks if a ship is already in that position or above or below
                    System.out.println("Ship cannot be placed here.");
                }
                if(i == 0 && getTileLeft(currentPos, 1) == 's' ){ //checks if there is a ship to the left
                    canBePlaced = false;
                    System.out.println("Ship cannot be placed here.");
                }
                if(i == length - 1 && getTileRight(currentPos, 1) == 's' ){ //checks if there is a ship to the right
                    canBePlaced = false;
                    System.out.println("Ship cannot be placed here.");
                }
            }
        } else if(isVertical){
            for(int i = 0; i < length; i++){
                currentPos = getPosBelow(pos, i);
                if(getTile(currentPos) == 's' || getTileLeft(currentPos, 1) == 's' || getTileRight(currentPos, 1) == 's'){  //checks if ship is already in that pos or to either side
                    canBePlaced = false;
                    System.out.println("Ship cannot be placed here.");
                }
                if(i == 0 && getTileAbove(currentPos, 1) == 's'){    //checks if there is a ship above
                    canBePlaced = false;
                    System.out.println("Ship cannot be placed here.");
                }
                if(i == length - 1 && getTileBelow(currentPos, 1) == 's'){    //checks if there is a ship below
                    canBePlaced = false;
                    System.out.println("Ship cannot be placed here.");
                }
            }
        }

        //Places ship tiles
        if(isVertical && canBePlaced){
            for(int i = 0; i < length; i++){
                board[getRow(pos)+ i][getCol(pos)] = 's';
            }
        } else if(!isVertical && canBePlaced){
            for(int i = 0; i < length; i++){
                board[getRow(pos)][getCol(pos) + i] = 's';
            }
        }
    }

    void printBoard(){
        System.out.println("     A   B   C   D   E   F   G   H   I   J");
        for(int boardRow = 0; boardRow < 10; boardRow++){
            System.out.println("   +---+---+---+---+---+---+---+---+---+---+");

            if(boardRow != 9){
                System.out.print(" " + (boardRow + 1) + " ");
            } else {
                System.out.print(10 + " ");
            }
            System.out.print("|");
            for(int i = 0; i < 10; i++){
                switch(board[boardRow + 1][i + 1]){
                    case 's':{
                        System.out.print(ANSI_BLUE + " ⛴ " + ANSI_RESET);
                        break;
                    }
                    case 'h':{
                        System.out.print(ANSI_RED + " X " + ANSI_RESET);
                        break;
                    }
                    case 'm':{
                        System.out.print(ANSI_GREEN + " O " + ANSI_RESET);
                        break;
                    }
                    case '.':{
                        System.out.print("   ");
                        break;
                    }
                }
                System.out.print("|");
            }
            System.out.print("\n");
        }
        System.out.println("   +---+---+---+---+---+---+---+---+---+---+");
    }

    private int getRow(String pos){
        return Integer.parseInt(pos.substring(1));
    }

    private int getCol(String pos){
        return (int) pos.charAt(0) - 64;
    }
    private String getPosBelow(String pos, int n){
        return String.valueOf((char) pos.charAt(0)) + (Integer.parseInt(pos.substring(1)) + n);
    }
    private String getPosAbove(String pos, int n){
        return String.valueOf((char) pos.charAt(0)) + (Integer.parseInt(pos.substring(1)) - n);
    }
    private String getPosLeft(String pos, int n){
        return String.valueOf((char) (pos.charAt(0) - n)) + (Integer.parseInt(pos.substring(1)));
    }
    private String getPosRight(String pos, int n){
        return String.valueOf((char) (pos.charAt(0) + n)) + (Integer.parseInt(pos.substring(1)));
    }
    private char getTile(String pos){
        return board[getRow(pos)][getCol(pos)];
    }
    private void setTile(String pos, char value){
        board[getRow(pos)][getCol(pos)] = value;
    }
    private char getTileAbove(String pos, int x){
        return board[getRow(pos) - x][getCol(pos)];
    }
    private char getTileBelow(String pos, int x){
        return board[getRow(pos) + x][getCol(pos)];
    }
    private char getTileRight(String pos, int x){
        return board[getRow(pos)][getCol(pos) + x];
    }
    private char getTileLeft(String pos, int x){
        return board[getRow(pos)][getCol(pos) - x];
    }
    private char getTileUL(String pos){
        return board[getRow(pos) - 1][getCol(pos) - 1];
    }
    private char getTileUR(String pos){
        return board[getRow(pos) - 1][getCol(pos) + 1];
    }
    private char getTileBL(String pos){
        return board[getRow(pos) - 1][getCol(pos) + 1];
    }
    private char getTileBR(String pos){
        return board[getRow(pos) + 1][getCol(pos) + 1];
    }
    private boolean areTilesAround(String pos, String above, String right, String below, String left){
        char tileAbove1, tileAbove2, tileRight1, tileRight2, tileBelow1, tileBelow2, tileLeft1, tileLeft2;
        if(above.equals("ship")){
            tileAbove1 = 's';
            tileAbove2 = 'h';
        } else {
            tileAbove1 = 'm';
            tileAbove2 = '.';
        }
        if(right.equals("ship")){
            tileRight1 = 's';
            tileRight2 = 'h';
        } else {
            tileRight1 = 'm';
            tileRight2 = '.';
        }
        if(below.equals("ship")){
            tileBelow1 = 's';
            tileBelow2 = 'h';
        } else {
            tileBelow1 = 'm';
            tileBelow2 = '.';
        }
        if(left.equals("ship")){
            tileLeft1 = 's';
            tileLeft2 = 'h';
        } else {
            tileLeft1 = 'm';
            tileLeft2 = '.';
        }
        return (getTileAbove(pos, 1) == tileAbove1 || getTileAbove(pos, 1) == tileAbove2) && (getTileBelow(pos, 1) == tileBelow1 || getTileBelow(pos, 1) == tileBelow2) && (getTileLeft(pos, 1) == tileLeft1 || getTileLeft(pos, 1) == tileLeft2) && (getTileRight(pos, 1) == tileRight1 || getTileRight(pos, 1) == tileRight2);
    }
}