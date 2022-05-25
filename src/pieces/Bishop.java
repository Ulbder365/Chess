package pieces;

import board.Board;

import javax.swing.*;
import java.util.Objects;

public class Bishop extends Piece{
    public Bishop(String color) {
        super(color);
    }

    @Override
    public boolean displacement(Board board, int previousX, int previousY, int destinationX, int destinationY) {
        return moveDiagonally(board, previousX, previousY, destinationX, destinationY);
    }

    @Override
    public boolean kill(Board board, int previousX, int previousY, int destinationX, int destinationY) {
        return moveDiagonally(board, previousX, previousY, destinationX, destinationY);
    }

    @Override
    public ImageIcon image() {
        if(Objects.equals(this.color, "white")) return new ImageIcon("./images/Pieces/White/bishop.png");
        return new ImageIcon("./images/Pieces/Black/bishop.png");
    }
}
