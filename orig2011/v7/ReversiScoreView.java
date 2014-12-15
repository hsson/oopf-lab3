package orig2011.v7;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import orig2011.v7.ReversiModel.Turn;

import javax.swing.*;

public class ReversiScoreView extends JFrame implements PropertyChangeListener {

    private Turn playerTurn = Turn.BLACK;
    private int blackScore = 0;
    private int whiteScore = 0;

    public ReversiScoreView() {
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.add(new ScorePanel());
        this.setLocationRelativeTo(null);
        this.pack();
        this.setVisible(true);
    }

    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getSource().getClass() == ReversiModel.class) {
            if (evt.getPropertyName().equals("whiteScore")) {
                whiteScore = (Integer) evt.getNewValue();
            } else if (evt.getPropertyName().equals("blackScore")) {
                blackScore = (Integer) evt.getNewValue();
            } else if (evt.getPropertyName().equals("turn")) {
                playerTurn = (Turn) evt.getNewValue();
            }
        }

        repaint();

        System.out.println(playerTurn.toString() + " - " + whiteScore + " - " + blackScore);
    }

    public class ScorePanel extends JPanel {

        JLabel pTurn, scoreLabel;

        public ScorePanel() {
            pTurn = new JLabel("Turn: " + playerTurn.toString());
            scoreLabel = new JLabel("W " + whiteScore + " : " + blackScore + " B");

            this.setLayout(new GridLayout(2, 1));

            this.add(pTurn);
            this.add(scoreLabel);

            this.setPreferredSize(new Dimension(500, 200));
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            updateFontSize(pTurn);
            updateFontSize(scoreLabel);

            pTurn.setText("Turn: " + playerTurn.toString());
            scoreLabel.setText("W " + whiteScore + " : " + blackScore + " B");

        }
    }

    private void updateFontSize(JLabel label) {
        Font labelFont = label.getFont();
        String labelText = label.getText();

        int stringWidth = label.getFontMetrics(labelFont).stringWidth(labelText);
        int componentWidth = label.getWidth();

        // Find out how much the font can grow in width.
        double widthRatio = (double)componentWidth / (double)stringWidth;

        int newFontSize = (int)(labelFont.getSize() * widthRatio);
        int componentHeight = label.getHeight();

        // Pick a new font size so it will not be larger than the height of label.
        int fontSizeToUse = Math.min(newFontSize, componentHeight);

        // Set the label's font size to the newly determined size.
        label.setFont(new Font(labelFont.getName(), Font.PLAIN, fontSizeToUse));
    }
}
