import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;

public class RowSelect extends MouseAdapter {
    private String photoPath = "";
    private String [] splitDate = null;
    private JDialog updateFrame;


    @Override
    public void mouseClicked(MouseEvent e) {

        final JTable table = (JTable) e.getSource();

        if(e.getClickCount() == 2)
        {
            try{
                splitDate = GlobalVariables.DatabaseImport.get(table.getSelectedRow()).elementAt(2).split(" ");
            }
            catch(Exception eee){
                return;
            }

            StaticTasks.basicSettingsClass keepOnTop = new StaticTasks.basicSettingsClass();
            updateFrame = keepOnTop.createDialog(null);
            updateFrame.setIconImage((new ImageIcon(getClass().getResource("image/birthdayIcon.png"),
                    "tray icon")).getImage());
            updateFrame.setLayout(new BorderLayout());
            updateFrame.setTitle("Update info");
            //updateFrame.setResizable(false);
            updateFrame.setLocationRelativeTo(null);

            int birthMonth = Arrays.asList(new GlobalVariables().strmon).indexOf(splitDate[1].replaceFirst(",", ""));
            int birthday = Integer.parseInt(splitDate[0])-1;
            photoPath = new BirthdayDatabase().getDataBySearch(
                    GlobalVariables.DatabaseImport.get(table.getSelectedRow()).elementAt(1),
                    birthday, birthMonth);

            updateFrame.add(new AddPanel(updateFrame, "Update",birthday, birthMonth, photoPath,
                    GlobalVariables.DatabaseImport.get(table.getSelectedRow()).elementAt(1)), BorderLayout.CENTER);


            JButton cancelUpdate = new JButton("Cancel");
            cancelUpdate.setFont(new Font("Times new Roman", Font.PLAIN, 16));
            cancelUpdate.setFocusable(false);
            cancelUpdate.setSize(100, 30);
            cancelUpdate.setLocation(240, 140);
            cancelUpdate.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    updateFrame.dispose();
                }
            });
            //updateFrame.add(cancelUpdate);

            updateFrame.setVisible(true);
        }
    }


}