import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;


public class SnakeGame extends JPanel implements ActionListener, KeyListener{

    private class Tile {
        int x;
        int y;

        Tile(int x, int y){
            this.x = x;
            this.y = y;
        }
    }
    int boardW;
    int boardH;
    int tileSize = 25;

    // Snake
    Tile snakeHead;
    ArrayList<Tile> snakeBody;

    // Target
    Tile target;

    // Random
    Random random;


    // game logic
    Timer gameLoop;
    // movement of snake
    int velocityX;
    int velocityY;

    boolean gameOver = false;



    SnakeGame(int boardW, int boardH){
        this.boardW = boardW;
        this.boardH = boardH;

        setPreferredSize(new Dimension(this.boardW, this.boardH));
        setBackground(Color.black);
        addKeyListener(this);
        setFocusable(true);

        snakeHead = new Tile(5,5);
        snakeBody = new ArrayList<Tile>();

        target = new Tile(10,10);
        random = new Random();
        placeTarget();

        velocityX = 0;
        velocityY = 0;

        gameLoop = new Timer(100, this);
        gameLoop.start();
    }


    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){

        // Drawing Grid
        // for(int i=0; i<boardW/tileSize; i++){
            
        //     g.drawLine(i*tileSize, 0, i*tileSize, boardH);
        //     g.drawLine(0, i*tileSize, boardW, i*tileSize);
        // }


        // Target
        g.setColor(Color.red);
        // g.fillRect(target.x * tileSize, target.y * tileSize,tileSize, tileSize);
        g.fill3DRect(target.x * tileSize, target.y * tileSize,tileSize, tileSize, true);

        // Snake Head
        g.setColor(Color.green);
        // g.fillRect(snakeHead.x * tileSize, snakeHead.y * tileSize, tileSize, tileSize);
        g.fill3DRect(snakeHead.x * tileSize, snakeHead.y * tileSize, tileSize, tileSize, true);
        

        // Snake Body
        for(int i=0; i<snakeBody.size(); i++){
            Tile snakePart = snakeBody.get(i);
            // g.fillRect(snakePart.x * tileSize, snakePart.y * tileSize, tileSize, tileSize);
            g.fill3DRect(snakePart.x * tileSize, snakePart.y * tileSize, tileSize, tileSize, true);

        }
        // Score
        g.setFont(new Font("Arial", Font.PLAIN, 16));

        if(gameOver){
            g.setColor(Color.red);
            g.drawString("Game Over: " + String.valueOf(snakeBody.size()), tileSize - 16, tileSize);
        }
        else{
            g.drawString("Score: " + String.valueOf(snakeBody.size()), tileSize-16, tileSize);
        }
    }

    public void placeTarget(){
        target.x = random.nextInt(boardW/tileSize);
        target.y = random.nextInt(boardH/tileSize);
    }

    public boolean collision(Tile tile1, Tile tile2){
        return tile1.x == tile2.x && tile1.y == tile2.y;
    }

    public void move() {
        // hit target
        if(collision(snakeHead, target)){
            snakeBody.add(new Tile(target.x, target.y));
            placeTarget();
        }
        
        // Snake Body
        for(int i=snakeBody.size()-1; i >= 0; i--){
            Tile snakePart = snakeBody.get(i);

            if(i == 0){
                snakePart.x = snakeHead.x;
                snakePart.y = snakeHead.y;
            }
            else{
                Tile prevSnakePart = snakeBody.get(i-1);
                snakePart.x = prevSnakePart.x;
                snakePart.y = prevSnakePart.y;

            }
        }

        // Snake Head
        snakeHead.x += velocityX;
        snakeHead.y += velocityY;

        // game over conditions
        for(int i=0; i<snakeBody.size(); i++){
            Tile snakePart = snakeBody.get(i);
            // collide with the snake head
            if(collision(snakeHead, snakePart)){
                gameOver = true;
            }
        }

        if(snakeHead.x*tileSize < 0 || snakeHead.x*tileSize > boardW || snakeHead.y * tileSize < 0 || snakeHead.y * tileSize > boardH){
            gameOver = true;
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();

        if(gameOver){
            gameLoop.stop();
        }
        // System.out.println("repainted");
    }



    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_UP && velocityY != 1){
            velocityX = 0;
            velocityY = -1;
        }
        else if(e.getKeyCode() == KeyEvent.VK_DOWN && velocityY != -1){
            velocityX = 0;
            velocityY = 1;
        }
        else if(e.getKeyCode() == KeyEvent.VK_LEFT && velocityX != 1){
            velocityX = -1;
            velocityY = 0;
        }
        else if(e.getKeyCode() == KeyEvent.VK_RIGHT && velocityX != -1){
            velocityX = 1;
            velocityY = 0;
        }
    }

    // We don't need this
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}
