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

    char[][] board;

    Board(){
        board = new char[10][10];
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                board[i][j] = '.';
            }
        }
    }

    void printString(){
        System.out.println("     A   B   C   D   E   F   G   H   I   J");
        for(int boardRow = 0; boardRow < 9; boardRow++){
            System.out.println("   +---+---+---+---+---+---+---+---+---+---+");

            System.out.print(" " + (boardRow + 1) + " ");
            System.out.print("|");
            for(int i = 0; i < 10; i++){
                switch(board[boardRow][i]){
                    case 's':{
                        System.out.print(ANSI_BLUE + " S " + ANSI_RESET);
                        break;
                    }
                    case 'h':{
                        System.out.print(ANSI_RED + " H " + ANSI_RESET);
                        break;
                    }
                    case 'm':{
                        System.out.print(ANSI_GREEN + " M " + ANSI_RESET);
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
            switch(board[9][i]){
                case 's':{
                    System.out.print(ANSI_BLUE + " S " + ANSI_RESET);
                    break;
                }
                case 'h':{
                    System.out.print(ANSI_RED + " H " + ANSI_RESET);
                    break;
                }
                case 'm':{
                    System.out.print(ANSI_GREEN + " M " + ANSI_RESET);
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
}