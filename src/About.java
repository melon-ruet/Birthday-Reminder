import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by melon on 6/4/14.
 */
public class About implements ActionListener{

    JDialog aboutDialog;

    @Override
    public void actionPerformed(ActionEvent e) {
        StaticTasks.basicSettingsClass basicTop = new StaticTasks.basicSettingsClass();
        aboutDialog = basicTop.createDialog(null);
        Container cont = aboutDialog.getContentPane();
        aboutDialog.setLayout(new BoxLayout(cont, BoxLayout.Y_AXIS));
        aboutDialog.setIconImage((new ImageIcon(getClass().getResource("image/birthdayIcon.png"))).getImage());
        aboutDialog.setTitle("About");
        aboutDialog.setLocation(400, 200);
        //aboutDialog.setResizable(false);

        JLabel imageLabel = new JLabel(new ImageIcon(new ImageIcon(getClass().
                getResource("image/refreshIcon.png")).getImage().getScaledInstance(150, 150, Image.SCALE_AREA_AVERAGING)));
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        aboutDialog.add(imageLabel);

        JLabel aboutLabel = new JLabel("Birthday Reminder is developed by Refresh Desk");
        aboutLabel.setFont(new Font("Serif", Font.PLAIN, 18));
        aboutLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        aboutDialog.add(aboutLabel);

        aboutDialog.add(Box.createRigidArea(new Dimension(0, 10)));

        JLabel aboutLabel2 = new JLabel("Developer");
        aboutLabel2.setForeground(Color.BLUE);
        aboutLabel2.setAlignmentX(Component.CENTER_ALIGNMENT);
        aboutLabel2.setFont(new Font("Serif", Font.PLAIN, 20));
        aboutDialog.add(aboutLabel2);

        JTextPane aboutText = new JTextPane();
        aboutText.setFont(new Font("Serif", Font.PLAIN, 16));
        aboutText.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.GREEN, Color.RED));
        aboutText.setEditable(false);
        aboutText.setAlignmentX(Component.CENTER_ALIGNMENT);
        aboutText.setText("Mahabubur Rahaman Melon\nEmail:  melon.ruet@gmail.com");
        aboutDialog.add(aboutText);

        aboutDialog.add(Box.createRigidArea(new Dimension(0, 10)));

        JLabel aboutLabel3 = new JLabel("Specifications");
        aboutLabel3.setFont(new Font("Serif", Font.PLAIN, 20));
        aboutLabel3.setAlignmentX(Component.CENTER_ALIGNMENT);
        aboutLabel3.setForeground(Color.BLUE);
        aboutDialog.add(aboutLabel3);


        JTextPane aboutText2 = new JTextPane();
        aboutText2.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.GREEN, Color.RED));
        aboutText2.setEditable(false);
        aboutText2.setAlignmentX(Component.CENTER_ALIGNMENT);
        aboutText2.setText("Product version:\t1.0\n"+
                        "Java version:\t1.2 ~ 1.7\n"+
                        "System:\t\tWindows, Linux");

        aboutDialog.add(aboutText2);
        aboutDialog.setResizable(false);
        aboutDialog.pack();
        aboutDialog.setVisible(true);
    }
}
