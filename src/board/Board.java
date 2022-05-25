package board;

import pieces.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import static javax.swing.JOptionPane.showMessageDialog;


public class Board {

    Piece[][] board;
    String whoseTurn = "white";
    public List<Move> movesList = new ArrayList<>();
    Move moves = new Move(movesList);

    public Board(Piece[][] board) {
        this.board = board;
        startPosition();
    }
    //Getters
    public Piece getBoardElement(int x, int y) {
        return board[x][y];
    }
    public Move getMoves() { return moves; }
    public String getWhoseTurn() { return whoseTurn; }
    //Setters
    public void setBoard(int x, int y, Piece piece) {
        this.board[x][y] = piece;
    }
    public void startPosition() {
        setBoard(0, 0, new Rook("white"));
        setBoard(1, 0, new Knight("white"));
        setBoard(2, 0, new Bishop("white"));
        setBoard(3, 0, new Queen("white"));
        setBoard(4, 0, new King("white"));
        setBoard(5, 0, new Bishop("white"));
        setBoard(6, 0, new Knight("white"));
        setBoard(7, 0, new Rook("white"));
        for (int i = 0; i < 8; i++) {
            setBoard(i, 1, new Pawn("white"));
        }

        setBoard(0, 7, new Rook("black"));
        setBoard(1, 7, new Knight("black"));
        setBoard(2, 7, new Bishop("black"));
        setBoard(3, 7, new Queen("black"));
        setBoard(4, 7, new King("black"));
        setBoard(5, 7, new Bishop("black"));
        setBoard(6, 7, new Knight("black"));
        setBoard(7, 7, new Rook("black"));
        for (int i = 0; i < 8; i++) {
            setBoard(i, 6, new Pawn("black"));
        }
    }
    public void clearPrevious(int x, int y) {
        setBoard(x, y, null);
    }

    //Main Methods
    public boolean makeMove(int previousX, int previousY, int destinationX, int destinationY, Piece piece) {
        if (piece.move(this, previousX, previousY, destinationX, destinationY)) {
            moves.saveMove(previousX, previousY, destinationX, destinationY, getBoardElement(destinationX, destinationY), piece);

            //Castle case{
            if (getBoardElement(destinationX, destinationY) != null)
                if (castle(destinationX, destinationY, piece)) {
                    makeCastle(previousX,previousY,destinationX, destinationY, piece);
                    return true;
                }
            //}

            clearPrevious(previousX, previousY);
            setBoard(destinationX, destinationY, piece);
            return true;
        }
        return false;
    }

    public void afterMove() {
        if(promote(moves.getLastMove())) {
            changeTurn(whoseTurn);
        }
            changeTurn(whoseTurn);
            System.out.println(moves.movesList);
            if (checkCheck()) System.out.println("Check");
            if (0 != checkMovesLeft()) System.out.println("Left " + checkMovesLeft() + " moves to do");
            else if (checkCheck()) {
                showMessageDialog(null, "Check Mate, " + whoseTurn + " loses");
            }
            else showMessageDialog(null, "Pat it is a draw");
    }

    public void changeTurn(String whoseTurn) {
        if (Objects.equals(whoseTurn, "white")) this.whoseTurn = "black";
        if (Objects.equals(whoseTurn, "black")) this.whoseTurn = "white";
    }

    //Monitor state of the game Methods
    public int checkMovesLeft() {
        int possiblesMoves = 0;
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                if (getBoardElement(i, j) != null)
                    if (Objects.equals(whoseTurn, getBoardElement(i, j).getColor()))
                        for (int k = 0; k < 8; k++)
                            for (int l = 0; l < 8; l++)
                                if (this.board[i][j].move(this, i, j, k, l))
                                    possiblesMoves++;
        return possiblesMoves;
    }

    public boolean checkCheck() {
        int[] xy = findKing(whoseTurn);
        return treatedSquare(whoseTurn, xy[0], xy[1]);
    }

    //Helper Methods
    private int[] findKing(String color) {
        int[] xy = new int[2];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (getBoardElement(i, j) != null) {
                    if (getBoardElement(i, j).getClass().getName().equals("pieces.King") && Objects.equals(getBoardElement(i, j).getColor(), color)) {
                        xy[0] = i;
                        xy[1] = j;}}}}
        return xy;
    }

    public boolean treatedSquare(String color, int destinationX, int destinationY) {
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++) {
                if (getBoardElement(i, j) != null)
                    if (!Objects.equals(getBoardElement(i, j).getColor(), color))
                        if (getBoardElement(i, j).kill(this, i, j, destinationX, destinationY)) return true;
            }
        return false;
    }

    public boolean willStopCheck(int previousX, int previousY, int destinationX, int destinationY) {
        boolean unchecked;
        moves.saveMove(previousX, previousY, destinationX, destinationY, getBoardElement(destinationX, destinationY), getBoardElement(previousX, previousY));
        setBoard(destinationX, destinationY, getBoardElement(previousX, previousY));
        clearPrevious(previousX, previousY);
        unchecked = !checkCheck();
        changeTurn(whoseTurn);
        moves.restoreMove(this, moves.getLastMove());
        moves.deleteLastMove();
        return unchecked;
    }

    //Castle Methods
    private boolean castle(int destinationX, int destinationY, Piece piece) {
        return Objects.equals(piece.getColor(), getBoardElement(destinationX, destinationY).getColor());
    }

    private void makeCastle(int previousX, int previousY, int destinationX,int destinationY, Piece  piece) {
        if(destinationX ==7) {
        setBoard(5, destinationY, piece);
        setBoard(6, destinationY, getBoardElement(destinationX, destinationY));
        }
        else {
        setBoard(2, destinationY, piece);
        setBoard(1, destinationY, getBoardElement(destinationX, destinationY));
        }
        clearPrevious(previousX, previousY);
        clearPrevious(destinationX, destinationY);
    }

    //Promote Methods

    public boolean promote(Move lastMove) {
        if(!Objects.equals(whoseTurn, lastMove.newPiece.getColor()) || !lastMove.newPiece.getClass().getName().equals("pieces.Pawn")) return false;

        if(Objects.equals(lastMove.newPiece.getColor(), "white"))
                return lastMove.destinationY == 7;
                else return lastMove.destinationY == 0;
    }

    public void makePromote(Move lastMove, int pickedY) {
        if(pickedY == 0) setBoard(lastMove.destinationX,lastMove.destinationY,new Rook(whoseTurn));
        if(pickedY == 1) setBoard(lastMove.destinationX,lastMove.destinationY,new Bishop(whoseTurn));
        if(pickedY == 2) setBoard(lastMove.destinationX,lastMove.destinationY,new Knight(whoseTurn));
        if(pickedY == 3) setBoard(lastMove.destinationX,lastMove.destinationY,new Queen(whoseTurn));
        changeTurn(whoseTurn);
    }
}
