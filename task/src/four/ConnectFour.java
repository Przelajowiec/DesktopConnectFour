package four;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConnectFour extends JFrame {
    private JButton[][] buttons;
    private int click = 0;
    private Color baselineColor = new Color(0, 102, 204); // Baseline color (Blue)
    private Color winningColor = new Color(255, 255, 51); // Winning color (Yellow)
    private boolean gameFinished = false;

    public ConnectFour() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());
        setTitle("Connect Four");

        add(createBoardPanel(), BorderLayout.CENTER);
        add(createResetButton(), BorderLayout.SOUTH);

        setVisible(true);
    }

    private JPanel createBoardPanel() {
        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(6, 7));

        buttons = new JButton[6][7];

        String[] columnNames = {"A", "B", "C", "D", "E", "F", "G"};

        for (int i = 5; i > -1; i--) {
            for (int j = 0; j < 7; j++) {
                JButton nameButton = new JButton(" ");
                String buttonName = "Button" + columnNames[j] + (i + 1);
                nameButton.setName(buttonName);
                nameButton.setActionCommand(buttonName);
                nameButton.setBackground(baselineColor);
                boardPanel.add(nameButton);
                buttons[i][j] = nameButton;

                final int column = j;
                final int row = i;

                nameButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (!gameFinished) {
                            int lowestEmptyRow = getLowestEmptyRow(column);
                            if (lowestEmptyRow != -1) {
                                if (click % 2 == 0) {
                                    buttons[lowestEmptyRow][column].setText("X");
                                    checkForWinner(lowestEmptyRow, column, "X");
                                } else {
                                    buttons[lowestEmptyRow][column].setText("O");
                                    checkForWinner(lowestEmptyRow, column, "O");
                                }
                                click++;
                                checkForTie();
                            }
                        }
                    }
                });
            }
        }

        return boardPanel;
    }

    private JButton createResetButton() {
        JButton resetButton = new JButton("Reset");
        resetButton.setName("ButtonReset");
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetGame();
            }
        });

        return resetButton;
    }

    private void resetGame() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                buttons[i][j].setText(" ");
                buttons[i][j].setEnabled(true);
                buttons[i][j].setBorder(BorderFactory.createLineBorder(baselineColor)); // Reset border color
            }
        }
        click = 0;
        gameFinished = false;
    }

    private void checkForWinner(int row, int column, String player) {
        // Implement the logic to check for a winner in all directions (horizontal, vertical, diagonal)
        // Change the border color of the winning cells to winningColor
        // If a winner is found, set gameFinished to true

        if (checkDirection(row, column, player, 0, 1) || // Check horizontally
                checkDirection(row, column, player, 1, 0) || // Check vertically
                checkDirection(row, column, player, 1, 1) || // Check diagonally (bottom-left to top-right)
                checkDirection(row, column, player, 1, -1)) { // Check diagonally (top-left to bottom-right)
            gameFinished = true;
            JOptionPane.showMessageDialog(this, "Player " + player + " wins!");
        }
    }

    private boolean checkDirection(int row, int col, String player, int rowDirection, int colDirection) {
        int count = 0;
        for (int i = -3; i <= 3; i++) {
            int currentRow = row + i * rowDirection;
            int currentCol = col + i * colDirection;
            if (currentRow >= 0 && currentRow < 6 && currentCol >= 0 && currentCol < 7 &&
                    buttons[currentRow][currentCol].getText().equals(player)) {
                count++;
            }
        }
        if (count >= 4) {
            for (int i = -3; i <= 3; i++) {
                int currentRow = row + i * rowDirection;
                int currentCol = col + i * colDirection;
                buttons[currentRow][currentCol].setBorder(BorderFactory.createLineBorder(winningColor));
                gameFinished = true;
            }
            return true;
        }
        return false;
    }


    private boolean checkLine(int row, int col, int rowDirection, int colDirection, String player) {
        int count = 0;
        for (int i = -3; i <= 3; i++) {
            int currentRow = row + i * rowDirection;
            int currentCol = col + i * colDirection;
            if (currentRow >= 0 && currentRow < 6 && currentCol >= 0 && currentCol < 7 &&
                    buttons[currentRow][currentCol].getText().equals(player)) {
                count++;
            }
        }
        if (count == 4) {
            for (int i = -3; i <= 3; i++) {
                int currentRow = row + i * rowDirection;
                int currentCol = col + i * colDirection;
                buttons[currentRow][currentCol].setBorder(BorderFactory.createLineBorder(winningColor));
                gameFinished = true;
            }
            return true;
        }
        return false;
    }

    private int getLowestEmptyRow(int column) {
        for (int i = 0; i <= 5; i++) {
            if (buttons[i][column].getText().equals(" ")) {
                return i;
            }
        }
        return -1; // Column is full
    }

    private void checkForTie() {
        boolean isTie = true;
        for (int i = 0; i < 7; i++) {
            if (buttons[5][i].getText().equals(" ")) {
                isTie = false;
                break;
            }
        }
        if (isTie) {
            JOptionPane.showMessageDialog(this, "It's a tie!");
            disableAllButtons();
            gameFinished = true;
        }
    }

    private void disableAllButtons() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                buttons[i][j].setEnabled(false);
            }
        }
        gameFinished = true;
    }
}
