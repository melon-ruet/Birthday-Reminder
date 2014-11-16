
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import javax.print.attribute.standard.Media;
import javax.swing.*;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *
 * @author melon
 */
public class StaticTasks {


    public void setLookAndFeel(){
        try {
            if(GlobalVariables.prefs.get("USER_INTERFACE", "Nimbus").equals("Nimbus"))
                UIManager.setLookAndFeel(new NimbusLookAndFeel());

            else if(GlobalVariables.prefs.get("USER_INTERFACE", "").equals("System Default"))
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

            else if(GlobalVariables.prefs.get("USER_INTERFACE", "").equals("Metal"))
                UIManager.setLookAndFeel(new MetalLookAndFeel());
        }

        catch(Exception eee){}
    }


    public void QuitDialog() {
        int result = JOptionPane.showConfirmDialog(null, "Do you want to exit?", "Exit",
                JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    public boolean testInet(String site) {
        Socket sock = new Socket();
        InetSocketAddress addr = new InetSocketAddress(site, 80);
        try {
            sock.connect(addr, 3000);
            return true;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "No internet connection. Try again.", "Connection",
                    JOptionPane.ERROR_MESSAGE, null);
            return false;
        } finally {
            try {
                sock.close();
            } catch (IOException e) {
            }
        }
    }

    public int getDaysNo(int monthSelect)
    {
        int no = 31;
        if (monthSelect==1)   no=29;

        if (monthSelect==3 || monthSelect==5 || monthSelect==8 || monthSelect==10)
            no=30;
        return no;
    }

    public static class basicSettingsClass extends JFileChooser {
        @Override
        protected JDialog createDialog(Component parent){
            super.removeAll();
            super.validate();
            super.setPreferredSize(new Dimension(0,0));
            super.setLayout(null);

            return super.createDialog(parent);
        }
    }

    public void PlaySound() {
        try {
            String gongFile;

            if("Default".equals(GlobalVariables.prefs.get("AUDIO_PATH", ""))
                    || "".equals(GlobalVariables.prefs.get("AUDIO_PATH", ""))) {
                gongFile = getClass().getResource("bd.wav").getFile().replace("%20"," ");
            }
            else
                gongFile = new File(GlobalVariables.prefs.get("AUDIO_PATH", "")).toString().replace("%20", " ");

            InputStream in = new FileInputStream(gongFile);
            AudioStream audioStream = new AudioStream(in);
            AudioPlayer.player.start(audioStream);
        } catch (Exception e) {
        }
    }

    public static class waitForNextDay extends Thread {

        JDialog LinuxDialog;

        waitForNextDay(JDialog dialog){
            LinuxDialog = dialog;
        }

        @Override
        public void run() {
            try
            {
                Calendar now = GregorianCalendar.getInstance();
                sleep((24 * 60 * 60 * 1000 + 10000) - (now.get(Calendar.HOUR_OF_DAY) * 60 * 60 * 1000
                        + now.get(Calendar.MINUTE) * 60 * 1000 + now.get(Calendar.SECOND) * 1000));

                if(SystemTray.isSupported())
                    BirthdayTray.showTray();
                else{
                    LinuxDialog.dispose();
                    new LinuxShowBirthday();
                }
            } catch (Exception exx) { }
        }
    }

}
