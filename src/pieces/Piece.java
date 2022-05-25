package pieces;

import board.Board;

import javax.swing.*;
import java.util.Objects;

public abstract class Piece {
    String color;

    public Piece(String color) {
        this.color = color;
    }

    public String getColor() {return color;}

    public boolean move(Board board, int previousX, int previousY, int destinationX, int destinationY){
        boolean destinationEmpty = board.getBoardElement(destinationX,destinationY) == null;

        if(!Objects.equals(color, board.getWhoseTurn())){return false;}
        if(previousX == destinationX && previousY == destinationY){return false;}
        if(!board.willStopCheck(previousX, previousY, destinationX,destinationY)) return false;
        if(destinationEmpty) return displacement(board, previousX, previousY, destinationX, destinationY);
        else if(Objects.equals(board.getBoardElement(destinationX,destinationY).color, color)) return false;
        else return kill(board, previousX, previousY, destinationX, destinationY);
    }

    public boolean moveDiagonally(Board board, int previousX, int previousY, int destinationX, int destinationY){
        if(previousX == destinationX && previousY == destinationY){return false;}
        int deltaX = Math.abs(destinationX - previousX);
        int deltaY = Math.abs(destinationY - previousY);
        if(deltaY != deltaX) {return  false;}
        return clearDiagonally(board, previousX, previousY, destinationX, destinationY);
    }

    private boolean clearDiagonally(Board board, int previousX, int previousY, int destinationX, int destinationY){
        int charX = 1, charY = 1, distance = Math.abs(previousY-destinationY);
        if(previousX-destinationX>0){ charX = -1;}
        if(previousY-destinationY>0){ charY = -1;}
        return clearWay(board,previousX,previousY,charX,charY,distance);
    }

    public boolean moveVertically(Board board, int previousX, int previousY, int destinationX, int destinationY){
        if(previousX == destinationX && previousY == destinationY){return false;}
        int deltaX = Math.abs(destinationX - previousX);
        int deltaY = Math.abs(destinationY - previousY);
        if(deltaY != 0 && deltaX != 0) {return  false;}
        return clearVertically(board, previousX, previousY, destinationX, destinationY);
    }

    private boolean clearVertically(Board board, int previousX, int previousY, int destinationX, int destinationY) {
        int charX = 1, charY = 1, distance = 1;
        if(previousX-destinationX==0){
            charX = 0;
            if(previousY-destinationY>0) charY = -1;
            distance = Math.abs(previousY-destinationY);
        }
        if(previousY-destinationY==0){
            charY = 0;
            if(previousX-destinationX>0) charX = -1;
            distance = Math.abs(previousX-destinationX);
        }
        return clearWay(board,previousX,previousY,charX,charY,distance);
    }

    private boolean clearWay(Board board, int previousX, int previousY, int charX, int charY, int distance){
        for(int i = 0 ;i < distance-1; i++, previousX+=charX,previousY+=charY){
            if(board.getBoardElement(previousX+charX,previousY+charY) != null){return false;}
        }
        return true;
    }

    abstract public ImageIcon image();
    abstract public boolean displacement(Board board, int previousX, int previousY, int destinationX, int destinationY);
    abstract public boolean kill(Board board, int previousX, int previousY, int destinationX, int destinationY);
}
