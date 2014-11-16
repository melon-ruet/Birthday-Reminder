import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;

/**
 * Created by melon on 6/11/14.
 */
public class ShowTodaysBirthday {

    public static String birthdayDisplay;

    public JScrollPane getTodaysBirthday() {
        JPanel detailedPanel = new JPanel();
        detailedPanel.setLayout(new BoxLayout(detailedPanel, BoxLayout.Y_AXIS));
        JScrollPane detailedScroll = new JScrollPane();
        detailedScroll.setViewportView(detailedPanel);

        birthdayDisplay = "";
        try {
            ResultSet result = new BirthdayDatabase().getTodaysBirthday();
            while (result.next()) {
                birthdayDisplay += result.getString("name") + "\n";
                JLabel detailedLabel = new JLabel(result.getString("name"));
                detailedLabel.setIcon(new ImageIcon((new ImageIcon(result.getString("photo_link"))).getImage().
                        getScaledInstance(50, 50, Image.SCALE_AREA_AVERAGING)));
                detailedLabel.setFont(new Font("Times New Roman", Font.PLAIN, 30));
                detailedPanel.add(detailedLabel);
                detailedPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            }


        } catch (Exception e) {
        }
        return detailedScroll;
    }
}
