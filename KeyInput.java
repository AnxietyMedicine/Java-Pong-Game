import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * A class that deals with key input from users
 */
public class KeyInput extends KeyAdapter {

    private Paddle p1, p2;
    private boolean isMovingUp1 = false, isMovingDown1 = false;
    private boolean isMovingUp2 = false, isMovingDown2 = false;

    public KeyInput(Paddle pd1, Paddle pd2) {
        p1 = pd1;
        p2 = pd2;
    }

    /**
     * A method that deals with key presses
     * @param e - KeyEvent
     */
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_UP) {
            p2.switchDirection(-1);
            isMovingUp2 = true;
        }
        if (key == KeyEvent.VK_DOWN) {
            p2.switchDirection(1);
            isMovingDown2 = true;
        }
        if (key == KeyEvent.VK_W) {
            p1.switchDirection(-1);
            isMovingUp1 = true;
        }
        if (key == KeyEvent.VK_S) {
            p1.switchDirection(1);
            isMovingDown1 = true;
        }
    }

    /**
     * A method that deals with key releases
     * (stops all paddle movement if no key is pressed)
     * @param e - KeyEvent
     */
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_UP) {
            isMovingUp2 = false;
        }
        if (key == KeyEvent.VK_DOWN) {
            isMovingDown2 = false;
        }
        if (key == KeyEvent.VK_W) {
            isMovingUp1 = false;
        }
        if (key == KeyEvent.VK_S) {
            isMovingDown1 = false;
        }

        if (!isMovingUp1 && !isMovingDown1) {
            p1.stop();
        }

        if (!isMovingUp2 && !isMovingDown2) {
            p2.stop();
        }
    }
}
