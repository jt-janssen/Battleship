import java.util.Scanner;

public class Board {

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


    Board(){
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
            isShipSunk(pos);
        } else if (getTile(pos) == '.' || getTile(pos) == 'b'){
            setTile(pos, 'm');
        } else {
            //do nothing
        }
    }

    private boolean isShipSunk(String pos){
        if((getTileAbove(pos, 1) == 'm' || getTileAbove(pos, 1) == '.') && (getTileRight(pos, 1) == 'm' || getTileRight(pos, 1) == '.') && (getTileBelow(pos, 1) == 'm' || getTileBelow(pos, 1) == '.') && (getTileLeft(pos, 1) == 'm' || getTileLeft(pos, 1) == '.')){
            return true;
        } else {
            return false;
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
        for(int numOfShips = 1; numOfShips <= 4; numOfShips++){
            System.out.print("Position of Ship " + numOfShips + " (1x1): ");
            placeShip(1, false, keys.nextLine());
            printBoard();
        }
        for(int numOfShips = 5; numOfShips <= 8; numOfShips++){
            System.out.print("Position of Ship " + numOfShips + " (1x1): ");
            placeShip(2, false, keys.nextLine());
            printBoard();
        }
        for(int numOfShips = 8; numOfShips <= 9; numOfShips++){
            System.out.print("Position of Ship " + numOfShips + " (1x1): ");
            placeShip(3, false, keys.nextLine());
            printBoard();
        }
        System.out.print("Position of Ship 10 (4x1): ");
        placeShip(4, false, keys.nextLine());
        printBoard();
    } 

    private void placeShip(int length, boolean isVertical, String pos){
        if(length < 1){
            length = 1;
        } else if (length > 4){
            length = 4;
        }

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
        if(!isVertical){
            for(int i = 0; i < length; i++){
                if(board[getRow(pos)][getCol(pos) + i] == 's' || board[getRow(pos) - 1][getCol(pos) + i] == 's' ||board[getRow(pos) + 1][getCol(pos) + i] == 's'){
                    throw new IllegalArgumentException("Ship cannot be placed here");   //checks if a ship is already in that position or above or below
                }
                if(i == 0 && board[getRow(pos)][getCol(pos) - 1 + i] == 's' ){ //checks if there is a ship to the left
                    throw new IllegalArgumentException("Ship cannot be placed here");
                } else if(i == length - 1 && board[getRow(pos)][getCol(pos) + 1 + i] == 's' ){ //checks if there is a ship to the right
                    throw new IllegalArgumentException("Ship cannot be placed here");
                }
                board[getRow(pos)][getCol(pos) + i] = 's';  //sets ship tile
            }
        } else if(isVertical){
            for(int i = 0; i < length; i++){
                if(board[getRow(pos)+ i][getCol(pos)] == 's' || board[getRow(pos)+ i][getCol(pos) - 1] == 's' || board[getRow(pos)+ i][getCol(pos) + 1] == 's'){  //checks if ship is already in that pos or to either side
                    throw new IllegalArgumentException("Ship cannot be placed here");
                }
                if(i == 0 && board[getRow(pos) - 1 + i][getCol(pos)] == 's'){    //checks if there is a ship above
                    throw new IllegalArgumentException("Ship cannot be placed here");
                } else if(i == length -1 && board[getRow(pos) + 1 + i][getCol(pos)] == 's'){    //checks if there is a ship below
                    throw new IllegalArgumentException("Ship cannot be placed here");
                }
                board[getRow(pos)+ i][getCol(pos)] = 's';   //sets ship tile
            }
        }
    }

    void printBoard(){
        System.out.println("     A   B   C   D   E   F   G   H   I   J");
        for(int boardRow = 0; boardRow < 9; boardRow++){
            System.out.println("   +---+---+---+---+---+---+---+---+---+---+");

            System.out.print(" " + (boardRow + 1) + " ");
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

        System.out.print(10);
        System.out.print(" |");
        for(int i = 0; i < 10; i++){
            switch(board[10][i + 1]){
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
        System.out.println("   +---+---+---+---+---+---+---+---+---+---+");
    }

    private int getRow(String pos){
        return Integer.parseInt(pos.substring(1));
    }

    private int getCol(String pos){
        return (int) pos.charAt(0) - 64;
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

}