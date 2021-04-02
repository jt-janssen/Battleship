public class Battleship{

    public static void main(String[] args) {
        Board playerBoard= new Board();
        playerBoard.setShips();
        playerBoard.attack();
    }
}