
import java.awt.AWTException;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JDialog;

public class BirthdayTray {

    private static Thread waitingThread;
    public static TrayIcon trayIcon;
    private static PopupMenu popup;
    public static SystemTray systemTray;
    private static JDialog detailedInfo;

    public static boolean showTray() {

        if (!SystemTray.isSupported()) {
            return false;
        }


        StaticTasks.basicSettingsClass setting = new StaticTasks.basicSettingsClass();
        detailedInfo = setting.createDialog(null);


        detailedInfo.add(new ShowTodaysBirthday().getTodaysBirthday());

        systemTray = SystemTray.getSystemTray();
        systemTray.remove(trayIcon);

        popup = new PopupMenu();
        trayIcon = new TrayIcon((new ImageIcon(BirthdayTray.class.getResource("image/birthdayIcon.png"),
                "tray icon")).getImage());
        trayIcon.setPopupMenu(popup);

        if (ShowTodaysBirthday.birthdayDisplay.equals("")) {
            trayIcon.setToolTip("No birthday");
        } else {
            trayIcon.setToolTip("Say Happy Birthday to:\n" + ShowTodaysBirthday.birthdayDisplay);
        }
        trayIcon.setImageAutoSize(true);

        // Create a popup menu components
        MenuItem detailedItem = new MenuItem("Detailed birthday");
        MenuItem exitItem = new MenuItem("Exit");


        //Add components to popup menu
        popup.add(detailedItem);
        popup.addSeparator();
        popup.add(exitItem);


        try {
            systemTray.add(trayIcon);
        } catch (AWTException e) {
            return false;
        }

        if ((ShowTodaysBirthday.birthdayDisplay).equals("")) {
            trayIcon.displayMessage("No Birthday", null, TrayIcon.MessageType.NONE);
        } else {
            trayIcon.displayMessage("Say Happy Birthday to: ", ShowTodaysBirthday.birthdayDisplay, TrayIcon.MessageType.NONE);
        }


        //play sound
        if (GlobalVariables.prefs.getBoolean("PLAY_SOUND", true) && !ShowTodaysBirthday.birthdayDisplay.equals("")) {
            new StaticTasks().PlaySound();
        }

        //Tray actions
        trayIcon.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getButton() == MouseEvent.BUTTON1)
                    new Main();
            }
        });


        detailedItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                detailedInfo.setIconImage((new ImageIcon(BirthdayTray.class.getResource("birthdayIcon.png"))).getImage());
                detailedInfo.setTitle("Detailed Birthday Info");
                detailedInfo.setSize(380, 290);
                detailedInfo.setLocation(500, 300);
                detailedInfo.setResizable(false);
                detailedInfo.setVisible(true);
            }
        });

        exitItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                GlobalVariables.prefs.putBoolean("EXIT_OR_NOT", true);
                System.exit(0);
            }
        });
        waitingThread = new StaticTasks.waitForNextDay(null);
        waitingThread.start();
        try {
            waitingThread.wait();
            showTray();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }

}