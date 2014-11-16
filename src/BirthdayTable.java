import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.Vector;

/**
 * Created by melon on 6/3/14.
 */
public class BirthdayTable extends JTable{

    public static BirthdayTableModel model;

    public BirthdayTable(){
        model = new BirthdayTableModel();
        setModel(model);
        setFillsViewportHeight(true);
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        setFocusable(false);
        setRowHeight(30);
        getTableHeader().setFont(new Font("Times New Roman", Font.PLAIN, 20));
        setShowGrid(true);
        //BirthdayTable.setFont(new Font("SiyamRupali", Font.PLAIN, 16));
        //sorter = new TableRowSorter<BirthdayTableModel>(model);
        //BirthdayTable.setRowSorter(sorter);
        TableColumn zeroColumn = getColumnModel().getColumn(0);
        //zeroColumn.setCellEditor(BirthdayTable.getDefaultEditor(Boolean.class));
        //zeroColumn.setCellRenderer(BirthdayTable.getDefaultRenderer(Boolean.class));
        zeroColumn.setHeaderRenderer((new CheckBoxHeader(this)));
        zeroColumn.setPreferredWidth(50);
        zeroColumn.setResizable(false);


        getColumnModel().getColumn(1).setPreferredWidth(180);
        getColumnModel().getColumn(2).setPreferredWidth(150);


        addMouseListener(new RowSelect());
        TableColumnModel columnModel = getColumnModel();
        for(int j = 1; j < columnModel.getColumnCount(); j++)
            columnModel.getColumn(j).setCellRenderer(new RowRenderer());
    }
}
