import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.io.File;
import java.util.Arrays;
import java.util.Vector;

class RowRenderer extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table,
                                                   Object value,
                                                   boolean isSelected,
                                                   boolean hasFocus,
                                                   int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        if (row % 2 == 0) {
            setBackground(Color.LIGHT_GRAY);
        } else {
            setBackground(new Color(205, 247, 235));
        }

        table.getTableHeader().setDraggedColumn(null);
        String [] splitDate = GlobalVariables.DatabaseImport.get(row).elementAt(2).split(" ");

        String result = new BirthdayDatabase().getDataBySearch(
                GlobalVariables.DatabaseImport.get(row).elementAt(1), Integer.parseInt(splitDate[0])-1,
                Arrays.asList(new GlobalVariables().strmon).indexOf(splitDate[1]));

        if(new File(result).exists())
            setToolTipText("<html><img width=\"200\" height=\"200\"src=\"file://" +
                    result + "\">");
        else
            setToolTipText(null);
        return this;
    }
}