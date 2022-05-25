package board;

import gui.MovesPanel;
import pieces.Piece;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Move {
    public List<Move> movesList = new ArrayList<>();
    MovesPanel movesPanel = new MovesPanel(movesList);

    int previousX;
    int previousY;
    int destinationX;
    int destinationY;
    Piece oldPiece;
    Piece newPiece;

    public MovesPanel getMovesPanel() {return movesPanel;}
    public int getDestinationX() { return destinationX; }
    public int getDestinationY() { return destinationY; }
    public Piece getNewPiece() { return newPiece; }
    public int getPreviousX() {return previousX;}
    public int getPreviousY() {return previousY;}

    public Move(List<Move> moves) {
        this.movesList = moves;
    }

    public Move(int previousX, int previousY, int destinationX, int destinationY, Piece oldPiece, Piece newPiece) {
        this.previousX = previousX;
        this.previousY = previousY;
        this.destinationX = destinationX;
        this.destinationY = destinationY;
        this.oldPiece = oldPiece;
        this.newPiece = newPiece;
    }

    public void saveMove(int previousX, int previousY, int destinationX, int destinationY, Piece oldPiece, Piece newPiece) {
        movesList.add(new Move(previousX, previousY, destinationX, destinationY, oldPiece, newPiece));
        movesPanel.drawMovesPanel(movesList);
    }
    public Move getLastMove() {
        int size = movesList.size();
        return movesList.get(size-1);
    }
    public void restoreMoveByClick(Board board, Move move){
        if(move.oldPiece!= null && move.newPiece!=null){
            if(Objects.equals(move.oldPiece.getColor(), move.newPiece.getColor()))
            {
                System.out.println("Enter");
                System.out.println(move.destinationX);
                System.out.println(move.destinationY);
                if(move.destinationX == 7){
                    board.setBoard(6,move.destinationY,null);
                    board.setBoard(5,move.destinationY,null);}

                if(move.destinationX == 0){
                    board.setBoard(1,move.destinationY,null);
                    board.setBoard(2,move.destinationY,null);}
            }
        }
        board.setBoard(move.previousX, move.previousY, move.newPiece);
        board.setBoard(move.destinationX, move.destinationY, move.oldPiece);
        board.changeTurn(board.whoseTurn);
        movesPanel.drawMovesPanel(movesList);
    }

    public void restoreMove(Board board, Move move){
        board.setBoard(move.previousX, move.previousY, move.newPiece);
        board.setBoard(move.destinationX, move.destinationY, move.oldPiece);
        board.changeTurn(board.whoseTurn);
    }

    public void deleteLastMove() {
        int size = movesList.size();
        movesList.remove(size-1);
    }
}
