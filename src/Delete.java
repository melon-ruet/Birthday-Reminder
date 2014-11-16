import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Delete implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        String[] splittedString;
        boolean updateTray = false;
        boolean confirmDelete = true;
        for (int checkingDelete = 0; checkingDelete < GlobalVariables.DatabaseImport.size(); checkingDelete++)
            if (GlobalVariables.DatabaseImport.get(checkingDelete).elementAt(0).equals(String.valueOf(true))) {
                if (confirmDelete) {
                    if (JOptionPane.showConfirmDialog(null, "Are you sure to delete?", "Confirm Delete",
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.NO_OPTION)
                        return;
                    confirmDelete = false;
                }
                splittedString = GlobalVariables.DatabaseImport.get(checkingDelete).elementAt(2).split(" ");

                new BirthdayDatabase().deleteEntry(GlobalVariables.DatabaseImport.get(checkingDelete).elementAt(1),
                        Integer.parseInt(splittedString[0]) - 1,
                        Arrays.asList(new GlobalVariables().strmon).indexOf(splittedString[1]));

                //if(Integer.parseInt(splittedString[0]) == GregorianCalendar.getInstance().get(Calendar.DATE) &&
                //Arrays.asList(new GlobalVariables().strmon).indexOf(splittedString[1]) =
                //GregorianCalendar.getInstance().get(Calendar.MONTH))
                //updateTray = true;
            }

        //if(updateTray)  Birthday_tray.showTray();
        int index = LeftPanel.month.getSelectedIndex();
        if(index > 0)
            LeftPanel.month.setSelectedIndex(index-1);
        else
            LeftPanel.month.setSelectedIndex(index+1);

        LeftPanel.month.setSelectedIndex(index);
    }
}