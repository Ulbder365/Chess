package pieces;

import board.Board;

import javax.swing.*;
import java.util.Objects;

public class King extends Piece{

    public King(String color) {
        super(color);
    }

    private boolean kingLogic(int previousX, int previousY, int destinationX, int destinationY){
        int deltaX = Math.abs(previousX - destinationX);
        int deltaY = Math.abs(previousY - destinationY);
        return deltaX < 2 && deltaY < 2;
    }

    private boolean canICastle(Board board, int previousX, int previousY, int destinationX, int destinationY){
       if(Objects.equals(color, "white")) return castleCondition(board, previousX, previousY, destinationX, destinationY,  0);
       else if (Objects.equals(color, "black")) return castleCondition(board, previousX, previousY, destinationX, destinationY,  7);
       return false;
    }

    private boolean castleCondition(Board board, int previousX, int previousY, int destinationX, int destinationY, int row){
        if(previousX == 4 && previousY == row)
            if((destinationX == 7 || destinationX == 0) && destinationY == row && board.getBoardElement(destinationX,destinationY)!=null)
                if(Objects.equals(board.getBoardElement(destinationX, destinationY).color, color) && board.getBoardElement(destinationX, destinationY).getClass().getName().equals("pieces.Rook")){
                    if(destinationX == 7)
                        if(board.treatedSquare(board.getWhoseTurn(), 4, row)
                                ||board.treatedSquare(board.getWhoseTurn(), 5, row)
                                || board.treatedSquare(board.getWhoseTurn(), 6, row))
                            return false;
                    if(destinationX == 0)
                        if(board.treatedSquare(board.getWhoseTurn(), 1, row)
                                ||board.treatedSquare(board.getWhoseTurn(), 2, row)
                                || board.treatedSquare(board.getWhoseTurn(), 3, row)
                                || board.treatedSquare(board.getWhoseTurn(), 4, row))
                            return false;
                    return moveVertically(board, previousX, previousY, destinationX, destinationY);
                }
        return false;
    }

    @Override
    public boolean move(Board board, int previousX, int previousY, int destinationX, int destinationY) {
        if(!Objects.equals(color, board.getWhoseTurn())){return false;}
        if (board.treatedSquare(color, destinationX, destinationY)) {return false;}
        if(canICastle(board, previousX, previousY, destinationX, destinationY)){return true;}
        return super.move(board, previousX, previousY, destinationX, destinationY);
    }

    @Override
    public boolean displacement(Board board, int previousX, int previousY, int destinationX, int destinationY) {
        return kingLogic(previousX, previousY, destinationX, destinationY);
    }

    @Override
    public boolean kill(Board board, int previousX, int previousY, int destinationX, int destinationY) {
        return kingLogic(previousX, previousY, destinationX, destinationY);
    }

    @Override
    public ImageIcon image() {
        if(Objects.equals(this.color, "white")) return new ImageIcon("./images/Pieces/White/king.png");
        return new ImageIcon("./images/Pieces/Black/king.png");
    }
}
