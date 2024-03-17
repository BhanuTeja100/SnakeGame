// Swing Components to set up the GUI
import javax.swing.*;

public class App {
    public static void main(String[] args) throws Exception {
        // Declaring variables for the board size
        int boardW = 600;
        int boardH = 600;
        // int tileSize = 25;
        
        // Implementing Jframe Class 
        // Represents the window of the application
        JFrame frame = new JFrame("Snake");

        // Setting up various properties of the frame
        // 
        frame.setVisible(true); 
        frame.setSize(boardW, boardH);
        frame.setLocationRelativeTo(null); // This keeps the frame at the center of the screen
        frame.setResizable(false); // We can't change the size of the frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Creating Instance of the snake game
        // Represents actual game panel
        SnakeGame snakeGame = new SnakeGame(boardW, boardH);
        frame.add(snakeGame);
        frame.pack();
        snakeGame.requestFocus();

    }
}
