import javax.swing.*;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class CheckBoxHeader extends JCheckBox implements TableCellRenderer, MouseListener {
    protected CheckBoxHeader rendererComponent;
    protected int column;
    protected boolean mousePressed = false;

    public CheckBoxHeader(final JTable BirthdayTable) {
        rendererComponent = this;
        rendererComponent.setHorizontalAlignment(CENTER);
        rendererComponent.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent ee) {
                if (ee.getSource() instanceof AbstractButton == false) return;
                boolean checked = ee.getStateChange() == ItemEvent.SELECTED;
                for(int x = 0; x < BirthdayTable.getRowCount(); x++)
                    BirthdayTable.setValueAt(checked,x,0);
            }
        });
    }


    @Override
    public Component getTableCellRendererComponent(
            JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        JTableHeader header = table.getTableHeader();
        if (table != null && header != null) {
            //rendererComponent.setForeground(header.getForeground());
            //rendererComponent.setBackground(header.getBackground());
            //rendererComponent.setFont(header.getFont());
            header.addMouseListener(rendererComponent);
        }
        setColumn(column);
        //if(table.getRowCount()==0) {rendererComponent.setSelected(false); }
        return rendererComponent;//header.repaint();
    }


    protected void setColumn(int column) {
        this.column = column;
    }

    public int getColumn() {
        return column;
    }


    protected void handleClickEvent(MouseEvent e) {
        if (mousePressed) {
            mousePressed=false;
            JTableHeader header = (JTableHeader)(e.getSource());
            JTable tableView = header.getTable();
            TableColumnModel columnModel = tableView.getColumnModel();
            int viewColumn = columnModel.getColumnIndexAtX(e.getX());
            int CloumN = tableView.convertColumnIndexToModel(viewColumn);

            if (viewColumn == this.column && e.getClickCount() == 1 && CloumN != -1)
                doClick();

        }
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        handleClickEvent(e);
        ((JTableHeader)e.getSource()).repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mousePressed = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
}