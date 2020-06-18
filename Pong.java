import java.awt.*;
import java.awt.image.BufferStrategy;

/**
 * A game that simulates a two player Pong game
 * Controls are W/S for Player 1 and UP_ARROW/DOWN_ARROW for Player 2
 *
 * Author: Matt Wurl
 */
public class Pong extends Canvas implements Runnable {

    public static final int WIDTH = 1000;
    public static final int HEIGHT = WIDTH * 9 / 16; // 16:9 aspect ratio

    public boolean isRunning = false;
    private Thread pongThread;

    private Ball ball;
    private Paddle paddle1, paddle2;

    public Pong() {
        canvasSetup();
        initialize();

        new Window("Pong", this);

        this.addKeyListener(new KeyInput(paddle1, paddle2));
        this.setFocusable(true);
    }

    /**
     * Ensures a range to update position of a paddle
     * @param val - awaited updated y-position of paddle
     * @param min - minimum paddle y-position value
     * @param max - maximum paddle y-position value
     * @return the minimum between the max of val and min and the max
     */
    public static int ensureRange(int val, int min, int max) {
        return Math.min(Math.max(val, min), max);
    }

    /**
     * Initializes all of the objects that are needed
     */
    private void initialize() {
        ball = new Ball();
        paddle1 = new Paddle(Color.BLUE, true);
        paddle2 = new Paddle(Color.MAGENTA, false);
    }

    /**
     * Sets up the canvas
     */
    private void canvasSetup() {
        Dimension d = new Dimension(WIDTH, HEIGHT);
        this.setPreferredSize(d);
        this.setMinimumSize(d);
        this.setMaximumSize(d);
    }

    /**
     * Runs the game
     */
    @Override
    public void run() {
        this.requestFocus();

        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;

        while (isRunning) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            while (delta >= 1) {
                update();
                delta--;
            }

            if (isRunning) {
                draw();
                frames++;
            }

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                //System.out.println("FPS: " + frames);
                frames = 0;
            }
        }
        stop();
    }

    /**
     * Updates the Pong game
     */
    private void update() {
        // Update ball
        ball.update(paddle1, paddle2);

        // Update paddles
        paddle1.update(ball);
        paddle2.update(ball);
    }

    /**
     * Draws the objects onto the frame
     * (Required for Canvas)
     */
    private void draw() {
        // Initialize drawing tools
        BufferStrategy buffer = this.getBufferStrategy();

        if (buffer == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = buffer.getDrawGraphics();

        // Draw background
        drawBackground(g);

        // Draw ball
        ball.draw(g);

        // Draw paddles and score
        paddle1.draw(g);
        paddle2.draw(g);

        // Dispose
        g.dispose();
        buffer.show();
    }

    /**
     * Draws the background
     * @param g - Graphics
     */
    private void drawBackground(Graphics g) {
        // Black fill
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        // Dashed middle line
        g.setColor(Color.WHITE);
        Graphics2D g2d = (Graphics2D) g;
        Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{10}, 0);
        g2d.setStroke(dashed);
        g2d.drawLine(WIDTH / 2, 0, WIDTH / 2, HEIGHT);
    }

    /**
     * Starts the game
     */
    public void start() {
        pongThread = new Thread(this);
        pongThread.start();
        isRunning = true;
    }

    /**
     * Stops the game
     */
    public void stop() {
        try {
            pongThread.join();
            isRunning = false;
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    /**
     * Sin function to randomize initial
     * velocity of the ball
     * @param d - random value
     * @return - new velocity of the ball
     */
    public static int sinFunc(double d) {
        return (d <= 0) ? -1 : 1;
    }

    /**
     * Main function to create a new Pong object
     * @param args - command line arguments
     */
    public static void main(String[] args) {
        new Pong();
    }
}