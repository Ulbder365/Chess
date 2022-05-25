package gui;

import board.Board;

import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {
    public GUI(Board board) {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(900,520);
        this.setTitle("ChessByUlbder");

        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));

        BoardPanel boardPanel = new BoardPanel(board);
        boardPanel.setPreferredSize(new Dimension(550,520));

        MovesPanel movesPanel = board.getMoves().getMovesPanel();
        movesPanel.setPreferredSize(new Dimension(350,520));
        movesPanel.setBackground(Color.PINK);

        container.add(boardPanel);
        container.add(movesPanel);

        this.add(container);
        this.setVisible(true);
    }
}
