import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by melon on 6/11/14.
 */
public class LinuxShowBirthday extends JDialog{

    public LinuxShowBirthday() {
        setLayout(new BorderLayout());

        setTitle("Today's Birthday");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);


        //setUndecorated(true);

        add(new ShowTodaysBirthday().getTodaysBirthday(), BorderLayout.CENTER);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();
        setLocation(width - 210, height - 210);

        JPanel optionPanel = new JPanel(new GridLayout(1,2));
        JButton confirm = new JButton("Exit");
        confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.exit(0);
            }
        });


        optionPanel.add(confirm);
        JButton mainWindow = new JButton("View");
        mainWindow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                new Main();
            }
        });
        optionPanel.add(mainWindow);
        add(optionPanel, BorderLayout.SOUTH);


        setSize(new Dimension(200,200));
        setResizable(false);
        //setAlwaysOnTop(true);
        setVisible(true);

        if (GlobalVariables.prefs.getBoolean("PLAY_SOUND", true) && !ShowTodaysBirthday.birthdayDisplay.equals("")) {
            new StaticTasks().PlaySound();
        }

        StaticTasks.waitForNextDay waitingThread = new StaticTasks.waitForNextDay(this);
        waitingThread.start();
    }


}
