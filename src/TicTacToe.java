import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import static java.awt.BorderLayout.*;

public class TicTacToe extends JFrame {

    private final Label topLabel = new Label("Kolej gracza X");
    private final JButton[][] board = new JButton[3][3];
    private Player currentPlayer = Player.X;
    private boolean won = false;


    public TicTacToe() throws HeadlessException {
        setTitle("Tic Tac Toe");
        setSize(500, 500);
        setLayout(new BorderLayout());

        JPanel top = new JPanel(new FlowLayout());
        top.add(topLabel);
        getContentPane().add(top, PAGE_START);

        JPanel gameBoard = new JPanel(new GridLayout(3, 3));
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                JButton button = new JButton("-");
                board[i][j] = button;
                button.addActionListener(this::executePlayerMove);
                gameBoard.add(button);
            }
        }

        getContentPane().add(gameBoard, CENTER);

        JPanel bottom = new JPanel(new FlowLayout());
        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(this::resetBoard);
        bottom.add(resetButton);

        JButton closeButton = new JButton("Zamknij");
        closeButton.addActionListener((e) -> System.exit(0));
        bottom.add(closeButton);

        getContentPane().add(bottom, PAGE_END);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void resetBoard(ActionEvent e) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j].setText("-");
            }
        }
        topLabel.setText("Kolej gracza " + currentPlayer);
        won = false;
    }

    private void executePlayerMove(ActionEvent e) {
        if (e.getSource() instanceof JButton button && playerCanMove(button)) {
            button.setText(currentPlayer.name());
            if (!playerWon()) {
                changePlayer();
                topLabel.setText("Kolej gracza " + currentPlayer);
            }
        }
    }

    private boolean playerCanMove(JButton button) {
        return button.getText().equals("-") && !won;
    }

    private void changePlayer() {
        currentPlayer = currentPlayer == Player.X ? Player.O : Player.X;
    }

    private boolean playerWon() {
        for (int i = 0; i < 3; i++) {
            if (checkRow(i) || checkColumn(i)) {
                won = true;
                break;
            }
        }

        if (!won && ((
                board[0][0].getText().equals(currentPlayer.name())
                        && board[1][1].getText().equals(currentPlayer.name())
                        && board[2][2].getText().equals(currentPlayer.name())
        ) || (
                board[0][2].getText().equals(currentPlayer.name())
                        && board[1][1].getText().equals(currentPlayer.name())
                        && board[2][0].getText().equals(currentPlayer.name())
        ))) {
            won = true;
        }

        if (won) {
            topLabel.setText("Wygrał gracz " + currentPlayer.name());

            int result = JOptionPane.showConfirmDialog(this,
                    "Zwyciężył gracz " + currentPlayer + ". Czy chcesz zagra jeszcze raz?",
                    "Koniec gry",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);

            if (result == JOptionPane.YES_OPTION) {
                resetBoard(null);
            }
            if (result == JOptionPane.NO_OPTION) {
                System.out.println(0);
            }
        }
        return won;
    }

    private boolean checkRow(int i) {
        return board[i][0].getText().equals(currentPlayer.name())
                && board[i][1].getText().equals(currentPlayer.name())
                && board[i][2].getText().equals(currentPlayer.name());
    }

    private boolean checkColumn(int i) {
        return board[0][i].getText().equals(currentPlayer.name())
                && board[1][i].getText().equals(currentPlayer.name())
                && board[2][i].getText().equals(currentPlayer.name());
    }

    public static void main(String[] args) {

        TicTacToe ttt = new TicTacToe();
    }
}
