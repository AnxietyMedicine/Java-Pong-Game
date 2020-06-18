import javax.swing.*;

public class Window {

    public Window(String title, Pong pongGame) {
        JFrame frame = new JFrame(title);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(pongGame);
        frame.pack();
        frame.setLocationRelativeTo(null); // Game appears centered in the screen
        frame.setVisible(true);

        pongGame.start();
    }
}
