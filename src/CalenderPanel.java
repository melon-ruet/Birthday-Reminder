import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by melon on 6/1/14.
 */
public class CalenderPanel extends JPanel{

    private JButton calendarButton[];

        CalenderPanel(int selectedIndex){
            setLayout(new GridLayout(7,7,1,1));
            setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            JLabel sun = new JLabel("Sun", JLabel.CENTER);

            sun.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.red, Color.BLACK));
            //sun.setForeground(Color.RED);
            JLabel mon = new JLabel("Mon", JLabel.CENTER);
            mon.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.red, Color.BLACK));
            JLabel tue = new JLabel("Tue", JLabel.CENTER);
            tue.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.red, Color.BLACK));
            JLabel wed = new JLabel("Wed", JLabel.CENTER);
            wed.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.red, Color.BLACK));
            JLabel thu = new JLabel("Thu", JLabel.CENTER);
            thu.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.red, Color.BLACK));
            JLabel fri = new JLabel("Fri", JLabel.CENTER);
            fri.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.red, Color.BLACK));
            JLabel sat = new JLabel("Sat", JLabel.CENTER);
            sat.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.red, Color.BLACK));

            add(sun);
            add(mon);
            add(tue);
            add(wed);
            add(thu);
            add(fri);
            add(sat);

            JPanel p[]= new JPanel[42];
            for (int x=0; x<42; x++)
            {
                p[x]= new JPanel();
                p[x].setLayout(new GridLayout(1,1));
                //p[x].setBackground(Color.WHITE);
                add(p[x]);
                validate();
            }

            boolean ListOfResult[] = new BirthdayDatabase().getBirthdayBymonth(selectedIndex, "");
            Calendar now = GregorianCalendar.getInstance();
            Calendar cal = new GregorianCalendar(now.get(Calendar.YEAR), selectedIndex, 1);
            int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
            calendarButton = new JButton[31];
            for (int i=0; i<new StaticTasks().getDaysNo(selectedIndex); i++)
            {
                calendarButton[i]= new JButton();
                //calendarButton[i].setFont(new Font("Times New Roman", Font.PLAIN, 12));
                calendarButton[i].setFocusable(false);
                calendarButton[i].setText(Integer.toString(i+1));

                if(ListOfResult[i]) calendarButton[i].setBackground(Color.CYAN);
                p[dayOfWeek-1].add(calendarButton[i]);
                dayOfWeek++;
            }

            if(GregorianCalendar.getInstance().get(Calendar.MONTH) == selectedIndex){
                calendarButton[now.get(Calendar.DATE)-1].setForeground(Color.RED);
                calendarButton[now.get(Calendar.DATE)-1].setToolTipText("Today");
            }
        }

    }

