package pieces;

import board.Board;

import javax.swing.*;
import java.util.Objects;

public class Queen extends Piece{
    public Queen(String color) {
        super(color);
    }

    @Override
    public boolean displacement(Board board, int previousX, int previousY, int destinationX, int destinationY) {
        return  moveVertically(board, previousX, previousY, destinationX, destinationY) ||
                moveDiagonally(board, previousX, previousY, destinationX, destinationY);
    }

    @Override
    public boolean kill(Board board, int previousX, int previousY, int destinationX, int destinationY) {
        return  moveVertically(board, previousX, previousY, destinationX, destinationY) ||
                moveDiagonally(board, previousX, previousY, destinationX, destinationY);
    }

    @Override
    public ImageIcon image() {
        if(Objects.equals(this.color, "white")) return new ImageIcon("./images/Pieces/White/queen.png");
        return new ImageIcon("./images/Pieces/Black/queen.png");
    }
}
