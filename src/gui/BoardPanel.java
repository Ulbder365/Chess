package gui;

import board.Board;
import pieces.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

public class BoardPanel extends JPanel {

    Board board;
    Point previousPoint;
    Piece selectedPiece;

    BoardPanel(Board board){
        this.board = board;
        ClickListener clickListener = new ClickListener();
        this.addMouseListener(clickListener);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintBoard(g);
        paintPieces(g);

        if(board.getMoves().movesList.size() > 0 )
            if(board.promote(board.getMoves().getLastMove()))
                paintPromote(g, board.getWhoseTurn());
    }

    public void paintBoard(Graphics g){
        int li=0;
        for(int i = 0; i<8; i++){
            for(int j = 0; j<8; j++){
                g.setColor(Color.gray);
                if(li%2==0) g.setColor(Color.cyan);
                if(selectedPiece != null) {
                    boolean destinationEmpty = board.getBoardElement(i,j) == null;
                    boolean selectedPieceIsAbleToMove = selectedPiece.move(board, previousPoint.x / 60, previousPoint.y / 60, i, j);
                    if(selectedPieceIsAbleToMove) {
                        if (!destinationEmpty) {
                            if (!Objects.equals(selectedPiece.getColor(), board.getBoardElement(i, j).getColor())) g.setColor(Color.red);
                            else g.setColor(Color.pink);
                        } else g.setColor(Color.lightGray);
                        if(selectedPiece.getClass().getName().equals("pieces.Pawn"))
                        if(Objects.equals(selectedPiece.getColor(), "white") && j == 7 ||
                                (Objects.equals(selectedPiece.getColor(), "black") && j == 0))
                            g.setColor(Color.blue);
                    }
                }
                g.fillRect(i*60,j*60,60,60);
                li++;
            }
            li++;
        }
    }

    private void paintPieces(Graphics g) {
        for(int i = 0; i<8; i++)
            for (int j = 0; j < 8; j++)
                if(board.getBoardElement(i,j)!=null) board.getBoardElement(i,j).image().paintIcon(this,g,i*60,j*60);
    }

    public void paintPromote(Graphics g,String color){
        Rook  rook = new Rook(color);
        Bishop  bishop = new Bishop(color);
        Knight knight = new Knight(color);
        Queen queen = new Queen(color);
        rook.image().paintIcon(this,g,8*60,0);
        bishop.image().paintIcon(this,g,8*60,60);
        knight.image().paintIcon(this,g,8*60,2*60);
        queen.image().paintIcon(this,g,8*60,3*60);

    }

    private class ClickListener extends MouseAdapter {
        public void mousePressed(MouseEvent event){
            if(SwingUtilities.isLeftMouseButton(event)){
                mouseLeftPressed(event);
            }else mouseRightPressed();
        }

        public void mouseLeftPressed(MouseEvent event){
            int x = (int) event.getPoint().getX()/60;
            int y = (int) event.getPoint().getY()/60;
            boolean promotionEvent = false;

            if(board.getMoves().movesList.size() > 0 )
                if(board.promote(board.getMoves().getLastMove())) {
                   promotionEvent=true;
                }

            if(x >= 0 && x <=7 && y >= 0 && y <=7 && !promotionEvent){
            if(board.getBoardElement(x,y)!=null) {
                selectedPiece = board.getBoardElement(x, y);
                repaint();
                } previousPoint = event.getPoint();
            }

            else if(x==8 && y <= 3 && y >= 0 && promotionEvent){
                board.makePromote(board.getMoves().getLastMove(),y);
                repaint();
            }
        }

        public void mouseRightPressed(){
            if(board.getMoves().movesList.size()>=1) {
                board.getMoves().restoreMoveByClick(board, board.getMoves().getLastMove());
                board.getMoves().deleteLastMove();
                repaint();
            }
        }

        public void mouseReleased(MouseEvent event) {
            Point currentPoint = event.getPoint();
            if(previousPoint != null && selectedPiece != null) {
                if(board.makeMove(
                        (int) previousPoint.getX() / 60,
                        (int) previousPoint.getY() / 60,
                        (int) currentPoint.getX() / 60,
                        (int) currentPoint.getY() / 60,
                        selectedPiece))
                board.afterMove();
                selectedPiece = null;
                repaint();
            }
        }
    }
}
