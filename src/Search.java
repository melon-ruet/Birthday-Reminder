import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.AbstractList;

public class Search extends KeyAdapter {

    @Override
    public void keyReleased(KeyEvent e) {
        JTextField source = (JTextField) e.getSource();
        GlobalVariables.DatabaseImport.removeAllElements();
        new BirthdayDatabase().getBirthdayBymonth(LeftPanel.month.getSelectedIndex(), source.getText().toLowerCase());
        BirthdayTable.model.fireTableDataChanged();
    }
}