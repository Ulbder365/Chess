import board.Board;
import gui.GUI;
import pieces.Piece;

public class Main {
    public static void main(String[] args) {
        Board board = new Board(new Piece[8][8]);
        GUI frame = new GUI(board);
    }
}
