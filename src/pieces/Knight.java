package pieces;

import board.Board;

import javax.swing.*;
import java.util.Objects;

public class Knight extends Piece {
    public Knight(String color) {
        super(color);
    }

    private boolean knightLogic(int previousX, int previousY, int destinationX, int destinationY){
        if(Math.abs(destinationX - previousX) != 1 && Math.abs(destinationX - previousX) != 2){return false;}
        else if(Math.abs(destinationY - previousY) != 1 && Math.abs(destinationY - previousY) != 2){return false;}
        else return Math.abs(destinationX - previousX) + Math.abs(destinationY - previousY) == 3;
    }

    @Override
    public boolean displacement(Board board, int previousX, int previousY, int destinationX, int destinationY) {
        return knightLogic(previousX, previousY, destinationX, destinationY);
    }

    @Override
    public boolean kill(Board board, int previousX, int previousY, int destinationX, int destinationY) {
        return knightLogic(previousX, previousY, destinationX, destinationY);
    }

    @Override
    public ImageIcon image() {
        if(Objects.equals(this.color, "white")) return new ImageIcon("./images/Pieces/White/knight.png");
        return new ImageIcon("./images/Pieces/Black/knight.png");
    }
}
