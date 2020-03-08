import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;

public class View extends JPanel implements KeyListener {
    final JFrame window;
    final Game game;

    private Boolean performingAnimation = false;
    private int lineAnimationStep = 0;
    private int[] eliminatedRows = {};
    private Runnable completion = () -> {};

    static final Color[] colors = {Color.black,
        new Color(0x0540F2), // I
        new Color(0x3D6AF2), // O
        new Color(0x02732A), // T
        new Color(0xF2E205), // Z
        new Color(0xF24130), // S
        new Color(0x02732A), // L
        new Color(0xF2E205), // J
    };

    public View(Game game) {
        this.game = game;
        setPreferredSize(new Dimension(200, 400));

        window = new JFrame("Tetris");
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.add(this);
        window.addKeyListener(this);
        window.pack();

        new Thread(() -> {
            while (true) {
                try {
                    game.step();
                    if (performingAnimation) {
                        while (lineAnimationStep < 3) {
                            lineAnimationStep++;
                            repaint();
                            Thread.sleep(150);
                        }
                        lineAnimationStep = -1;
                        eliminatedRows = new int[0];
                        completion.run();
                        performingAnimation = false;
                    }
                    repaint();
                    Thread.sleep(500);
                } catch (InterruptedException e) {}
            }
        }).start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int[] coord : game.getAllBlockCoords()) {
            g.setColor(colors[coord[2]]);
            int size = 18;
            if (lineAnimationStep > 0 && Arrays.stream(eliminatedRows).anyMatch(i -> i == coord[1])) {
                size = 18 - lineAnimationStep * 6;
            }
            g.fillRect(coord[0] * 20 + 10 - size/2, coord[1] * 20 + 10 - size/2, size, size);
        }
    }

    public void performLineAnimation(int[] rows, Runnable completion) {
        eliminatedRows = rows;
        this.completion = completion;
        performingAnimation = true;
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if (performingAnimation) return;
        switch (keyEvent.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                game.moveLeft();
                break;
            case KeyEvent.VK_RIGHT:
                game.moveRight();
                break;
            case KeyEvent.VK_DOWN:
                game.step();
                break;
            case KeyEvent.VK_UP:
            case KeyEvent.VK_SPACE:
                game.rotate();
                break;
            case KeyEvent.VK_R:
                game.performGameOver();
                break;
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }
}
