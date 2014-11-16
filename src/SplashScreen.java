
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import javax.imageio.ImageIO;

public class SplashScreen extends JFrame implements Runnable {

    public SplashScreen() {
        setLayout(new BorderLayout());
        setSize(404, 310);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setUndecorated(true);
        try {
            setIconImage(ImageIO.read(SplashScreen.class.getResourceAsStream("image/birthdayIcon.png")));
        } catch (IOException ex) {
        }
        ImageIcon img = new ImageIcon(getClass().getResource("image/birthdayIcon.png"));
        JLabel imageLabel = new JLabel(img);
        add(imageLabel, BorderLayout.CENTER);
        setVisible(true);
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1);
        } catch (InterruptedException ex) {
        }

    }
}