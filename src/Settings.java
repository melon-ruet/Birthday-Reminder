import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.util.prefs.Preferences;

/**
 * Created by melon on 6/4/14.
 */
public class Settings implements ActionListener {

    Preferences prefs = GlobalVariables.prefs;
    private JTextField audioLocation;
    private JCheckBox runStartup;
    private JCheckBox playAudio;
    private JTextField imageLocation;
    private JComboBox selectUI;

    @Override
    public void actionPerformed(ActionEvent e) {

        StaticTasks.basicSettingsClass basicTop = new StaticTasks.basicSettingsClass();
        final JDialog basicSettings = basicTop.createDialog(null);
        basicSettings.setIconImage((new ImageIcon(getClass().getResource("image/birthdayIcon.png"),
                "tray icon")).getImage());
        basicSettings.setLayout(null);
        basicSettings.setTitle("Birthday Reminder : Settings");
        basicSettings.setLocationRelativeTo(null);
        basicSettings.setSize(380, 260);
        basicSettings.setResizable(false);


        runStartup = new JCheckBox("Load on System startup");
        runStartup.setBounds(20, 10, 200, 20);
        runStartup.setFocusable(false);
        runStartup.setSelected(prefs.getBoolean("LOAD_STARTUP", false));
        runStartup.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (Startup.checkSelection) {
                    if (runStartup.isSelected()) {
                        try {
                            if (System.getProperty("os.name").toLowerCase().equals("linux")) {
                                new Startup().linuxStartup(runStartup);
                            } else {
                                new Startup().writeStringValue("SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Run", "Birthday Reminder",
                                        new Startup().readString("BirthdayReminderPath"));
                            }
                        } catch (Exception ex) {
                        }
                    } else {
                        try {
                            if (System.getProperty("os.name").toLowerCase().equals("linux")) {
                                Process process = Runtime.getRuntime().exec("gksu rm /etc/xdg/autostart/Birthday.desktop");
                                process.waitFor();
                                if (process.exitValue() == 0) {
                                    GlobalVariables.prefs.putBoolean("LOAD_STARTUP", false);
                                } else {
                                    Startup.checkSelection = false;
                                    runStartup.setSelected(true);
                                }
                            } else {
                                new Startup().deleteValue("SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Run", "Birthday Reminder");
                            }
                        } catch (Exception exx) {
                        }
                    }
                } else {
                    Startup.checkSelection = true;
                }
            }
        });
        basicSettings.add(runStartup);

        playAudio = new JCheckBox("Play Music at opening");
        playAudio.setBounds(20, 40, 200, 20);
        playAudio.setFocusable(false);
        playAudio.setSelected(prefs.getBoolean("PLAY_SOUND", false));
        playAudio.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (playAudio.isSelected()) prefs.putBoolean("PLAY_SOUND", true);
                else prefs.putBoolean("PLAY_SOUND", false);
            }
        });
        basicSettings.add(playAudio);


        JLabel audioLabel = new JLabel("Select Music");
        audioLabel.setBounds(20, 80, 80, 30);
        basicSettings.add(audioLabel);

        audioLocation = new JTextField();
        audioLocation.setBounds(120, 80, 160, 30);
        audioLocation.setEditable(false);
        if(prefs.get("AUDIO_PATH", "") == "")
            prefs.put("AUDIO_PATH", "Default");
        setAudioPath();
        basicSettings.add(audioLocation);

        JButton defaultAudio = new JButton("D");
        defaultAudio.setFocusable(false);
        defaultAudio.setBounds(285, 80, 40, 30);
        defaultAudio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prefs.put("AUDIO_PATH", "Default");
                setAudioPath();
            }
        });
        basicSettings.add(defaultAudio);


        JButton selectAudio = new JButton(".");
        selectAudio.setFocusable(false);
        selectAudio.setBounds(330, 80, 30, 30);
        selectAudio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser audioChooser = new JFileChooser();
                audioChooser.setFileFilter(new FileNameExtensionFilter("WAV", "wav"));
                audioChooser.setDialogTitle("Select a audio file");
                audioChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                audioChooser.setMultiSelectionEnabled(false);
                audioChooser.setCurrentDirectory(new File(prefs.get("AUDIO_PATH", "")));
                int choose = audioChooser.showOpenDialog(basicSettings);
                if (JFileChooser.APPROVE_OPTION == choose) {
                    String audioPath = audioChooser.getSelectedFile().getAbsolutePath();
                    prefs.put("AUDIO_PATH", audioPath);
                    setAudioPath();
                }
            }
        });
        basicSettings.add(selectAudio);

        JLabel imageLabel = new JLabel("Image Location");
        imageLabel.setBounds(20, 130, 100, 30);
        basicSettings.add(imageLabel);

        imageLocation = new JTextField();
        imageLocation.setBounds(120, 130, 200, 30);
        imageLocation.setText(prefs.get("IMAGE_PATH", System.getProperty("user.home")));
        imageLocation.setToolTipText(prefs.get("IMAGE_PATH", System.getProperty("user.home")));
        basicSettings.add(imageLocation);


        JButton selectImage = new JButton(".");
        selectImage.setFocusable(false);
        selectImage.setBounds(330, 130, 30, 30);
        selectImage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser imageChooser = new JFileChooser();
                imageChooser.setDialogTitle("Select image Location");
                imageChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                imageChooser.setMultiSelectionEnabled(false);
                imageChooser.setCurrentDirectory(new File(prefs.get("IMAGE_PATH", System.getProperty("user.home"))));
                int choose = imageChooser.showOpenDialog(basicSettings);
                if (JFileChooser.APPROVE_OPTION == choose) {
                    prefs.put("IMAGE_PATH", imageChooser.getSelectedFile().getAbsolutePath());
                    imageLocation.setText(prefs.get("IMAGE_PATH", System.getProperty("user.home")));
                    imageLocation.setToolTipText(prefs.get("IMAGE_PATH", System.getProperty("user.home")));
                }
            }
        });
        basicSettings.add(selectImage);


        JLabel UILabel = new JLabel("Select UI");
        UILabel.setBounds(20, 180, 100, 30);
        basicSettings.add(UILabel);

        String[] userInterface = {"System Default", "Nimbus", "Metal"};
        selectUI = new JComboBox(userInterface);
        selectUI.setBounds(120, 180, 150, 30);
        selectUI.setSelectedItem(prefs.get("USER_INTERFACE", "System Default"));
        selectUI.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                prefs.put("USER_INTERFACE", selectUI.getSelectedItem().toString());
            }
        });
        basicSettings.add(selectUI);

        basicSettings.setVisible(true);
    }

    private void setAudioPath() {
        audioLocation.setText(prefs.get("AUDIO_PATH", ""));
        audioLocation.setToolTipText(prefs.get("AUDIO_PATH", ""));
    }
}
