package gui;

import board.Move;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MovesPanel  extends JPanel {
    public List<Move> movesList;

    public void drawMovesPanel(List<Move> movesList) {
        this.movesList = movesList;
        repaint();
    }

    public MovesPanel(List<Move> movesList) {
        this.movesList = movesList;
    }

    private char numberToLetter(int number){
        return switch (number) {
            case 0 -> 'a';
            case 1 -> 'b';
            case 2 -> 'c';
            case 3 -> 'd';
            case 4 -> 'e';
            case 5 -> 'f';
            case 6 -> 'g';
            case 7 -> 'h';
            default -> 'X';
        };
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintMoves(g);
    }

    public void paintMoves(Graphics g) {
        int li = 0;
        int mod = 0;
        g.drawString("White moves: ",0,20);
        g.drawString("Black moves: ",150,20);
        for (Move i : movesList){
            String[] name = i.getNewPiece().getClass().getName().split("\\.");
            String move = name[1] + " " + i.getNewPiece().getColor() + " " +
                    numberToLetter(i.getPreviousX())  + i.getPreviousY() + " -> " + numberToLetter(i.getDestinationX()) + i.getDestinationY();
            g.drawString(move,150*(mod%2),20*(2+ (li/2)));
            li++; mod++;
        }
    }
}
