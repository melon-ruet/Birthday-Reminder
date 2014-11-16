import javax.swing.table.AbstractTableModel;
import java.util.Vector;

public class BirthdayTableModel extends AbstractTableModel {

    private final Vector<String> columnNames;
    public BirthdayTableModel(){
        columnNames = new Vector<String>();
        columnNames.addElement("");
        columnNames.addElement("Name");
        columnNames.addElement("Date of birth");
    }

    @Override
    public int getColumnCount() {
        return columnNames.size();
    }

    @Override
    public int getRowCount() {
        return GlobalVariables.DatabaseImport.size();
    }

    @Override
    public String getColumnName(int col) {
        return columnNames.elementAt(col);
    }

    @Override
    public Object getValueAt(int row, int col) {
        if(col==0) return Boolean.valueOf(GlobalVariables.DatabaseImport.get(row).get(col));
        return GlobalVariables.DatabaseImport.get(row).get(col);
    }

    @Override
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }


    @Override
    public boolean isCellEditable(int row, int col) {
        if(col==0) return true;
        return false;
    }

    @Override
    public void setValueAt(Object value, int row, int col) {
        GlobalVariables.DatabaseImport.get(row).setElementAt(value.toString(), col);
    }
}