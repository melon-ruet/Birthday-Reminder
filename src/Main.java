

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.Vector;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * @author melon
 * @version 1.0.1
 */
public class Main extends JFrame {

    public Main() {
        setTitle("Birthday Reminder");
        setLayout(new GridLayout(1, 1));
        setLocation(200, 50);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(700, 500));

        GlobalVariables.DatabaseImport = new Vector<Vector<String>>();

        setJMenuBar(new Menu().setMenu());
        try {
            setIconImage(ImageIO.read(getClass().getResourceAsStream("image/birthdayIcon.png")));
        } catch (IOException ex) {
        }

        JPanel leftPanel = new LeftPanel();
        add(leftPanel);
        JPanel rightPanel = new RightPanel();
        add(rightPanel);

        pack();
        setVisible(true);
    }

    public static void main(String[] args) throws ClassNotFoundException, InterruptedException {

        new StaticTasks().setLookAndFeel();

        if (new JustOneLock().isAppActive()) {
            JOptionPane.showMessageDialog(null, "Birthday Reminder is running on System tray", "Running...",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }

        final SplashScreen s = new SplashScreen();
        s.run();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                s.dispose();
                boolean chkTray = BirthdayTray.showTray();
                if (!chkTray) {
                    new LinuxShowBirthday();
                }
            }
        });
    }
}
