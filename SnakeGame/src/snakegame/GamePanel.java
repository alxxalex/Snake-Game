package snakegame;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import javax.swing.Timer;


/* @author alex*/
public class GamePanel extends javax.swing.JPanel implements ActionListener {

    public final int WIDTH = 600;
    public final int HEIGHT = 600;
    private final int UNIT = 25;
    private final int GAME_UNITS = (WIDTH / UNIT * HEIGHT / UNIT);
    private Random r;
    private int appleX = 0, appleY = 0;
    private int bodyParts = 6, score = 0;
    private int[] snakeX = new int[GAME_UNITS];
    private int[] snakeY = new int[GAME_UNITS];
    private Timer t = new Timer(80, this);
    private char direction = 'R';
    private boolean start = false;

    public GamePanel() {
        initComponents();
        this.setBackground(Color.black);
        this.setSize(WIDTH, HEIGHT);
        this.setFocusable(true);
        this.addKeyListener(new myKeyAdapter());
        r = new Random();
        snakeX[0] = 7 * UNIT;
        snakeY[0] = 2 * UNIT;
        appleX = r.nextInt(WIDTH / UNIT) * UNIT;
        appleY = r.nextInt(HEIGHT / UNIT) * UNIT;
        for (int i = 1; i < bodyParts; i++) {
            snakeX[i] = snakeX[i - 1] - UNIT;
            snakeY[i] = snakeY[i - 1];
        }
        start = true;
        lbScore.setText("Score: " + score);
    }

    public void paint(Graphics g) {
        if (start == true) {
            super.paint(g);
            newApple(g);
            createSnake(g);

            t.start();
        } else {
            super.paint(g);
            gameOver(g);
        }

    }

    private void newApple(Graphics g) {
        g.setColor(Color.red);
        g.fillOval(appleX, appleY, UNIT, UNIT);
    }

    private void createSnake(Graphics g) {
        g.setColor(Color.green);

        for (int i = 0; i < bodyParts; i++) {
            if (i == 0) {
                g.setColor(Color.green);
                g.fillRect(snakeX[i], snakeY[i], UNIT, UNIT);
            } else {
                g.setColor(new Color(45, 180, 0));
                g.fillRect(snakeX[i], snakeY[i], UNIT, UNIT);
            }
        }
    }

    private void move() {
        if (snakeX[0] < WIDTH && snakeY[0] < HEIGHT && snakeX[0] >= 0 && snakeY[0] >= 0) {
            for (int i = bodyParts - 1; i > 0; i--) {
                snakeX[i] = snakeX[i - 1];
                snakeY[i] = snakeY[i - 1];
            }
            switch (direction) {
                case 'R':
                    snakeX[0] += UNIT;
                    break;
                case 'L':
                    snakeX[0] -= UNIT;
                    break;
                case 'U':
                    snakeY[0] -= UNIT;
                    break;
                case 'D':
                    snakeY[0] += UNIT;
                    break;
                default:
                    throw new AssertionError();
            }
        } else {
            start = false;
        }
    }

    private void checkAppleEaten() {
        if (snakeX[0] == appleX && snakeY[0] == appleY) {
            bodyParts++;
            appleX = r.nextInt(WIDTH / UNIT) * UNIT;
            appleY = r.nextInt(HEIGHT / UNIT) * UNIT;
            score++;
            lbScore.setText("Score: " + score);
        }
    }

    private void checkColision() {
        for (int i = 1; i < bodyParts; i++) {
            if (snakeX[0] == snakeX[i] && snakeY[0] == snakeY[i]) {
                start = false;
            }
        }
    }

    private void gameOver(Graphics g) {
        lbScore.setForeground(Color.red);
        lbScore.setText("Your Score: " + score);
        g.setColor(Color.red);
        g.setFont(new Font("Segoe", Font.PLAIN, 36));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Game over!", (WIDTH - metrics.stringWidth("Game over!")) / 2, HEIGHT / 2);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lbScore = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(600, 600));

        lbScore.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        lbScore.setForeground(new java.awt.Color(255, 255, 255));
        lbScore.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(118, Short.MAX_VALUE)
                .addComponent(lbScore, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(115, 115, 115))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(lbScore, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(477, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    @Override
    public void actionPerformed(ActionEvent e) {
        if (start == true) {
            checkAppleEaten();
            move();
            checkColision();
        }
        repaint();
    }

    public class myKeyAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if (key == 37 && direction != 'R') {
                direction = 'L';
            } else if (key == 38 && direction != 'D') {
                direction = 'U';
            } else if (key == 39 && direction != 'L') {
                direction = 'R';
            } else if (key == 40 && direction != 'U') {
                direction = 'D';
            }
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lbScore;
    // End of variables declaration//GEN-END:variables
}
