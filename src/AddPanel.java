import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by melon on 6/1/14.
 */
public class AddPanel extends JPanel implements ActionListener{
    private JTextField personName;
    private JButton InsertButton;
    private JButton selectPicture;
    private JTextField imageLocation;
    private JComboBox add_month;
    private JComboBox add_day;
    private String prevName;
    private int prevDay;
    private int prevMonth;
    private JDialog updateFrame;


    AddPanel(JDialog frame, String buttonName, int day, int month, String imagePath, String name){

        updateFrame = frame;
        prevName = name;
        prevDay = day;
        prevMonth = month;

        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        JLabel dateLabel = new JLabel("Select date");
        dateLabel.setFont(new Font("Times new Roman", Font.PLAIN, 16));
        c.weightx = 1d;
        c.weighty = 1d;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        add(dateLabel, c);

        add_day = new JComboBox(new GlobalVariables().setDaysList(0));
        c.gridx = 1;
        c.gridy = 0;
        add_day.setSelectedIndex(day);
        add(add_day, c);

        add_month = new JComboBox(new GlobalVariables().strmon);
        add_month.addActionListener(this);
        c.gridx = 2;
        c.gridy = 0;
        add_month.setSelectedIndex(month);
        add(add_month, c);

        JLabel dayLabel = new JLabel("day");
        dayLabel.setForeground(Color.MAGENTA);
        c.gridx = 1;
        c.gridy = 1;
        add(dayLabel, c);

        JLabel monthLabel = new JLabel("month");
        monthLabel.setForeground(Color.MAGENTA);
        c.gridx = 2;
        c.gridy = 1;
        add(monthLabel,c);

        JLabel pictureLabel = new JLabel("Select picture");
        c.gridx = 0;
        c.gridy = 3;
        add(pictureLabel, c);

        imageLocation = new JTextField();
        c.gridx = 1;
        c.gridy = 3;
        imageLocation.setText(imagePath);
        add(imageLocation, c);

        selectPicture = new JButton(".");
        selectPicture.setFont(new Font("Times new Roman", Font.PLAIN, 16));
        selectPicture.setFocusable(false);
        selectPicture.addActionListener(this);

        c.gridx = 2;
        c.gridy = 3;
        add(selectPicture,c);

        JLabel nameLabel = new JLabel("Name");
        nameLabel.setFont(new Font("Times new Roman", Font.PLAIN, 16));
        c.gridx = 0;
        c.gridy = 4;
        add(nameLabel, c);

        personName = new JTextField();
        c.gridx = 1;
        c.gridy = 4;
        personName.setText(name);
        add(personName, c);

        InsertButton = new JButton(buttonName);
        InsertButton.setFocusable(false);
        InsertButton.addActionListener(this);
        c.gridx = 1;
        c.gridy = 5;
        add(InsertButton,c);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == selectPicture){
            JFileChooser photoChooser = new JFileChooser();
            photoChooser.setFileFilter(new FileNameExtensionFilter("Images","jpeg", "jpg", "JPG", "JPEG", "gif", "png"));
            photoChooser.setDialogTitle("Select an image file");
            photoChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            photoChooser.setMultiSelectionEnabled(false);
            photoChooser.setCurrentDirectory(new File(GlobalVariables.prefs.get("PHOTO_PATH", "")));
            int choose = photoChooser.showOpenDialog(null);
            if(JFileChooser.APPROVE_OPTION == choose) {
                imageLocation.setText(photoChooser.getSelectedFile().getAbsolutePath());
            }
        }

        else if(e.getSource() == add_month){
            int monthSelect = add_month.getSelectedIndex();
            int daySelect = add_day.getSelectedIndex();
            if(monthSelect==3 || monthSelect==5 || monthSelect==8 || monthSelect==10){
                add_day.setModel(new DefaultComboBoxModel(new GlobalVariables().setDaysList(1)));
            }
            else if(monthSelect == 1){
                add_day.setModel(new DefaultComboBoxModel(new GlobalVariables().setDaysList(2)));
            }
            else
                add_day.setModel(new DefaultComboBoxModel(new GlobalVariables().setDaysList(0)));

            if(new StaticTasks().getDaysNo(monthSelect) -1 >= daySelect)
                add_day.setSelectedIndex(daySelect);
        }

        else if(e.getSource() == InsertButton){
            if(InsertButton.getText() == "Insert"){
                if(new BirthdayDatabase().InsertData(personName.getText(), add_day.getSelectedIndex(),
                        add_month.getSelectedIndex(), imageLocation.getText(), true, prevName, prevDay, prevMonth)){
                    LeftPanel.month.setSelectedIndex(add_month.getSelectedIndex());
                    personName.setText("");
                    add_day.setSelectedIndex(0);
                    add_month.setSelectedIndex(0);
                    imageLocation.setText("");
                }
            }
            else {
                if(new BirthdayDatabase().InsertData(personName.getText(), add_day.getSelectedIndex(),
                        add_month.getSelectedIndex(), imageLocation.getText(), false, prevName, prevDay, prevMonth)){

                    if((add_day.getSelectedIndex()+1) == GregorianCalendar.getInstance().get(Calendar.DATE) &&
                            add_month.getSelectedIndex() == GregorianCalendar.getInstance().get(Calendar.MONTH))
                        //Birthday_tray.showTray();
                    ;
                    updateFrame.dispose();
                    int index = LeftPanel.month.getSelectedIndex();
                    if(index==0)
                        LeftPanel.month.setSelectedIndex(index+1);
                    else
                        LeftPanel.month.setSelectedIndex(index-1);

                    LeftPanel.month.setSelectedIndex(index);
                }
            }
        }
    }}

