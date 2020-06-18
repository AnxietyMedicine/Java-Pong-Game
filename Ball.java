import java.awt.*;

public class Ball {

    public static final int SIZE = 16;

    private int x;
    private int y;
    private int xVel;
    private int yVel;

    public Ball() {
        try {
            Thread.sleep(5000); // Wait for program to load (5 seconds)
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        reset();
    }

    /**
     * Gets the x-position of the ball
     * @return x-pos
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the y-position of the ball
     * @return y-pos
     */
    public int getY() {
        return y;
    }

    /**
     * Resets the ball to the center
     */
    private void reset() {
        // Initial position
        x = Pong.WIDTH / 2 - SIZE / 2;
        y = Pong.HEIGHT / 2 - SIZE / 2;

        // Initial velocities
        xVel = Pong.sinFunc(Math.random() * 2.0 - 1);
        yVel = Pong.sinFunc(Math.random() * 2.0 - 1);
    }

    /**
     * Changes the x-direction of the ball
     */
    public void changeXDir() {
        xVel *= -1;
    }

    /**
     * Changes the y-direction of the ball
     */
    public void changeYDir() {
        yVel *= -1;
    }

    /**
     * Draws the ball object
     * @param g - Graphics
     */
    public void draw(Graphics g) {
        g.setColor(Color.white);
        g.fillRect(x, y, SIZE, SIZE);
    }

    /**
     * Updates the ball object
     * @param p1 - Paddle1 object (left)
     * @param p2 - Paddle2 object (right)
     */
    public void update(Paddle p1, Paddle p2) {
        // Update movement
        int speed = 5;
        x += xVel * speed;
        y += yVel * speed;

        // Collisions
        // Ceiling and floor
        if (y + SIZE >= Pong.HEIGHT || y <= 0) {
            changeYDir();
            //System.out.println("DEBUG: Ball collision");
        }

        // Walls
        if (x + SIZE >= Pong.WIDTH) {
            p1.addScore();
            //System.out.println("DEBUG: Player 1 scores");
            reset();
        }

        if (x <= 0) {
            p2.addScore();
            //System.out.println("DEBUG: Player 2 scores");
            reset();
        }
    }
}