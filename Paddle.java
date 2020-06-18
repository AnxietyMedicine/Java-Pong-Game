import java.awt.*;

public class Paddle {

    private int x;
    private int y;
    private int vel = 0;
    private int width = 22;
    private int height = 85;
    private int score = 0;
    private Color color;
    private boolean isLeft;

    public Paddle(Color c, boolean isLeft) {
        color = c;

        this.isLeft = isLeft;

        if (isLeft) {
            x = 0;
        } else {
            x = Pong.WIDTH - width;
        }
        y = Pong.HEIGHT / 2 - height / 2;
    }

    /**
     * Adds a point to the paddle (user)
     */
    public void addScore() {
        score++;
    }

    /**
     * Draws the paddles and the score strings
     * @param g - Graphics
     */
    public void draw(Graphics g) {
        // Draw paddles
        g.setColor(color);
        g.fillRect(x, y, width, height);

        // Draw score
        int xPosOfString;
        String scoreString = Integer.toString(score);
        Font font = new Font("Roboto", Font.PLAIN, 50);

        int stringWidth = g.getFontMetrics(font).stringWidth(scoreString) + 1;
        int padding = 25;

        if (isLeft) {
            xPosOfString = Pong.WIDTH / 2 - padding - stringWidth;
        } else {
            xPosOfString = Pong.WIDTH / 2 + padding;
        }

        g.setFont(font);
        g.drawString(scoreString, xPosOfString, 50);
    }

    /**
     * Updates the positions of the paddles
     * @param b - the Ball object
     */
    public void update(Ball b) {
        // Update position
        y = Pong.ensureRange(y += vel, 0, Pong.HEIGHT - height);

        int ballX = b.getX();
        int ballY = b.getY();

        // Collisions with ball
        if (isLeft) {
            if (ballX <= width && ballY + Ball.SIZE >= y && ballY <= y + height) {
                b.changeXDir();
                //System.out.println("DEBUG: Collision with left paddle");
            }
        } else {
            if (ballX + Ball.SIZE >= Pong.WIDTH - width && ballY + Ball.SIZE >= y && ballY <= y + height) {
                b.changeXDir();
                //System.out.println("DEBUG: Collision with right paddle");
            }
        }
    }

    /**
     * Switches direction of the paddle
     * @param direction - new direction
     */
    public void switchDirection(int direction) {
        int speed = 10;
        vel = speed * direction;
    }

    /**
     * Stops the paddle from moving
     */
    public void stop() {
        vel = 0;
    }
}
