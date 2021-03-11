package com.zetcode;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.util.Random;

public class Board extends JPanel implements ActionListener {

    private final int B_WIDTH = 600;
    private final int B_HEIGHT = 600;
    private final int DOT_SIZE = 10;
    private final int ALL_DOTS = (B_WIDTH * B_HEIGHT) / DOT_SIZE;
    private final int RAND_POS = 29;
    private int DELAY = 340;

    private final int x[] = new int[ALL_DOTS];
    private final int y[] = new int[ALL_DOTS];

    private int dots;
    private int apple_x;
    private int apple_y;

    private boolean leftDirection = false;
    private boolean rightDirection = true;
    private boolean upDirection = false;
    private boolean downDirection = false;
    private boolean inGame = true;

    private Timer timer;
    private Image ball;
    private Image apple;
    private Image head;
    private Image taili;
    private Image tailr;
    private Image taill;
    private Image tailu;
    private Image taild;

    private Random random = new Random();
    private String score;
    private String speed;
    private String highscore = "";
    private int highScoreCheck, scoreCheck;
    private Speed timerSpeed = new Speed();
    private String aEatSound;
    private Music appleEat;
    private String gOverSound;
    private Music gameOver;
    private String hSound;
    private Music hit;
    private String gStart;
    private Music gameStart;
    private Tailpos tail = new Tailpos(x[0], y[0]);
    private Headpos headPos = new Headpos(x[0], y[0]);
    private boolean stopped = false;

    public Board() {

        initBoard();
    }

    private void initBoard() {

        addKeyListener(new TAdapter());
        setBackground(Color.black);
        setFocusable(true);

        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        loadSounds();
        loadImages();
        initGame();

    }

    private void loadSounds() {
        gStart = "src/resources/gamestart.wav";
        gameStart = new Music();
        aEatSound = "src/resources/apple.wav";
        appleEat = new Music();
        gOverSound = "src/resources/gameOver.wav";
        gameOver = new Music();
        hSound = "src/resources/hit.wav";
        hit = new Music();
    }

    private void loadImages() {

        ImageIcon iid = new ImageIcon("src/resources/dot.png");
        ball = iid.getImage();

        ImageIcon iia = new ImageIcon("src/resources/apple.png");
        apple = iia.getImage();

        ImageIcon iih = new ImageIcon("src/resources/head.png");
        head = iih.getImage();
        ImageIcon iitD = new ImageIcon("src/resources/tailD.png");
        taild = iitD.getImage();
        ImageIcon iitL = new ImageIcon("src/resources/tailL.png");
        taill = iitL.getImage();
        ImageIcon iitU = new ImageIcon("src/resources/tailU.png");
        tailu = iitU.getImage();
        ImageIcon iitR = new ImageIcon("src/resources/tailR.png");
        tailr = iitR.getImage();
    }

