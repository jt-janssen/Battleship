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
        board = new char[11][11];
        for(int i = 0; i < 11; i++){
            for(int j = 0; j < 11; j++){
                board[i][j] = '.';
            }
        }
    }

    void dropBomb(String pos){
        if(board[getRow(pos)][getCol(pos)] == 's'){
            board[getRow(pos)][getCol(pos)] = 'h';
        } else if (board[getRow(pos)][getCol(pos)] == '.' || board[getRow(pos)][getCol(pos)] == 'b'){
            board[getRow(pos)][getCol(pos)] = 'm';
        } else {
            //do nothing
        }
    }

    // private boolean checkForSinkage(String pos){

    // }

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

    void placeShip(int length, boolean isVertical, String pos){
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
                if(board[getRow(pos)][getCol(pos) + i] == 's'){
                    throw new IllegalArgumentException("Ship cannot be placed here");   //checks if a ship is already in that position
                }
                board[getRow(pos)][getCol(pos) + i] = 's';  //sets ship tile

                board[getRow(pos) - 1][getCol(pos) + i] = 'b';  //sets barriers above and below
                board[getRow(pos) + 1][getCol(pos) + i] = 'b';

                if(i == 0){ //places barriers left
                    board[getRow(pos)][getCol(pos)  - 1+ i] = 'b';
                    board[getRow(pos) + 1][getCol(pos) -1+ i] = 'b';
                    board[getRow(pos) - 1][getCol(pos)-1 + i] = 'b';
                }
                if(i == length - 1){    //places barriers right 
                    board[getRow(pos)][getCol(pos)  + 1+ i] = 'b';
                    board[getRow(pos) + 1][getCol(pos) + 1 + i] = 'b';
                    board[getRow(pos) - 1][getCol(pos) + 1 + i] = 'b';
                }
            }
        } else if(isVertical){
            for(int i = 0; i < length; i++){
                if(board[getRow(pos)+ i][getCol(pos)] == 's'){
                    throw new IllegalArgumentException("Ship cannot be placed here");
                }
                board[getRow(pos)+ i][getCol(pos)] = 's';   //sets ship tile
                board[getRow(pos)+ i][getCol(pos) - 1] = 'b';   //sets barriers to either side
                board[getRow(pos)+ i][getCol(pos) + 1] = 'b';
                if(i == 0){ //creates barriers above
                    board[getRow(pos)+ i - 1][getCol(pos)] = 'b';
                    board[getRow(pos)+ i - 1][getCol(pos)- 1] = 'b';
                    board[getRow(pos)+ i - 1][getCol(pos)+ 1] = 'b';
                }
                if(i == length - 1){    //creates barriers below
                    board[getRow(pos)+ i + 1][getCol(pos)] = 'b';
                    board[getRow(pos)+ i + 1][getCol(pos)- 1] = 'b';
                    board[getRow(pos)+ i + 1][getCol(pos)+ 1] = 'b';
                }

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
                    case 'b':{
                        System.out.print(" b ");
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
                case 'b':{
                    System.out.print(" b ");
                    break;
                }
            }
            System.out.print("|");
        }
        System.out.print("\n");
        System.out.println("   +---+---+---+---+---+---+---+---+---+---+");
    }

    private int getRow(String pos){
        return Integer.parseInt(pos.substring(1)) - 1;
    }

    private int getCol(String pos){
        return (int) pos.charAt(0) - 65;
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