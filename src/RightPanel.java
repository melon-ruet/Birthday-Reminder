import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by melon on 6/3/14.
 * @email melon.ruet@gmail.com
 */
public class RightPanel extends JPanel implements ActionListener{

    public static JTextField searchBox = new JTextField();;

    public RightPanel(){
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10),
                BorderFactory.createLineBorder(Color.BLUE)));

        JPanel searchPanel = new JPanel(new GridLayout(1,2));
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        JLabel searchLabel = new JLabel("Search");
        searchLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        searchPanel.add(searchLabel);

        //searchBox.setFont(new Font("Siyam Rupali", Font.PLAIN, 16));
        //final searchClass searchName= new searchClass ();
        searchBox.addKeyListener(new Search());
        searchPanel.add(searchBox);
        add(searchPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(1,3));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10,0,10,0));
        JButton deleteButton = new JButton();
        //deleteButton.setFont(new Font("Times new Roman", Font.PLAIN, 16));
        deleteButton.setText("Delete");
        deleteButton.setFocusable(false);
        //final deleteEntryClass deleteEntryHandler= new deleteEntryClass ();
        deleteButton.addActionListener(new Delete());
        buttonPanel.add(deleteButton);


        //view all entry button
        JButton viewAll = new JButton();
        viewAll.setText("View all");
        viewAll.setFocusable(false);
        viewAll.addActionListener(this);
        buttonPanel.add(viewAll);


        add(buttonPanel, BorderLayout.SOUTH);

        JScrollPane pane = new JScrollPane(new BirthdayTable());

        //pane.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        add(pane, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        LeftPanel.month.setSelectedIndex(12);
    }
}
