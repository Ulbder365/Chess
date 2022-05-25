package pieces;

import board.Board;

import javax.swing.*;
import java.util.Objects;

public class Pawn extends Piece{
    public Pawn(String color) {
        super(color);
    }

    private boolean twoForward(Board board, int previousX, int previousY, int destinationY){
        if(previousY == 1 && Objects.equals(color, "white") && destinationY-previousY == 2 && board.getBoardElement(previousX,previousY+1)==null) return true;
        return previousY == 6 && Objects.equals(color, "black") && destinationY - previousY == -2 && board.getBoardElement(previousX,previousY - 1) == null;
    }

    private void enPassant() {
        //still not implemented
    }
    
    @Override
    public boolean displacement(Board board, int previousX, int previousY, int destinationX, int destinationY) {
        if(previousX != destinationX){return false;}
        else if(twoForward(board, previousX, previousY, destinationY)) {return true;}
        else if(Objects.equals(color, "black") && previousY - destinationY == 1){return true;}
        else return Objects.equals(color, "white") && destinationY - previousY == 1;
    }

    @Override
    public boolean kill(Board board, int previousX, int previousY, int destinationX, int destinationY) {
        if(destinationX - previousX != -1 && destinationX - previousX != 1){return false;}
        else if(Objects.equals(color, "black") && previousY - destinationY == 1){return true;}
        else return Objects.equals(color, "white") && destinationY - previousY == 1;
    }

    @Override
    public ImageIcon image() {
        if(Objects.equals(this.color, "white")) return new ImageIcon("./images/Pieces/White/pawn.png");
        return new ImageIcon("./images/Pieces/Black/pawn.png");
    }

}
