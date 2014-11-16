import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by melon on 6/1/14.
 */

public class LeftPanel extends JPanel implements ItemListener{
    JPanel monthViewPanel;
    JPanel calenderPanel;
    public static JComboBox month;

    public LeftPanel(){
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(10,10,10,10),
                BorderFactory.createLineBorder(Color.BLUE)));
        add(new AddPanel(null, "Insert",0,0,"",""), BorderLayout.NORTH);

        JLabel monthViewLabel = new JLabel("View by month");
        String monthIndex[] = {"January","February","March","April","May","June",
                "July","August","September","October","November","December","View All"};
        month = new JComboBox(monthIndex);
        month.addItemListener(this);


        monthViewPanel = new JPanel(new BorderLayout(10,0));
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10,10));
        panel.add(monthViewLabel);
        panel.add(month);
        monthViewPanel.add(panel, BorderLayout.NORTH);
        month.setSelectedIndex(GregorianCalendar.getInstance().get(Calendar.MONTH));
        add(monthViewPanel, BorderLayout.CENTER);
    }


    @Override
    public void itemStateChanged(ItemEvent e) {
        if(calenderPanel!=null){
            monthViewPanel.remove(calenderPanel);
            monthViewPanel.repaint();
        }
        JComboBox box = (JComboBox) e.getSource();
        calenderPanel = new CalenderPanel(box.getSelectedIndex());
        monthViewPanel.add(calenderPanel, BorderLayout.CENTER);
        monthViewPanel.revalidate();

        RightPanel.searchBox.setText("");

        if(BirthdayTable.model != null)
            BirthdayTable.model.fireTableDataChanged();
    }
}