    private void initGame() {
        gameStart.playSound(gStart);
        dots = 3;

        for (int z = 0; z < dots; z++) {
            x[z] = 50 - z * 10;
            y[z] = 50;
        }

        locateApple();

        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);
    }

    private void doDrawing(Graphics g) {
        if (highscore.equalsIgnoreCase("")) {
            highscore = Score.getHighScore();
        }
        if ((inGame) && (!stopped)) {
            Font small = new Font("Helvetica", Font.BOLD, 14);
            FontMetrics metr = getFontMetrics(small);

            g.setColor(Color.white);
            g.setFont(small);
            score = String.valueOf((dots - 3) * 10);
            speed = String.valueOf(140 - timer.getDelay());
            g.drawString("Current score : " + score, 0, 20);
            g.drawString("Current speed : " + speed, 140, 20);
            g.drawString("Highscore : " + highscore, 280, 20);
            g.drawImage(apple, apple_x, apple_y, this);

            for (int z = 0; z < dots; z++) {
                tail.updatePos(x[dots - 1], y[dots - 1]);
                if (z == 0) {
                    g.drawImage(head, x[z], y[z], this);

                } else if (z == (dots - 1)) {
                    if (headPos.getPos().equals(tail.getPos()) && rightDirection) {
                        taili = tailr;
                    }
                    if (headPos.getPos().equals(tail.getPos()) && leftDirection) {
                        taili = taill;
                    }
                    if (headPos.getPos().equals(tail.getPos()) && upDirection) {
                        taili = tailu;
                    }
                    if (headPos.getPos().equals(tail.getPos()) && downDirection) {
                        taili = taild;
                    }
                    g.drawImage(taili, x[z], y[z], this);

                } else {
                    g.drawImage(ball, x[z], y[z], this);
                }
            }

            Toolkit.getDefaultToolkit().sync();

        } else if ((inGame) && (stopped)) {
            gamePause(g);
            timer.stop();
        } else {

            gameOver(g);

        }

    }

    private void gameOver(Graphics g) {

        String msg = "Game Over";
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = getFontMetrics(small);

        gameOver.playSound(gOverSound);
        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (B_WIDTH - metr.stringWidth(msg)) / 2, B_HEIGHT / 2);
    }

    private void gamePause(Graphics g) {

        String msg = "Game Pause";
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (B_WIDTH - metr.stringWidth(msg)) / 2, B_HEIGHT / 2);
    }

    private void checkApple() {

        if ((x[0] == apple_x) && (y[0] == apple_y)) {

            DELAY = timerSpeed.setDelay(timer, DELAY, 2);
            timerSpeed.updateTimer(timer, DELAY);

            appleEat.playSound(aEatSound);
            dots++;
            locateApple();
        }
    }

    private void move() {

        for (int z = dots; z > 0; z--) {
            x[z] = x[(z - 1)];
            y[z] = y[(z - 1)];
        }

        if (leftDirection) {
            x[0] -= DOT_SIZE;
        }

        if (rightDirection) {
            x[0] += DOT_SIZE;
        }

        if (upDirection) {
            y[0] -= DOT_SIZE;
        }

        if (downDirection) {
            y[0] += DOT_SIZE;
        }
    }

    private void checkCollision() {
        highScoreCheck = Integer.parseInt((highscore.split(":")[1]));
        scoreCheck = Integer.parseInt(score);
        for (int z = dots; z > 0; z--) {

            if ((z > 4) && (x[0] == x[z]) && (y[0] == y[z])) {
                inGame = false;
                hit.playSound(hSound);
                Score.checkScore(scoreCheck, highScoreCheck);
            }
        }

        if (y[0] >= B_HEIGHT) {
            inGame = false;
            hit.playSound(hSound);
            Score.checkScore(scoreCheck, highScoreCheck);
        }

        if (y[0] < 0) {
            inGame = false;
            hit.playSound(hSound);
            Score.checkScore(scoreCheck, highScoreCheck);
        }

        if (x[0] >= B_WIDTH) {
            inGame = false;
            hit.playSound(hSound);
            Score.checkScore(scoreCheck, highScoreCheck);
        }

        if (x[0] < 0) {
            inGame = false;
            hit.playSound(hSound);
            Score.checkScore(scoreCheck, highScoreCheck);
        }

        if (!inGame) {
            timer.stop();
        }
    }

    private void locateApple() {

        apple_x = random.nextInt((int) (B_WIDTH / DOT_SIZE)) * DOT_SIZE;

        apple_y = random.nextInt((int) (B_HEIGHT / DOT_SIZE)) * DOT_SIZE;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (inGame) {

            checkApple();
            checkCollision();
            move();
        }

        repaint();
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            if ((key == KeyEvent.VK_LEFT) && (!rightDirection) && (!leftDirection)) {
                leftDirection = true;
                upDirection = false;
                downDirection = false;
                headPos.updatePos(x[0], y[0]);
                tail.updatePos(x[dots - 1], y[dots - 1]);
                tail.printPos();
                // System.out.println("head at : " + x[0] + " , " + y[0] + " Direction : L");
                // System.out.println("tail at : " + x[dots - 1] + " , " + y[dots - 1]);
            }

            if ((key == KeyEvent.VK_RIGHT) && (!leftDirection) && (!rightDirection)) {
                rightDirection = true;
                upDirection = false;
                downDirection = false;
                headPos.updatePos(x[0], y[0]);
                tail.updatePos(x[dots - 1], y[dots - 1]);
                tail.printPos();
                // System.out.println("head at : " + x[0] + " , " + y[0] + " Direction : R");
                // System.out.println("tail at : " + x[dots - 1] + " , " + y[dots - 1]);
            }

            if ((key == KeyEvent.VK_UP) && (!downDirection) && (!upDirection)) {
                upDirection = true;
                rightDirection = false;
                leftDirection = false;
                headPos.updatePos(x[0], y[0]);
                tail.updatePos(x[dots - 1], y[dots - 1]);
                tail.printPos();
                // System.out.println("head at : " + x[0] + " , " + y[0] + " Direction : U");
                // System.out.println("tail at : " + x[dots - 1] + " , " + y[dots - 1]);
            }

            if ((key == KeyEvent.VK_DOWN) && (!upDirection) && (!downDirection)) {
                downDirection = true;
                rightDirection = false;
                leftDirection = false;
                headPos.updatePos(x[0], y[0]);
                tail.updatePos(x[dots - 1], y[dots - 1]);
                tail.printPos();
                // System.out.println("head at : " + x[0] + " , " + y[0] + " Direction : D");
                // System.out.println("tail at : " + x[dots - 1] + " , " + y[dots - 1]);
            }

            if ((key == KeyEvent.VK_ESCAPE) && (inGame) && (!stopped)) {

                stopped = true;

            } else if ((key == KeyEvent.VK_ESCAPE) && (inGame) && (stopped)) {

                stopped = false;
                timer.start();

            }
        }
    }
}